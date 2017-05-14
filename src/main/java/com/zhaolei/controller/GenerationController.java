package com.zhaolei.controller;

import com.alibaba.fastjson.JSONObject;
import com.zhaolei.dao.FrameworkDao;
import com.zhaolei.dao.ScenarioDao;
import com.zhaolei.dao.TextDao;
import com.zhaolei.dao.UserDao;
import com.zhaolei.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhaolei on 2017/4/22.
 */
@Controller
@RequestMapping("/textGeneration")
public class GenerationController {
    @Autowired
    private UserDao userDao;
    @Autowired
    private ScenarioDao scenarioDao;
    @Autowired
    private TextDao textDao;
    @Autowired
    private FrameworkDao frameworkDao;

    @GetMapping
    public String generationPage(@RequestParam(value = "userName") String userName,
                                 Model model){
        model.addAttribute("userName",userName);
        User user =userDao.getUserByAccount(userName);
        List<Scenario> scenarios = scenarioDao.getScenarioByUserId(user.getId());
        model.addAttribute("scenarios",scenarios);
        List<Framework> frameworks = new ArrayList<>();
        for(Scenario scenario:scenarios)
        {
            List<Framework> frameworks1 = frameworkDao.getFrameworkByScenarioId(scenario.getId());
            for(Framework framework:frameworks1)
               frameworks.add(framework);
        }
        String[] fms = new String[frameworks.size()];
        int scenarioId[] =new int[frameworks.size()];
        int i=0;
        for(Framework framework:frameworks){
            fms[i]=framework.getContent();
            scenarioId[i++]=framework.getScenarioId();
        }
        model.addAttribute("frameworks",fms);
        model.addAttribute("scenarioId",scenarioId);
        return "index_generation";
    }

    @PostMapping
    @ResponseBody
    public JSONObject generation(@RequestParam("userName") final String userName,
                                 @RequestParam("scenarioName") final String scenarioName,
                                 @RequestParam("framework") final String framework,
                                 @RequestParam("replaces") final String replaces,
                                 @RequestParam("labels") final String labels){
        JSONObject jsonObject = new JSONObject();

        if(scenarioName.equals("--请选择场景--")){
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

        User user = userDao.getUserByAccount(userName);
        Scenario scenario = scenarioDao.getScenarioByName(user.getId(),scenarioName);
        List<Text> texts = textDao.getTextByScenarioId(scenario.getId());
        if(texts.size()==0)
        {
            jsonObject.put("status","error");
            jsonObject.put("errorString","This scenario has not been trained!");
            return jsonObject;
        }
        List<Framework> frameworks = frameworkDao.getFrameworkByScenarioId(scenario.getId());

        TextGeneration textGeneration = new TextGeneration(texts,framework);
        String text = textGeneration.generate(labels,replaces);
        jsonObject.put("status","ok");
        jsonObject.put("generationText",text);
        return jsonObject;
    }

}
