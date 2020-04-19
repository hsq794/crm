package com.shsxt.crm.controller;

import com.shsxt.base.BaseController;
import com.shsxt.crm.annotaions.CrmLog;
import com.shsxt.crm.annotaions.RequirePermission;
import com.shsxt.crm.model.ResultInfo;
import com.shsxt.crm.query.SaleChanceQuery;
import com.shsxt.crm.service.SaleChanceService;
import com.shsxt.crm.service.UserService;
import com.shsxt.crm.utils.LoginUserUtil;
import com.shsxt.crm.vo.SaleChance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("sale_chance")
public class SaleChanceController extends BaseController {

    @Resource
    private SaleChanceService saleChanceService;

    @Resource
    private UserService userService;

    @Autowired
    private  HttpServletRequest request;

    @RequestMapping("index")
    @CrmLog(module = "营销机会",oper = "主页展示")
    public String index(){
        return "sale_chance";
    }

    @RequestMapping("list")
    @ResponseBody
    @RequirePermission(code = "101001")
    @CrmLog(module = "营销管理",oper = "多条件查询")
    public Map<String,Object> querySaleChancesByParams(SaleChanceQuery saleChanceQuery){
        return saleChanceService.querySaleChancesByParams(saleChanceQuery);
    }

    @RequestMapping("save")
    @ResponseBody
    @RequirePermission(code = "101002")
    @CrmLog(module = "营销管理",oper = "添加")
    public ResultInfo saveSaleChance(SaleChance saleChance){
        saleChance.setCreateMan(userService.selectByPrimaryKey(LoginUserUtil.releaseUserIdFromCookie(request)).getTrueName());
        saleChanceService.saveSaleChance(saleChance);
        return success("机会数据添加成功");
    }

    @RequestMapping("update")
    @ResponseBody
    @RequirePermission(code = "101004")
    @CrmLog(module = "营销管理",oper = "更新")
    public ResultInfo updateSaleChance(SaleChance saleChance){
        saleChanceService.updateSaleChance(saleChance);
        return success("机会数据更新成功!");
    }

    @RequestMapping("delete")
    @ResponseBody
    @RequirePermission(code = "101003")
    @CrmLog(module = "营销管理",oper = "删除")
    public ResultInfo deleteSaleChance(Integer[] ids){
        //System.out.println(ids[0]);
        saleChanceService.deleteBatch(ids);
        return success("机会数据删除成功!");
    }

    @RequestMapping("updateDevResult")
    @ResponseBody
    public ResultInfo updateDevResult(Integer sid,Integer  devResult){
        saleChanceService.updateSaleChanceDevResult(sid,devResult);
        return success("开发数据成功!");
    }

}
