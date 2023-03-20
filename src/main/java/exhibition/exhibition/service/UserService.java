package exhibition.exhibition.service;

import exhibition.exhibition.config.PasswordEncoder;
import exhibition.exhibition.domain.User;
import exhibition.exhibition.dto.Authentication;
import exhibition.exhibition.dto.CreateUser;
import exhibition.exhibition.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Transactional
    public CreateUser.Response join(CreateUser.Request request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("중복된 이메일 입니다.");
        }

        User user = User.builder()
                .email(request.getEmail())
                .name(request.getName())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        return CreateUser.Response.fromEntity(userRepository.save(user));
    }

    public User signIn(Authentication.Request request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 이메일 입니다."));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        return user;
    }
}
