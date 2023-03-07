# spider-flow-elasticsearch

#### 介绍
spider-flow Elasticsearch插件

#### 使用方法
导入db文件夹sp_elasticsearch.sql文件到数据库  
在 spider-flow-web/pom.xml引用  

```
<dependency>
 	<groupId>org.spiderflow</groupId>
 	<artifactId>spider-flow-elasticsearch</artifactId>
</dependency>
```

在 spider-flow/pom.xml引用
```
<dependency>
    <groupId>org.spiderflow</groupId>
    <artifactId>spider-flow-elasticsearch</artifactId>
    <version>${spider-flow.version}</version>
</dependency>
```


在 spider-flow/pom.xml，防止环境引用springboot自动传入的依赖，version版本要与使用的ES版本一致(或者用其他排除自动配置的方法例如在application-*.properties中配置spring.autoconfigure.exclude)
``` 
<dependency>
    <groupId>org.elasticsearch</groupId>
    <artifactId>elasticsearch</artifactId>
    <version>6.8.13</version>
</dependency>
```
注意spider-flow-elasticsearch/pom.xml中依赖的版本与ES版本一致


#### 使用操作
首先先创建Elasticsearch数据源
使用如下方式进行调用
```
${elasticsearch.aliasName（创建Elasticsearch数据源时填写的别名）.insertByListMap('对应es的index名','对应es的type',[{key : value},{key : value}])}
```
或插入json格式的字符串数组批量插入
```
${elasticsearch.aliasName（创建Elasticsearch数据源时填写的别名）.insertByJSONString('index','type',['{key : value}','{key : value}'])}
```
更多方法请查看ElasticSearchClientExtension源码

