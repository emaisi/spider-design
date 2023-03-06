# spider-flow-ocr

#### 介绍
spider-flow proxypool ip代理池插件

#### 使用方法
导入db文件夹sp_proxy.sql文件到数据库  
在 spider-flow-web pom.xml引用

```
<dependency>
 	<groupId>org.spiderflow</groupId>
 	<artifactId>spider-flow-proxypool</artifactId>
</dependency>
```


### 方法

### proxypool.add

|       参数名       |       描述       | 可否为空 |
| :----------------: | :--------------: | :------: |
|     ip(String)     |      ip地址      |    否    |
|   port(Integer)    |      端口号      |    否    |
|    type(String)    | 类型(http,https) |    否    |
| anonymous(boolean) |    是否是高匿    |    否    |

### proxypool.http

- 随机获取一个http代理

|       参数名       |       描述       | 可否为空 |
| :----------------: | :--------------: | :------: |
| anonymous(boolean) | 是否获取高匿代理 |    是    |

TIP

返回值类型：String ip:port

### proxypool.https

- 随机获取一个https代理

|       参数名       |       描述       | 可否为空 |
| :----------------: | :--------------: | :------: |
| anonymous(boolean) | 是否获取高匿代理 |    是    |

TIP

返回值类型：String ip:port
