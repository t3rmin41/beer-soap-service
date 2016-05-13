package com.memorynotfound.config;

import javax.xml.ws.Endpoint;

import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.ws.config.annotation.EnableWs;
import org.apache.cxf.transport.servlet.CXFServlet;

import com.memorynotfound.beer.BeerService;
import com.memorynotfound.endpoint.BeerEndpoint;

@EnableWs
@Configuration
@ComponentScan(basePackages = {"com.memorynotfound.endpoint"})
public class AppConfig {

    @Bean
    public ServletRegistrationBean messageDispatcherServlet(ApplicationContext appContext){
        return new ServletRegistrationBean(new CXFServlet(), "/ws/*");
    }

    @Bean(name=Bus.DEFAULT_BUS_ID)
    public SpringBus springBus() {
        return new SpringBus();
    }

    @Bean
    public BeerService beerService() {
        return new BeerEndpoint();
    }
    
    @Bean
    public Endpoint endpoint() {
        EndpointImpl endpoint = new EndpointImpl(springBus(), beerService());
        endpoint.publish("/beerservice");
        endpoint.setWsdlLocation("src/main/resources/contracts/wsdl/beer-wsdl.wsdl");
        return endpoint;
    }
}
