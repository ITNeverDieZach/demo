package com.zachcc.mbg.entities;

import java.io.Serializable;
import lombok.Data;

/**
 * role_permission
 * @author 
 */
@Data
public class RolePermission implements Serializable {
    private Integer id;

    private String role;

    private String permission;

    private static final long serialVersionUID = 1L;
}