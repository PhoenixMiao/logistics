package com.phoenix.logistics.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;

/**
 * tb_collection  generated at 2019-12-06 17:50:51 by: undestiny
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tb_collection{

	@Id
	private Integer id;
	private String company_url;
	private Integer is_delete = 0;
	private long user_id;
	private String company_name;
	private Integer order = 0;

}
