package com.lauro.resource.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import javax.swing.*;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

@Configuration
public class ThymeleafTemplateConfig {
    @Bean
    public Context context() {
        return new Context();
    }
}
