package com.github.gregwhitaker.ratpacksse.example;

import com.github.gregwhitaker.ratpacksse.example.domain.Metrics;
import com.github.gregwhitaker.ratpacksse.example.service.MetricsService;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.reactivestreams.Publisher;
import ratpack.handling.Context;
import ratpack.handling.Handler;
import ratpack.jackson.Jackson;
import ratpack.sse.ServerSentEvents;
import rx.RxReactiveStreams;

@Singleton
public class MetricsHandler implements Handler {

    private final MetricsService metricsService;

    @Inject
    public MetricsHandler(MetricsService metricsService) {
        this.metricsService = metricsService;
    }

    @Override
    public void handle(Context ctx) throws Exception {
        Publisher<Metrics> metricsStream = RxReactiveStreams
                .toPublisher(metricsService.metrics());

        ServerSentEvents events = ServerSentEvents.serverSentEvents(metricsStream, metricsEvent -> {
            metricsEvent.id(Long.toString(System.currentTimeMillis()));
            metricsEvent.data(metrics -> Jackson.getObjectWriter(ctx).writeValueAsString(metrics));
        });

        ctx.render(events);
    }
}
