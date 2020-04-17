package com.shsxt.crm.dao;

import com.shsxt.base.BaseMapper;
import com.shsxt.crm.vo.SaleChance;
import org.apache.ibatis.annotations.Param;

public interface SaleChanceMapper extends BaseMapper<SaleChance,Integer> {

    int updateByDevResult(@Param("sid") Integer sid,@Param("devResult") Integer devResult);
}