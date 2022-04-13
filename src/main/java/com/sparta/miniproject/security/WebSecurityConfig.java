package com.sparta.miniproject.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private RestAuthenticationFailureHandler restAuthenticationFailureHandler;
    @Autowired
    private RestAuthenticationSuccessHandler restAuthenticationSuccessHandler;
    @Autowired
    private RestLogoutSucccessHandler restLogoutSucccessHandler;

    @Bean
    public BCryptPasswordEncoder encodePassword() { // BCryptPasswordEncoder 은 제공되어짐
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) {
        web
                .ignoring()
                .antMatchers("/h2-console/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
// 회원 관리 처리 API (POST /user/**) 에 대해 CSRF 무시
        http.csrf().disable();
        http.formLogin().disable();
        http.addFilterAt(getAuthenticationFilter(), RestUsernamePasswordAuthenticationFilter.class);

        http.authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/images/**").permitAll()
                .antMatchers("/api/**").permitAll()
                .antMatchers("/user/**").permitAll()
// 그 외 어떤 요청이든 '인증'
                .anyRequest().authenticated()
                .and()
                // [로그아웃 기능]
                .logout()
                // 로그아웃 처리 URL
                .logoutUrl("/user/logout")
                .logoutSuccessHandler(restLogoutSucccessHandler)
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID").permitAll();
    }

    protected RestUsernamePasswordAuthenticationFilter getAuthenticationFilter(){
        RestUsernamePasswordAuthenticationFilter authFilter = new RestUsernamePasswordAuthenticationFilter();
        try{
            authFilter.setUsernameParameter("username");
            authFilter.setPasswordParameter("password");
            authFilter.setFilterProcessesUrl("/user/login"); // 로그인에 대한 POST 요청을 받을 url을 정의합니다. 해당 코드가 없으면 정상적으로 작동하지 않습니다.
            authFilter.setAuthenticationManager(this.authenticationManagerBean());
            authFilter.setAuthenticationFailureHandler(restAuthenticationFailureHandler);
            authFilter.setAuthenticationSuccessHandler(restAuthenticationSuccessHandler);
        } catch (Exception e){
            e.printStackTrace();
        }
        return authFilter;
    }


}
