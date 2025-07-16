import com.reciclaveis.reciclaveis.entity.User;
import com.reciclaveis.reciclaveis.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
public class AuthenticatedUserProvider {

    @Autowired
    private UserRepository userRepository;

    @Value("${jwt.secret}")
    private String jwtSecret;

    public User getUserFromRequest(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7); // remove "Bearer "
            String email = Jwts.parser()
                    .setSigningKey(Base64.getDecoder().decode(jwtSecret)) // ⚠️ necessário!
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();

            return userRepository.findByEmail(email).orElse(null);
        }
        return null;
    }
}
