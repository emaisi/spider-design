package org.spiderflow.elasticsearch.executor.function;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.elasticsearch.client.RestHighLevelClient;
import org.spiderflow.annotation.Comment;
import org.spiderflow.elasticsearch.model.ElasticSearchSource;
import org.spiderflow.elasticsearch.service.ElasticSearchSourceService;
import org.spiderflow.elasticsearch.utils.ElasticSearchUtils;
import org.spiderflow.executor.FunctionExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 蔡茂昌
 */
@Component
@Comment("ElasticSearch常用方法")
public class ElasticSearchFunctionExecutor extends HashMap<String, Object> implements FunctionExecutor {

    public final static Map<String, RestHighLevelClient> ELASTICSEARCH_CLIENTS = new HashMap<>();

    private static ElasticSearchSourceService elasticSearchSourceService;

    @Autowired
    public void setElasticSearchSourceService(ElasticSearchSourceService elasticSearchSourceService) {
        ElasticSearchFunctionExecutor.elasticSearchSourceService = elasticSearchSourceService;
    }

    @Override
    public String getFunctionPrefix() {
        return "elasticsearch";
    }

    private synchronized static RestHighLevelClient findElasticClient(String alias) {
        RestHighLevelClient client = ELASTICSEARCH_CLIENTS.get(alias);
        if (client == null) {
            ElasticSearchSource elasticSearchSource = elasticSearchSourceService.getOne(new QueryWrapper<ElasticSearchSource>().eq("alias", alias).orderByDesc("create_date"));
            if (elasticSearchSource == null) {
                throw new NullPointerException("找不到ElasticSearch数据源'" + alias + "'");
            }
            client = ElasticSearchUtils.createElasticSearchClient(elasticSearchSource);
            ELASTICSEARCH_CLIENTS.put(alias, client);
        }
        return client;
    }


    @Override
    public Object get(Object key){
        String alias = key == null ? null : key.toString();
        return findElasticClient(alias);
    }

}
