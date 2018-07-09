package com.zhaolong.lesson4.controller;

import com.alibaba.fastjson.JSON;
import com.zhaolong.lesson4.entity.GoodEntity;
import com.zhaolong.lesson4.entity.QGoodEntity;
import com.zhaolong.lesson4.entity.viewEntity.RedisHashResponse;
import com.zhaolong.lesson4.jpa.GoodJPA;
import com.zhaolong.lesson4.utils.InquirerUtils;
import com.zhaolong.lesson4.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/redis")
public class RedisController {
    @Autowired
    private GoodJPA goodJPA;

    @GetMapping("/getRedisString")
    public Object getRedisString() {
        String cacheName = "good";
        String casheId = "string";
        if (RedisUtils.isExistence(cacheName, casheId)) {/*是否存在*/
            return JSON.parseArray(RedisUtils.getRedisString(cacheName, casheId), GoodEntity.class);
        } else {
            List<GoodEntity> list = getGoodEntityList("5de46db5a73b4f429133d5acab07ec19");
            String goodEntityJson = JSON.toJSONString(list);

            RedisUtils.setRedisString(cacheName, casheId, goodEntityJson);
            RedisUtils.setExpireTime(cacheName, casheId, 5, TimeUnit.MINUTES);
            return list;
        }
    }

    @GetMapping("/getRedisHash")
    public Object getRedisHash() {
        String cacheName = "good";
        String casheId = "hash";
        List<RedisHashResponse> RedisHashList = new ArrayList<RedisHashResponse>();
        if (RedisUtils.isExistence(cacheName, casheId)) {
            Map<String, String> hashMap = RedisUtils.GetRedisHash.getRedisHash(cacheName, casheId);
            if (hashMap != null) {
                for (Map.Entry<String, String> entry : hashMap.entrySet()) {
                    RedisHashResponse redisHashResponse = new RedisHashResponse();
                    redisHashResponse.setKey(entry.getKey());
                    redisHashResponse.setValue(JSON.parseArray(entry.getValue(), GoodEntity.class));
                    RedisHashList.add(redisHashResponse);
                }
            }
        } else {
            String uuid = UUID.randomUUID().toString();
            List<GoodEntity> list = getGoodEntityList("5de46db5a73b4f429133d5acab07ec19");
            RedisUtils.setRedisHash(cacheName, casheId, uuid, list);
            RedisHashResponse redisHashResponse = new RedisHashResponse();
            redisHashResponse.setKey(uuid);
            redisHashResponse.setValue(list);
            RedisHashList.add(redisHashResponse);

            String uuid1 = UUID.randomUUID().toString();
            List<GoodEntity> list1 = getGoodEntityList("9c73fe5e3bf84d58a68c236bc0af0402");
            RedisUtils.setRedisHash(cacheName, casheId, uuid1, list1);
            RedisHashResponse redisHashResponse1 = new RedisHashResponse();
            redisHashResponse1.setKey(uuid);
            redisHashResponse1.setValue(list);
            RedisHashList.add(redisHashResponse1);
        }

        return RedisHashList;
    }

    @GetMapping("/getRedisHashByFieid")
    public Object getRedisHashByFieid() {
        String cacheName = "good";
        String casheId = "hash";
        String fieid = "df3ced27-d8e9-4ad8-86c4-d1f0664714d3";
        Object value = RedisUtils.GetRedisHash.getRedisHashByFieid(cacheName, casheId, fieid);
        if (value != null) {
            List<GoodEntity> obj = JSON.parseArray(value.toString(), GoodEntity.class);
            RedisUtils.setRedisHash(cacheName, casheId, fieid, obj);
            return obj;
        }
        return null;
    }

    @GetMapping("/setRedisLeftList")
    public Object setRedisLeftList() {
        String cacheName = "good";
        String casheId = "List";
        List<GoodEntity> list = getGoodEntityList("5de46db5a73b4f429133d5acab07ec19");
        RedisUtils.SetRedisList.setRedisLeftList(cacheName, casheId,  list);
        return list;
    }

    @GetMapping("/getRedisRightList")
    public Object getRedisRightList() {
        String cacheName = "good";
        String casheId = "List";
        return RedisUtils.GetRedisList.getRedisRightList(cacheName, casheId);
    }

    private List<GoodEntity> getGoodEntityList(String uuid) {
        QGoodEntity qGoodEntity = QGoodEntity.goodEntity;
        InquirerUtils inquirer = new InquirerUtils();
        inquirer.putExpression(qGoodEntity.typeGuid.guid.eq(uuid));
        return inquirer.iteratorToList(goodJPA.findAll(inquirer.buidleQuery()));
    }


}


