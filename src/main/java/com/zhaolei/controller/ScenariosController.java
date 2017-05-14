package com.zhaolei.controller;

import com.alibaba.fastjson.JSONObject;
import com.zhaolei.dao.FrameworkDao;
import com.zhaolei.dao.ScenarioDao;
import com.zhaolei.dao.UserDao;
import com.zhaolei.model.Framework;
import com.zhaolei.model.Scenario;
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
@RequestMapping("/scenariosManage")
public class ScenariosController {

    @Autowired
    private ScenarioDao scenarioDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private FrameworkDao frameworkDao;

    @GetMapping
    public String scenariosPage(@RequestParam(value = "userName") String userName,
                                Model model){
        model.addAttribute("userName",userName);
        User user =userDao.getUserByAccount(userName);
        List<Scenario> scenarios = scenarioDao.getScenarioByUserId(user.getId());
        model.addAttribute("scenarios",scenarios);
        return "index_scenarios";
    }

    @PostMapping(path = "/addScenario")
    @ResponseBody
    public JSONObject addScenario(@RequestParam("userName") final String userName,
                                  @RequestParam("scenarioName") final String scenarioName ){
        JSONObject jsonObject = new JSONObject();
        User user= userDao.getUserByAccount(userName);
        Scenario scenario = scenarioDao.getScenarioByName(user.getId(),scenarioName);

        if(scenario != null)
        {
            jsonObject.put("status","error");
            jsonObject.put("errorString","The Scenario Exists!");
            return jsonObject;
        }
        else{
            scenario = new Scenario();
            scenario.setName(scenarioName);
            scenario.setUserId(user.getId());
            scenarioDao.insert(scenario);
            jsonObject.put("status","ok");
            jsonObject.put("errorString","");
            return jsonObject;
        }

    }

    @PostMapping(path = "/delScenario")
    @ResponseBody
    public JSONObject delScenario(@RequestParam("userName") final String userName,
                                  @RequestParam("scenarioName") final String scenarioName ){
        JSONObject jsonObject = new JSONObject();
        User user= userDao.getUserByAccount(userName);
        Scenario scenario = scenarioDao.getScenarioByName(user.getId(),scenarioName);

        if(scenario == null)
        {
            jsonObject.put("status","error");
            jsonObject.put("errorString","The Scenario not Exist!");
            return jsonObject;
        }
        else{
            scenarioDao.deleteScenarioById(scenario.getId());
            jsonObject.put("status","ok");
            jsonObject.put("errorString","");
            return jsonObject;
        }
    }

    @PostMapping(path = "/addFramework")
    @ResponseBody
    public JSONObject delScenario(@RequestParam("userName") final String userName,
                                  @RequestParam("scenarioName") final String scenarioName,
                                  @RequestParam("frameworkContent") final String frameworkContent){
        System.out.println(frameworkContent);
        JSONObject jsonObject = new JSONObject();
        if(frameworkContent.isEmpty()|| frameworkContent.trim().isEmpty())
        {
            jsonObject.put("status","error");
            jsonObject.put("errorString","Please input the framework!");
            return jsonObject;
        }

        User user = userDao.getUserByAccount(userName);
        Scenario scenario = scenarioDao.getScenarioByName(user.getId(),scenarioName);
        Framework framework = frameworkDao.getFrameworkByName(scenario.getId(),frameworkContent);
        if(framework != null)
        {
            jsonObject.put("status","error");
            jsonObject.put("errorString","The Framework Exists!");
            return jsonObject;
        }
        else{
            framework = new Framework();
            framework.setScenarioId(scenario.getId());
            framework.setContent(frameworkContent);
            frameworkDao.insert(framework);
            jsonObject.put("status","ok");
            jsonObject.put("errorString","");
            return jsonObject;
        }
    }

}
