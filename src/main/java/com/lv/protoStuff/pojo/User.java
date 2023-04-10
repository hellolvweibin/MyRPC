package com.lv.protoStuff.pojo;

import lombok.Builder;
import lombok.Data;

/**
 * @Project ：lv-RPC
 * @Author ：Levi_Bee
 * @Date ：2023/3/30 15:39
 * @description ：User 类
 */
@Data
@Builder
public class User {
    private String id;
    private String name;
    private Integer age;
    private String desc;
}
