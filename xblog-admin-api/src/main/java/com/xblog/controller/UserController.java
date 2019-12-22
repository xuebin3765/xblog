package com.xblog.controller;

import com.xblog.commons.controller.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 用户controller
 * @author xuebin3765@163.com
 * @Date 2018/9/9 0009 23:20
 */
@ResponseBody
@RequestMapping("/admin/user")
public class UserController extends BaseController {

//    @Resource
//    private UsersService usersService;
//    /**
//     * 1、注册
//     * 2、登陆
//     * 3、修改信息
//     * 4、退出登录
//     * 5、查看个人详细信息
//     */
//    @RequestMapping
//    public ResponseBean register(@RequestBody @Valid RegisterUserDTO userDTO){
//        logger.info(" into UserController , register ; userDTO : {} " , userDTO);
//        try {
//            User users = usersService.register(userDTO);
//            return successReq("注册成功" , users);
//        }catch (OperateException e){
//            return failureReq("注册失败" , e.getMessage());
//        }
//    }

}
