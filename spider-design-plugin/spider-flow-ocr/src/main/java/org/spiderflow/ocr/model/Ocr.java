package org.spiderflow.ocr.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("sp_ocr")
public class Ocr {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String ocrName;

    private String appId;

    private String apiKey;

    private String secretKey;

    private String ocrType;

    public Ocr() {
        super();
    }

    public Ocr(String appId, String apiKey, String secretKey, String ocrType) {
        super();
        this.appId = appId;
        this.apiKey = apiKey;
        this.secretKey = secretKey;
        this.ocrType = ocrType;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOcrType() {
        return ocrType;
    }

    public void setOcrType(String ocrType) {
        this.ocrType = ocrType;
    }

    public String getOcrName() {
        return ocrName;
    }

    public void setOcrName(String ocrName) {
        this.ocrName = ocrName;
    }
}
