package com.lhstack.microservice.common.resp;

/**
 * @class: ResultEnum
 * @author: lhstack
 * @date:2020/9/27
 * @description:
 * @since: 1.8
 **/
public enum ResultEnum {

    /**
     * 操作成功
     */
    SUCCESS(200,true,"operation success"),


    /**
     * 创建成功
     */
    CREATED(201,true,"create success"),


    /**
     * 临时重定向
     */
    REDIRECT_TEMPORARY(307,true,"temporary redirect"),


    /**
     * 永久重定向
     */
    REDIRECT_PERMANENT(301,true,"permanent redirect"),

    /**
     * 资源未找到
     */
    NOT_FOUND(404,false,"not found"),



    /**
     * 未授权
     */
    NON_AUTHORITATIVE_INFORMATION(203, false,"Non-Authoritative Information"),

    /**
     * 拒绝请求
     */
    BAD_REQUEST(400,false,"bad request"),

    /**
     * 被禁止的
     */
    FORBIDDEN(403,false, "Forbidden"),

    /**
     * 未经许可
     */
    UNAUTHORIZED(401, false,"Unauthorized"),

    /**
     * 网络错误
     */
    INTERNAL_SERVER_ERROR(500, false,"Internal Server Error"),

    VALIDATED_ERROR(505,false,"Validated Error");

    ResultEnum(Integer status,boolean state,String msg){
        this.state = state;
        this.status = status;
        this.msg = msg;
    }

    private Integer status;

    private boolean state;

    private String msg;

    public Integer getStatus() {
        return status;
    }

    public boolean isState() {
        return state;
    }

    public String getMsg() {
        return msg;
    }

    public ResultEnum setMsg(String msg) {
        this.msg = msg;
        return this;
    }
}
