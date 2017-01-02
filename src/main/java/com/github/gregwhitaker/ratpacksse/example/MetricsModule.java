package com.github.gregwhitaker.ratpacksse.example;

import com.github.gregwhitaker.ratpacksse.example.service.MetricsService;
import com.github.gregwhitaker.ratpacksse.example.service.RxMetricsService;
import com.google.inject.AbstractModule;

public class MetricsModule extends AbstractModule {

    @SuppressWarnings("PointlessBinding")
    @Override
    protected void configure() {
        bind(MetricsService.class).to(RxMetricsService.class);
        bind(MetricsHandler.class);
    }
}
