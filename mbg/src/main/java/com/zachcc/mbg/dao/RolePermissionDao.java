package com.zachcc.mbg.dao;

import com.zachcc.mbg.entities.RolePermission;
import org.springframework.stereotype.Repository;

@Repository
public interface RolePermissionDao {
    int deleteByPrimaryKey(Integer id);

    int insert(RolePermission record);

    int insertSelective(RolePermission record);

    RolePermission selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RolePermission record);

    int updateByPrimaryKey(RolePermission record);
}