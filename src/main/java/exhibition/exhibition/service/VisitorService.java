package exhibition.exhibition.service;

import exhibition.exhibition.dto.AuthenticationTokens;
import exhibition.exhibition.exception.ErrorCode;
import exhibition.exhibition.exception.ExhibitionException;
import exhibition.exhibition.security.PasswordEncoder;
import exhibition.exhibition.domain.Visitor;
import exhibition.exhibition.dto.SignIn;
import exhibition.exhibition.dto.CreateVisitor;
import exhibition.exhibition.security.JwtProvider;
import exhibition.exhibition.repository.VisitorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VisitorService {
    private final VisitorRepository visitorRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public CreateVisitor.Response signUp(CreateVisitor.Request request) {
        if (visitorRepository.existsByEmail(request.getEmail())) {
            throw new ExhibitionException(ErrorCode.DUPLICATED_EMAIL);
        }

        Visitor visitor = Visitor.builder()
                .email(request.getEmail())
                .name(request.getName())
                .visitorName(request.getVisitorName())
                .roles(List.of("VISITOR"))
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        return CreateVisitor.Response.fromEntity(visitorRepository.save(visitor));
    }

    public SignIn.Response signIn(SignIn.Request request) {
        Visitor visitor = visitorRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ExhibitionException(ErrorCode.NOT_FOUND_VISITOR));

        if (!passwordEncoder.matches(request.getPassword(), visitor.getPassword())) {
            throw new ExhibitionException(ErrorCode.PASSWORD_UN_MATCH);
        }

        AuthenticationTokens authenticationTokens = jwtProvider.generateTokens(visitor.getId(), visitor.getRoles());

        return SignIn.Response.builder()
                .name(visitor.getName())
                .tokens(authenticationTokens)
                .build();
    }
}
