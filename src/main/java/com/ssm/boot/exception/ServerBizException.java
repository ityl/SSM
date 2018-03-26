
package com.ssm.boot.exception;


import com.ssm.boot.enums.ErrorCodeEnum;

public class ServerBizException extends Exception {
    private static final long serialVersionUID = -1557974392802481353L;
    private ErrorCodeEnum errCode;

    public ServerBizException() {
        super(ErrorCodeEnum.SERVER_SYSERR.getRemark());
        this.errCode = ErrorCodeEnum.SERVER_SYSERR;
    }

    public ServerBizException(String errorMessage) {
        super(errorMessage);
        this.errCode = ErrorCodeEnum.SERVER_SYSERR;
    }

    public ServerBizException(ErrorCodeEnum errCode) {
        super(errCode.getRemark());
        this.errCode = errCode;
    }

    public ServerBizException(String message, ErrorCodeEnum errCode) {
        super(message);
        this.errCode = errCode;
    }

    public ServerBizException(Throwable cause, ErrorCodeEnum errCode) {
        super(cause);
        this.errCode = errCode;
    }

    public ServerBizException(String message, Throwable cause, ErrorCodeEnum errCode) {
        super(message, cause);
        this.errCode = errCode;
    }

    public ErrorCodeEnum getErrCode() {
        return this.errCode;
    }

    public void setErrCode(ErrorCodeEnum errCode) {
        this.errCode = errCode;
    }
}
