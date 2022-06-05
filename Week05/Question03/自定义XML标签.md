# 自定义XML标签

[Spring](https://www.notion.so/Spring-a9cab3152f8949309907718e70a6ef87) → XML章节

# 1. 关键配置

- `spring.handlers`: 定义解析xml元素的处理类。
- `spring.schemas`: 指定xsd文件的位置。
- `xxx.xsd`: 类似于语法规范，约束自定义标签的属性类型等。

# 2. 实现

![建一个 `META-INF`目录，并创建 `spring.handler` 和 `spring.schemas` 两个文件。](Untitled.png)

建一个 `META-INF`目录，并创建 `spring.handler` 和 `spring.schemas` 两个文件。

## 2.1 定义解析XML元素处理类

### 2.1.1 spring.handlers

```java
http\://www.springframework.org/schema/custom=com.maccoy.advanced.spring.custom.xml.CustomNamespaceHandler
```

### 2.1.2 自定义NamespaceHandle

```java
package com.maccoy.advanced.spring.custom.xml;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

public class CustomNamespaceHandler extends NamespaceHandlerSupport {

    @Override
    public void init() {
        registerBeanDefinitionParser("user", new CustomBeanDefinitionParser());
    }
}
```

```java
package com.maccoy.advanced.spring.custom.xml;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

public class CustomBeanDefinitionParser extends AbstractSingleBeanDefinitionParser {

    @Override
    protected Class<?> getBeanClass(Element element) {
        return User.class;
    }

    @Override
    protected void doParse(Element element, BeanDefinitionBuilder builder) {
        String name = element.getAttribute("name");
        if (StringUtils.hasLength(name)) builder.addPropertyValue("name", name);
        String age = element.getAttribute("age");
        if (StringUtils.hasLength(age)) builder.addPropertyValue("age", Integer.valueOf(age));
    }
}
```

```java
package com.maccoy.advanced.spring.custom.xml;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class User {

    private String name;

    private Integer age;

}
```

### 2.1.3 spring.schemas

```java
http\://www.springframework.org/schema/custom.xsd=META-INF/custom.xsd
```

### 2.1.4 处理xsd文件

```java
<?xml version="1.0" encoding="UTF-8" standalone="no"?>

<xsd:schema xmlns:xsd="http://www.w3.org/2001/XMLSchema"
            targetNamespace="http://www.springframework.org/schema/custom"
            elementFormDefault="qualified">
    <xsd:element name="user">
        <xsd:complexType>
            <xsd:attribute name="id" type="xsd:string">
            </xsd:attribute>
            <xsd:attribute name="name" type="xsd:string">
            </xsd:attribute>
            <xsd:attribute name="age" type="xsd:integer">
            </xsd:attribute>
        </xsd:complexType>
    </xsd:element>
</xsd:schema>
```

### 2.1.5 使用

> 在Spring XML配置文件application.xml中，添加自己的命名空间和schemas位置就可以了.
> 

```java
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:custom="http://www.springframework.org/schema/custom"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
         http://www.springframework.org/schema/beans/spring-beans-3.2.xsd
         http://www.springframework.org/schema/custom
         http://www.springframework.org/schema/custom.xsd">

    <custom:user id="cookies" age="100" name="customXml" />

</beans>
```

### 2.1.6 测试

```java
public static void main(String[] args) {
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
		User user = (User) applicationContext.getBean("cookies");
		System.out.println(user);
}
```