package br.com.cotiinformatica.components;

import java.util.UUID;
import java.util.function.Function;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Component
public class JwtTokenComponent {

	public UUID getIdFromToken(String token) {
		return UUID.fromString(getClaimFromToken(token, Claims::getSubject));
	}
	public String getPerfilFromToken(String token) {
        return getClaimFromToken(token, claims -> claims.get("perfil", String.class));
    }
	
	private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		
		var secretKey = "ad78f028-170c-4cb3-afb9-4b0ad192fdff";
		
		final Claims claims = Jwts.parser()
				.setSigningKey(secretKey)
				.parseClaimsJws(token)
				.getBody();
		
		return claimsResolver.apply(claims);
	}
	
}
