package com.xblog.commons.utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * Description: RSA加解密算法
 * Created by Administrator
 * Date 2019/12/6 20:51
 */
public class RSAUtil {

    /**
     * 对打加密明文长度
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;

    /**
     * 最大解密密文长度
     */
    private static final int MAX_DECRYPT_BLOCK = 128;

    /**
     * 生成密钥对
     *
     * @return 密钥对对象
     * @throws NoSuchAlgorithmException 异常
     */
    public static KeyPair getKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(1024);
        return generator.generateKeyPair();
    }

    /**
     * 获取私钥
     *
     * @param privateKey 私钥字符串
     * @return 私钥对象
     * @throws NoSuchAlgorithmException 异常信息
     */
    public static PrivateKey getPrivateKey(String privateKey) throws Exception {
        KeyFactory factory = KeyFactory.getInstance("RSA");
        byte[] bytes = Base64.decodeBase64(privateKey.getBytes());
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(bytes);
        return factory.generatePrivate(keySpec);
    }

    /**
     * 获取公钥
     *
     * @param publicKey 公钥字符串
     * @return 公钥对象
     * @throws NoSuchAlgorithmException 异常
     */
    public static PublicKey getPublicKey(String publicKey) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        byte[] decodedKey = Base64.decodeBase64(publicKey.getBytes());
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decodedKey);
        return keyFactory.generatePublic(keySpec);
    }

    /**
     * 加密
     * @param str 要加密的字符串
     * @param publicKey 公钥
     * @return 密文
     * @throws Exception 异常
     */
    public static String encrypt(String str, PublicKey publicKey) throws Exception {
        if (StringUtils.isBlank(str) || publicKey == null) {
            throw new RuntimeException("加密相关数据异常，加密失败！");
        }
        byte[] bytes = encryptOrDecrypt(str, publicKey, Cipher.ENCRYPT_MODE);
        // 加密后的字符串，加密内容用Base64编码，以UTF-8编码输出
        return Base64.encodeBase64String(bytes);
    }

    public static String decrypt(String str, PrivateKey privateKey) throws Exception {
        byte[] byteStr = encryptOrDecrypt(str, privateKey, Cipher.DECRYPT_MODE);
        // 解密后的内容
        return new String(byteStr, StandardCharsets.UTF_8);
    }

    /**
     * 加密或解密公共方法
     * @param str 需要加解密的字符串
     * @param key 公钥或私钥
     * @param type 1：加密，2：解密
     * @return jia
     */
    private static byte[] encryptOrDecrypt(String str, Key key, int type) throws Exception {
        if (StringUtils.isBlank(str) || key == null || type > 2 || type < 1){
            throw new RuntimeException("参数非法，加密或解密失败！");
        }
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(type, key);
        byte[] bytes = type == Cipher.DECRYPT_MODE? Base64.decodeBase64(str.getBytes()) : str.getBytes();
        int inputLen = bytes.length;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        int offset = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        int maxLength = type == Cipher.DECRYPT_MODE? MAX_DECRYPT_BLOCK : MAX_ENCRYPT_BLOCK;
        while (inputLen - offset > 0){
            if (inputLen - offset > maxLength){
                cache = cipher.doFinal(bytes, offset, maxLength);
            }else {
                cache = cipher.doFinal(bytes, offset, inputLen - offset);
            }
            outputStream.write(cache, 0, cache.length);
            i++;
            offset = i * maxLength;
        }
        byte[] byteStr = outputStream.toByteArray();
        outputStream.close();
        return byteStr;
    }

    /**
     * 数据签名
     * @param str 需要签名的数据
     * @param privateKey 私钥
     * @return 签名串
     */
    public static String sign(String str, PrivateKey privateKey) throws Exception {
        byte[] bytes = privateKey.getEncoded();
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(bytes);
        KeyFactory factory = KeyFactory.getInstance("RSA");
        PrivateKey key = factory.generatePrivate(keySpec);
        Signature signature = Signature.getInstance("MD5withRSA");
        signature.initSign(key);
        signature.update(str.getBytes());
        return Base64.encodeBase64String(signature.sign());
    }

    /**
     * 验证签名
     * @param str 字符串
     * @param publicKey 公钥
     * @param sign 签名
     * @return 签名是否正确
     */
    public static boolean verify(String str, PublicKey publicKey, String sign) throws Exception {
        byte[] bytes = publicKey.getEncoded();
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(bytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey key = keyFactory.generatePublic(keySpec);
        Signature signature = Signature.getInstance("MD5withRSA");
        signature.initVerify(key);
        signature.update(str.getBytes());
        return signature.verify(Base64.decodeBase64(sign.getBytes()));
    }

    public static void main(String[] args) {
        try {
            try {
                // 生成密钥对
                KeyPair keyPair = getKeyPair();
                String privateKey = new String(Base64.encodeBase64(keyPair.getPrivate().getEncoded()));
                String publicKey = new String(Base64.encodeBase64(keyPair.getPublic().getEncoded()));
                System.out.println("私钥:" + privateKey);
                System.out.println("公钥:" + publicKey);
                // RSA加密
                String secretId="AKIDOefRyI4ExVoY2LhOHanna6MPFpCLerEN";
                String secretKey="DBsKV4OkrlRvBBwQfeOu98wVpsfDbjcG";
                String regionName="ap-chengdu";

                String secretIdData = encrypt(secretId, getPublicKey(publicKey));
                String secretKeyData = encrypt(secretKey, getPublicKey(publicKey));
                String regionNameData = encrypt(regionName, getPublicKey(publicKey));
                System.out.println("加密后secretId:" + secretIdData);
                System.out.println("加密后secretKey:" + secretKeyData);
                System.out.println("加密后regionName:" + regionNameData);

            } catch (Exception e) {
                e.printStackTrace();
                System.out.print("加解密异常");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
