package org.spiderflow.elasticsearch.web;

import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.spiderflow.common.CURDController;
import org.spiderflow.elasticsearch.mapper.ElasticSearchMapper;
import org.spiderflow.elasticsearch.model.ElasticSearchSource;
import org.spiderflow.elasticsearch.service.ElasticSearchSourceService;
import org.spiderflow.elasticsearch.utils.ElasticSearchUtils;
import org.spiderflow.executor.PluginConfig;
import org.spiderflow.model.JsonBean;
import org.spiderflow.model.Plugin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 蔡茂昌
 */
@RestController
@RequestMapping("/elasticsearch")
public class ElasticSearchController extends CURDController<ElasticSearchSourceService, ElasticSearchMapper,ElasticSearchSource> implements PluginConfig {

    @RequestMapping("/test")
    public JsonBean<String> test(ElasticSearchSource elasticSearchSource){
        try(RestHighLevelClient client = ElasticSearchUtils.createElasticSearchClient(elasticSearchSource)){
            client.ping(RequestOptions.DEFAULT);
            return new JsonBean<String>(1, "测试成功");
        } catch (Exception e) {
            return new JsonBean<String>(0, e.getMessage());
        }
    }

    @Override
    public Plugin plugin() {
        return new Plugin("ElasticSearch数据源", "elasticsearch.html");
    }
}
