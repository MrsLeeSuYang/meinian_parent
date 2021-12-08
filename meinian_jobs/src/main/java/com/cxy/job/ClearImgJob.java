package com.cxy.job;

import com.cxy.constant.RedisConstant;
import com.cxy.util.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisPool;

import java.util.Set;

public class ClearImgJob {

    @Autowired
    JedisPool jedisPool;

    //清理七牛云上”垃圾图片“的方法
    public void clearImg(){
        Set<String> pics = jedisPool.getResource().sdiff(RedisConstant.SETMEAL_PIC_RESOURCES,RedisConstant.SETMEAL_PIC_DB_RESOURCES);
        for(String pic : pics){
            QiniuUtils.deleteFileFromQiniu(pic);
            System.out.println("删除垃圾图片：pic = " + pic);
            jedisPool.getResource().srem(RedisConstant.SETMEAL_PIC_RESOURCES,pic);
        }
    }
}
