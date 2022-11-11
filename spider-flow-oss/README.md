# spider-flow-oss

#### 介绍
spider-flow OSS插件

#### 使用方法
导入db文件夹sp_oss.sql文件到数据库  
在 spider-flow-web pom.xml引用  

```
<dependency>
 	<groupId>org.spiderflow</groupId>
 	<artifactId>spider-flow-oss</artifactId>
 	<version>${spider-flow.version}</version>
</dependency>
```


#### 使用操作
 **例子在example文件夹**   
上传（支持两种方式）：  
```
${resp.bytes.ossUpload('oss_id（配置的主键）','static/1.jpg')}  
${'http://1.jpg'.ossUpload('oss_id（配置的主键）','static/1.jpg')}  
```
返回值：  

```
name:文件名称  
path:文件夹路径  
size:文件大小（单位：b）  
url:文件http地址  
```

删除：  

```
${'static/1.jpg'.ossDelete()}
```

