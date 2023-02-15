package com.tweet.config;


import com.tweet.security.CustomUserDetailService;
import com.tweet.security.JwtAuthenticationEntryPoint;
import com.tweet.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    public static final String[] PUBLIC_URL = {
            "/authentication/token",
            "/authentication/user-register",
            "/authentication/forget-password",
            "/authentication/send-otp/{email}",
            "/authentication/update-forget-password",
			"/authentication/get-user-data/{email}",
           "/api/v1.0/tweets/**",


    };

    @Autowired
    private JwtAuthenticationEntryPoint jwtEntryPoint;

    @Autowired
    private JwtAuthenticationFilter jwtAuthFilter;

    @Autowired
    private CustomUserDetailService customUserDetailService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors();
        http
                .csrf().disable()
                .authorizeHttpRequests()
                .antMatchers(PUBLIC_URL).permitAll()
                .antMatchers(HttpMethod.GET).permitAll()
                .anyRequest().authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling().authenticationEntryPoint(jwtEntryPoint);



        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);


        http.authenticationProvider(daoAuthenticationProvider());
        DefaultSecurityFilterChain defaultSecurityFilterChain = http.build();

        return defaultSecurityFilterChain;


    }

	/*
	protected void configure(HttpSecurity http) throws Exception {
		// TODO Auto-generated method stub

		http
		.csrf().disable()
		.cors().disable()
		.authorizeRequests()
		.antMatchers(PUBLIC_URL).permitAll()
		.anyRequest().authenticated()
		.and()
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and()
		.exceptionHandling().authenticationEntryPoint(jwtEntryPoint);


		http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);


	}*/



    @Bean
    protected AuthenticationManager authenticationManagerBean(AuthenticationConfiguration auth) throws Exception {
        // TODO Auto-generated method stub
        return auth.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider=new DaoAuthenticationProvider();
        System.out.println("config"+customUserDetailService);
        provider.setUserDetailsService(customUserDetailService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

	/*
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	*/

}
