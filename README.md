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

控制层与服务层框架选择：jdk17 - springboot2.6.11 - springcloud2021.0.4 - springcloudalibaba2021.0.4.0 

持久层数据库选择：Qiniu对象存储，关系型数据库MySQL，非关系数据库Redis，文档型数据库MongoDB，消息通信中间件RocketMQ，搜索索引库ElasticSearch，注册中心Nacos。

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

#### 2. 数据库ER图



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

本项目的服务基于业务逻辑进行纵向拆分，考虑到业务的关联程度我们将服务拆分成四个部分：**用户服务、视频服务、社交服务、搜索服务**。这种拆分方法充分考虑到了自洽性与扩展性，各自与数据库表对应，实现高内聚低耦合以及代码复用，也利于后期进行扩展。

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

#### 2. 实体设计

用户实体设计如下：

```java
@TableName("tb_user")
public class User {
    /**
     * 用户id,采用数据库自增
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 手机号，唯一
     */
    @TableField("phone")
    private String phone;
    /**
     * 用户名
     */
    @TableField("username")
    private String username;

    /**
     * 密码
     */
    @TableField("password")
    private String password;
    /**
     * 盐
     */
    @TableField("salt")
    private String salt;

    /**
     * 用户头像url
     */
    @TableField("image")
    private String image;

    /**
     * 用户个性签名
     */
    @TableField("signature")
    private String signature;

    /**
     * 用户状态，0 正常，1 禁用
     */
    @TableField("status")
    private Integer status;

    /**
     * 用户关注数
     */
    @TableField("attention")
    private Integer attention;

    /**
     * 用户粉丝数
     */
    @TableField("followers")
    private Integer followers;

    /**
     * 用户作品数
     */
    @TableField("works")
    private Integer works;

    /**
     * 用户获赞数
     */
    @TableField("likes")
    private Integer likes;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField("update_time")
    private LocalDateTime updateTime;

}
```

#### 3. 重点功能设计

用户模块主要负责以用户为中心的功能部分，包括用户注册、用户登录、用户信息查询等。

**用户注册**

在用户注册功能中，需要考虑到手机号合法程度、是否重复注册等情况。由于账户信息是最重要的数据之一，所以在校验参数的同时，还需要考虑到并发注册的问题，即同一时刻有多个手机号进入注册账号的代码块导致数据库插入出错或者其他错误。

![用户并发注册](resource/imgs/用户注册.png)

所以在注册过程中，需要使用**Redisson对手机号进行加锁**，保证同一时刻同一个手机号只能有一个线程进入注册的逻辑，保证账户的唯一性也避免出现插入出错。

**用户信息修改**

在本项目中，用户信息的修改不仅仅涉及到数据库与缓存，还涉及到搜索索引库，因为**用户名需要被搜索**。所以在用户修改了账户数据后，需要将索引库也进行更新，考虑到性能与用户体验，在本系统中采用RocketMQ异步通信的方式告知ES服务更新索引库。

![用户信息修改](resource/imgs/用户信息修改.png)

**用户信息查询**

在本系统的视频场景中，用户信息查询较为频繁，而本系统中将用户信息分为两种：

第一种是在评论区、视频界面、关注列表展示等出现的用户数据，这一类用户数据只包括头像昵称，属于不常变化的简略信息，并且这部分数据会查询得极为频繁；需要在Redis中进行缓存避免大量数据库请求。

另外一种就是查询用户主页，这种数据不仅包括第一种简略数据，还包括关注人数，粉丝数作品数等详细数据信息，这种数据需要点击用户主页才会被查询，属于不那么频繁的请求。该类数据需要向对应的服务发起远程调用，将得到数据结果与基本信息进行组合返回给用户。

将两种信息分开，可以避免许多无用的数据的查询与传输，大大减少了网络IO，也减少了第一种信息查询的时间，优化用户体验。

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

#### 2. 实体设计

视频实体设计如下：

