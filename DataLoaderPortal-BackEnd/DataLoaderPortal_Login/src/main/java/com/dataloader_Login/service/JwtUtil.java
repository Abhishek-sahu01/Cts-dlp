package com.dataloader_Login.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtUtil {

	private static final Logger log = LoggerFactory.getLogger(JwtUtil.class);

	private String secretkey = "sampletest";
	
	private String claimsDesc="Claims {}";
	private String tokenDesc="Token {}";
	private String startDesc="START";

	public String extractUsername(String token) throws ExpiredJwtException{
		log.info(startDesc);
		log.debug(tokenDesc, token);
		
		String extractClaim = extractClaim(token, Claims::getSubject);
		log.debug("Extract Claim {}", extractClaim);
		log.info("END");
		return extractClaim;
		
		
		

	}

	private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) throws ExpiredJwtException{
		log.info(startDesc);
		log.debug(tokenDesc, token);
		log.debug("Claims Resolver {}", claimsResolver);
	
			final Claims claims = extractAllClaims(token);
			log.debug(claimsDesc, claims);
			T apply = claimsResolver.apply(claims);
			log.debug("Apply {}", apply);
			log.info("END");
			return apply;
		

	}
	

	private Claims extractAllClaims(String token) throws ExpiredJwtException{
		log.info(startDesc);
		log.debug(tokenDesc, token);
		
			Claims claims = Jwts.parser().setSigningKey(secretkey).parseClaimsJws(token).getBody();
			log.debug(claimsDesc, claims);
			log.info("END");
			return claims;
		

	}

	public Date extractExpiration(String token) throws ExpiredJwtException{
		log.info(startDesc);
		log.debug(tokenDesc, token);
		
			Date expiryDate = extractClaim(token, Claims::getExpiration);
			log.debug("Expiry Date {}", expiryDate);
			log.info("END");
			return expiryDate;
		
	}

	private Boolean isTokenExpired(String token) throws ExpiredJwtException {

		log.debug(tokenDesc, token);
		
			boolean isTokenExpired = extractExpiration(token).before(new Date());
			log.debug("Is Token Expired{}", isTokenExpired);
			log.info("END");
			return isTokenExpired;
		
	}

	public String generateToken(UserDetails userDetails) {
		log.info(startDesc);
		log.debug("User Details {}", userDetails);
		Map<String, Object> claims = new HashMap<>();
		log.debug(claimsDesc, claims);
		String createToken = createToken(claims, userDetails.getUsername());
		log.debug(tokenDesc, createToken);
		log.info("END");
		return createToken;
	}

	private String createToken(Map<String, Object> claims, String username) {
		log.info(startDesc);
		log.debug(claimsDesc, claims);
		log.debug("Username {}", username);
		String token = Jwts.builder().setClaims(claims).setSubject(username)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))
				.signWith(SignatureAlgorithm.HS256, secretkey).compact();
		log.debug(tokenDesc, token);
		log.info("END");
		return token;
	}

	public Boolean validateToken(String token, UserDetails userDetails) throws ExpiredJwtException  {
		log.info(startDesc);
		log.debug(tokenDesc, token);
		log.debug("User Details {}", userDetails);
		final String username = extractUsername(token);
		log.debug("Username {}", username);
		log.info("END");
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	private <T> T extractClaimForExpiry(String token,Function<Claims, T> claimsResolver) throws ExpiredJwtException{
		log.info(startDesc);
		log.debug(tokenDesc, token);
		log.debug("Claims Resolver {}", claimsResolver);
		
			final Claims claims = Jwts.parser().setSigningKey(secretkey).parseClaimsJws(token).getBody();
			log.debug(claimsDesc, claims);
			T apply = claimsResolver.apply(claims);
			log.debug("Apply {}", apply);
			log.info("END");
			return apply;
		
	}
	public Boolean validateToken(String token) throws ExpiredJwtException{
		log.info(startDesc);

		
			
			Date expiryDate = extractClaimForExpiry(token, Claims::getExpiration);
			boolean isTokenExpired = expiryDate.before(new Date());
			return !isTokenExpired;
		
	}

}
