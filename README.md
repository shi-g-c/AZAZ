# 七牛云1024创作节 - 西南联大队 - AZAZ

## 项目介绍

本项目是**西南联大队**参加**七牛云1024创作节**的作品，项目名为AZAZ音视频，是一个基于七牛云的对象存储服务开发的分布式音视频网页软件项目，实现了视频观看、视频互动、用户社交等功能，并且在项目开发过程中考虑到了分布式、并发与高流量问题，大数据量下仍然能提供高效可靠的音视频服务。

## 项目架构

### 总览：

本项目采用前后端分离的架构，前端使用Vue框架，后端使用SpringBoot+SpringCloudAlibaba框架。使用到的中间件有MySQL，MongoDB，Redis，ElasticSearch，Nacos，RocketMQ等。

### 框架

本项目使用的后端框架为spring全家桶，框架版本对应为 jdk17 - springboot2.6.11 - springcloud2021.0.4 - springcloudalibaba2021.0.4.0 。

### 技术选型

#### 前端技术选型



#### 后端技术选型：

**控制层与服务层框架选择：**jdk17 - springboot2.6.11 - springcloud2021.0.4 - springcloudalibaba2021.0.4.0 

**持久层数据库选择：**Qiniu对象存储，关系型数据库MySQL，非关系数据库Redis，文档型数据库MongoDB，消息通信中间件RocketMQ，搜索索引库ElasticSearch，注册中心Nacos。

### AZAZ音视频顶层技术选型设计

![顶层模块](resource/imgs/azaz系统顶层模块设计图.png)

### AZAZ音视频项目架构

![AZAZ音视频项目架构](resource/imgs/AZAZ系统架构图.jpg)

### AZAZ数据库表结构设计

#### 1. 项目数据库表一览

| 表名               | 表含义     | 备注                         |
| ------------------ | ---------- | ---------------------------- |
| tb_user            | 用户表     | 保存系统用户信息             |
| tb_video           | 视频表     | 保存视频信息                 |
| tb_like            | 点赞关系表 | 保存用户和视频的点赞关系对应 |
| tb_collect         | 收藏关系表 | 保存用户和视频的收藏关系对应 |
| tb_follow          | 关注关系表 | 保存用户和用户的关注关系对应 |
| tb_comment         | 评论表     | 保存用户对视频或者评论的回复 |
| tb_private_message | 私信信息表 | 保存用户与用户之间的私信     |



## 功能描述

### 接口文档地址

