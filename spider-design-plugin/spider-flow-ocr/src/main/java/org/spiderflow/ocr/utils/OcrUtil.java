package org.spiderflow.ocr.utils;

import com.baidu.aip.ocr.AipOcr;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.spiderflow.ocr.model.Ocr;

import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class OcrUtil {

    private static HashMap<String, AipOcr> aipOcrMap = new HashMap<String, AipOcr>();

    private static HashMap<String, String> options = new HashMap<String, String>();

    static {
        options.put("detect_direction", "true");
        options.put("detect_language", "true");
        options.put("probability", "true");
    }

    public static AipOcr getAipOcr(Ocr ocr) {
        String ocrMapKey = ocr.getAppId() + ocr.getApiKey() + ocr.getSecretKey();
        AipOcr aipOcr = aipOcrMap.get(ocrMapKey);
        if (aipOcr == null) {
            aipOcr = new AipOcr(ocr.getAppId(), ocr.getApiKey(), ocr.getSecretKey());
            aipOcrMap.put(ocrMapKey, aipOcr);
        }
        return aipOcr;
    }

    public static int getErrorCode(JSONObject bgJson) {
        if (!bgJson.isNull("error_code")) {
            int errorCode = bgJson.getInt("error_code");
            return errorCode;
        }
        return -996;
    }

    public static boolean isImageFormatError(int errorCode) {
        if (errorCode != -996) {
            // 图片格式不对
            if (errorCode == ErrorCode.IMAGE_FORMAT_ERROR) {
                return true;
            }
        }
        return false;
    }

    public static boolean isUrlResponseInvalid(int errorCode) {
        if (errorCode != -996) {
            if (errorCode == ErrorCode.URL_RESPONSE_INVALID) {
                return true;
            }
        }
        return false;
    }

    public static byte[] imgConvert(byte[] bytes) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        ByteArrayInputStream input = new ByteArrayInputStream(bytes);
        ImageIO.write(ImageIO.read(input), "png", output);
        return output.toByteArray();
    }

    public static JSONObject ocrIdentify(Ocr ocr, byte[] bytes) throws Exception {
        try {
            AipOcr aipOcr = getAipOcr(ocr);
            JSONObject bgJson = aipOcr.basicGeneral(bytes, options);
            if (isImageFormatError(getErrorCode(bgJson))) {
                return aipOcr.basicGeneral(imgConvert(bytes), options);
            }
            return bgJson;
        } catch (Exception e) {
            throw e;
        }
    }

    public static JSONObject ocrIdentify(Ocr ocr, String url) throws IOException {
        AipOcr aipOcr = getAipOcr(ocr);
        JSONObject bgJson = aipOcr.basicGeneralUrl(url, options);
        if (isUrlResponseInvalid(getErrorCode(bgJson))) {
            return aipOcr.basicGeneral(imgConvert(Jsoup.connect(url).ignoreContentType(true).execute().bodyAsBytes()), options);
        }
        return bgJson;
    }

    private static class ErrorCode {

        /**
         * 图片格式不对
         */
        private static final int IMAGE_FORMAT_ERROR = 216201;

        /**
         * 响应无效
         */
        private static final int URL_RESPONSE_INVALID = 282113;

    }

}
