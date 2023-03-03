package org.spiderflow.translate.client.baidu;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @className: BaiduTranslator
 * @description: TODO 类描述
 * @author: Dong
 * @date: 2022/6/7
 **/
@Configuration
@ConfigurationProperties(prefix = "translator.baidu")
@Slf4j
public class BaiduTranslator {
    private String APP_ID;
    private String SECURITY_KEY;

    public String getAPP_ID() {
        return APP_ID;
    }

    public void setAPP_ID(String APP_ID) {
        this.APP_ID = APP_ID;
    }

    public String getSECURITY_KEY() {
        return SECURITY_KEY;
    }

    public void setSECURITY_KEY(String SECURITY_KEY) {
        this.SECURITY_KEY = SECURITY_KEY;
    }

    public String translate(String SourceText, String form, String to) {
        TransApi api = new TransApi(APP_ID, SECURITY_KEY);
        String transResult = api.getTransResult(SourceText, form, to);
        log.info("百度云翻译,返回数据为:{}",transResult);
        JSONObject js = JSON.parseObject(transResult);
        JSONArray trans_result = (JSONArray) js.get("trans_result");
        JSONObject o = (JSONObject)trans_result.get(0);
        String targetText =(String) o.get("dst");
        JSONObject json = new JSONObject();
        json.put("targetText", targetText);
        return targetText;

    }
}
