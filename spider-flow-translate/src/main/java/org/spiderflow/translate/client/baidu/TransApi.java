package org.spiderflow.translate.client.baidu;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.http.HttpUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class TransApi {
    private static final String TRANS_API_HOST = "http://api.fanyi.baidu.com/api/trans/vip/translate";

    private String appid;
    private String securityKey;

    public TransApi(String appid, String securityKey) {
        this.appid = appid;
        this.securityKey = securityKey;
    }

    public String getTransResult(String query, String from, String to) {
        Map<String, Object> params = buildParams(query, from, to);
         return  HttpUtil.get(TRANS_API_HOST, params);
    }

    private Map<String, Object> buildParams(String query, String from, String to) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("q", query);
        params.put("from", from);
        params.put("to", to);

        params.put("appid", appid);

        // 随机数
        String salt = String.valueOf(System.currentTimeMillis());
        params.put("salt", salt);

        // 签名
        String src = appid + query + salt + securityKey; // 加密前的原文
        params.put("sign", SecureUtil.md5(src));

        return params;
    }

}
