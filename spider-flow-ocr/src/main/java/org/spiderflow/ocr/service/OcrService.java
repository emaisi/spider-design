package org.spiderflow.ocr.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.helper.StringUtil;
import org.spiderflow.ocr.mapper.OcrMapper;
import org.spiderflow.ocr.model.Ocr;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Service
public class OcrService extends ServiceImpl<OcrMapper, Ocr> {

    private Map<String,Ocr> cachedOcr = new HashMap<>();

    public Ocr get(String id){
        synchronized (cachedOcr){
            if(!cachedOcr.containsKey(id)){
                cachedOcr.put(id,getBaseMapper().selectById(id));
            }
            return cachedOcr.get(id);
        }
    }

    @Override
    public boolean removeById(Serializable id) {
        synchronized (cachedOcr){
            cachedOcr.remove(id);
            return super.removeById(id);
        }
    }

    @Override
    public boolean saveOrUpdate(Ocr entity) {
        if(entity.getId() != null){
            synchronized (cachedOcr){
                cachedOcr.remove("" + entity.getId());
                return super.saveOrUpdate(entity);
            }
        }
        return super.saveOrUpdate(entity);
    }
}
