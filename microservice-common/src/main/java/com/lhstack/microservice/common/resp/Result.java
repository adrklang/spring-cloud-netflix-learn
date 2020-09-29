package com.lhstack.microservice.common.resp;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @class: Result
 * @author: lhstack
 * @date:2020/9/27
 * @description:
 * @since: 1.8
 **/
@Data
@Accessors(chain = true)
public class Result<T> implements Serializable {

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
    private T data;


    public Result(ResultEnum resultEnum){
        this.state = resultEnum.isState();
        this.status = resultEnum.getStatus();
        this.msg = resultEnum.getMsg();
    }
    public Result(ResultEnum resultEnum,T data){
        this.state = resultEnum.isState();
        this.status = resultEnum.getStatus();
        this.msg = resultEnum.getMsg();
        this.data = data;
    }

    public Result() {
    }

    public Result(int status, boolean state, String msg, T data) {
        this.status = status;
        this.state = state;
        this.msg = msg;
        this.data = data;
    }

    /**
     * 成功请求
     * @return
     */
    public static Result ok(){
        return new Result(ResultEnum.SUCCESS);
    }

    /**
     *
     * @param data 数据
     * @param <T> 范型
     * @return
     */
    public static <T> Result<T> ok(T data){
        return new Result<>(ResultEnum.SUCCESS,data);
    }

    public static Result failed(ResultEnum resultEnum){
        return new Result(resultEnum);
    }

    public static <T> Result<T> failed(ResultEnum resultEnum,T data){
        return new Result<>(resultEnum,data);
    }
}
