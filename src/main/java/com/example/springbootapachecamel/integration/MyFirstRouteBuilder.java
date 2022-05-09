package com.example.springbootapachecamel.integration;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class MyFirstRouteBuilder extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("timer:mikestimer?delay=2000&period=4000&repeatCount=5")
                .setBody((exchange -> {
                    String myheadersin = exchange.getIn().getHeaders().toString();
                    return "Hello... " + LocalDateTime.now() + ", headers: " + myheadersin;
                }))
                .to("log:mikeslog")
                .to("file:output");
    }
}
