package com.zachcc.mbg.entities;

import java.io.Serializable;
import lombok.Data;

/**
 * user_role
 * @author 
 */
@Data
public class UserRole implements Serializable {
    private Integer id;

    private String name;

    private String uuid;

    private String role;

    private Integer userId;

    private static final long serialVersionUID = 1L;
}