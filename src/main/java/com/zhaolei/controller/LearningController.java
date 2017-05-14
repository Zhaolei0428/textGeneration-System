package com.zhaolei.controller;

import com.alibaba.fastjson.JSONObject;
import com.zhaolei.dao.ScenarioDao;
import com.zhaolei.dao.TextDao;
import com.zhaolei.dao.UserDao;
import com.zhaolei.model.Scenario;
import com.zhaolei.model.Text;
import com.zhaolei.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by zhaolei on 2017/4/22.
 */
@Controller
@RequestMapping("/machineLearning")
public class LearningController {
    @Autowired
    private UserDao userDao;
    @Autowired
    private ScenarioDao scenarioDao;
    @Autowired
    private TextDao textDao;

    @GetMapping
    public String learningPage(@RequestParam(value = "userName") String userName,
                               Model model){
        model.addAttribute("userName",userName);
        User user =userDao.getUserByAccount(userName);
        List<Scenario> scenarios = scenarioDao.getScenarioByUserId(user.getId());
        model.addAttribute("scenarios",scenarios);
        return "index_learning";
    }

    @PostMapping
    @ResponseBody
    public JSONObject jsonObject(@RequestParam("userName") final String userName,
                                 @RequestParam("scenarioName") final String scenarioName,
                                 @RequestParam("labels") final String labels,
                                 @RequestParam("content") final String content){
        JSONObject jsonObject=new JSONObject();
        if(scenarioName.isEmpty()){
            jsonObject.put("status","error");
            jsonObject.put("errorString","Please add Scenarios first!");
            return jsonObject;
        }
        if(labels.isEmpty())
        {
            jsonObject.put("status","error");
            jsonObject.put("errorString","Please input the labels!");
            return jsonObject;
        }
        if(content.isEmpty())
        {
            jsonObject.put("status","error");
            jsonObject.put("errorString","Please input the text!");
            return jsonObject;
        }

        User user = userDao.getUserByAccount(userName);
        Scenario scenario = scenarioDao.getScenarioByName(user.getId(),scenarioName);
        Text text = new Text();
        text.setScenarioId(scenario.getId());
        text.setLabels(labels.trim());
        text.setContent(content);
        textDao.insert(text);
        System.out.println(text);
        jsonObject.put("status","ok");
        jsonObject.put("errorString","");
        return jsonObject;
    }

}
