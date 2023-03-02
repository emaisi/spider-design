2023-03-02
- 新增URL函数${url.completeUrl(strVar,'./a.html')}
- 新增string函数${string.defaultIfEmpty(str1,str2)}
- 引用hutool工具包
- 翻译新增使用默认平台功能。
- 把commons-io、commons-codec工具包替换为hutool工具包
- 升级mongodb到3.12.12
- 升级springboot版本，从2.0.7.RELEASE升级到2.7.9
- 升级mybatis.plus到3.5.3.1
- 升级guava到31.1-jre
- 升级连接池druid到1.2.16
- 升级fastjson到1.12.83
- 升级transmittable到2.14.2
- 升级selenium版本到4.8.1

2023-03-01
- 升级为0.5.1版本
- 新增spider-flow-translate翻译插件


2022-11-11
- 升级mysql驱动版本为8.0.31

- 整合插件

[翻译插件](https://gitee.com/emaisi/spider-design/tree/master/spider-flow-translate)

[minio插件](https://gitee.com/emaisi/spider-design/tree/master/spider-flow-minio)
 
[Selenium插件](https://gitee.com/emaisi/spider-design/tree/master/spider-flow-selenium)
 
[Redis插件](https://gitee.com/emaisi/spider-design/tree/master/spider-flow-redis)
 
[OSS插件](https://gitee.com/emaisi/spider-design/tree/master/spider-flow-oss)

[Mongodb插件](https://gitee.com/emaisi/spider-design/tree/master/spider-flow-mongodb)
 
[IP代理池插件](https://gitee.com/emaisi/spider-design/tree/master/spider-flow-proxypool)
 
[OCR识别插件](https://gitee.com/emaisi/spider-design/tree/master/spider-flow-ocr)
 
[电子邮箱插件](https://gitee.com/emaisi/spider-design/tree/master/spider-flow-mailbox)



<p align="center">
    <img src="https://www.spiderflow.org/images/logo.svg" width="600">
</p>
<p align="center">
    <a target="_blank" href="https://www.oracle.com/technetwork/java/javase/downloads/index.html"><img src="https://img.shields.io/badge/JDK-1.8+-green.svg" /></a>
    <a target="_blank" href="https://www.spiderflow.org"><img src="https://img.shields.io/badge/Docs-latest-blue.svg"/></a>
    <a target="_blank" href="https://github.com/ssssssss-team/spider-flow/releases"><img src="https://img.shields.io/github/v/release/ssssssss-team/spider-flow?logo=github"></a>
    <a target="_blank" href='https://gitee.com/ssssssss-team/spider-flow'><img src="https://gitee.com/ssssssss-team/spider-flow/badge/star.svg?theme=white" /></a>
    <a target="_blank" href='https://github.com/ssssssss-team/spider-flow'><img src="https://img.shields.io/github/stars/ssssssss-team/spider-flow.svg?style=social"/></a>
    <a target="_blank" href="LICENSE"><img src="https://img.shields.io/:license-MIT-blue.svg"></a>
    <a target="_blank" href="https://shang.qq.com/wpa/qunwpa?idkey=10faa4cf9743e0aa379a72f2ad12a9e576c81462742143c8f3391b52e8c3ed8d"><img src="https://img.shields.io/badge/Join-QQGroup-blue"></a>
</p>

[介绍](#介绍) | [特性](#特性) | [插件](#插件) | <a target="_blank" href="http://demo.spiderflow.org">DEMO站点</a> | <a target="_blank" href="https://www.spiderflow.org">文档</a> | <a target="_blank" href="https://www.spiderflow.org/changelog.html">更新日志</a> | [截图](#项目部分截图) | [其它开源](#其它开源项目) | [免责声明](#免责声明)

## 介绍
平台以流程图的方式定义爬虫,是一个高度灵活可配置的爬虫平台

## 特性
- [x] 支持Xpath/JsonPath/css选择器/正则提取/混搭提取
- [x] 支持JSON/XML/二进制格式
- [x] 支持多数据源、SQL select/selectInt/selectOne/insert/update/delete
- [x] 支持爬取JS动态渲染(或ajax)的页面
- [x] 支持代理
- [x] 支持自动保存至数据库/文件
- [x] 常用字符串、日期、文件、加解密等函数
- [x] 支持插件扩展(自定义执行器，自定义方法）
- [x] 任务监控,任务日志
- [x] 支持HTTP接口
- [x] 支持Cookie自动管理
- [x] 支持自定义函数

## 插件
- [x] [Selenium插件](https://gitee.com/ssssssss-team/spider-flow-selenium)
- [x] [Redis插件](https://gitee.com/ssssssss-team/spider-flow-redis)
- [x] [OSS插件](https://gitee.com/ssssssss-team/spider-flow-oss)
- [x] [Mongodb插件](https://gitee.com/ssssssss-team/spider-flow-mongodb)
- [x] [IP代理池插件](https://gitee.com/ssssssss-team/spider-flow-proxypool)
- [x] [OCR识别插件](https://gitee.com/ssssssss-team/spider-flow-ocr)
- [x] [电子邮箱插件](https://gitee.com/ssssssss-team/spider-flow-mailbox)

## 项目部分截图
### 爬虫列表
![爬虫列表](https://images.gitee.com/uploads/images/2020/0412/104521_e1eb3fbb_297689.png "list.png")
### 爬虫测试
![爬虫测试](https://images.gitee.com/uploads/images/2020/0412/104659_b06dfbf0_297689.gif "test.gif")
### Debug
![Debug](https://images.gitee.com/uploads/images/2020/0412/104741_f9e1190e_297689.png "debug.png")
### 日志
![日志](https://images.gitee.com/uploads/images/2020/0412/104800_a757f569_297689.png "logo.png")

## 其它开源项目
- [spider-flow-vue，spider-flow的前端](https://gitee.com/ssssssss-team/spider-flow-vue)
- [magic-api，一个以XML为基础自动映射为HTTP接口的框架](https://gitee.com/ssssssss-team/magic-api)
- [magic-api-spring-boot-starter](https://gitee.com/ssssssss-team/magic-api-spring-boot-starter)


## 免责声明
请勿将`spider-flow`应用到任何可能会违反法律规定和道德约束的工作中,请友善使用`spider-flow`，遵守蜘蛛协议，不要将`spider-flow`用于任何非法用途。如您选择使用`spider-flow`即代表您遵守此协议，作者不承担任何由于您违反此协议带来任何的法律风险和损失，一切后果由您承担。
