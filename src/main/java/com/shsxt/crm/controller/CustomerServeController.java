package com.shsxt.crm.controller;

import com.shsxt.base.BaseController;
import com.shsxt.crm.model.ResultInfo;
import com.shsxt.crm.query.CustomerServeQuery;
import com.shsxt.crm.service.CustomerServeService;
import com.shsxt.crm.vo.CustomerServe;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

@Controller
@RequestMapping("customer_serve")
public class CustomerServeController extends BaseController {

    @Resource
    private CustomerServeService customerServeService;

    @RequestMapping("index/{type}")
    public String index(@PathVariable Integer type){
        if(type==1){
            return "customer_serve_create";
        }else if(type==2){
            return "customer_serve_assign";
        }else if(type==3){
            return "customer_serve_proce";
        }else if(type==4){
            return "customer_serve_feed_back";
        }else if(type==5){
            return "customer_serve_archive";
        }else{
            return "";
        }
    }

    @RequestMapping("saveOrUpdateCustomerServe")
    @ResponseBody
    public ResultInfo saveOrUpdateCustomerServe(CustomerServe customerServe){
        customerServeService.saveOrUpdateCustomerServe(customerServe);
        return success("操作成功");
    }

    @RequestMapping("list")
    @ResponseBody
    public Map<String,Object> queryCustomerServeByParams(CustomerServeQuery customerServeQuery){
        return customerServeService.queryByParamsForDataGrid(customerServeQuery);
    }

}
