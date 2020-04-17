package com.shsxt.crm.controller;

import com.shsxt.base.BaseController;
import com.shsxt.crm.dao.CustomerLossMapper;
import com.shsxt.crm.query.CustomerLossQuery;
import com.shsxt.crm.service.CustomerLossService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

@Controller
@RequestMapping("customer_loss")
public class CustomerLossController extends BaseController {

    @Resource
    private CustomerLossService customerLossService;

    @RequestMapping("index")
    private String index(){
        return "customer_loss";
    }

    @RequestMapping("list")
    @ResponseBody
    public Map<String,Object> queryCustomerLossByParams(CustomerLossQuery customerLossQuery){
        return customerLossService.queryByParamsForDataGrid(customerLossQuery);
    }
}
