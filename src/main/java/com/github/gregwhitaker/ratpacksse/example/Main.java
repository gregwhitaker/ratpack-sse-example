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

import ratpack.guice.Guice;
import ratpack.rx.RxRatpack;
import ratpack.server.BaseDir;
import ratpack.server.RatpackServer;

/**
 * Example application that returns system metrics to clients via Server-Sent Events.
 */
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
