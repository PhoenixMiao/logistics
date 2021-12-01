package com.phoenix.logistics.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * tb_company  generated at 2019-12-06 17:50:51 by: undestiny
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tb_company{
	private Long id;
	private Long user_id;

	private String company_name;

	private String company_address;

	private String company_phone;

	private String company_fax;

	private String company_mobile;

	private String wechat;

	private String qq;
	private String company_content;
	private Integer company_browse_num;
	private Integer company_times;
	private Integer is_show;
	private String company_image;
	private Integer is_delete;
	private String company_logo;
	private Timestamp add_time;
	private Timestamp update_time;

	private String company_product;

	private String company_intro;
	private String company_file_url;
	private String company_url;
	private byte company_status;
	private Timestamp endtime;

	private String company_contacts;
	private String company_content1;

}
