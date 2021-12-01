package com.phoenix.logistics.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * tb_view_record  generated at 2019-12-06 17:50:51 by: undestiny
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tb_view_record{
	private long id;
	private long company_id;
	private Timestamp add_time;

}
