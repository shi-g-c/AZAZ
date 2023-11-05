# 七牛云1024创作节 - 西南联大队 - AZAZ

## 项目介绍

本项目是西南联大队参加七牛云1024创作节的作品，项目名为AZAZ，是一个基于七牛云的对象存储服务开发的音视频软件，实现了视频观看、视频互动、用户交互等功能，并且在项目开发过程中考虑到了并发与高流量问题，构建高并发、大数据量下仍然能提供高效可靠的视频服务。

## 项目架构

### 总览：

本项目采用前后端分离的架构，前端使用Vue框架，后端使用SpringBoot+SpringCloudAlibaba框架。使用到的中间件有MySQL，MongoDB，Redis，ElasticSearch，Nacos，RocketMQ等，具体版本与初始化语句见 [后端启动](#后端启动)。

### 框架

本项目使用的后端框架为spring全家桶，框架版本对应为 jdk17 - springboot2.6.11 - springcloud2021.0.4 - springcloudalibaba2021.0.4.0 。

### 中间件

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

### AZAZ音视频顶层模块设计

![顶层模块](resource/imgs/azaz系统顶层模块设计图.png)

### AZAZ音视频项目架构

![AZAZ音视频项目架构](resource/imgs/AZAZ系统架构图.jpg)

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

## 分模块介绍

### 用户模块

### 视频模块

### 社交模块

### 搜索模块

## 项目快速启动

### 前端启动



### 后端启动

#### 1. 初始化MySQL数据库表结构

本项目的MySQL数据库表结构设计文档地址：[AZAZ数据库表结构设计](https://sqrmcnsjwo8.feishu.cn/docx/HOVZdtwVDorY2TxNSL1cYBlynLb?from=from_copylink)

可以使用路径 *resource/DB/azaz.sql* 的sql脚本快速生成表结构。

#### 2.初始化ElasticSearch索引库

本项目针对用户表和视频表建立了索引库，用来搜索用户名或者视频标题。

向ElasticSearch索引库添加Mapping映射的json语句在 *resource/ES* 路径下， 有*esmapping-user.json*与*esmapping-video.json*两个json文件, 分别用来建立用户与视频的索引映射。

#### 3.按照上述中间件的版本部署中间件

#### 4.配置中间件的地址端口用户名密码等

#### 5.依次启动各个服务


## 项目成员及分工

| 姓名   | 职责 | 分工                                                         |
| ------ | ---- | ------------------------------------------------------------ |
| 石功创 | 队长 | 分析整体架构；编写需求文档；设计数据库表结构；用户模块、社交模块、搜索模块的后端开发；文档编写；测试 |
| 陈彦希 | 队员 | 分析整体架构；辅助设计数据库表结构；视频模块的后端开发；文档编写；测试 |
| 杨博涵 | 队员 | 分析整体架构；整个项目的前端开发；测试                       |

