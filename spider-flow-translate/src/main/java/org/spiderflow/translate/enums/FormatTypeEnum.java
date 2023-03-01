package org.spiderflow.translate.enums;

public enum FormatTypeEnum {
    /** 用户状态 */
    HTML("html"),
    TEXT("text");

    private final String info;

    private FormatTypeEnum(String info)
    {
        this.info = info;
    }

    public String getInfo()
    {
        return info;
    }

}
