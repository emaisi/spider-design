# spider-flow-translate

#### 介绍
spider-flow translate插件

#### 使用方法
在 spider-flow-web/pom.xml引用  

```
<dependency>
 	<groupId>org.spiderflow</groupId>
 	<artifactId>spider-flow-translate</artifactId>
 	<version>0.0.1</version>
</dependency>
```
配置好翻译的key，在spider-flow-web中的application.properties
支持五种配置，1:tencent，2:讯飞，3:百度，4:有道,5:阿里云
```
translator.tencent.secret-id=aaaaa
translator.tencent.secret-key=bbbb
translator.xfyun.APPID=
translator.xfyun.APISecret=
translator.xfyun.APIKey=
translator.baidu.APP_ID= 
translator.baidu.SECURITY_KEY= 
translator.youdao.APP_KEY=
translator.youdao.APP_SECRET=
translator.aliyun.ACCESS_KEY_ID= 
translator.aliyun.ACCESS_KEY_SECRET= 
translator.enable=3
```


#### 使用操作
使用如下方式进行调用
```
#直接从英文翻译中文，使用百度、腾讯、讯飞、有道、阿里
${fy.translateBd('Year s night.An aged man was standing at a window. He raised his mournful eyes towards the deep blue sky,')}  
${fy.translateTx('Year s night.An aged man was standing at a window. He raised his mournful eyes towards the deep blue sky,')}  
${fy.translateXf('Year s night.An aged man was standing at a window. He raised his mournful eyes towards the deep blue sky,')}  
${fy.translateYd('Year s night.An aged man was standing at a window. He raised his mournful eyes towards the deep blue sky,')}  
${fy.translateAli('Year s night.An aged man was standing at a window. He raised his mournful eyes towards the deep blue sky,')}  
```
```
#指定平台平台从中文翻译成英文。1:tencent，2:讯飞，3:百度，4:有道,5:阿里云
${fy.translate('Year s night.An aged man was standing at a window. He raised his mournful eyes towards the deep blue sky,',3)} 

```
```
#指定平台，翻译成指定目标语言。zh：中文；  1:tencent，2:讯飞，3:百度，4:有道,5:阿里云。
${translate('Year s night.An aged man was standing at a window. He raised his mournful eyes towards the deep blue sky,','zh',3)} 
```
```
#translate(String sourceText, String form, String to, Integer type)
#指定原语言，指定目标语言，指定平台翻译成文本。
${translate('Year s night.An aged man was standing at a window. He raised his mournful eyes towards the deep blue sky,','en','zh',3)} 
```

