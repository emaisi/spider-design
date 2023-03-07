package org.spiderdesign.ocr.controller;

import org.spiderdesign.common.CURDController;
import org.spiderdesign.executor.PluginConfig;
import org.spiderdesign.model.Plugin;
import org.spiderdesign.ocr.mapper.OcrMapper;
import org.spiderdesign.ocr.model.Ocr;
import org.spiderdesign.ocr.service.OcrService;
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
