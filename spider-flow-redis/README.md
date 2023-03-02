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
</dependency>
```

### 定义Redis数据源
- host：redis host/ip
- alias(别名)：特别重要，后续使用时都会使用到别名
- port：redis端口号
- 密码：redis密码，没有不填即可
- 数据库索引，默认为0
- 最大连接数，默认为8
- 最大空闲连接数，默认为8
- 最小空闲连接数，默认为0


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

### 开始使用

> TIP
>
> 可以在所有支持表达式的地方使用

```javascript
//其中xxxx是之前配置的别名
//调用redis set命令
${redis.xxxx.set('key','value')}
//调用redis get命令
${redis.xxxx.get('key')}
//调用redis setex命令
${redis.xxxx.setex('key',10,value)}
```
