package exhibition.exhibition.controller;

import exhibition.exhibition.security.JwtProvider;
import exhibition.exhibition.service.FollowService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/follow")
public class FollowController {

    private final FollowService followService;
    private final JwtProvider jwtProvider;

    @PostMapping("/{authorName}")
    public void followAuthor(@PathVariable String authorName,
                             @RequestHeader("Authorization") String token) {
        Long visitorId = jwtProvider.getAuthentication(token).getId();
        followService.follow(visitorId, authorName);
    }
}
