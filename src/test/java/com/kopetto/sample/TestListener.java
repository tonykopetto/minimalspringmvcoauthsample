package com.kopetto.sample;


import java.util.Arrays;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.Scope;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.context.support.SimpleThreadScope;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.TestExecutionListener;

/**
 */
public class TestListener implements TestExecutionListener {

    @Override
    public void beforeTestClass(TestContext testContext) throws Exception {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void prepareTestInstance(TestContext testContext) throws Exception {
        //To change body of implemented methods use File | Settings | File Templates.
        setSecurityContextHolder();
        
        //support request scoped and session scoped beans in tests
        if (testContext.getApplicationContext() instanceof GenericApplicationContext) {
            GenericApplicationContext context = (GenericApplicationContext) testContext.getApplicationContext();
            ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
            Scope requestScope = new SimpleThreadScope();
            beanFactory.registerScope("request", requestScope);
            Scope sessionScope = new SimpleThreadScope();
            beanFactory.registerScope("session", sessionScope);
        }        
    }

    @Override
    public void beforeTestMethod(TestContext testContext) throws Exception {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void afterTestMethod(TestContext testContext) throws Exception {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void afterTestClass(TestContext testContext) throws Exception {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    private void setSecurityContextHolder() {
    	
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_ADMINISTRATOR");
    	User user = new User("admin", "admin", Arrays.asList(authority));
    	
        AnonymousAuthenticationToken auth = new AnonymousAuthenticationToken("ID", user, Arrays.asList(authority));
        SecurityContextHolder.getContext().setAuthentication(auth);

    }    
}
