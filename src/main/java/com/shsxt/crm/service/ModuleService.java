package com.shsxt.crm.service;

import com.shsxt.base.BaseService;
import com.shsxt.crm.dao.ModuleMapper;
import com.shsxt.crm.dto.TreeDto;
import com.shsxt.crm.vo.Module;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@SuppressWarnings("all")
public class ModuleService extends BaseService<Module,Integer> {

    @Autowired
    private ModuleMapper moduleMapper;

    public List<TreeDto> queryAllModules(){
        return moduleMapper.queryAllModules();
    }
}
