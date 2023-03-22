package exhibition.exhibition.config.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import exhibition.exhibition.dto.Authentication;
import exhibition.exhibition.exception.ErrorCode;
import exhibition.exhibition.exception.ErrorResponse;
import exhibition.exhibition.exception.ExhibitionException;
import exhibition.exhibition.provider.JwtProvider;
import lombok.RequiredArgsConstructor;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(urlPatterns = {"/visitors/*", "/auth/createAuthor/*", "/"})
@RequiredArgsConstructor
public class VisitorFilter implements Filter {

    private static final String TOKEN_HEADER = "Authorization";
    private final JwtProvider jwtProvider;
    private final ObjectMapper objectMapper;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        response.setContentType("application/json");
        String jwt = req.getHeader(TOKEN_HEADER);
        try {
            Authentication auth = jwtProvider.getAuthentication(jwt);
            boolean authorized = auth.getRoles().stream()
                    .anyMatch(a -> a.equals("VISITOR"));
            if (!authorized) {
                throw new ExhibitionException(ErrorCode.ACCESS_DENIED);
            }

            chain.doFilter(request, response);
        } catch (ExhibitionException e) {
            String body = objectMapper.writeValueAsString(new ErrorResponse(e.getErrorCode()));

            response.getWriter().write(body);
        }
    }
}
