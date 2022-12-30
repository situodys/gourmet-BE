package kw.ic.backend.global.auth.util;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class JwtUtil {

    private static final String AUTHORIZATION = "Authorization";
    private static final String BEARER = "Bearer ";
    private static final int TOKEN_START_INDEX =7;

    public String resolveToken(HttpServletRequest request) {
        String header = request.getHeader(AUTHORIZATION);
        if (StringUtils.hasText(header) && header.startsWith(BEARER)) {
            return header.substring(TOKEN_START_INDEX);
        }
        return null;
    }
}
