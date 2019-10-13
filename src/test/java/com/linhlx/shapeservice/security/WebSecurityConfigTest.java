package com.linhlx.shapeservice.security;

import org.junit.Before;
import org.junit.Test;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import javax.sql.DataSource;
import javax.xml.crypto.Data;

import static org.mockito.Mockito.*;

public class WebSecurityConfigTest {

    private DataSource dataSource;
    private WebSecurityConfig webSecurityConfig;
    private AuthenticationManagerBuilder authenticationManagerBuilder;
    private HttpSecurity httpSecurity;

    @Before
    public void setUp(){
        dataSource = mock(DataSource.class);
        webSecurityConfig = new WebSecurityConfig(dataSource);
    }

    @Test
    public void shouldConfigureJDBC() throws Exception{
        webSecurityConfig.configure(authenticationManagerBuilder);
    }

    @Test
    public void shouldConfigureRouteSecurity() throws Exception {
        webSecurityConfig.configure(httpSecurity);
    }

}
