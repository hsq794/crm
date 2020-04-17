package com.shsxt.crm.dao;

import com.shsxt.base.BaseMapper;
import com.shsxt.crm.vo.CustomerOrder;

import java.util.Map;

public interface CustomerOrderMapper extends BaseMapper<CustomerOrder,Integer> {

    Map<String, Object> queryOrderDetailByOrderId(Integer orderId);

    CustomerOrder queryLastCustomerOrderByCusId(Integer cusId);
}