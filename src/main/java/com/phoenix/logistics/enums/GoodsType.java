package com.phoenix.logistics.enums;

public enum GoodsType {

    DAILY("DAILY","日用品"),
    FOODS("FOODS","食品"),
    FILES("FILES","文件"),
    CLOTHES("CLOTHES","衣物"),
    DIGITALS("DIGITALS","数码产品"),
    OTHERS("OTHERS","其他")
    ;

    private String name;

    private String description;

    GoodsType(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

}
