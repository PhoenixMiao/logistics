package com.phoenix.logistics.dto;

import com.phoenix.logistics.entity.Tb_user;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.phoenix.logistics.entity.Tb_admin_user;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {

    private int id;
    private String username;
    private int type;//0为普通用户，1为企业用户，2为普通管理员，3为超管

    public UserDTO(Tb_user user){
        id = Integer.parseInt(user.getId()+"");
        username = user.getUser_name();
        type = 0;
    }

    public UserDTO(Tb_admin_user admin){
        id = admin.getId();
        username = admin.getUser_name();
        type = 2;
    }

}
