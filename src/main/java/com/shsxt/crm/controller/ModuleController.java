package com.shsxt.crm.controller;

import com.shsxt.base.BaseController;
import com.shsxt.crm.dto.TreeDto;
import com.shsxt.crm.service.ModuleService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("module")
public class ModuleController extends BaseController {

    @Resource
    private ModuleService moduleService;

    @RequestMapping("queryAllModules")
    @ResponseBody
    public List<TreeDto> queryAllModules(){
       return moduleService.queryAllModules();
    }
}