本项目的接口文档地址为：[ApiFox在线接口文档](https://apifox.com/apidoc/shared-c70f669d-dc50-47fd-8dab-250c85ee9d6b)

也可以使用路径 *resource/API/AZAZ音视频.openapi.json* 文档来生成，版本是openapi3.0

### 全部功能点及其描述

全部功能点及其描述的在线地址为：[AZAZ音视频全部功能点描述](https://sqrmcnsjwo8.feishu.cn/docx/J6KMdqVN5opT0OxT5Z9cEg9OnFd?from=from_copylink)

具体如下：

#### 用户模块

1. **用户注册**：用户提交手机和密码即可注册用户，系统会自动生成用户名与头像
2. **用户登录**：已注册用户可以通过手机号与密码登录系统
3. **用户信息查询**：用户可以在个人中心看到自己的个人信息，包括用户名、头像等
4. **用户信息修改**：用户可以个性化修改自己的头像、用户名等
5. **用户主页查询**：用户可以查看自己或者其他人的主页，信息包括用户名，粉丝数，关注数，获赞数，作品列表等

#### 视频模块

1. **获取视频流**：用户可以获取系统推荐的视频流
2. **只看某一分类的视频**：用户可以选择只看某一分类的视频
3. **发布视频**：用户可以上传视频，可以选择封面标题分类等等，分类有：1-直播 2-体育 3-游戏 4-番剧 5-知识 6-娱乐 7-美食 8-时尚 9-热点
4. **查看视频详细信息**：用户可以点击视频封面或者朋友推荐的视频查看详细信息
5. **点赞视频**：用户可以对视频进行点赞
6. **取消视频点赞**：用户可以取消对视频的点赞
7. **收藏视频**：用户可以收藏视频
8. **取消视频收藏**：用户可以取消对已收藏视频的收藏
9. **查询收藏列表**：用户可以查询自己的收藏列表
10. **查询用户作品数**：用户可以查询自己的作品数，展示在主页
11. **查询发布列表**：用户可以查询自己的作品
12. **查询用户获赞数**：用户可以在主页看到自己的获赞数。
13. **评论视频**：用户可以对视频发表评论
14. **回复评论**：用户可以回复评论
15. **获取评论列表**：用户可以查询视频的评论列表

#### 社交模块

1. **关注用户**：用户可以关注其他用户
2. **取消关注**：用户可以取消对其他用户的关注
3. **查询是否关注**：查询自己是否关注其他用户
4. **查询是否互关**：判断两个用户是否互相关注
5. **查询互关列表**：查询自己的互关列表，用于分享视频
6. **查询关注数**：查询自己关注的人数
7. **查询粉丝数**：查询自己的粉丝数
8. **查询关注列表**：查询自己关注用户的详细列表
9. **查询最近聊天列表**：查询自己最近聊天的朋友
10. **查询私聊历史记录**查询自己与某一个朋友的聊天记录
11. **发送私信**：向其他用户发送私信，未互关用户只能发送不超过三条消息
12. **分享视频**：用户可以向自己的互关朋友分享视频

#### 搜索模块

1. **用户搜索**：用户可以通过搜索框按照用户名的关键词检索用户
2. **视频搜索**：用户可以通过搜索框按照视频的标题的关键词检索视频

## 模块介绍

### 概览

项目的基本层级结构如下：

```shell
├── .idea
├── azaz-common # 通用包，包括常量类、通用工具、异常模型等
├── azaz-feign-api # OpenFeign远程调用
├── azaz-gateway # SpringCloud Gateway
├── azaz-model # 模型，包括dto，vo，pojo等
├── azaz-page # 前端页面
├── azaz-service # 实际的业务服务包
│   ├── azaz-interact # 社交服务
│   ├── azaz-search # 搜索服务
│   ├── azaz-user # 用户服务
│   └── azaz-video # 视频服务
├── pom.xml # pom依赖文件
├── project-log # logback日志
└── resource # 项目资源，包括启动sql等
```

本项目的服务基于业务逻辑进行纵向拆分，考虑到业务的关联程度我们将服务拆分成四个部分：**用户服务、视频服务、社交服务、搜索服务**。这种拆分方法充分考虑到了自洽性与扩展性，各自与数据库表对应，实现高内聚低耦合以及代码复用，也利用后期进行扩展。

### 用户模块

#### 1. 项目层级结构

```shell
├── pom.xml # pom依赖文件
└── src # 项目源文件
    └── main
        ├── java
        │   └── com
        │       └── azaz
        │           ├── UserApplication.java # 用户服务启动类
        │           ├── config # 配置包
        │           │   ├── MybatisConfig.java # MybatisPlus配置
        │           │   ├── RedissonConfig.java # Redisson配置
        │           │   └── WebMvcConfiguration.java # SpringMVC配置
        │           ├── controller # 控制包
        │           │   ├── UserInfoController.java # 用户信息控制
        │           │   └── UserLoginController.java # 用户登录注册控制
        │           ├── interceptor # 拦截器
        │           │   └── TokenInterceptor.java # token拦截器
        │           ├── mapper # 持久层
        │           │   ├── UserLoginMapper.java
        │           │   └── UserMapper.java
        │           └── service # 服务层
        │               ├── UserInfoService.java  # 用户信息服务接口
        │               ├── UserLoginService.java # 用户登录注册服务接口
        │               └── impl
        │                   ├── UserInfoServiceImpl.java # 用户信息服务接口实现类
        │                   └── UserLoginServiceImpl.java # 用户登录注册服务接口实现类
        └── resources
            ├── application.yml # 中间件日志等配置
            ├── bootstrap.yaml # nacos配置
            ├── logback.xml # 日志配置
            └── mapper # Mybatis的xml文件
```

### 视频模块

#### 1.项目层级结构

```shell
├── pom.xml # pom依赖文件
└── src # 项目源文件
    └── main
        ├── java
        │   └── com
        │       └── azaz
        │           ├── VideoApplication.java # 视频服务启动类
        │           ├── config # 配置包
        │           │   ├── MybatisConfig.java #  
        │           │   ├── RedissonConfig.java
        │           │   └── WebMvcConfiguration.java
        │           ├── controller # 控制包
        │           │   └── VideoController.java # 视频控制
        │           ├── interceptor # 拦截器包
        │           │   └── TokenInterceptor.java # token拦截器
        │           ├── mapper # 持久层
        │           │   ├── CommentMapper.java
        │           │   └── VideoMapper.java
        │           └── service # 服务包
        │               ├── DbOpsService.java # mongoDB操作服务
        │               ├── VideoDoLikeService.java # 点赞收藏服务
        │               ├── VideoUploadService.java # 视频发布服务
        │               └── impl
        │                   ├── DbOpsServiceImpl.java # mongoDB操作服务实现类
        │                   ├── VideoDoLikeServiceImpl.java # 点赞收藏服务实现类
        │                   └── VideoUploadServiceImpl.java # 视频发布服务实现类
        └── resources
            ├── application.yml # 中间件日志等配置
            ├── bootstrap.yaml # nacos配置
            ├── logback.xml # 日志配置
            └── mapper # Mybatis的xml文件
```



### 社交模块

#### 1.项目层级结构

```shell
├── pom.xml # pom依赖文件
└── src # 项目源文件
    └── main
        ├── java
        │   └── com
        │       └── azaz
        │           ├── InteractApplication.java # 社交服务启动类
        │           ├── config # 配置包
        │           │   ├── MybatisConfig.java # MybatisPlus配置
        │           │   ├── RedissonConfig.java # Redisson配置
        │           │   └── WebMvcConfiguration.java # SpringMVC配置
        │           ├── controller # 控制包
        │           │   ├── PrivateMessageController.java # 私信控制
        │           │   └── UserFollowController.java # 关注控制
        │           ├── interceptor # 拦截器包
        │           │   └── TokenInterceptor.java # token拦截器
        │           ├── mapper # 持久层包
        │           │   ├── FollowMapper.java
        │           │   └── PrivateMessageMapper.java
        │           ├── mq # RocketMQ消息队列
        │           │   └── PrivateMessageListener.java # 监听消息发送
        │           └── service # 服务包
        │               ├── PrivateMessageService.java # 私信服务
        │               ├── UserFollowService.java # 关注服务
        │               └── impl 
        │                   ├── PrivateMessageServiceImpl.java # 私信服务实现类
        │                   └── UserFollowServiceImpl.java # 关注服务实现类
        └── resources
            ├── application.yml # 中间件日志等配置
            ├── bootstrap.yaml # nacos配置
            ├── logback.xml # 日志配置
            └── mapper # Mybatis的xml文件
```



### 搜索模块

#### 1.项目层级结构

```shell
├── pom.xml # pom依赖文件
└── src # 项目源文件
    └── main
        ├── java
        │   └── com
        │       └── azaz
        │           ├── SearchApplication.java # 搜索服务启动类
        │           ├── config # 配置包
        │           │   ├── ElasticSearchConfig.java # ElasticSearch配置
        │           │   ├── MybatisConfig.java # MybatisPlus配置
        │           │   └── WebMvcConfiguration.java # SpringMVC配置
        │           ├── controller # 控制包
        │           │   └── SearchController.java # 搜索控制
        │           ├── interceptor # 过滤器
        │           │   └── TokenInterceptor.java #token过滤器
        │           ├── mq # RocketMQ消费者
        │           │   ├── UserInfoListener.java # 监听用户信息的增删改
        │           │   └── VideoPublishListener.java # 监听视频信息的增删改
        │           └── service # 服务包
        │               ├── SearchService.java # 搜索服务接口
        │               └── impl
        │                   └── SearchServiceImpl.java # 搜索服务接口实现类
        └── resources
            ├── application.yml # 中间件日志等配置
            ├── bootstrap.yaml # nacos配置
            ├── logback.xml # 日志配置
            └── mapper # Mybatis的xml文件
```

## 项目快速启动

### 前端启动



### 后端启动

#### 1. 初始化MySQL数据库表结构

本项目的MySQL数据库表结构设计文档地址：[AZAZ数据库表结构设计](https://sqrmcnsjwo8.feishu.cn/docx/HOVZdtwVDorY2TxNSL1cYBlynLb?from=from_copylink)

可以使用路径 *resource/DB/azaz.sql* 的sql脚本快速生成表结构。

#### 2.初始化ElasticSearch索引库

本项目针对用户表和视频表建立了索引库，用来搜索用户名或者视频标题。

向ElasticSearch索引库添加Mapping映射的json语句在 *resource/ES* 路径下， 有*esmapping-user.json*与*esmapping-video.json*两个json文件, 分别用来建立用户与视频的索引映射。

#### 3. 克隆项目到本地

#### 4.部署项目中用到的中间件

本项目使用到的中间件及其版本如图所示:

| 中间件        | 版本   |
| ------------- | ------ |
| Qiniu对象存储 | 7.2.7  |
| MySQL         | 8.0.27 |
| Redis         | 6.2.6  |
| Nacos         | 2.03   |
| ElasticSearch | 7.12.1 |
| RocketMQ      | 4.5.1  |
| MongoDB       | 5.0.5  |

#### 5.配置中间件的地址端口用户名密码等

#### 6.依次启动各个服务

## Demo视频展示


## 项目成员及分工

| 姓名   | 职责 | 分工                                                         |
| ------ | ---- | ------------------------------------------------------------ |
| 石功创 | 队长 | 分析整体架构；编写需求文档；设计数据库表结构；后端技术选型；编写接口文档；用户模块、社交模块、搜索模块的后端开发；说明文档编写；测试 |
| 陈彦希 | 队员 | 分析整体架构；辅助设计数据库表结构；后端技术选型；编写接口文档；视频模块的后端开发；说明文档编写；测试 |
| 杨博涵 | 队员 | 参与分析整体架构；前端技术选型；项目的前端开发；接口文档编写；辅助编写说明文档；测试 |

