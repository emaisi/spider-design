package org.spiderflow.mailbox.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.spiderflow.mailbox.mapper.MailMapper;
import org.spiderflow.mailbox.model.Mail;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Service
public class MailService extends ServiceImpl<MailMapper, Mail> {

    private Map<String,Mail> cachedMail = new HashMap<>();

    public Mail get(String id){
        synchronized (cachedMail){
            if(!cachedMail.containsKey(id)){
                cachedMail.put(id,getBaseMapper().selectById(id));
            }
            return cachedMail.get(id);
        }
    }

    @Override
    public boolean removeById(Serializable id) {
        synchronized (cachedMail){
            cachedMail.remove(id);
            return super.removeById(id);
        }
    }

    @Override
    public boolean saveOrUpdate(Mail entity) {
        if(entity.getId() != null){
            synchronized (cachedMail){
                cachedMail.remove("" +entity.getId());
                return super.saveOrUpdate(entity);
            }
        }
        return super.saveOrUpdate(entity);
    }
}
