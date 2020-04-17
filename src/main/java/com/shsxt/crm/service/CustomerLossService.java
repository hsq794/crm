package com.shsxt.crm.service;

import com.shsxt.base.BaseService;
import com.shsxt.crm.dao.CustomerLossMapper;
import com.shsxt.crm.dao.CustomerMapper;
import com.shsxt.crm.vo.CustomerLoss;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class CustomerLossService extends BaseService<CustomerLoss,Integer> {

    @Resource
    private CustomerLossMapper customerLossMapper;

    public CustomerLoss queryCustomerLossByCusNo(String cusNo) {
        return customerLossMapper.queryCustomerLossByCusNo(cusNo);
    }
}
