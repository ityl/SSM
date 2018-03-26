
package com.ssm.boot.enums;

public enum ErrorCodeEnum {
    INVALID_PARAM(1002, "参数非法"),
    SERVER_SYSERR(1007, "系统错误");

    private int code;
    private String remark;

    private ErrorCodeEnum(int code, String remark) {
        this.code = code;
        this.remark = remark;
    }

    public String getRemark() {
        return this.remark;
    }

    public int getCode() {
        return this.code;
    }

    public static enum ErrorCodedEnum {
        ;

        private ErrorCodedEnum() {
        }
    }
}
