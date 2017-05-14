package com.zhaolei.controller;

import com.zhaolei.dao.UserDao;
import com.zhaolei.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;
/**
 * Created by zhaolei on 2017/4/13.
 */
@Controller
@RequestMapping("/index")
public class HomeController {

    @GetMapping
    public String index(@RequestParam(value = "userName") String userName,
                         Model model){
        model.addAttribute("userName",userName);
        return "index";
    }
}
