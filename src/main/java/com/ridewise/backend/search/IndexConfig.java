package com.ridewise.backend.search;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class IndexConfig {

    @Bean
    public SpatialIndex index() {
        return new SpatialIndex(new ArrayList<>(), 9);
    }
}
