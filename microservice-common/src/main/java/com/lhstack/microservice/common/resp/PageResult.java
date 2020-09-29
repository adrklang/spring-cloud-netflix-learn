package com.lhstack.microservice.common.resp;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @class: PageResult
 * @author: lhstack
 * @date:2020/9/27
 * @description:
 * @since: 1.8
 **/
@Data
public class PageResult<T> implements Serializable {

    /**
     * 响应状态码
     */
    private int status;

    /**
     * 响应状态 true | false
     */
    private boolean state;

    /**
     * 响应消息
     */
    private String msg;

    /**
     * 响应数据
     */
    private List<T> data;

    /**
     * 数据长度
     */
    private long total;

    public PageResult(ResultEnum resultEnum){
        this.state = resultEnum.isState();
        this.msg = resultEnum.getMsg();
        this.status = resultEnum.getStatus();
    }

    public PageResult() {
    }

    public PageResult(ResultEnum resultEnum, List<T> data, long total){
        this.data = data;
        this.total = total;
        this.state = resultEnum.isState();
        this.msg = resultEnum.getMsg();
        this.status = resultEnum.getStatus();
    }

    public PageResult(String msg,List<T> data,long total){
        this(ResultEnum.SUCCESS.setMsg(msg),data,total);
    }

    public PageResult(int status, boolean state, String msg, List<T> data, long total) {
        this.status = status;
        this.state = state;
        this.msg = msg;
        this.data = data;
        this.total = total;
    }



    public PageResult(List<T> data, long total){
        this(ResultEnum.SUCCESS,data,total);
    }
}
