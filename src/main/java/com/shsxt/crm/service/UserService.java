package com.shsxt.crm.service;


import com.shsxt.base.BaseService;
import com.shsxt.crm.dao.UserMapper;
import com.shsxt.crm.dao.UserRoleMapper;
import com.shsxt.crm.model.UserModel;
import com.shsxt.crm.utils.AssertUtil;
import com.shsxt.crm.utils.Md5Util;
import com.shsxt.crm.utils.UserIDBase64;
import com.shsxt.crm.vo.User;
import com.shsxt.crm.vo.UserRole;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserService extends BaseService<User,Integer> {

    @Resource
    private UserMapper userMapper;

    @Resource
    private UserRoleMapper userRoleMapper;

    public UserModel login(String userName, String userPwd){

        /**
         * 1.参数校验
         *      用户名 非空
         *      密码  非空
         * 2.根据用户名  查询用户记录
         * 3.校验用户存在性
         *    不存在  -- 记录不存在  结束方法
         * 4.存在
         *      校验密码
         *          密码错误-- 密码不正确  方法结束
         * 5.密码正确
         *      用户登录成功   返回用户相关信息
         *
         */
        checkLoginParams(userName,userPwd);
        User user=userMapper.queryUserByUserName(userName);
        AssertUtil.isTrue(null==user,"用户已注销或不存在!");
        AssertUtil.isTrue(!(user.getUserPwd().equals(Md5Util.encode(userPwd))),"密码错误!");
        return buildUserModelInfo(user);
    }

    private UserModel buildUserModelInfo(User user) {
        return new UserModel(UserIDBase64.encoderUserID(user.getId()),user.getUserName(),user.getTrueName());
    }

    private void checkLoginParams(String userName, String userPwd) {
        AssertUtil.isTrue(StringUtils.isBlank(userName),"用户不能为空!");
        AssertUtil.isTrue(StringUtils.isBlank(userPwd),"用户密码不能为空!");

    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void updateUserPassword(Integer userId,String oldPassword,String newPassword,String confirmPassword){
        /**
         * 1.参数校验
         *  userId 非空  记录必须存在
         *  oldPassword 非空  必须与数据库一致
         *  newPassword 非空  新密码不能与原始密码相同
         * 2.设置用户新密码
         *   新密码加密
         * 3.执行更新
         *
         */
        checkParams(userId,oldPassword,newPassword,confirmPassword);
        User user=selectByPrimaryKey(userId);
        user.setUserPwd(Md5Util.encode(newPassword));
        AssertUtil.isTrue(updateByPrimaryKeySelective(user)<1,"密码更新失败!");

    }

    private void checkParams(Integer userId, String oldPassword, String newPassword, String confirmPassword) {
        User user=selectByPrimaryKey(userId);
        AssertUtil.isTrue(null==userId || null==user,"用户未登录或不存在!");
        AssertUtil.isTrue(StringUtils.isBlank(oldPassword),"请输入原始密码!");
        AssertUtil.isTrue(StringUtils.isBlank(newPassword),"请输入新密码!");
        AssertUtil.isTrue(StringUtils.isBlank(confirmPassword),"请输入确认密码!");
        AssertUtil.isTrue(!(newPassword.equals(confirmPassword)),"新密码输入不一致!");
        AssertUtil.isTrue(!(user.getUserPwd().equals(Md5Util.encode(oldPassword))),"原始密码不正确!");
        AssertUtil.isTrue(oldPassword.equals(newPassword),"新密码不能与旧密码一样!");

    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void saveUser(User user){
        /**
         * 1.参数校验
         *      用户名   非空  唯一
         *      email  非空  格式合法
         *      手机号  非空  格式合法
         * 2.设置默认参数
         *      isValid 1
         *      createDate  updateDate
         *      userPwd  123456  -->md5加密
         * 3.执行添加  判断结果
         */
        checkParams(user.getUserName(),user.getEmail(),user.getPhone());
        User tem=userMapper.queryUserByUserName(user.getUserName());
        AssertUtil.isTrue(null !=tem && (tem.getIsValid())==1,"该用户存在!");
        user.setIsValid(1);
        user.setCreateDate(new Date());
        user.setUpdateDate(new Date());
        user.setUserPwd(Md5Util.encode("123456"));
        AssertUtil.isTrue(insertHasKey(user)==null,"添加用户失败!");
        int userId=user.getId();
        /**
         * 用户角色分配
         *      userId
         *      roleIds
         */
        relationUserRole(userId,user.getRoleIds());

    }

    private void relationUserRole(int userId, List<Integer> roleIds) {
        /**
         * 用户角色分配
         *      原始角色不存在    添加新的角色记录
         *      原始角色存在       添加新的角色记录
         *      原始角色存在       清空所有角色
         *      原始角色存在       移除部分角色
         * 如何进行角色分配
         *  如果用户原始角色存在   首先清空原始所有角色
         *  添加新的角色记录到用户角色表
         */
        int count=userRoleMapper.countUserRoleByUserId(userId);
        if(count>0){
            AssertUtil.isTrue(userRoleMapper.deleteUserRoleByUserId(userId)!=count,"用户角色分配失败!");

        }
        if(null!=roleIds && roleIds.size()>0){
            List<UserRole> userRoles=new ArrayList<UserRole>();
            roleIds.forEach(roleId->{
                UserRole userRole=new UserRole();
                userRole.setUserId(userId);
                userRole.setRoleId(roleId);
                userRole.setCreateDate(new Date());
                userRole.setUpdateDate(new Date());
                userRoles.add(userRole);
            });
            AssertUtil.isTrue(userRoleMapper.insertBatch(userRoles)<userRoles.size(),"用户角色分配失败!");
        }


    }

    private void checkParams(String userName, String email, String phone){
        AssertUtil.isTrue(StringUtils.isBlank(userName),"用户名不能为空!");
        AssertUtil.isTrue(StringUtils.isBlank(email),"请输入邮箱地址!");
        AssertUtil.isTrue(StringUtils.isBlank(phone),"手机号码格式不合法!");
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void updateUser(User user){
        /**
         * 1.参数校验
         *     id 非空  记录必须存在
         *     用户名  非空   唯一
         *     email  非空  格式合法
         *     手机号 非空  格式合法
         * 2.设置默认参数
         *        uddateDate
         * 3.执行更新  判断结果
         */
        AssertUtil.isTrue(null==user.getId()||null==selectByPrimaryKey(user.getId()),"待更新记录不存在!");
        checkParams(user.getUserName(),user.getEmail(),user.getPhone());
        User temp=userMapper.queryUserByUserName(user.getUserName());
        if(null!=temp && temp.getIsValid()==1){
            AssertUtil.isTrue(!(user.getId().equals(temp.getId())),"该用户存在!");
        }
        user.setUpdateDate(new Date());
        AssertUtil.isTrue(updateByPrimaryKeySelective(user)<1,"用户记录更新失败!");

        relationUserRole(user.getId(),user.getRoleIds());
    }

    public void deleteUser(Integer userId){
        System.out.println(userId);
        User user=selectByPrimaryKey(userId);
        AssertUtil.isTrue(null==userId || null==user,"待删除记录不存在!");
        int count=userRoleMapper.countUserRoleByUserId(userId);
        if(count>0){
            AssertUtil.isTrue(userRoleMapper.deleteUserRoleByUserId(userId)!=count,"用户角色分配失败!");

        }
        user.setIsValid(0);
        AssertUtil.isTrue(updateByPrimaryKeySelective(user)<1,"用户记录删除失败!");

    }





}