```java
public class Video  {
    /**
     * 视频id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 视频作者id
     */
    @TableField("author_id")
    private Long authorId;

    /**
     * 视频作者名
     */
    @TableField("title")
    private String title;

    /**
     * 分区，0为热门，其他待定
     */
    @TableField("section")
    private Integer section;

    /**
     * 视频封面url
     */
    @TableField("cover_url")
    private String coverUrl;

    /**
     * 视频url
     */
    @TableField("video_url")
    private String videoUrl;

    /**
     * 视频状态。0正常    1删除
     */
    @TableField("status")
    private Integer status;

    /**
     * 创建时间
     */
    @TableField("create_time")
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField("update_time")
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    /**
     * 点赞数
     */
    @TableField("likes")
    private Long likes;

    /**
     * 收藏数
     */
    @TableField("collects")
    private Long collects;

    /**
     * 评论数
     */
    @TableField("comments")
    private Long comments;
}
```

评论实体设计如下：

```java
public class Comment {
    /**
     * 评论id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 视频id
     */
    @TableField("video_id")
    private Long videoId;

    /**
     * 用户id
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 用户名
     */
    @TableField("user_name")
    private String userName;

    /**
     * 用户头像
     */
    @TableField("image")
    private String image;

    /**
     * 父评论id
     */
    @TableField("parent_id")
    private Long parentId;

    /**
     * 评论内容
     */
    @TableField("content")
    private String content;

    /**
     * 点赞数
     */
    @TableField("status")
    private Integer status;

    /**
     * 创建时间
     */
    @TableField("create_time")
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField("update_time")
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

}
```

#### 3. 重点功能设计

视频模块主要负责以视频为中心的功能部分，包括上传，发布视频，对视频进行点赞，评论，收藏等操作，展示用户收藏的视频等。

**视频点赞设计**

此功能考虑高并发和高存储情况下并发点赞和大量点赞的需求

考虑到此项目需要用到:

   (1)获得当前视频点赞的总数，

   (2)获得用户的点赞列表，

   (3)判断当前用户是否对此视频点赞

这三个相关功能

故通过以下设计完成点赞功能

  1.存储

     1.接到点赞请求时，前端传来videoId，用threadlocal获得当前用户的userId

     2.将视频的点赞集合以set形式存入redis中，其中set的key为前缀+videoId,value为以userId组成的set集合(集合元素的唯一性避免了重复点赞的操作)

     3.将集合数据异步传递到mongodb上进行持久化

     4.将视频的点赞总数以kv形式存入redis中，其中set的key为前缀+videoId,value为点赞总数+1(此操作用redisson加了分布式锁)

     5.将点赞总数数据通过RocketMq异步传输到mysql数据库中进行持久化。

     6.将用户和对应点赞视频存入用户点赞关系表中(mysql),方便以后查询用户的喜欢列表

  2.查询

      1.查询视频的总点赞量，直接从redis中取(key若失效，从mysql里面拉取)

      2.查询用户的点赞列表，在mysql用户视频点赞关系表中查询

      3.查询当前用户是否对视频点赞(查用户id在不在redis中的set)，先从redis中获取该视频的点赞用户set，再判断当前用户id是否在此集合中。若redis失效，从mongodb中拉取 
        数据，并刷新到redis中。

**使用MongoDB的原因**

在存储每个视频的点赞用户ID操作中，选择了使用MongoDB存储而非传统的MYSQL，主要有下述原因
   
**视频评论设计**

视频的评功能需要考虑到评论还会有评论的情况。

  1.存储时表的主要字段

    父评论ID:记录此条评论是对于哪条评论的回复,若是对视频的直接评论,此字段值为0

    视频ID:记录此条评论是哪个视频的评论

    对于父评论ID和视频ID加联合索引，方便查询

  2.查询操作

    在查询时,根据此条评论ID和对应视频ID即可快速查处此条评论的回复。

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

#### 2. 实体设计

私信实体设计如下：

```java
@TableName("tb_private_message")
public class PrivateMessage {
    /**
     * 私信id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 发送者id
     */
    @TableField("sender_id")
    private Long senderId;

    /**
     * 接收者id
     */
    @TableField("receiver_id")
    private Long receiverId;
    /**
     * 私信类型，0-默认 1-朋友分享的视频 2-系统消息
     */
    @TableField("message_type")
    private Integer messageType;

    /**
     * 私信内容
     */
    @TableField("message_content")
    private String messageContent;

    /**
     * 私信状态，0-正常 1-删除
     */
    @TableField("status")
    private Integer status;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;
}
```

#### 3. 重点功能设计

**用户搜索**



**视频搜索**

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

#### 2. 重点功能设计

搜索功能使用ElasticSearch倒排索引数据库

**用户搜索**



**视频搜索**

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

