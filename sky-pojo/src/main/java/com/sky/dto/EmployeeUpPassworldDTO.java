package com.sky.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class EmployeeUpPassworldDTO implements Serializable {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("新密码")
    private String newPassword;

    @ApiModelProperty("旧密码")
    private String oldPassworld;

    private String password;
}
