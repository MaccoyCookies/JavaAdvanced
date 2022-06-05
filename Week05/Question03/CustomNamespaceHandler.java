package com.maccoy.advanced.spring.custom.xml;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

public class CustomNamespaceHandler extends NamespaceHandlerSupport {

    @Override
    public void init() {
        registerBeanDefinitionParser("student", new CustomBeanDefinitionParser());
    }
}
