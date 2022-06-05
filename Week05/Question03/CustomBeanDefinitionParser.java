package com.maccoy.advanced.spring.custom.xml;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

public class CustomBeanDefinitionParser extends AbstractSingleBeanDefinitionParser {

    private final static String STUDENT_NAME = "student";

    @Override
    protected Class<?> getBeanClass(Element element) {
        if (STUDENT_NAME.equals(element.getLocalName())) return Student.class;
        return null;
    }

    @Override
    protected void doParse(Element element, BeanDefinitionBuilder builder) {
        String name = element.getAttribute("name");
        if (StringUtils.hasLength(name)) builder.addPropertyValue("name", name);
        String age = element.getAttribute("age");
        if (StringUtils.hasLength(age)) builder.addPropertyValue("age", Integer.valueOf(age));
    }
}
