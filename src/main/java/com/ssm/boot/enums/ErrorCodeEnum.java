package com.ssm.boot.enums;

public enum ErrorCodeEnum {

    /** 参数非法 */
    INVALID_PARAM(1002, "参数非法"),

    /************* 系统公用 *******************************/
    SERVER_SYSERR(1001, "系统内部错误"),
    SYSTEM_INVALID_PARAM(1002, "参数非法"),
    SYSTEM_ITEM_NOT_EXISTED(1003, "请求项不存在"),
    SYSTEM_NO_PERMISSIONS(1004,"权限拒绝"),
    SYSTEM_MOBILE_INVALID(1005,"手机号非法"),
    SYSTEM_ID_CARD_INVALID(1006,"身份证号非法"),
    SYSTEM_EMPTY_PARAM(1007,"参数不能为空"),


    /***************登录错误码**************/
    LOGIN_VERCODE_ERROR(1101,"验证码错误"),
    LOGIN_USER_NO_EXIST(1102,"用户不存在"),
    LOGIN_PASSWORD_ERROR(1103,"密码错误"),
    LOGIN_WX_NOT_INIT(1104, "微信账户未初始化"),
    LOGIN_TOO_FREQUENT(1105,"登录过于频繁,请10分钟后再登录"),
    LOGIN_PSWD_UNUSUAL(1106,"密码异常"),
    LOGIN_BAN_USER(1107,"账号被禁用"),
    LOGIN_ERROR(1108,"登录失败"),
    LOGIN_ACCOUNT_PASSWORD_ERROR(1109,"用户名密码错误或账户待审核"),
    USER_CHANGE_ERROR(1110,"用户名密码错误或账户待审核"),


    /**************用户错误码****************/
    USER_SESSION_EXPIRE(1201,"会话过期"),
    USER_GEN_ID_FAIL(1202, "生成用户id异常"),
    USER_MOBILE_NO_ERROR(1203, "手机号码错误"),
    USER_OLDPASSWORD_ERROR(1204,"旧密码错误"),
    USER_CHECK_FAILED(1205,"用户信息校验失败"),
    USER_DEL_ERROR(1206,"删除用户失败"),
    USER_ADDROLE_ERROR(1207,"用户添加权限失败"),

    /****************验证码错误码********************/
    VERCODE_ERROR(1401,"验证码错误"),
    VERCODE_EXPIRE(1402,"验证码过期"),
    VERCODE_FREQUENT(1403,"不能频繁下发验证码"),
    VERCODE_TODAY_LIMITED(1404,"今日验证码已超限"),
    VERCODE_FAIL(1405,"发送验证码失败"),


    /**************业务错误码*********************/
    INVALID_MOBILE_NO(2002, "手机号错误");



    private int code;

    private String remark;
    
    ErrorCodeEnum(int code, String remark) {
        this.code = code;
        this.remark = remark;
    }

    public String getRemark() {
        return remark;
    }

    public int getCode() {
        return code;
    }

    public enum ErrorCodedEnum {

    }
}
