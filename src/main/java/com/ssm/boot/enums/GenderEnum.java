
package com.ssm.boot.enums;

public enum GenderEnum {
    MALE(1, "男"),
    FEMALE(2, "女");

    private int code;
    private String remark;

    private GenderEnum(int code, String remark) {
        this.code = code;
        this.remark = remark;
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
