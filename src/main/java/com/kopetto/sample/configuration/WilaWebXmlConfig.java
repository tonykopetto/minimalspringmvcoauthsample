package com.kopetto.sample.configuration;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.DispatcherServlet;

/**Initialize web.xml
 *
 */
public class WilaWebXmlConfig implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
        rootContext.register(MainContext.class, WebAppContext.class);

        //add MVC dispatcher servlet and map it to /
        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher", new DispatcherServlet(rootContext));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");
        dispatcher.setAsyncSupported(true);

        //add spring characterEncoding filter
        //to always have encoding on all requests
        EnumSet<DispatcherType> dispatcherTypes = EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.ERROR);

        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);

        FilterRegistration.Dynamic characterEncoding = servletContext.addFilter("characterEncoding", characterEncodingFilter);
        characterEncoding.addMappingForUrlPatterns(dispatcherTypes, true, "/*");
        characterEncoding.setAsyncSupported(true);
        
        //add spring security filter
        FilterRegistration.Dynamic security = servletContext.addFilter("springSecurityFilterChain", new DelegatingFilterProxy());
        security.addMappingForUrlPatterns(dispatcherTypes, true, "/*");
        security.setAsyncSupported(true);

        //FilterRegistration.Dynamic sitemesh = servletContext.addFilter("sitemesh", new ConfigurableSiteMeshFilter());
        //sitemesh.addMappingForUrlPatterns(dispatcherTypes, true, "*.jsp");

        //add HiddenHttpMethodFilter  filter
        //this will make sure that spring MVC works well with PUT/DELETE requests
        //made with a hidde _method field
        HiddenHttpMethodFilter hiddenHttpMethodFilter  = new HiddenHttpMethodFilter  ();
        FilterRegistration.Dynamic hiddenHttpMethod = servletContext.addFilter("hiddenHttpMethodFilter", hiddenHttpMethodFilter);
        hiddenHttpMethod.addMappingForUrlPatterns(dispatcherTypes, true, "/*");
        hiddenHttpMethod.setAsyncSupported(true);

        //add spring contextloader listener
        servletContext.addListener(new ContextLoaderListener(rootContext));
    }
}