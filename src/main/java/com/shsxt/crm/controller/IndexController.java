package com.shsxt.crm.controller;

import com.shsxt.base.BaseController;
import com.shsxt.crm.dao.PermissionMapper;
import com.shsxt.crm.service.PermissionService;
import com.shsxt.crm.service.UserService;
import com.shsxt.crm.utils.LoginUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController extends BaseController {

    @Autowired
    private PermissionService permissionService;

    @Resource
    private UserService userService;

    /**
     * 登录页
     */
    @RequestMapping("index")
    public String index(){
        return "index";
    }

    /**
     * 后台管理主页面
     */
    @RequestMapping("main")
    public String main(HttpServletRequest request){
        Integer userId= LoginUserUtil.releaseUserIdFromCookie(request);
        //System.out.println(userId);
        List<String> permissions=permissionService.queryUserHasRolesHasPermissions(userId);
        request.getSession().setAttribute("permissions",permissions);
       // System.out.println(permissions.toString());
        request.setAttribute("user",userService.selectByPrimaryKey(userId));
        return "main";
    }

}
