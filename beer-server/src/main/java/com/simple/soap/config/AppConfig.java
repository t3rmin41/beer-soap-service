package com.simple.soap.config;

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

import com.simple.soap.authentication.BasicAuthAuthorizationInterceptor;
import com.simple.soap.endpoint.BeerEndpoint;
import com.soap.simple.beer.BeerService;

@EnableWs
@Configuration
@ComponentScan(basePackages = {"com.simple.soap.endpoint"})
public class AppConfig {

    @Bean
    public ServletRegistrationBean messageDispatcherServlet(ApplicationContext appContext) {
        CXFServlet cxfServlet = new CXFServlet();
        return new ServletRegistrationBean(cxfServlet, "/ws/*");
    }

    @Bean(name=Bus.DEFAULT_BUS_ID)
    public SpringBus springBus() {
        SpringBus springBus = new SpringBus();
        springBus.getInInterceptors().add(new BasicAuthAuthorizationInterceptor());
        return springBus;
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
