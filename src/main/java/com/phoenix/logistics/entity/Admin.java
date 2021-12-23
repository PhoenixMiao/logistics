package com.phoenix.logistics.entity;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.sf.saxon.functions.Serialize;

import javax.persistence.Id;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("Controller 管理员")
public class Admin implements Serializable {
    @Id
    @ApiModelProperty("管理员id")
    private Long id;

    @ApiModelProperty("管理员用户名")
    private String username;

    @ApiModelProperty("密码")
    private String password;
}
