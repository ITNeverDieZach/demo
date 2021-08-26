package com.zachcc.mbg.dao;

import com.zachcc.mbg.entities.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    List<User> selectUsers();

    User selectByPrimaryKey(Integer id);

    User selectByUUID(String uuid);

    User selectByName(String name);

    User getUserPermission(@Param(value = "name") String name);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    int deleteByUUID(String uuid);

}