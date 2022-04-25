package com.example.springbootapachecamel.integration;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.camel.model.rest.RestConfigurationDefinition;
import org.springframework.stereotype.Component;

@Component
public class FromRestToRabbitMQRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        CamelContext context = new DefaultCamelContext();
        RestConfigurationDefinition restConfigurationDefinition = restConfiguration()
                .contextPath("/mike")
                .port(9095)
                .apiContextPath("/api-my")
                .apiProperty("api.title", "Try this REST API")
                .apiProperty("api.version", "v17")
                .apiProperty("api.mike", "Tihi")
                .component("servlet")
                .bindingMode(RestBindingMode.json);

        rest("/api/")
                .id("api-route")
                .consumes("application/json")
                .post("/bean")
                .bindingMode(RestBindingMode.json_xml)
                .type(MikesBean.class)
                .to("direct:remoteService");

        from("direct:remoteService")
                .routeId("direct-route")
                .tracing()
                .log(">>> ${body.id}")
                .log(">>> ${body.name}")
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        MikesBean bodyIn = (MikesBean) exchange.getIn().getBody();
                        bodyIn.setName(bodyIn.getName() + "_tis");
                        exchange.getIn().setBody(bodyIn);
                    }
                })
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(200));
    }
}
