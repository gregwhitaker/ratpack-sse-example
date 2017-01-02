package com.github.gregwhitaker.ratpacksse.example.domain;

public class MemoryMetrics {

    private double totalPhysicalMemory;
    private double freePhysicalMemory;

    public double getTotalPhysicalMemory() {
        return totalPhysicalMemory;
    }

    public void setTotalPhysicalMemory(double totalPhysicalMemory) {
        this.totalPhysicalMemory = totalPhysicalMemory;
    }

    public double getFreePhysicalMemory() {
        return freePhysicalMemory;
    }

    public void setFreePhysicalMemory(double freePhysicalMemory) {
        this.freePhysicalMemory = freePhysicalMemory;
    }
}
