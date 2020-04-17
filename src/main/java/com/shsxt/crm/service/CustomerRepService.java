package com.shsxt.crm.service;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.shsxt.base.BaseService;
import com.shsxt.crm.dao.CustomerLossMapper;
import com.shsxt.crm.dao.CustomerRepMapper;
import com.shsxt.crm.utils.AssertUtil;
import com.shsxt.crm.vo.CustomerRep;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class CustomerRepService extends BaseService<CustomerRep,Integer> {
    @Resource
    private CustomerRepMapper customerRepMapper;

    @Resource
    private CustomerLossMapper customerLossMapper;

    public void saveCustomerRepByLossId(Integer lossId,CustomerRep customerRep){
        AssertUtil.isTrue(null==lossId,"请输入客户流失的id!");
        //判断是否存在
        AssertUtil.isTrue(customerLossMapper.selectByPrimaryKey(lossId)==null,"客户流失id不存在！");
        customerRep.setLossId(lossId);
        customerRep.setIsValid(1);
        customerRep.setCreateDate(new Date());
        customerRep.setUpdateDate(new Date());
        AssertUtil.isTrue(insertSelective(customerRep)<1,"客户流失缓存添加失败!");
    }

    public void updateCustomerRep(CustomerRep customerRep) {
        System.out.println(customerRep.getId());
        AssertUtil.isTrue(customerRep.getId()==null,"请输入流失缓存id!");
        AssertUtil.isTrue(selectByPrimaryKey(customerRep.getId())==null,"流失缓存id不存在!");
        customerRep.setIsValid(1);
        customerRep.setUpdateDate(new Date());
        AssertUtil.isTrue(updateByPrimaryKeySelective(customerRep)<1,"待流失缓存更新失败！");
    }

    public void deleteCustomerRep(CustomerRep customerRep) {
        System.out.println(customerRep.getId());
        AssertUtil.isTrue(customerRep.getId()==null,"请输入流失缓存id!");
        AssertUtil.isTrue(selectByPrimaryKey(customerRep.getId())==null,"流失缓存id不存在!");
        AssertUtil.isTrue(deleteByPrimaryKey(customerRep.getId())<1,"删除失败!");
    }
}
