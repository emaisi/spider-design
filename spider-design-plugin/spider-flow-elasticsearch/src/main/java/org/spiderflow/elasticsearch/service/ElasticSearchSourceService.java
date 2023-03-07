package org.spiderflow.elasticsearch.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.elasticsearch.client.RestHighLevelClient;
import org.spiderflow.elasticsearch.executor.function.ElasticSearchFunctionExecutor;
import org.spiderflow.elasticsearch.mapper.ElasticSearchMapper;
import org.spiderflow.elasticsearch.model.ElasticSearchSource;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * @author 蔡茂昌
 */
@Service
public class ElasticSearchSourceService extends ServiceImpl<ElasticSearchMapper, ElasticSearchSource> {
    @Override
    public boolean saveOrUpdate(ElasticSearchSource entity) {
        if (entity.getId() != null) {
            removeOldElasticSearchSource(entity.getId());
        }
        return super.saveOrUpdate(entity);
    }

    @Override
    public boolean removeById(Serializable id) {
        removeOldElasticSearchSource(id);
        return super.removeById(id);
    }

    private void removeOldElasticSearchSource(Serializable id) {
        ElasticSearchSource oldSource = getById(id);
        if (oldSource != null) {
            RestHighLevelClient client = ElasticSearchFunctionExecutor.ELASTICSEARCH_CLIENTS.get(oldSource.getAlias());
            if (client != null) {
                try {
                    client.close();
                } catch (Exception e) {
                }
            }
            ElasticSearchFunctionExecutor.ELASTICSEARCH_CLIENTS.remove(oldSource.getAlias());
        }
    }
}
