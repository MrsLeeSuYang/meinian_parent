package com.cxy.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.cxy.constant.MessageConstant;
import com.cxy.constant.RedisConstant;
import com.cxy.entity.PageResult;
import com.cxy.entity.QueryPageBean;
import com.cxy.entity.Result;
import com.cxy.pojo.Setmeal;
import com.cxy.service.SetmealService;
import com.cxy.util.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    @Reference
    SetmealService setmealService;

    @Autowired
    JedisPool jedisPool;

    @RequestMapping("/getTravelGroupIdsBySetmealId")
    public Result getTravelGroupIdsBySetmealId(Integer setmealId){
        try {
            List<Integer> travelGroupIds = setmealService.getTravelGroupIdsBySetmealId(setmealId);
            return new Result(true,"根据套餐游查询跟团游成功",travelGroupIds);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"根据套餐游查询跟团游失败");
        }
    }

    @RequestMapping("/getById")
    public Result getById(Integer id){
        try {
            Setmeal setmeal = setmealService.getById(id);
            return new Result(true,MessageConstant.QUERY_SETMEAL_SUCCESS,setmeal);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_SETMEAL_FAIL);
        }
    }

    @RequestMapping("/delete")
    public Result delete(Integer id){//@RequestParam("id")
        try {
            setmealService.delete(id);
            return new Result(true,MessageConstant.DELETE_SETMEAL_SUCCESS);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new Result(false,e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.DELETE_SETMEAL_FAIL);
        }
    }

    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult pageResult = setmealService.findPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize(),queryPageBean.getQueryString());
        return pageResult;
    }

    @RequestMapping("/add")
    public Result add(Integer[] travelgroupIds, @RequestBody Setmeal setmeal){
        try {
            setmealService.add(travelgroupIds,setmeal);
            return new Result(true,MessageConstant.ADD_SETMEAL_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.ADD_SETMEAL_FAIL);
        }
    }

    @RequestMapping("/upload")
    public Result upload(@RequestParam("imgFile") MultipartFile imgFile){
        try {
            //1.获取原始文件名称
            String originalFilename = imgFile.getOriginalFilename();

            //2，生成新的文件名称（防止上传同名文件被覆盖）
            int index = originalFilename.lastIndexOf(".");
            String suffix = originalFilename.substring(index);
            String filename = UUID.randomUUID().toString() + suffix;

            //3.调用工具类，上传图片到七牛云
            QiniuUtils.upload2Qiniu(imgFile.getBytes(),filename);

            //*************************补充代码，用来解决七牛云上“垃圾图片”问题*************************
            //将上传图片名称存入Redis，基于Redis的Set集合存储
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_RESOURCES,filename);

            //4.返回结果
            return new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS,filename);
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.PIC_UPLOAD_FAIL);
        }
    }
}