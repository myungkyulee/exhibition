package exhibition.exhibition.config.filter;

import exhibition.exhibition.dto.Authentication;
import exhibition.exhibition.provider.JwtProvider;
import lombok.RequiredArgsConstructor;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = {"/visitors/*", "/auth/createAuthor/*", "/"})
@RequiredArgsConstructor
public class VisitorFilter implements Filter {

    private static final String TOKEN_HEADER = "Authorization";
    private final JwtProvider jwtProvider;
    private FilterConfig filterConfig;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        String jwt = req.getHeader(TOKEN_HEADER);

        Authentication auth = jwtProvider.getAuthentication(jwt);
        boolean authorized = auth.getRoles().stream()
                .anyMatch(a -> a.equals("VISITOR"));
        if (!authorized) {
            throw new ServletException("접근 권한이 없습니다.");
        }
        /* else if (filterConfig != null) {
            ServletContext context = filterConfig.getServletContext();
            String loginPage = context.getInitParameter("signIn");
            if (loginPage != null && !"".equals(loginPage)) {

            }
        } else {

        }
*/
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
