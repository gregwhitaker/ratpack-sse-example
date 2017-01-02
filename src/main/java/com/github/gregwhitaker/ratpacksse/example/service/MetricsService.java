package com.github.gregwhitaker.ratpacksse.example.service;

import com.github.gregwhitaker.ratpacksse.example.domain.Metrics;
import rx.Observable;

public interface MetricsService {

    Observable<Metrics> metrics();
}
