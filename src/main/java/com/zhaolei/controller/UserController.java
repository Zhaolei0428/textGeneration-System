package com.zhaolei.controller;

import com.zhaolei.config.RegExConfig;
import com.zhaolei.dao.UserDao;
import com.zhaolei.model.User;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.alibaba.fastjson.JSONObject;

import javax.validation.constraints.Null;
import java.io.Console;
import java.util.List;

/**
 * Created by zhaolei on 2017/4/13.
 */
@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserDao userDao;

    @GetMapping(path = "/login")
    public String toLogin(){
        return "login";
    }

    @PostMapping(path = "/login")
    @ResponseBody
    public JSONObject login(@RequestParam("userName")@NotEmpty final String userName,
                            @RequestParam("userPassword") @NotEmpty final String userPassword ){

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("url", "/index");
        if(userName.isEmpty())
        {
            jsonObject.put("status","error");
            jsonObject.put("errorString","please input userName!");
            return jsonObject;
        }
        else if(userPassword.isEmpty())
        {
            jsonObject.put("status","error");
            jsonObject.put("errorString","please input password!");
            return jsonObject;
        }
        else{
            User user = userDao.getUserByAccount(userName);
            if(user == null){
                jsonObject.put("status","error");
                jsonObject.put("errorString","Account not exist!");
                return jsonObject;
            }
            else if(!user.getPassword().equals(userPassword))
            {
                jsonObject.put("status","error");
                jsonObject.put("errorString","Password error!");
                return jsonObject;
            }
            else{
                jsonObject.put("status","ok");
                jsonObject.put("errorString","");
                return jsonObject;
            }
        }
    }

    @GetMapping(path = "/register")
    public String toRegister(){
        return "register";
    }


    @PostMapping(path = "/register")
    @ResponseBody
    public JSONObject register(@RequestParam("userName") @NotEmpty final String userName,
                              @RequestParam("userPassword") @NotEmpty final String userPassword,
                              @RequestParam("userRepeatPassword") @NotEmpty final String userRepeatPassword){

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("url", "/users/login");

        if (!UserUtil.checkPasswordIsMatchRegAndEqual(userPassword, userRepeatPassword)){
            jsonObject.put("status", "error");
            jsonObject.put("errorString", "password error");
            return jsonObject;
        }

        if (! UserUtil.checkUserNameIsNotExist(userDao,userName)){
            jsonObject.put("status", "error");
            jsonObject.put("errorString", "userName exists!");
            return jsonObject;
        }

        User user = new User();
        user.setAccount(userName);
        user.setPassword(userPassword);
        userDao.insert(user);

        jsonObject.put("status", "ok");
        jsonObject.put("errorString", "");
        return jsonObject;
    }

    @GetMapping(path = "logout")
    public String logout(){
        return "login"; }


    @PostMapping(path = "/alt_pwd")
    @ResponseBody
    public JSONObject altPassword(@RequestParam("userName") final String userName,
                                  @RequestParam("oldPassword")  final String oldPassword,
                                  @RequestParam("newPassword") final String newPassword,
                                  @RequestParam("repeatPassword") final String repeatPassword){
        JSONObject jsonObject = new JSONObject();
        User user = userDao.getUserByAccount(userName);
        if(userName.isEmpty()){
            jsonObject.put("status", "error");
            jsonObject.put("errorString", "Please input old password!");
            return jsonObject;
        }
        else if(!oldPassword.equals(user.getPassword())){
            jsonObject.put("status", "error");
            jsonObject.put("errorString", "old password error!");
            return jsonObject;
        }
        else if (!UserUtil.checkPasswordIsMatchRegAndEqual(newPassword, repeatPassword)){
            jsonObject.put("status", "error");
            jsonObject.put("errorString", "new password error");
            return jsonObject;
        }
        else{
            user.setPassword(newPassword);
            userDao.update(user);
            jsonObject.put("status", "ok");
            jsonObject.put("errorString", "");
            return jsonObject;
        }
    }

    public static class UserUtil{

        private static boolean checkUserNameIsNotExist(UserDao userDao,String userName){
            User user = userDao.getUserByAccount(userName);
            if(user == null)
                return true;
            return false;
        }

        private static boolean checkPasswordIsMatchRegAndEqual(String password, String repeatPassword){
            return  password.matches(RegExConfig.password)
                    && repeatPassword.matches(RegExConfig.password)
                    && password.equals(repeatPassword);
        }

    }

}
