package com.dataloader_Login.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.endpoint.web.CorsEndpointProperties;
import org.springframework.boot.actuate.autoconfigure.endpoint.web.WebEndpointProperties;
import org.springframework.boot.actuate.autoconfigure.web.server.ManagementPortType;
import org.springframework.boot.actuate.endpoint.ExposableEndpoint;
import org.springframework.boot.actuate.endpoint.web.EndpointLinksResolver;
import org.springframework.boot.actuate.endpoint.web.EndpointMapping;
import org.springframework.boot.actuate.endpoint.web.EndpointMediaTypes;
import org.springframework.boot.actuate.endpoint.web.ExposableWebEndpoint;
import org.springframework.boot.actuate.endpoint.web.WebEndpointsSupplier;
import org.springframework.boot.actuate.endpoint.web.annotation.ControllerEndpointsSupplier;
import org.springframework.boot.actuate.endpoint.web.annotation.ServletEndpointsSupplier;
import org.springframework.boot.actuate.endpoint.web.servlet.WebMvcEndpointHandlerMapping;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity.IgnoredRequestConfigurer;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StringUtils;

import com.dataloader_Login.service.AdminDetailsService;

@SuppressWarnings("deprecation")
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	private static final Logger log = LoggerFactory.getLogger(WebSecurityConfig.class);
	@Autowired
	private AdminDetailsService adminDetailsService;

	@Autowired
	private JwtRequestFilter jwtRequestFilter;
	
	private String startDesc="START";

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		log.info(startDesc);
		super.configure(auth);
		auth.userDetailsService(adminDetailsService);
		log.info("END");
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		log.info(startDesc);
		IgnoredRequestConfigurer antMatchers = web.ignoring().antMatchers("/login", "/h2-console/**",
				"/configuration/ui","/forgot_password","/reset_password","/validate", "/configuration/security", "/webjars/**", "/manage/health/**");
		log.debug("Antmatchers {}",antMatchers);
		log.info("END");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		log.info(startDesc);
		SessionManagementConfigurer<HttpSecurity> sessionCreationPolicy = http.csrf().disable().authorizeRequests()
				.antMatchers("/login").permitAll().anyRequest().authenticated().and().exceptionHandling().and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		log.debug("Session Creation Policy {}",sessionCreationPolicy);
		HttpSecurity addFilterBefore = http.addFilterBefore(jwtRequestFilter,
				UsernamePasswordAuthenticationFilter.class);
		log.debug("Add Filter Before {}",addFilterBefore);
		log.info("END");

	}

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		log.info(startDesc);
		log.info("END");
		return super.authenticationManagerBean();
	}

	@Bean
	public PasswordEncoder getPasswordEncoder() {
		log.info(startDesc);
		log.info("END");
		return NoOpPasswordEncoder.getInstance();
	}
	
	@Bean
	public WebMvcEndpointHandlerMapping webEndpointServletHandlerMapping(WebEndpointsSupplier webEndpointsSupplier, ServletEndpointsSupplier servletEndpointsSupplier, ControllerEndpointsSupplier controllerEndpointsSupplier, EndpointMediaTypes endpointMediaTypes, CorsEndpointProperties corsProperties, WebEndpointProperties webEndpointProperties, Environment environment) {
	        List<ExposableEndpoint<?>> allEndpoints = new ArrayList<>();
	        Collection<ExposableWebEndpoint> webEndpoints = webEndpointsSupplier.getEndpoints();
	        allEndpoints.addAll(webEndpoints);
	        allEndpoints.addAll(servletEndpointsSupplier.getEndpoints());
	        allEndpoints.addAll(controllerEndpointsSupplier.getEndpoints());
	        String basePath = webEndpointProperties.getBasePath();
	        EndpointMapping endpointMapping = new EndpointMapping(basePath);
	        boolean shouldRegisterLinksMapping = this.shouldRegisterLinksMapping(webEndpointProperties, environment, basePath);
	        return new WebMvcEndpointHandlerMapping(endpointMapping, webEndpoints, endpointMediaTypes, corsProperties.toCorsConfiguration(), new EndpointLinksResolver(allEndpoints, basePath), shouldRegisterLinksMapping, null);
	    }


	private boolean shouldRegisterLinksMapping(WebEndpointProperties webEndpointProperties, Environment environment, String basePath) {
	        return webEndpointProperties.getDiscovery().isEnabled() && (StringUtils.hasText(basePath) || ManagementPortType.get(environment).equals(ManagementPortType.DIFFERENT));
	    }

}
