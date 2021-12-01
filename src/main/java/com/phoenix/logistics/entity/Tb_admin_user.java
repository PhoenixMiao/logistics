package com.phoenix.logistics.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * tb_admin_user  generated at 2019-12-06 17:50:51 by: undestiny
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tb_admin_user{
	private Integer id;
	private String user_name;
	private String user_password;

}
