package com.github.gregwhitaker.ratpacksse.example;

import ratpack.guice.Guice;
import ratpack.rx.RxRatpack;
import ratpack.server.BaseDir;
import ratpack.server.RatpackServer;

public class Main {

    /**
     * Main entry-point of the application.
     */
    public static void main(String... args) throws Exception {
        RxRatpack.initialize();

        RatpackServer.start(s -> s
                .serverConfig(c -> c.baseDir(BaseDir.find()))
                .registry(Guice.registry(b -> b.module(MetricsModule.class)))
                .handlers(chain -> chain
                        .get("metrics", MetricsHandler.class)
                        .files(f -> f
                                .dir("public").indexFiles("index.html"))));
    }
}
