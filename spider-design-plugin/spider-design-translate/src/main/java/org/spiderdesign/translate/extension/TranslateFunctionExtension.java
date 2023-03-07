package org.spiderdesign.translate.extension;

import org.spiderdesign.executor.FunctionExecutor;
import org.spiderdesign.translate.client.Translator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TranslateFunctionExtension implements FunctionExecutor {

    private static Translator translator;
    //原语言，设置自动
    private static String sourceLanguage = "auto";
    //目标语言，设置中文
    private static String desc = "zh";

    @Autowired
    public void setRedisSourceService(Translator translator) {
        TranslateFunctionExtension.translator = translator;
    }

    @Override
    public String getFunctionPrefix() {
        return "fy";    //方法前缀,调用本类的方法时均以${fy.xxx()}方法调用
    }

    /**
     * 翻译
     *
     * @param sourceText 需要翻译的原文本
     * @param form       原语言
     * @param to         目标翻译
     * @param type       1:tencent，2:讯飞，3:百度，4:有道,5:阿里云
     * @return
     */
    public static String translateByPlatform(String sourceText, String form, String to, Integer type) {
        return translator.translate(sourceText, form, to, type);
    }
    /**
     * 翻译,根据默认设置的平台
     *
     * @param sourceText 需要翻译的原文本
     * @param form       原语言
     * @param to         目标翻译
     * @return
     */
    public static String translate(String sourceText, String form, String to) {
        return translator.translate(sourceText, form, to);
    }

    /**
     * 原语言自动识别
     *
     * @param sourceText 需要翻译的原文本
     * @param to         目标翻译
     * @param type       1:tencent，2:讯飞，3:百度，4:有道,5:阿里云
     * @return
     */
    public static String translateByPlatform(String sourceText, String to, Integer type) {
        return translator.translate(sourceText, sourceLanguage, to, type);
    }
    /**
     * 原语言自动识别
     *
     * @param sourceText 需要翻译的原文本
     * @param to         目标翻译
     * @return
     */
    public static String translate(String sourceText, String to) {
        return translator.translate(sourceText, sourceLanguage, to);
    }

    /**
     * 原语言自动识别，目标语言设置为中文
     *
     * @param sourceText 需要翻译的原文本
     * @param type       1:tencent，2:讯飞，3:百度，4:有道,5:阿里云
     * @return
     */
    public static String translateByPlatform(String sourceText, Integer type) {
        return translator.translate(sourceText, sourceLanguage, desc, type);
    }
    /**
     * 原语言自动识别，目标语言设置为中文
     *
     * @param sourceText 需要翻译的原文本
     * @return
     */
    public static String translate(String sourceText) {
        return translator.translate(sourceText, sourceLanguage, desc);
    }



}
