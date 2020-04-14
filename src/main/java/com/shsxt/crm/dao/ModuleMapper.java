package com.shsxt.crm.dao;

import com.shsxt.base.BaseMapper;
import com.shsxt.crm.dto.ModuleDto;
import com.shsxt.crm.dto.TreeDto;
import com.shsxt.crm.vo.Module;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ModuleMapper extends BaseMapper<Module,Integer> {

    public List<TreeDto> queryAllModules();

    Module queryModulesByModuleName(Integer grade, String moduleName);

    Module queryModuleByUrl(Integer grade, String url);

    Module queryModuleByOptValue(String optValue);

    List<Map<String, Object>> queryAllModulesByGrade(Integer grade);

    int countSubModuleByParentId(Integer mid);

   public List<ModuleDto> queryUserHasRoleHasModuleDtos(@Param("userId") Integer userId,@Param("grade") Integer grade,@Param("parentId") Integer parentId);


}