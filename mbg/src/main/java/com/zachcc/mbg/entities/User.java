package com.zachcc.mbg.entities;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * user
 * @author 
 */
@Data
public class User implements Serializable {
    private Integer id;

    private String name;

    private String password;

    private String gender;

    private String signature;

    private String img;

    private String uuid;

    private String background;

    private String telephone;

    List<String> roles;

    List<String> permissions;
    private static final long serialVersionUID = 1L;
}