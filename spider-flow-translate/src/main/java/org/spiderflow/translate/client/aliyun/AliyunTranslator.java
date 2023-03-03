package org.spiderflow.translate.client.aliyun;

import com.aliyun.alimt20181012.models.TranslateGeneralRequest;
import com.aliyun.alimt20181012.models.TranslateGeneralResponse;
import lombok.extern.slf4j.Slf4j;
import org.spiderflow.translate.enums.FormatTypeEnum;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Configuration
@ConfigurationProperties(prefix = "translator.aliyun")
@Slf4j
public class AliyunTranslator {
    private String ACCESS_KEY_ID;
    private String ACCESS_KEY_SECRET;


    public String getACCESS_KEY_ID() {
        return ACCESS_KEY_ID;
    }

    public void setACCESS_KEY_ID(String ACCESS_KEY_ID) {
        this.ACCESS_KEY_ID = ACCESS_KEY_ID;
    }

    public String getACCESS_KEY_SECRET() {
        return ACCESS_KEY_SECRET;
    }

    public void setACCESS_KEY_SECRET(String ACCESS_KEY_SECRET) {
        this.ACCESS_KEY_SECRET = ACCESS_KEY_SECRET;
    }

    public String translate(String SourceText, String form, String to) {

        try {
            // 工程代码泄露可能会导致AccessKey泄露，并威胁账号下所有资源的安全性。以下代码示例仅供参考，建议使用更安全的 STS 方式，更多鉴权访问方式请参见：https://help.aliyun.com/document_detail/378657.html
//            com.aliyun.alimt20181012.Client client = AliyunTranslator.createClient(ACCESS_KEY_ID, ACCESS_KEY_SECRET);
            com.aliyun.alimt20181012.Client client = AliyunTranslator.createClient("LTAI5tRwF5afCn66q5uJgPQf", "VOXpdaU6ViBV2RIsis4i6MF8v7mKxy");
            TranslateGeneralRequest request = new TranslateGeneralRequest()
                    .setFormatType(FormatTypeEnum.HTML.getInfo())
                    .setSourceLanguage(form)
                    .setTargetLanguage(to)
                    .setScene("general")
                    .setSourceText(SourceText);
            TranslateGeneralResponse response = client.translateGeneral(request);
            return response.body.data.translated;


        } catch (Exception _error) {
            log.error("阿里云翻译出错,错误原因:{}",_error.getMessage());
            return _error.getMessage();
        }

    }


    /**
     * 使用AK&SK初始化账号Client
     *
     * @param accessKeyId
     * @param accessKeySecret
     * @return Client
     * @throws Exception
     */
    public static com.aliyun.alimt20181012.Client createClient(String accessKeyId, String accessKeySecret) throws Exception {
        com.aliyun.teaopenapi.models.Config config = new com.aliyun.teaopenapi.models.Config()
                // 必填，您的 AccessKey ID
                .setAccessKeyId(accessKeyId)
                // 必填，您的 AccessKey Secret
                .setAccessKeySecret(accessKeySecret);
        // 访问的域名
        config.endpoint = "mt.cn-hangzhou.aliyuncs.com";
        return new com.aliyun.alimt20181012.Client(config);
    }

    public static void main(String[] args_) throws Exception {
        AliyunTranslator aliyunTranslator = new AliyunTranslator();
        String fsfs = aliyunTranslator.translate("اعلامیهٔ\u200C\u200C معاونیت اقتصادی ریاست الوزرا\u200Cء ا.ا.ا در مورد گزارش اخیر بانک جهانی پیرامون وضعیت اقتصادی افغانستان!",
                "ps", "zh");
        System.out.println("fffff:" + fsfs);
    }
}
