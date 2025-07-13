// src/main/java/com/reciclaveis/reciclaveis/util/AuthenticatedUserProvider.java
package com.reciclaveis.reciclaveis.util;

import com.reciclaveis.reciclaveis.entity.User;
import com.reciclaveis.reciclaveis.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class AuthenticatedUserProvider {

    @Autowired
    private UserRepository userRepository;

    private final String SECRET = "secreta123"; // sua chave JWT

    public User getUserFromRequest(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7); // remove "Bearer "
            String email = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();

            return userRepository.findByEmail(email).orElse(null);
        }
        return null;
    }
}
