# spider-flow-minio

#### 介绍
spider-flow minio插件

#### 使用方法
导入db文件夹sp_minio.sql文件到数据库  
在 spider-flow-web pom.xml引用  

```
<dependency>
 	<groupId>org.spiderflow</groupId>
 	<artifactId>spider-flow-minio</artifactId>
 	<version>${spider-flow.version}</version>
</dependency>
```


#### 使用操作
 **例子在example文件夹**   
上传（支持四种方式，当前只实现前两种，后续再增加）：  
```
${minio.upload('http://abc.com/1.jpg','minio_id（配置的主键）')}  
${minio.upload(['http://abc.com/1.jpg','http://abc.com/2.jpg'],'minio_id（配置的主键）')}  
${resp.bytes.minioUpload('minio_id（配置的主键）','static/1.jpg')}  // TODO
${'http://1.jpg'.minioUpload('minio_id（配置的主键）','static/1.jpg')}  // TODO
```
返回值：  

```
url:文件http地址  
urlList:文件http地址的list
```

删除：  

```
TODO
```

