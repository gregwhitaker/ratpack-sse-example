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
