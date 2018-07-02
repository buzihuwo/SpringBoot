package com.zhaolong.lesson6.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "LoggerInfos")
public class LoggerEntity implements Serializable {
    //编号
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;
    //客户端请求ip
    @Column(name = "ClientIp")
    private String clientIp;
    //客户端请求路径
    @Column(name = "Uri")
    private String uri;
    //终端请求方式,普通请求,ajax请求
    @Column(name = "Type")
    private String type;
    //请求方式method,post,get等
    @Column(name = "Method")
    private String method;
    //请求参数内容,json
    @Column(name = "ParamData")
    private String paramData;
    //请求接口唯一session标识
    @Column(name = "SessionId")
    private String sessionId;
    //请求时间
    @Column(name = "Time")
    private Timestamp time;
    //接口返回时间
    @Column(name = "ReturmTime")
    private String returnTime;
    //接口返回数据json
    @Column(name = "ReturnData")
    private String returnData;
    //请求时httpStatusCode代码，如：200,400,404等
    @Column(name = "HttpStatusCode")
    private String httpStatusCode;
    //请求耗时秒单位
    @Column(name = "TimeConsuming")
    private int timeConsuming;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getParamData() {
        return paramData;
    }

    public void setParamData(String paramData) {
        this.paramData = paramData;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public String getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(String returnTime) {
        this.returnTime = returnTime;
    }

    public String getReturnData() {
        return returnData;
    }

    public void setReturnData(String returnData) {
        this.returnData = returnData;
    }

    public String getHttpStatusCode() {
        return httpStatusCode;
    }

    public void setHttpStatusCode(String httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }

    public int getTimeConsuming() {
        return timeConsuming;
    }

    public void setTimeConsuming(int timeConsuming) {
        this.timeConsuming = timeConsuming;
    }
}
