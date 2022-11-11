package org.spiderflow.ocr.controller;

import org.spiderflow.common.CURDController;
import org.spiderflow.executor.PluginConfig;
import org.spiderflow.model.Plugin;
import org.spiderflow.ocr.mapper.OcrMapper;
import org.spiderflow.ocr.model.Ocr;
import org.spiderflow.ocr.service.OcrService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("ocr")
public class OcrController extends CURDController<OcrService, OcrMapper, Ocr> implements PluginConfig {
    @Override
    public Plugin plugin() {
        return new Plugin("OCR配置", "resources/ocrList.html");
    }
}
