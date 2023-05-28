package com.sjtu.rbj.bookstore.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Bojun Ren
 * @data 2023/05/27
 */
@Configuration(proxyBeanMethods = false)
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

}
