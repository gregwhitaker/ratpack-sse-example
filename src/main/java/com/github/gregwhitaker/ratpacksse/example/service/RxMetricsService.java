package com.github.gregwhitaker.ratpacksse.example.service;

import com.github.gregwhitaker.ratpacksse.example.domain.CpuMetrics;
import com.github.gregwhitaker.ratpacksse.example.domain.MemoryMetrics;
import com.github.gregwhitaker.ratpacksse.example.domain.Metrics;
import com.google.inject.Singleton;
import com.sun.management.OperatingSystemMXBean;
import rx.Observable;

import java.lang.management.ManagementFactory;
import java.util.concurrent.TimeUnit;

@Singleton
public class RxMetricsService implements MetricsService {

    private final OperatingSystemMXBean osBean;

    public RxMetricsService() {
        osBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);
    }

    @Override
    public Observable<Metrics> metrics() {
        return Observable.interval(1, TimeUnit.SECONDS)
                .map(aLong -> {
                    CpuMetrics cpuMetrics = new CpuMetrics();
                    cpuMetrics.setSystemCpuPercentage(osBean.getSystemCpuLoad());

                    MemoryMetrics memoryMetrics = new MemoryMetrics();
                    memoryMetrics.setTotalPhysicalMemory(osBean.getTotalPhysicalMemorySize());
                    memoryMetrics.setFreePhysicalMemory(osBean.getFreePhysicalMemorySize());

                    return new Metrics(cpuMetrics, memoryMetrics);
                });
    }
}
