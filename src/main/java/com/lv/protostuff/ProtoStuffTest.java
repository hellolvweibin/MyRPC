package com.lv.protostuff;

import com.lv.protostuff.pojo.Group;
import com.lv.protostuff.pojo.User;

import java.util.Arrays;

/**
 * @Project ：lv-RPC
 * @Author ：Levi_Bee
 * @Date ：2023/3/30 15:39
 * @description ：
 */
public class ProtoStuffTest {
    public static void main(String[] args) {
        //创建一个user对象
        User user = User.builder().id("1").age(20).name("张三").desc("programmer").build();
        //创建一个Group对象
        Group group = Group.builder().id("1").name("分组1").user(user).build();
        //使用ProtostuffUtils序列化
        byte[] data = ProtostuffUtils.serialize(group);
        System.out.println("=======");
        System.out.println("序列化后：" + Arrays.toString(data));
        Group result = ProtostuffUtils.deserialize(data, Group.class);
        System.out.println("反序列化后：" + result.toString());
    }
}
