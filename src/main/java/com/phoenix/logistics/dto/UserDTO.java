package com.phoenix.logistics.dto;

import com.phoenix.logistics.entity.Admin;
import com.phoenix.logistics.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO implements Serializable {

    private Long id;
    private String username;
    private int type;//0为普通用户，1为管理员

    public UserDTO(User user){
        id = user.getId();
        username = user.getUserName();
        type = 0;
    }

    public UserDTO(Admin admin){
        id = admin.getId();
        username = admin.getUserName();
        type = 1;
    }

}
