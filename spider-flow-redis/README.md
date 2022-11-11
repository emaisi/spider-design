# spider-flow-redis

#### 介绍
spider-flow Redis插件

#### 使用方法
导入db文件夹sp_redis.sql文件到数据库  
在 spider-flow-web/pom.xml引用  

```
<dependency>
 	<groupId>org.spiderflow</groupId>
 	<artifactId>spider-flow-redis</artifactId>
 	<version>0.0.1</version>
</dependency>
```


#### 使用操作
首先先创建Redis数据源
使用如下方式进行调用
```
${redis.use('aliasName（创建Redis数据源时填写的别名）').set('key','value')}  
```
```
${redis.aliasName（创建Redis数据源时填写的别名）.set('key','value')} 
```
```
${redis.aliasName（创建Redis数据源时填写的别名）.get('key')} 
```
```
${redis.aliasName（创建Redis数据源时填写的别名）.setex('key',10,'value')} 
```

