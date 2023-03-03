package org.spiderflow.translate.client;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.spiderflow.translate.client.aliyun.AliyunTranslator;
import org.spiderflow.translate.client.baidu.BaiduTranslator;
import org.spiderflow.translate.client.tencent.TencentTranslator;
import org.spiderflow.translate.client.xunfei.XfyunTranslator;
import org.spiderflow.translate.client.youdao.YoudaoTranslator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @className: Translator
 * @description: TODO 类描述
 * @author: Dong
 * @date: 2022/6/6
 **/
@Component
@Slf4j
public class Translator {

    static String[][] strings = {
            //我们的标准
            {"auto", "zh",   "en", "yue", "wyw", "ja", "ko", "fr", "es", "th", "ar", "ru",
                    "pt", "de", "it", "el", "nl", "pl", "bg", "et", "da", "fi", "cs", "ro", "sl", "sv", "hu", "zh-TW",
                    "vi", "af", "bn", "fa", "gu", "he", "hi", "hr", "id", "km", "lv", "mk", "mr", "ms", "no", "pa", "sl", "ta", "te", "tl", "tr", "uk", "ur"
            },
            //tencent
            {"auto", "zh", "en", "null", "null", "ja", "ko", "fr", "es", "th", "ar", "ru",
                    "pt", "de", "it", "null", "null", "null", "null", "null", "null", "null", "null", "null", "null", "null", "null", "zh-TW",
                    "vi", "null", "null", "null", "null", "null", "hi", "null", "id", "null", "null", "null", "null", "ms", "null", "null", "null", "null", "null", "null", "tr", "null", "null"

            },
            //xfyun
            {"null", "cn", "en", "null", "null", "ja", "ko", "fr", "es", "th", "ar", "ru",
                    "pt", "de", "it", "el", "nl", "pl", "bg", "null", "null", "fi", "cs", "ro", "null", "sv", "hu", "null",
                    "vi", "null", "bn", "fa", "null", "null", "hi", "null", "id", "null", "null", "null", "null", "ms", "null", "null", "null", "null", "null", "tl", "tr", "uk", "ur"
            },
            //baidu
            {"auto", "zh", "en", "yue", "wyw", "jp", "kor", "fra", "spa", "th", "ara", "ru",
                    "pt", "de", "it", "el", "nl", "pl", "bul", "est", "dan", "fin", "cs", "rom", "slo", "swe", "hu", "cht",
                    "vie", "null", "null", "null", "null", "null", "null", "null", "null", "null", "null", "null", "null", "null", "null", "null", "slo", "null", "null", "null", "null", "null", "null"
            },
            //youdao
            {"auto", "zh-CHS", "en", "yue", "null", "ja", "ko", "fr", "es", "th", "ar", "ru",
                    "pt", "de", "it", "el", "nl", "pl", "bg", "et", "da", "fi", "cs", "ro", "sl", "sv", "hu", "zh-CHT",
                    "vi", "af", "bn", "fa", "gu", "he", "hi", "hr", "id", "km", "lv", "mk", "mr", "ms", "no", "pa", "sl", "ta", "te", "tl", "tr", "uk", "ur"
            },
            //aliyun https://help.aliyun.com/document_detail/215387.html?spm=a2c4g.11186623.0.0.16262e50335mU1
            { "auto","zh", "en", "yue", "null", "ja", "ko", "fr", "es", "th", "ar", "ru",
                    "pt", "de", "it", "el", "nl", "pl", "bg", "et", "da", "fi", "cs", "ro", "sl", "sv", "hu", "zh-CHT",
                    "vi", "af", "bn", "fa", "gu", "he", "hi", "hr", "id", "km", "lv", "mk", "mr", "ms", "no", "pa", "sl", "ta", "te", "tl", "tr", "uk", "ur"
            }
//            //中国名称
//            { "自动","中文", "英文", "粤语", "null", "ja", "ko", "fr", "es", "th", "ar", "ru",
//                    "pt", "de", "it", "el", "nl", "pl", "bg", "et", "da", "fi", "cs", "ro", "sl", "sv", "hu", "zh-CHT",
//                    "vi", "af", "bn", "fa", "gu", "he", "hi", "hr", "id", "km", "lv", "mk", "mr", "ms", "no", "pa", "sl", "ta", "te", "tl", "tr", "uk", "ur"
//            }
    };


    public static void main(String[] args) {
        int a = 0, b=0, c=0, d=0, e=0;
        for (String s : strings[1]) {
          if (s.equals("null"))  {
                a++;
            }
        }
        for (String s : strings[2]) {
            if (s.equals("null"))  {
                b++;
            }
        }
        for (String s : strings[3]) {
            if (s.equals("null"))  {
                c++;
            }
        }
        for (String s : strings[4]) {
            if (s.equals("null"))  {
                d++;
            }
        }
        for (String s : strings[5]) {
            if (s.equals("null"))  {
                e++;
            }
        }
        System.out.println("一个支持："+strings[0].length);
        System.out.println("腾讯为null个数"+a);
        System.out.println("讯飞为null个数"+b);
        System.out.println("百度为null个数"+c);
        System.out.println("有道为null个数"+d);
        System.out.println("阿里云为null个数"+e);
    }

    @Autowired
    private TencentTranslator tencentTranslator; //1
    @Autowired
    private XfyunTranslator xfyunTranslator; //2
    @Autowired
    private BaiduTranslator baiduTranslator; //3
    @Autowired
    private YoudaoTranslator youdaoTranslator; //4
    @Autowired
    private AliyunTranslator aliyunTranslator; //5


    @Value("${translator.enable}")
    private Integer defaultType;

    public String translate(String sourceText, String form, String to) {
        return  translate(sourceText,form,to,defaultType);
    }
    public String translate(String sourceText, String form, String to, Integer type) {
        if(type==null){
            log.error("未配置使用的翻译平台");
            return "未配置使用的翻译平台";
        }
        if(StrUtil.isEmpty(sourceText)){
            return sourceText;
        }
        int formIndex = 0;
        int toIndex = 0;
        for (int i = 0; i < strings[0].length; i++) {
            if (form.equals(strings[0][i])) {
                formIndex = i;
                break;
            }
        }
        for (int i = 0; i < strings[0].length; i++) {
            if (to.equals(strings[0][i])) {
                toIndex = i;
                break;
            }
        }
        String re = null;
        switch (type) {
            case 1:
                form = strings[1][formIndex];
                to = strings[1][toIndex];
                if (form.equals("null") || to.equals("null")) {
                    break;
                }
                re = tencentTranslator.translate(sourceText, form, to);
                break;
            case 2:
                form = strings[2][formIndex];
                to = strings[2][toIndex];
                if (form.equals("null") || to.equals("null")) {
                    break;
                }
                re = xfyunTranslator.translate(sourceText, form, to);
                break;
            case 3:
                form = strings[3][formIndex];
                to = strings[3][toIndex];
                if (form.equals("null") || to.equals("null")) {
                    break;
                }
                re = baiduTranslator.translate(sourceText, form, to);
                break;
            case 4:
                form = strings[4][formIndex];
                to = strings[4][toIndex];
                if (form.equals("null") || to.equals("null")) {
                    break;
                }
                re = youdaoTranslator.translate(sourceText, form, to);
                break;
            case 5:
                form = strings[5][formIndex];
                to = strings[5][toIndex];
                if (form.equals("null") || to.equals("null")) {
                    break;
                }
                re = aliyunTranslator.translate(sourceText, form, to);
                break;
        }
        return re;
    }

}
