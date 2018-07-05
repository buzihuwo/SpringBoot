package com.zhaolong.lesson4.controller;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.zhaolong.lesson4.entity.*;
import com.zhaolong.lesson4.jpa.GoodJPA;
import com.zhaolong.lesson4.utils.Inquirer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;

@RestController
@RequestMapping("/good")
public class GoodController {

    @Autowired
    private GoodJPA goodJPA;

    @PersistenceContext
    private EntityManager entityManager;

    @GetMapping("/querylist")
    public List<GoodEntity> list() {
        //querydsl查询实体
        QGoodEntity qGoodEntity = QGoodEntity.goodEntity;
        //构建JPA查询对象
        JPAQuery<GoodEntity> jpaQuery = new JPAQuery<>(entityManager);
        //返回查询接口
        return jpaQuery.select(qGoodEntity).from(qGoodEntity).where(qGoodEntity.typeGuid.guid.eq("5de46db5a73b4f429133d5acab07ec19")).fetch();
    }

    @GetMapping("/join")
    public List<GoodEntity> join() {
        //querydsl查询实体
        QGoodEntity qGoodEntity = QGoodEntity.goodEntity;
//        //查询条件
//        BooleanExpression booleanExpression = qGoodEntity.typeGuid.guid.eq("5de46db5a73b4f429133d5acab07ec19").and(qGoodEntity.guid.eq("492312c415fc4c6284a20638d76b8698"));
//
//        //执行查询
//        Iterator<GoodEntity> iterator = goodJPA.findAll(booleanExpression).iterator();
//        List<GoodEntity> list = new ArrayList<GoodEntity>();
//        while (iterator.hasNext()) {
//            list.add(iterator.next());
//        }
//        return list;
        // 自定义查询对象
        Inquirer inquirer=new Inquirer();
        //添加查询条件
        inquirer.putExpression(qGoodEntity.typeGuid.guid.eq("5de46db5a73b4f429133d5acab07ec19"));
        inquirer.putExpression(qGoodEntity.guid.eq("492312c415fc4c6284a20638d76b8698"));
        //返回查询结果
        return inquirer.iteratorToList(goodJPA.findAll(inquirer.buidleQuery()));
    }

    @GetMapping("/add")
    public GoodEntity add() {
//        GoodTypeEntity goodTypeEntity = new GoodTypeEntity();
//        goodTypeEntity.setGuid(getUUID());
//        goodTypeEntity.setName("父" + (int) (Math.random() * 1000));
//        goodTypeEntity.setShow(true);
//        goodTypeEntity.setTime(new Date());

        GoodTypeEntity goodTypeEntity = goodJPA.findById("10e739794aae442baefd517c34b154f6").get().typeGuid;


        GoodEntity goodEntity = new GoodEntity();
        goodEntity.setGuid(getUUID());
        goodEntity.setTitle("子" + (int) (Math.random() * 1000));
        goodEntity.setTime(new Date());
        goodEntity.setTypeGuid(goodTypeEntity);

        goodJPA.save(goodEntity);
        return goodEntity;
    }

    private String getUUID() {
        String uuid = UUID.randomUUID().toString();    //获取UUID并转化为String对象
        return uuid.replace("-", "");
    }

}


