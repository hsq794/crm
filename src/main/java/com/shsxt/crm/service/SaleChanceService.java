package com.shsxt.crm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shsxt.base.BaseService;
import com.shsxt.crm.enums.DevResult;
import com.shsxt.crm.enums.StateStatus;
import com.shsxt.crm.query.SaleChanceQuery;
import com.shsxt.crm.utils.AssertUtil;
import com.shsxt.crm.utils.PhoneUtil;
import com.shsxt.crm.vo.SaleChance;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class SaleChanceService extends BaseService<SaleChance,Integer> {

    /**
     * 查询
     * @param saleChanceQuery
     * @return
     */
    public Map<String,Object> querySaleChancesByParams(SaleChanceQuery saleChanceQuery){
        Map<String,Object> result=new HashMap<String,Object>();
        PageHelper.startPage(saleChanceQuery.getPage(),saleChanceQuery.getRows());
        PageInfo<SaleChance> pageInfo=new PageInfo<>(selectByParams(saleChanceQuery));
        result.put("total",pageInfo.getTotal());
        result.put("rows",pageInfo.getList());
        //System.out.println(result.toString());
        return result;
    }

    /**
     * 添加操作
     * @param saleChance
     */
    public void saveSaleChance(SaleChance saleChance){
        /**
         * 1.参数校验
         *      customerName : 非空
         *      linkMan: 非空
         *      linkPhone : 非空 11位手机号
         * 2.设置相关参数默认值
         *      state: 默认未分配   如果选择分配人 state为已分配
         *      assignTime: 如果 如果选择分配人  时间为当前系统时间
         *      devResult: 默认未开发 如果选择分配人  devResult为开发中
         *      isValid:默认有效
         *      createDate updateDate : 默认当前系统时间
         * 3.执行添加  判断结果
         *
         */
        checkParams(saleChance.getCustomerName(),saleChance.getLinkMan(),saleChance.getLinkPhone());
        saleChance.setState(StateStatus.UNSTATE.getType());
        saleChance.setDevResult(DevResult.UNDEV.getStatus());
        if(StringUtils.isNoneBlank(saleChance.getAssignMan())){
            saleChance.setState(StateStatus.STATED.getType());
            saleChance.setDevResult(DevResult.DEVING.getStatus());
            saleChance.setAssignTime(new Date());
        }
        saleChance.setIsValid(1);
        saleChance.setCreateDate(new Date());
        saleChance.setUpdateDate(new Date());
        AssertUtil.isTrue(insertSelective(saleChance)<1,"机会数据添加失败!");
    }


    private void checkParams(String customerName, String linkMan, String linkPhone) {
        AssertUtil.isTrue(StringUtils.isBlank(customerName),"请输入机会来源!");
        AssertUtil.isTrue(StringUtils.isBlank(linkMan),"请输入联系人!");
        AssertUtil.isTrue(StringUtils.isBlank(linkPhone),"请输入手机号码!");
        AssertUtil.isTrue(!(PhoneUtil.isMobile(linkPhone)),"手机号码不合法!");
    }

    /**
     * 更新操作
     * @param saleChance
     */
    public void updateSaleChance(SaleChance saleChance){
        /**
         * 1.参数校验
         *    id 记录存在校验
         *    customerName : 非空
         *    linkMan : 非空
         *    linkPhone: 非空 11位手机号
         * 2.设置相关参数值
         *    updateDate:系统当前时间
         *          原始记录  未分配  修改后改为已分配（由配置人决定）
         *              state 0 --1
         *              assignTime 系统当前时间
         *              devResult 0---1
         *          原始记录  已分配   修改后 未分配
         *              state 1  --0
         *              assignTime  待定  null
         *              devResult 1 --0
         *  3.执行更新，判断结果
         */
        AssertUtil.isTrue(null==saleChance.getId(),"待更新记录不存在!");
        SaleChance temp=selectByPrimaryKey(saleChance.getId());
        AssertUtil.isTrue(null==temp,"待更新记录不存在!");
        checkParams(saleChance.getCustomerName(),saleChance.getLinkMan(),saleChance.getLinkPhone());
        if(StringUtils.isBlank(temp.getAssignMan())&& StringUtils.isNoneBlank(saleChance.getAssignMan())){
            saleChance.setState(StateStatus.STATED.getType());
            saleChance.setAssignTime(new Date());
            saleChance.setDevResult(DevResult.DEVING.getStatus());
        }else if(StringUtils.isNoneBlank(temp.getAssignMan())&& StringUtils.isBlank(saleChance.getAssignMan())){
            saleChance.setAssignMan("");
            saleChance.setState(StateStatus.UNSTATE.getType());
            saleChance.setAssignTime(null);
            saleChance.setDevResult(DevResult.UNDEV.getStatus());
        }
        saleChance.setUpdateDate(new Date());
        AssertUtil.isTrue(updateByPrimaryKeySelective(saleChance)<1,"机会数据更新失败!");
    }


    public void deleteSaleChancesByIds(Integer[] ids){
        AssertUtil.isTrue(null==ids || ids.length==0,"请选择待删除机会数据!");
        AssertUtil.isTrue(deleteBatch(ids)<ids.length,"机会数据删除失败!");
    }
}
