package com.phoenix.logistics.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Collection_group {

    @Id
    private Long id;
    private Integer user_id;
    private String group_name;
    private String collection_ids = "";

}
