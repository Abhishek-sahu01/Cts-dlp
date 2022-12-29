package com.dataloader_Login.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.dataloader_Login.service.AdminDetailsService;
import com.dataloader_Login.service.JwtUtil;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
	private static final Logger log = LoggerFactory.getLogger(JwtRequestFilter.class);

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private AdminDetailsService adminDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		log.info("START");
		log.debug("Request {}", request);
		log.debug("Response {}", response);
		log.debug("Response {}", filterChain);
		final String authHeader = request.getHeader("Authorization");
		log.debug("Auth Header {}", authHeader);
		String username = null;
		String jwt = null;
		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			jwt = authHeader.substring(7);
			log.debug("Jwt Token {}", jwt);
			try {
				username = jwtUtil.extractUsername(jwt);
			}
			catch(Exception e) {
				log.error("Exception {}",e);
				
			}
			log.debug("Username {}", username);
		}
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = this.adminDetailsService.loadUserByUsername(username);
			try {
				boolean valid=jwtUtil.validateToken(jwt, userDetails);
				if (valid) {
					log.info("Token is valid");
					UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
							userDetails, null, userDetails.getAuthorities());
					log.debug("Username Password Authentication Token {}", usernamePasswordAuthenticationToken);
					usernamePasswordAuthenticationToken
							.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				}
			} catch (Exception e) {

				e.printStackTrace();
			}
		}
		filterChain.doFilter(request, response);
		log.info("END");
	}

}
