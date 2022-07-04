# profile机制

> maven/spring 的 profile 机制，都是用于不同环境的特定配置切换的。比如项目再测试环境、开发环境、生产环境，有各自不同的数据库，生产环境不需要测试环境的一些测试依赖之类的。如果每次在不同环境运行都要去改配置文件，那就会很麻烦，而且有时忘记改了，还会出问题。这个机制大致就是为每个环境配置好各自的配置文件，相应的修改也修改相应的配置，这样切换的方便，不容易出问题。

### 1 **Maven profile的用法**

配置方式大致如下

```xml
<project>
   <profiles>
       <profile>
           <build>
               <defaultGoal>...</defaultGoal>
               <finalName>...</finalName>
               <resources>...</resources>
               <testResources>...</testResources>
               <plugins>...</plugins>
           </build>
           <reporting>...</reporting>
           <modules>...</modules>
           <dependencies>...</dependencies>
           <dependencyManagement>...</dependencyManagement>
           <distributionManagement>...</distributionManagement>
           <repositories>...</repositories>
           <pluginRepositories>...</pluginRepositories>
           <properties>...</properties>
       </profile>
   </profiles>
</project>
```

使用 -p 参数来激活一个profile

```
mvn package –P profileTest1
```

### 2 **Spring profile 的用法**

> 一般写各自的配置文件，比如application-dev.properties、application-test.properties等等，一般放到自建的 config目录下，或者resource目录也行

激活有两者用法

1. 全局的application.properties里指定，如下： `spring.profiles.active=dev`
2. 启动参数传入，如下：

- `-spring.profiles.active=dev`