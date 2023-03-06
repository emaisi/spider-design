package org.spiderflow.translate.enums;

public enum PlatformEnum {
    /** 翻译平台 1:tencent，2:讯飞，3:百度，4:有道,5:阿里云 */
    TENCENT(1,"腾讯"),
    XUNFEI(2,"讯飞"),
    BAIDU(3,"百度"),
    YOUDAO(4,"有道"),
    ALIYUN(5,"阿里云"),
    ;

    private final String info;
    private final Integer code;

    private PlatformEnum(Integer code,String info)
    {
        this.info = info;
        this.code = code;
    }

    public String getInfo()
    {
        return info;
    }
    public Integer getCode()
    {
        return code;
    }

}
