package org.spiderflow.ocr.executor.shape;

import org.apache.commons.lang3.math.NumberUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spiderflow.ExpressionEngine;
import org.spiderflow.context.SpiderContext;
import org.spiderflow.executor.ShapeExecutor;
import org.spiderflow.model.Shape;
import org.spiderflow.model.SpiderNode;
import org.spiderflow.ocr.model.Ocr;
import org.spiderflow.ocr.service.OcrService;
import org.spiderflow.ocr.utils.OcrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class OcrOperationExecutor implements ShapeExecutor {

    public static final String OCR_ID = "ocrId";

    public static final String BYTES_OR_URL = "bytesOrUrl";

    public static final String VARIABLE_NAME = "variableName";

    private static Logger logger = LoggerFactory.getLogger(OcrOperationExecutor.class);

    @Autowired
    private ExpressionEngine engine;

    @Autowired
    private OcrService ocrService;

    @Override
    public String supportShape() {
        return "ocrOperation";
    }

    @Override
    public Shape shape() {
        Shape shape = new Shape();
        shape.setImage("data:image/png;base64,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");
        shape.setLabel("OCR识别");
        shape.setName("ocrOperation");
        shape.setTitle("OCR识别");
        return shape;
    }

    @Override
    public void execute(SpiderNode node, SpiderContext context, Map<String, Object> variables) {
        try {
            int ocrId = NumberUtils.toInt(node.getStringJsonValue(OCR_ID, "0"), 0);
            if (ocrId == 0) {
                logger.debug("请选择OCR！");
            } else {
                Ocr ocr = ocrService.get(ocrId + "");
                String bytesOrUrl = node.getStringJsonValue(BYTES_OR_URL);
                JSONObject jsonResult = null;
                if (bytesOrUrl.startsWith("http")) {
                    jsonResult = OcrUtil.ocrIdentify(ocr, bytesOrUrl);
                } else {
                    byte[] bytes = (byte[]) engine.execute(bytesOrUrl, variables);
                    jsonResult = OcrUtil.ocrIdentify(ocr, bytes);
                }
                if (jsonResult != null) {
                    if (jsonResult.isNull("words_result")) {
                        logger.error(jsonResult.toString());
                    } else {
                        JSONArray wordsResult = jsonResult.getJSONArray("words_result");
                        if (wordsResult != null) {
                            double average = 0;
                            String identifyResult = "";
                            for (int i = 0; i < wordsResult.length(); i++) {
                                JSONObject probability = wordsResult.getJSONObject(i).getJSONObject("probability");
                                if (probability.getDouble("average") > average) {
                                    average = probability.getDouble("average");
                                    identifyResult = wordsResult.getJSONObject(i).getString("words");
                                }
                            }
                            variables.put(node.getStringJsonValue(VARIABLE_NAME), identifyResult);
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error("ocr操作错误：{}", e);
        }
    }

}