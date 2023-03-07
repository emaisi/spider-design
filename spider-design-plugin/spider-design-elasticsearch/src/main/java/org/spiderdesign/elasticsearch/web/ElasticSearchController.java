package org.spiderdesign.elasticsearch.web;

import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.spiderdesign.common.CURDController;
import org.spiderdesign.elasticsearch.mapper.ElasticSearchMapper;
import org.spiderdesign.elasticsearch.model.ElasticSearchSource;
import org.spiderdesign.elasticsearch.service.ElasticSearchSourceService;
import org.spiderdesign.elasticsearch.utils.ElasticSearchUtils;
import org.spiderdesign.executor.PluginConfig;
import org.spiderdesign.model.JsonBean;
import org.spiderdesign.model.Plugin;
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
