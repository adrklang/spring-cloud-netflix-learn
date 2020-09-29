package com.lhstack.microservice.common.exceptions;

/**
 * @class: ExistResourceException
 * @author: lhstack
 * @date:2020/9/27
 * @description:
 * @since: 1.8
 **/
public class ExistResourceException extends RuntimeException {

    public ExistResourceException() {
    }

    public ExistResourceException(String message) {
        super(message);
    }
}
