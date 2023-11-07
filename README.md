# 七牛云1024创作节 - 西南联大队 - AZAZ

## 项目介绍

本项目是**西南联大队**参加**七牛云1024创作节**的作品，项目名为AZAZ音视频，是一个基于七牛云的对象存储服务开发的分布式音视频网页软件项目，实现了视频观看、视频互动、用户社交等功能，并且在项目开发过程中考虑到了分布式、并发与高流量问题，大数据量下仍然能提供高效可靠的音视频服务。

## 项目架构

<<<<<<< HEAD
项目架构文档:[项目架构设计文档](docs/AZAZ音视频项目架构文档.md)

![AZAZ音视频项目架构](resource/imgs/AZAZ系统架构图.jpg)

## 功能描述

功能需求文档：[AZAZ音视频功能需求文档](docs/AZAZ音视频功能需求文档.md)
=======
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
>>>>>>> origin/dev-1102

## 项目快速启动

### 前端启动

#### 1. 进入azaz-page目录

```shell
cd /azaz/azaz-page
```

#### 2. 安装依赖，需要有node.js环境

```shell
<<<<<<< HEAD
npm install package-lock.json
```

#### 3. 修改后端请求地址
=======
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
>>>>>>> origin/dev-1102

前端项目监听端口在vite.config.js中配置

<<<<<<< HEAD
后端网关请求地址在public\config.js中配置
=======
在本项目中，用户信息的修改不仅仅涉及到数据库与缓存，还涉及到搜索索引库，因为**用户名需要被搜索**。所以在用户修改了账户数据后，需要将索引库也进行更新，考虑到性能与用户体验，在本系统中采用RocketMQ异步通信的方式告知ES服务更新索引库。
>>>>>>> origin/dev-1102

#### 4. 编译项目

```shell
npm run build
```

<<<<<<< HEAD
#### 5. 运行项目
=======
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

流程图如下
![输入图片说明](whiteboard_exported_image.png)

**使用MongoDB**

在存储每个视频的点赞用户ID操作中，选择了使用MongoDB存储而非传统的MYSQL，主要有下述原因

    1.面向集合存储，易存储对象类型的数据。在存放用户点赞ID集合时，传统的MYSQL数据结构单一，显得力不从心，而MongoDB自由的存储形式提供了一个完美的解决方案

    2.与生俱来的高可用、高水平扩展能力使得它在处理海量、高并发的数据应用时颇具优势。对于热点视频来说，点赞用户上百万甚至千万，如此大的存储量正好匹配了MongoDB的高性能

    3.JSON 结构和对象模型接近，开发代码量低，JSON的动态模型意味着更容易响应新的业务需求。

    4.点赞的用户ID并不是一个保证需要安全性的数据，即使丢失也无伤大雅。
  
**视频评论设计**

视频的评功能需要考虑到评论还会有评论的情况。

  1.存储时表的主要字段

    父评论ID:记录此条评论是对于哪条评论的回复,若是对视频的直接评论,此字段值为0

    视频ID:记录此条评论是哪个视频的评论

    对于父评论ID和视频ID加联合索引，方便查询

  2.查询操作

    在查询时,点击查看回复即可根据此条评论ID和对应视频ID即可快速查处此条评论的回复。

**视频流获取设计**

在视频获取时，需要对视频信息进行一定处理

    1.通过sql倒序查询id查到最后一次上传的videoId,如果前端传来的lastId值为0，则从最新发布的视频查起。

    2.在发布视频时将视频序列化为Jason字符串存入redis中

    3.根据前端传过来的lastVideoId到redis中取出10个视频并反序列化为视频类，再进行分区筛选

    4.根据视频id和用户id在redis中查询是否点赞或收藏
    
    5.通过feign远程调用用户模块的方法，将用户信息和视频信息一起封装

### 社交模块

#### 1.项目层级结构
>>>>>>> origin/dev-1102

```shell
npm run dev
```

<<<<<<< HEAD
#### 6. 访问页面
=======
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

>>>>>>> origin/dev-1102

在浏览器中输入 http://ip:port/AZAZ/#/即可访问到主页面

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



## TODO

### 1. 对用户进行分类

本项目中所有用户的信息都是按照一个套路进行处理，但是在实际的系统中会有活跃用户、非活跃用户、僵尸用户、粉丝量巨大的大V，普通用户等，如果所有信息统一处理，那么会比较消耗系统资源也不合理。所以后续改进中需要对用户进行分类，不同用户进行不同处理，活跃用户的信息可以常驻Redis,而非活跃用户则使用主动拉信息的模式，登录时才初始化缓存。

### 2. 对用户进行打标签，个性化推荐

本系统中由于各种限制，选择的视频流推送方式是按照时间顺序进行排序，在真实的系统中需要根据用户的喜好进行个性化推荐，需要根据用户常看视频完善用户画像，实现个性化推荐。同时也要尽可能保证用户看过的视频短时间内不会再次推荐给改用户。

### 3. 对视频进行分类

类似于用户信息，本系统中的视频信息也没有进行分类，后续应当将视频按照热度、点赞数、收藏数等数据分类，不同类型的数据进行不同的处理。


## 项目成员及分工

| 姓名   | 职责 | 分工                                                         |
| ------ | ---- | ------------------------------------------------------------ |
| 石功创 | 队长 | 分析整体架构；编写需求文档；设计数据库表结构；后端技术选型；编写接口文档；用户模块、社交模块、搜索模块的后端开发；说明文档编写；测试 |
| 陈彦希 | 队员 | 分析整体架构；辅助设计数据库表结构；后端技术选型；编写接口文档；视频模块的后端开发；说明文档编写；测试 |
| 杨博涵 | 队员 | 参与分析整体架构；前端技术选型；项目的前端开发；接口文档编写；辅助编写说明文档；测试 |

