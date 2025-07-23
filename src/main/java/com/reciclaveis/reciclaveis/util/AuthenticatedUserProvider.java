package com.reciclaveis.reciclaveis.util;

import com.reciclaveis.reciclaveis.entity.User;
import com.reciclaveis.reciclaveis.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class AuthenticatedUserProvider {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticatedUserProvider.class);
    private final UserRepository userRepository;
    private final JwtService jwtService;

    public AuthenticatedUserProvider(UserRepository userRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    /**
     * Extrai o usuário autenticado com base no token JWT enviado no cabeçalho Authorization.
     *
     * @param request Requisição HTTP contendo o cabeçalho Authorization
     * @return Usuário autenticado ou null se token inválido ou usuário não encontrado
     */
    public User getUserFromRequest(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7); // Remove "Bearer "

            try {
                String email = jwtService.extractUsername(token);
                return userRepository.findByEmail(email).orElse(null);
            } catch (Exception e) {
                logger.error("Erro ao extrair usuário do token: {}", e.getMessage());
                return null;
            }
        }

        return null;
    }
}
