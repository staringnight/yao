### 依赖本模块

引入时scope请设为provided,子项目可继承依赖，同级依赖不会传递此模块

```xml

<dependency>
    <groupId>com.dazhi100</groupId>
    <artifactId>dzyx-common</artifactId>
    <version>1.0.0-SNAPSHOT</version>
    <scope>provided</scope>
</dependency>
```

本项目会传递的依赖为

- `guava`    jdk的补充
- `hutool-core`     id工具，脱敏工具，io工具，file工具，resource工具
- [`hutool-crypto`](https://hutool.cn/docs/#/crypto/%E6%A6%82%E8%BF%B0) 加密工具
- `lombok` 简化开发
- [`mapstruct`](https://github.com/mapstruct/mapstruct) 需要idea下载插件，非常强大的bean转换工具，bean之间的转换请用这个，不要使用beanUtils（性能太差）
- [`metrics`](https://github.com/dropwizard/metrics) 数据埋点，应用监控工具类

### 目录结构描述

```
├── annotation                                     // 注解目录  
│   ├── ErrorCode                                  // 配置在请求参数模型field上，配合参数校验注解，校验不通过时，直接返回对应错误码  
│   └── NotWarpResponseBody                        // 配置在controller的method上，配置此参数，则不会被自动包装为ResultVo,可以以原始格式返回  
├── bean                                           // bean  
│   └── Result                                     // Api层统一的返回格式，只能使用success或error静态方法生成，中途无法被修改  
├── component                                      // 公共组件，需要被springIOC管理  
│   ├── infrastructure                             // 基础设施层公共组件。ACL的module扫描此包  
│   │   └──SnowflakeConfig                         // 雪花算法组件，需要yml中包含`snow.wokerId`和`snow.datacenterId`  
│   └── web                                        // web公共组件。  
│       ├── GlobalExceptionHandler                 // 全局异常捕捉处理，order等级低  
│       ├── WebExceptionHandler                    // web细分异常捕捉处理，order等级高  
│       ├── ResponseControllerAdvice               // 对httpResponse进行包装，将对象统一包装成resultVo，已包装活标注`@NotWarpResponseBody`则不再包装  
│       ├── SwaggerConfig                          // 对swagger的配置，需要yml中包含`swagger.enable`和`swagger.basePackage`  
│       └── WebConfig                              // 配置WebMvcConfigurer相关的东西  
├── constant                                       // 常量包   
│   ├── ApplicationConstant                        // Application公用常量  
│   ├── ExcelConstant                              // Excel常量  
│   └── ResultCode                                 // 错误码  
├── dict                                           // 字典，enum包  
│   ├── base  
│   │   ├── DescBaseEnum                           // Enum的描述  
│   │   ├── IdBaseEnum                             // Enum的id  
│   │   └── NromalEnum                             // 通用Enum模板 类似ON(0, "on")  
│   ├── ONOFF                                      // 开启关闭状态的Enum 
│   ├── ...待补充 
├── exception                                      // 通用exception包  
│   ├── ApiException                               // Api调用失败通用exception  
│   └── EnumException                              // Enum不合法通用exception  
└── utils
    ├── ApiAssert                                  // api快速失败，并抛出异常，用于检验
    ├── CryptoUtils                                // 加密相关工具
    ├── DataDesensitizedUtil                       // 脱敏相关工具
    ├── DateCategoryUtil                           // 日期分类工具，不包含自定义假期判断，如寒暑假，每个学校不一样，如有必要，应配合学校服务判断
    ├── ImgVerifyCodeUtil                          // 验证码工具类，暂时用之前的，如有必要，之后再进行更换
    ├── JSON                                       // 基于jackson封装的json工具类，请统一json解析工具使用，不要同时引入3种不同json解析工具
    ├── UUIDUtil                                   // 字面意思
    └── ...待补充

```
