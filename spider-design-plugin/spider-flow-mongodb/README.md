# spider-flow-mongodb

#### 介绍
spider-flow mongodb插件

#### 使用方法
导入db文件夹sp_mongo.sql文件到数据库  
在 spider-flow-web/pom.xml引用

```
<dependency>
 	<groupId>org.spiderflow</groupId>
 	<artifactId>spider-flow-mongodb</artifactId>
</dependency>
```


### 定义Mongodb数据源

- host：mongodb host/ip
- alias(别名)：特别重要，后续使用时都会使用到别名
- port：mongodb端口号
- 用户名/密码：mongodb用户名密码，没有不填即可
- 数据库名称，mongodb 数据库名

### 开始使用

> TIP
>
> 可以在所有支持表达式的地方使用

```javascript
//其中xxxx是之前配置的别名
//插入数据
${mongodb.xxxx.collectionName.insert([{key : value},{key : value}])}
${mongodb.xxxx.collectionName.insert({key : value})}
//查找数据
${mongodb.xxxx.collectionName.find({key : value}).skip(2).limit(3).list()}
//修改数据
${mongodb.xxxx.collectionName.update({key : oldValue},{key : newValue})}
${mongodb.xxxx.collectionName.updateMany({key : oldValue},{key : newValue})}
//查询总数
${mongodb.xxxx.collectionName.count({key : value})}
${mongodb.xxxx.collectionName.count()}
//删除数据
${mongodb.xxxx.collectionName.remove({key : value})}
${mongodb.xxxx.collectionName.removeOne({key : value})}
```
