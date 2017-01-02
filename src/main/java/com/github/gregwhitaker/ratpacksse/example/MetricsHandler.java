package com.github.gregwhitaker.ratpacksse.example;

import com.github.gregwhitaker.ratpacksse.example.domain.Metrics;
import com.github.gregwhitaker.ratpacksse.example.service.MetricsService;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.reactivestreams.Publisher;
import ratpack.func.Action;
import ratpack.handling.Context;
import ratpack.handling.Handler;
import ratpack.sse.Event;
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

        ServerSentEvents events = ServerSentEvents.serverSentEvents(metricsStream, new Action<Event<Metrics>>() {
            @Override
            public void execute(Event<Metrics> metricsEvent) throws Exception {
                metricsEvent.id(Long.toString(System.currentTimeMillis()));
                System.out.println(metricsEvent.getData());
            }
        });

        ctx.render(events);
    }
}
