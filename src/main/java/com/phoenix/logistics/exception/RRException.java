package com.phoenix.logistics.exception;

import lombok.Getter;
import com.phoenix.logistics.common.EnumExceptionType;

@Getter
public class RRException extends RuntimeException {

    private EnumExceptionType enumExceptionType;

    public RRException() {
    }

    public RRException(EnumExceptionType enumExceptionType) {
        this.enumExceptionType = enumExceptionType;
    }
}
