/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.SweetTooth.config;

import com.example.SweetTooth.service.UserCredentialsImplService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.IdTokenClaimNames;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
/**
 *
 * @author Lenovo
 */
@Configuration
@EnableWebSecurity
public class SecurityRules extends SecurityConfigurerAdapter{
   
    @Autowired
    private GoogleOAuth2SuccessHandler googleOAuth2SuccessHandler;

    @Bean
    public BCryptPasswordEncoder bCPE() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
	public ClientRegistrationRepository clientRegistrationRepository() {
		return new InMemoryClientRegistrationRepository(this.googleClientRegistration());
	}

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
       http
        .authorizeRequests((authorize) -> authorize
            .antMatchers("/", "/shop/**", "/register", "/h2-console/**", "/paySuccess", "/payNow", "/payNotApproved").permitAll()
            .antMatchers("/admin/**").hasRole("ADMIN")
            .anyRequest().authenticated()
        )
        .formLogin((formLogin) -> formLogin
            .loginPage("/login")
            .permitAll()
            .failureUrl("/login?error=true")
            .successHandler((request, response, authentication) -> {
                for (GrantedAuthority auth : authentication.getAuthorities()) {
                    if ("ADMIN".contains(auth.getAuthority())) {
                        response.sendRedirect("/adminHome");
                        return;
                    }
                }
                response.sendRedirect("/");
            })
            .usernameParameter("email")
            .passwordParameter("password")
        )
        .oauth2Login((oauth2Login) -> oauth2Login
            .loginPage("/login")
            .successHandler(googleOAuth2SuccessHandler))
        .logout((logout) -> logout
            .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
            .logoutSuccessUrl("/login")
            .invalidateHttpSession(true)
            .deleteCookies("JSESSIONID")
        )
        .exceptionHandling()
        .and()
        .csrf().disable();

    http.headers().frameOptions().disable();

    return http.build();
    }
      
    @Bean   
    public WebSecurityCustomizer webCustomizer() throws Exception {
        return (web)-> web.ignoring().antMatchers("/resources/**", "/static/**", "/images", "/ProductImages/**", "/css/**", "/js/**");
    
    } 
    
    private ClientRegistration googleClientRegistration() {
		return ClientRegistration.withRegistrationId("google")
			.clientId("Your OAuht Client ID")
			.clientSecret("Your OAuth Secret")
			.clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
			.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
			.redirectUri("{baseUrl}/login/oauth2/code/{registrationId}")
			.scope("openid", "profile", "email", "address", "phone")
			.authorizationUri("https://accounts.google.com/o/oauth2/v2/auth")
			.tokenUri("https://www.googleapis.com/oauth2/v4/token")
			.userInfoUri("https://www.googleapis.com/oauth2/v3/userinfo")
			.userNameAttributeName(IdTokenClaimNames.SUB)
			.jwkSetUri("https://www.googleapis.com/oauth2/v3/certs")
			.clientName("Google")
			.build();
	}
    }
