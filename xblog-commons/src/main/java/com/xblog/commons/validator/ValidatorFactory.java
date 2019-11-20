package com.xblog.commons.validator;

import com.commons.validator.annotation.*;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;

/**
 * desc: 校验框架静态类
 * author: xuebin3765@163.com
 * date: 2019/09/28
 */
public class ValidatorFactory {

    /**
     * 验证bean的所有字段
     *
     * @param t      bean
     * @param groups 校验组
     * @param <T>    bean class
     * @return ValidResult
     */
    public static <T> ValidResult validBean(T t, Class<?>... groups) {
        ValidResult validResult = new ValidResult();
        if (null == t) {
            validResult.addError("requestBean", "null");
            return validResult;
        }
        try {
            List<FiledValue> filedValues = getAllHasAnnotationFiledValues(t);
            // 有相关注解，需要进行字段验证
            Optional.ofNullable(filedValues).orElse(Lists.newArrayList())
                    .stream()
                    .forEach(filedValue -> {
                        Field field = filedValue.getField();
                        Object object = filedValue.getObject();
                        String message = validateFiled(field, object, t);
                        // 描述信息不为空，参数验证失败
                        if (StringUtils.isNotBlank(message)) {
                            validResult.addError(field.getName(), message);
                        }
                    });
        } catch (IntrospectionException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return validResult;
    }

    /**
     * 验证字段上的左右注解
     *
     * @param field      字段
     * @param fieldValue 字段值
     * @return 返回藐视信息，空说明验证通过
     */
    private static <T> String validateFiled(Field field, Object fieldValue, T t) {

        String fieldName = field.getName();

        // 验证notNull
        if (field.isAnnotationPresent(NotNull.class)) {
            NotNull notNull = field.getAnnotation(NotNull.class);
            String message = validate(fieldName, fieldValue, notNull);
            if (StringUtils.isNotBlank(message)) {
                return message;
            }
        }
        // 验证最小值
        if (field.isAnnotationPresent(Min.class)) {
            Min min = field.getAnnotation(Min.class);
            String message = validate(fieldName, fieldValue, min);
            if (StringUtils.isNotBlank(message)) {
                return message;
            }
        }

        // 验证最大值
        if (field.isAnnotationPresent(Max.class)) {
            Max max = field.getAnnotation(Max.class);
            String message = validate(fieldName, fieldValue, max);
            if (StringUtils.isNotBlank(message)) {
                return message;
            }
        }

        // 验证两个字段的值是否相等
        if (field.isAnnotationPresent(Equals.class)) {
            Equals equals = field.getAnnotation(Equals.class);
            String message = validate(fieldName, fieldValue, equals, t);
            if (StringUtils.isNotBlank(message)) {
                return message;
            }
        }

        // 验证字符串长度
        if (field.isAnnotationPresent(Length.class)) {
            Length length = field.getAnnotation(Length.class);
            String message = validate(fieldName, fieldValue, length);
            if (StringUtils.isNotBlank(message)) {
                return message;
            }
        }

        // 验证字符串长度
        if (field.isAnnotationPresent(Email.class)) {
            Email email = field.getAnnotation(Email.class);
            String message = validate(fieldName, fieldValue, email);
            if (StringUtils.isNotBlank(message)) {
                return message;
            }
        }

        return null;
    }

    /**
     * 比较两个字段是否相等
     *
     * @param filedName  源字段
     * @param fieldValue 目标字段
     * @param equals     注解
     * @param t          bean
     * @param <T>        泛型
     * @return 比较结果信息
     */
    private static <T> String validate(String filedName, Object fieldValue, Equals equals, T t) {
        String message = StringUtils.isNotBlank(equals.message()) ? equals.message() : filedName + "不能为空";
        if (null == fieldValue)
            return message;

        String fieldValueStr = String.valueOf(fieldValue);

        if (StringUtils.isBlank(fieldValueStr))
            return message;

        String filedNameTar = equals.value();

        Class clazz = t.getClass();
        try {
            PropertyDescriptor descriptor = new PropertyDescriptor(filedNameTar, clazz);
            Method method = descriptor.getReadMethod();
            Object object = method.invoke(t);
            String filedValue = String.valueOf(object);
            // 比较两个字段的值是否相等
            if (!fieldValueStr.equals(filedValue)) {
                message = filedName + "与" + filedNameTar + "不相等";
            } else {
                message = null;
            }
        } catch (Exception e) {
            message = filedName + "字段不存在";
        }
        return message;
    }

    /**
     * 对某个值进行某类注解的验证
     *
     * @param object  参数值
     * @param notNull 验证注解
     * @return
     */
    private static String validate(String filedName, Object object, NotNull notNull) {
        String message = StringUtils.isNotBlank(notNull.value()) ? notNull.value() : filedName + "不能为空";
        if (object != null) message = "";
        return message;

    }

    /**
     * 对某个值进行某类注解的验证
     *
     * @param object 参数值
     * @param min    最小值验证
     * @return 错误信息
     */
    private static String validate(String filedName, Object object, Min min) {
        int value = min.value();
        String message = StringUtils.isNotBlank(min.message()) ? min.message() : filedName + "大小不能小于" + value;
        String obj = String.valueOf(object);
        if (StringUtils.isNumeric(obj)) {
            int objInt = Integer.parseInt(obj);
            // 当前值大于最小值
            if (objInt >= value) {
                message = null;
            }
        } else {
            message = filedName + "不是数字";
        }
        return message;

    }

    /**
     * 对某个值进行某类注解的验证
     *
     * @param object 参数值
     * @param max    最大值验证
     * @return 错误信息
     */
    private static String validate(String filedName, Object object, Max max) {
        int value = max.value();
        String message = StringUtils.isNotBlank(max.message()) ? max.message() : filedName + "大小不能超过" + value;
        String obj = String.valueOf(object);
        if (StringUtils.isNumeric(obj)) {
            // 当前参数的值
            int objInt = Integer.parseInt(obj);
            // 当前值大于最小值
            if (objInt <= value) {
                message = null;
            }
        }
        return message;
    }

    /**
     * 对某个值进行某类注解的验证
     *
     * @param object 参数值
     * @param length 长度注解
     * @return 错误信息
     */
    private static String validate(String filedName, Object object, Length length) {
        int min = length.min();
        int max = length.max();
        String message = filedName + "长度在" + min + "和" + max + "之间";
        if (StringUtils.isNotBlank(length.message())) {
            message = length.message();
        }
        boolean hasError = false;
        // 长度不做限制
        if (min == 0 && max == 0) {
            return message;
        } else {
            String obj = String.valueOf(object);
            int size = obj != null ? obj.length() : 0;
            // 大小都有限制
            if (min > 0 && max > 0) {
                // 比大的大，比小的小
                if (min > size || max < size) {
                    hasError = true;
                    message = "长度大小应在 " + min + " 和 " + max + "之间";
                }
            } else {
                if (max > 0 && max < size) {
                    hasError = true;
                    message = "长度不能超过" + max;
                }

                if (min > 0 && min > size) {
                    hasError = true;
                    message = "长度不能小于" + min;
                }
            }
        }
        if (!hasError) {
            message = null;
        }
        return message;
    }

    /**
     * 对某个值进行某类注解的验证
     *
     * @param object 参数值
     * @param email  长度注解
     * @return 错误信息
     */
    private static String validate(String filedName, Object object, Email email) {
        String emailStr = String.valueOf(object);
        boolean isEmail = emailStr.matches("^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$");
        return isEmail ? null : StringUtils.isNotBlank(email.value()) ? email.value() : "邮箱地址不合法";
    }

    /**
     * 获取所有有注解的字段的值
     *
     * @param t bean
     * @return list
     */
    private static <T> List<FiledValue> getAllHasAnnotationFiledValues(T t) throws IntrospectionException, InvocationTargetException, IllegalAccessException {
        List<FiledValue> result = Lists.newArrayList();
        Class clazz = t.getClass();
        Field[] fields = FieldUtils.getAllFields(clazz);
        if (fields != null) {
            for (Field field : fields) {
                Annotation[] annotations = field.getAnnotations();
                if (null != annotations && annotations.length > 0) {
                    PropertyDescriptor descriptor = new PropertyDescriptor(field.getName(), clazz);
                    Method method = descriptor.getReadMethod();
                    result.add(new ValidatorFactory().new FiledValue(field, method.invoke(t)));
                }
            }
        }
        return result;
    }

    /**
     * 验证bean的某一个字段
     *
     * @param t            bean
     * @param propertyName 字段
     * @return ValidResult
     */
    public static <T> ValidResult validBeanProperty(T t, String propertyName) {
        ValidResult result = new ValidResult();
        return result;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class FiledValue {
        private Field field;
        private Object object;
    }


}
