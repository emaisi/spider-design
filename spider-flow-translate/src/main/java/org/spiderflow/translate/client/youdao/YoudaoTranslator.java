package org.spiderflow.translate.client.youdao;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * @className: YoudaoTranslator
 * @description: TODO 类描述
 * @author: Dong
 * @date: 2022/6/7
 **/
@Configuration
@ConfigurationProperties(prefix = "translator.youdao")
@Slf4j
public class YoudaoTranslator {


  //  private static Logger logger = LoggerFactory.getLogger(YoudaoTranslator.class);

    private static final String YOUDAO_URL = "https://openapi.youdao.com/api";

    private String APP_KEY;

    public String getAPP_KEY() {
        return APP_KEY;
    }

    public void setAPP_KEY(String APP_KEY) {
        this.APP_KEY = APP_KEY;
    }

    public String getAPP_SECRET() {
        return APP_SECRET;
    }

    public void setAPP_SECRET(String APP_SECRET) {
        this.APP_SECRET = APP_SECRET;
    }

    private String APP_SECRET;

    public String translate(String text, String from, String to)  {
        Map<String, String> params = new HashMap<String, String>();
        String q = text;
        String salt = String.valueOf(System.currentTimeMillis());
        params.put("from", from);
        params.put("to", to);
        params.put("signType", "v3");
        String curtime = String.valueOf(System.currentTimeMillis() / 1000);
        params.put("curtime", curtime);
        String signStr = APP_KEY + truncate(q) + salt + curtime + APP_SECRET;
        String sign = getDigest(signStr);
        params.put("appKey", APP_KEY);
        params.put("q", q);
        params.put("salt", salt);
        params.put("sign", sign);
        params.put("vocabId", "您的用户词表ID");
        /** 处理结果 */
        String s = null;
        try {
             s = requestForHttp(YOUDAO_URL, params);
        } catch (IOException e) {

            log.error("有道云翻译出错,错误原因:{}",e.getMessage());
        }
        JSONObject jsonObject = JSON.parseObject(s);
        JSONArray translation = (JSONArray) jsonObject.get("translation");
        String o = (String) translation.get(0);
        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("targetText",o);
        return o;

    }

    public  String requestForHttp(String url, Map<String, String> params) throws IOException {

        /** 创建HttpClient */
        CloseableHttpClient httpClient = HttpClients.createDefault();

        /** httpPost */
        HttpPost httpPost = new HttpPost(url);
        List<NameValuePair> paramsList = new ArrayList<NameValuePair>();
        Iterator<Map.Entry<String, String>> it = params.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> en = it.next();
            String key = en.getKey();
            String value = en.getValue();
            paramsList.add(new BasicNameValuePair(key, value));
        }
        httpPost.setEntity(new UrlEncodedFormEntity(paramsList, "UTF-8"));
        CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
        try {
            Header[] contentType = httpResponse.getHeaders("Content-Type");
            //logger.info("Content-Type:" + contentType[0].getValue());
            if ("audio/mp3".equals(contentType[0].getValue())) {
                //如果响应是wav
                HttpEntity httpEntity = httpResponse.getEntity();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                httpResponse.getEntity().writeTo(baos);
                byte[] result = baos.toByteArray();
                EntityUtils.consume(httpEntity);
                if (result != null) {//合成成功
                    String file = "合成的音频存储路径" + System.currentTimeMillis() + ".mp3";
                    byte2File(result, file);
                }
            } else {
                /** 响应不是音频流，直接显示结果 */
                HttpEntity httpEntity = httpResponse.getEntity();
                String json = EntityUtils.toString(httpEntity, "UTF-8");
                EntityUtils.consume(httpEntity);
                return json;
            }
        } finally {
            try {
                if (httpResponse != null) {
                    httpResponse.close();
                }
            } catch (IOException e) {
             //   logger.info("## release resouce error ##" + e);
            }
        }
        return null;
    }

    /**
     * 生成加密字段
     */
    public  String getDigest(String string) {
        if (string == null) {
            return null;
        }
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        byte[] btInput = string.getBytes(StandardCharsets.UTF_8);
        try {
            MessageDigest mdInst = MessageDigest.getInstance("SHA-256");
            mdInst.update(btInput);
            byte[] md = mdInst.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (byte byte0 : md) {
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    /**
     * @param result 音频字节流
     * @param file   存储路径
     */
    private  void byte2File(byte[] result, String file) {
        File audioFile = new File(file);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(audioFile);
            fos.write(result);

        } catch (Exception e) {
         //   logger.info(e.toString());
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public String truncate(String q) {
        if (q == null) {
            return null;
        }
        int len = q.length();
        String result;
        return len <= 20 ? q : (q.substring(0, 10) + len + q.substring(len - 10, len));
    }
}

