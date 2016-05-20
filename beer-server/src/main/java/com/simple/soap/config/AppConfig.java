package com.simple.soap.config;

import javax.xml.ws.Endpoint;

import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.apache.cxf.transport.servlet.CXFServlet;

import com.soap.simple.beer.BeerService;

@Configuration
@ComponentScan(basePackages = {"com.simple.soap.endpoint"})
public class AppConfig {

	@Autowired
	private BeerService beerService;
	
    @Bean
    public ServletRegistrationBean messageDispatcherServlet(ApplicationContext appContext) {
        CXFServlet cxfServlet = new CXFServlet();
        return new ServletRegistrationBean(cxfServlet, "/ws/*");
    }

    @Bean(name=Bus.DEFAULT_BUS_ID)
    public SpringBus springBus() {
        SpringBus springBus = new SpringBus();
        return springBus;
    }
    
    @Bean
    public Endpoint endpoint() {
        EndpointImpl endpoint = new EndpointImpl(springBus());
        Endpoint.publish("/beerservice", beerService);
        // add as much webservices as you need by Endpoint.publish("webserviceurl", implementation());
        // all webservices will be available at http://host:port/ws/webserviceurl
        // no WSDL needed since cxf generates interface to implement for webservice
        // path "/ws/*" is defined in CXFServlet where list of available webservices can be seen
        // Keep in mind that in WSDL soap:address location=... is exactly where webservice is
        return endpoint;
    }
}
