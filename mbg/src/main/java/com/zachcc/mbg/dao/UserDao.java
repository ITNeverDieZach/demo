package com.zachcc.mbg.dao;

import com.zachcc.mbg.entities.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    User selectByName(String name);

    User getUserPermission(@Param(value = "name") String name);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);


}