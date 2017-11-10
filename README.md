# ct 
电信客服项目

## 简介
通信运营商每时每刻会产生大量的通信数据，例如通话记录，短信记录，彩信记录，第三方服务资费等等繁多信息。数据量如此巨大，除了要满足用户的实时查询和展示之外，还需要定时定期的对已有数据进行离线的分析处理。例如，当日话单，月度话单，季度话单，年度话单，通话详情，通话记录等等。我们以此为背景，寻找一个切入点，实现这些功能。
## 项目架构图
![架构图](https://github.com/HansiChan/Others/blob/master/%E7%94%B5%E4%BF%A1%E9%A1%B9%E7%9B%AE%E6%9E%B6%E6%9E%84%E5%9B%BE.png)

### （1）开发工具：
* idea或者eclipse等java开发软件
* maven仓库3.3.9
* JDK1.8+
### （2）开发环境：
* 系统环境：windows和linux
* 编程语言  
  * hadoop-cdh5.3.6-2.5.0
  * zookeeper-cdh5.3.6-3.4.5
  * hbase-cdh5.3.6-0.98
  * hive-cdh5.3.6-0.13 
  * flume-cdh5.3.6-1.5.0
  * kafka-2.10-0.8.2.1
