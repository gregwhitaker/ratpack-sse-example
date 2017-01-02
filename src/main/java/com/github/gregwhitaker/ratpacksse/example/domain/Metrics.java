package com.github.gregwhitaker.ratpacksse.example.domain;

public class Metrics {

    private final CpuMetrics cpuMetrics;
    private final MemoryMetrics memoryMetrics;

    public Metrics(CpuMetrics cpuMetrics, MemoryMetrics memoryMetrics) {
        this.cpuMetrics = cpuMetrics;
        this.memoryMetrics = memoryMetrics;
    }

    public CpuMetrics getCpuMetrics() {
        return cpuMetrics;
    }

    public MemoryMetrics getMemoryMetrics() {
        return memoryMetrics;
    }
}
