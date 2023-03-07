package org.spiderflow.elasticsearch.executor.function.extension;


import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.admin.indices.analyze.AnalyzeRequest;
import org.elasticsearch.action.admin.indices.analyze.AnalyzeResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.spiderflow.annotation.Comment;
import org.spiderflow.annotation.Example;
import org.spiderflow.executor.FunctionExtension;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author 蔡茂昌
 */
@Component
public class ElasticSearchClientExtension implements FunctionExtension {
    @Override
    public Class<?> support() {
        return RestHighLevelClient.class;
    }

    @Comment("elasticsearch批量插入数据")
    @Example("${elasticsearch.aliasName.insertByJSONString('index','type',['{key : value}','{key : value}'])}")
    public static BulkResponse insertByJSONString(RestHighLevelClient client, String index, String type, List<String> jsonList) {
        BulkRequest bulkRequest = new BulkRequest();
        for (String json : jsonList) {
            IndexRequest indexRequest = new IndexRequest(index, type);
            indexRequest.source(json, XContentType.JSON);
            bulkRequest.add(indexRequest);
        }
        try {
            return client.bulk(bulkRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Comment("elasticsearch批量插入数据")
    @Example("${elasticsearch.aliasName.insertByListMap('index','type',[{key : value},{key : value}])}")
    public static BulkResponse insertByListMap(RestHighLevelClient client, String index, String type, List<Map<String, Object>> listMap) {
        BulkRequest bulkRequest = new BulkRequest();
        for (Map<String, Object> map : listMap) {
            map = timestampFilter(map);
            IndexRequest indexRequest = new IndexRequest(index, type);
            indexRequest.source(map);
            bulkRequest.add(indexRequest);
        }
        try {
            return client.bulk(bulkRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Comment("elasticsearch批量插入数据(指定id)")
    @Example("${elasticsearch.aliasName.insertByListMap('index','type',[{key : value},{key : value}],id_name)}")
    public static BulkResponse insertByListMap(RestHighLevelClient client, String index, String type, List<Map<String, Object>> listMap,String idName) {
        BulkRequest bulkRequest = new BulkRequest();
        for (Map<String, Object> map : listMap) {
            map = timestampFilter(map);
            IndexRequest indexRequest = new IndexRequest(index, type,map.get(idName).toString());
            indexRequest.source(map);
            bulkRequest.add(indexRequest);
        }
        try {
            return client.bulk(bulkRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static Map<String,Object> timestampFilter(Map<String,Object> map){
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if(entry.getValue() instanceof Timestamp){
                String timestampSting = entry.getValue().toString();
                map.put(entry.getKey(),timestampSting.substring(0,timestampSting.length()-2));
            }
        }
        return map;
    }

    @Comment("elasticsearch插入数据")
    @Example("${elasticsearch.aliasName.insert('index','type',{key : value})}")
    public static DocWriteResponse insert(RestHighLevelClient client, String index, String type, Map<String, Object> map) {
        IndexRequest indexRequest = new IndexRequest(index, type);
        map = timestampFilter(map);
        indexRequest.source(map);
        try {
            return client.index(indexRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Comment("elasticsearch插入数据(指定id)")
    @Example("${elasticsearch.aliasName.insert('index','type',{key : value},id_name)}")
    public static DocWriteResponse insert(RestHighLevelClient client, String index, String type, Map<String, Object> map,String id_name) {
        IndexRequest indexRequest = new IndexRequest(index, type,map.get(id_name).toString());
        map = timestampFilter(map);
        indexRequest.source(map);
        try {
            return client.index(indexRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Comment("elasticsearch删除数据")
    @Example("${elasticsearch.aliasName.delete('index','type','id')}")
    public static DocWriteResponse delete(RestHighLevelClient client, String index, String type, String id) {
        DeleteRequest deleteRequest = new DeleteRequest(index, type, id);
        try {
            return client.delete(deleteRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Comment("elasticsearch删除数据")
    @Example("${elasticsearch.aliasName.delete('index','type',id)}")
    public static DocWriteResponse delete(RestHighLevelClient client, String index, String type, Integer id) {
        DeleteRequest deleteRequest = new DeleteRequest(index, type, id.toString());
        try {
            return client.delete(deleteRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Comment("elasticsearch文档是否存在")
    @Example("${elasticsearch.aliasName.exists('index','type','id')}")
    public static boolean exists(RestHighLevelClient client, String index, String type, String id) {
        GetRequest getRequest = new GetRequest(index, type, id);
        getRequest.fetchSourceContext(new FetchSourceContext(false));
        getRequest.storedFields("_none_");
        try {
            return client.exists(getRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Comment("elasticsearch文档是否存在")
    @Example("${elasticsearch.aliasName.exists('index','type',id)}")
    public static boolean exists(RestHighLevelClient client, String index, String type, Integer id) {
        GetRequest getRequest = new GetRequest(index, type, id.toString());
        getRequest.fetchSourceContext(new FetchSourceContext(false));
        getRequest.storedFields("_none_");
        try {
            return client.exists(getRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Comment("elasticsearch根据id获取文档")
    @Example("${elasticsearch.aliasName.get('index','type','id')}")
    public static Map<String, Object> get(RestHighLevelClient client, String index, String type, String id) {
        GetRequest getRequest = new GetRequest(index, type, id);
        try {
            GetResponse getResponse = client.get(getRequest, RequestOptions.DEFAULT);
            if (getResponse.isExists()) {
                return getResponse.getSourceAsMap();
            } else {
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Comment("elasticsearch根据id获取文档")
    @Example("${elasticsearch.aliasName.get('index','type',id)}")
    public static Map<String, Object> get(RestHighLevelClient client, String index, String type,Integer id) {
        GetRequest getRequest = new GetRequest(index, type, id.toString());
        try {
            GetResponse getResponse = client.get(getRequest, RequestOptions.DEFAULT);
            if (getResponse.isExists()) {
                return getResponse.getSourceAsMap();
            } else {
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Comment("elasticsearch关键词提取(中文)")
    @Example("${elasticsearch.aliasName.analyzer('str')}")
    public static List<String> analyzer(RestHighLevelClient client, String str) {
        AnalyzeRequest request = new AnalyzeRequest();
        request.analyzer("ik_max_word");
        request.text(str);
        return analyze(client, request);
    }

    @Comment("elasticsearch关键词提取")
    @Example("${elasticsearch.aliasName.analyzer('str','analyzer')}")
    public static List<String> analyzer(RestHighLevelClient client, String str, String analyzer) {
        AnalyzeRequest request = new AnalyzeRequest();
        request.text(str);
        request.analyzer(analyzer);
        return analyze(client, request);
    }

    private static List<String> analyze(RestHighLevelClient client, AnalyzeRequest request) {
        List<String> words = new ArrayList<>();
        try {
            AnalyzeResponse analyze = client.indices().analyze(request, RequestOptions.DEFAULT);
            List<AnalyzeResponse.AnalyzeToken> tokens = analyze.getTokens();
            for (AnalyzeResponse.AnalyzeToken token : tokens) {
                words.add(token.getTerm());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return words;
    }

}
