package exhibition.exhibition.config.filter;

import exhibition.exhibition.dto.Authentication;
import exhibition.exhibition.provider.JwtProvider;
import lombok.RequiredArgsConstructor;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(urlPatterns = {"/authors/*"})
@RequiredArgsConstructor
public class AuthorFilter implements Filter {

    private static final String TOKEN_HEADER = "Authorization";
    private final JwtProvider jwtProvider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        String jwt = req.getHeader(TOKEN_HEADER);

        Authentication auth = jwtProvider.getAuthentication(jwt);

        auth.getRoles().stream()
                .filter(a -> a.equals("AUTHOR"))
                .findFirst()
                .orElseThrow(() -> new ServletException("접근 권한이 없습니다."));

        chain.doFilter(request, response);
    }
}
