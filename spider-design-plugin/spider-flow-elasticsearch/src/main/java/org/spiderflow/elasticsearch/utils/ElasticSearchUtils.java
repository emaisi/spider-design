package org.spiderflow.elasticsearch.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.spiderflow.elasticsearch.model.ElasticSearchSource;


/**
 * @author 蔡茂昌
 */
public class ElasticSearchUtils {

    public static RestHighLevelClient createElasticSearchClient(ElasticSearchSource elasticSearchSource) {

        RestClientBuilder restClientBuilder = RestClient.builder(new HttpHost(elasticSearchSource.getHost(), elasticSearchSource.getPort(), "http"));

        if (StringUtils.isNotEmpty(elasticSearchSource.getUsername()) && StringUtils.isNotEmpty(elasticSearchSource.getPassword())) {
            final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
            credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(elasticSearchSource.getUsername(), elasticSearchSource.getPassword()));
            restClientBuilder.setHttpClientConfigCallback(httpClientBuilder -> {
                httpClientBuilder.disableAuthCaching();
                return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
            });
        }

        return new RestHighLevelClient(restClientBuilder);
    }
}
