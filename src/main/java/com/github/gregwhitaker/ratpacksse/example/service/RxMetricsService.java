/*
 * Copyright 2017 Greg Whitaker
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
                    cpuMetrics.setSystemCpuPercentage(Math.round(osBean.getSystemCpuLoad() * 100.0) / 100.0);

                    MemoryMetrics memoryMetrics = new MemoryMetrics();
                    memoryMetrics.setTotalPhysicalMemory(osBean.getTotalPhysicalMemorySize() / 1024 / 1024);
                    memoryMetrics.setFreePhysicalMemory(osBean.getFreePhysicalMemorySize() / 1024 / 1024);

                    return new Metrics(cpuMetrics, memoryMetrics);
                });
    }
}
