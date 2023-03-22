package exhibition.exhibition.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import exhibition.exhibition.dto.Authentication;
import exhibition.exhibition.exception.ErrorCode;
import exhibition.exhibition.exception.ErrorResponse;
import exhibition.exhibition.exception.ExhibitionException;
import exhibition.exhibition.provider.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@WebFilter(urlPatterns = {"/authors/*", "/works"})
@RequiredArgsConstructor
public class AuthorFilter implements Filter {

    private static final String TOKEN_HEADER = "Authorization";
    private final JwtProvider jwtProvider;
    private final ObjectMapper objectMapper;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        res.setContentType("application/json");
        String jwt = req.getHeader(TOKEN_HEADER);

        try {
            Authentication auth = jwtProvider.getAuthentication(jwt);
            boolean authorized = auth.getRoles().stream()
                    .anyMatch(a -> a.equals("AUTHOR"));
            if (!authorized) {
                throw new ExhibitionException(ErrorCode.ACCESS_DENIED);
            }

            chain.doFilter(request, response);
        } catch (ExhibitionException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getErrorCode());

            res.setStatus(errorResponse.getStatus());
            String body = objectMapper.writeValueAsString(errorResponse);

            response.getWriter().write(body);
        }
    }
}
