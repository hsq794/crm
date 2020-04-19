package com.shsxt.crm.dao;

import com.shsxt.base.BaseMapper;
import com.shsxt.crm.query.CustomerQuery;
import com.shsxt.crm.vo.Customer;
import com.shsxt.crm.vo.CustomerLoss;

import java.util.List;
import java.util.Map;

public interface CustomerMapper extends BaseMapper<Customer,Integer> {


    Customer queryCustomerByName(String name);

    List<Customer> queryLossCustomers();

    int updateCustomerStateByIds(List<Integer> lossCusIds);


    List<Map<String, Object>> queryCustomerContributionByParams(CustomerQuery customerQuery);

    List<Map<String, Object>> countCustomerMake();
}