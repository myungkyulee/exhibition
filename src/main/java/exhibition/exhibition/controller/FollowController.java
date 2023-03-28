package exhibition.exhibition.controller;

import exhibition.exhibition.dto.FollowerInfo;
import exhibition.exhibition.dto.FollowingInfo;
import exhibition.exhibition.security.JwtProvider;
import exhibition.exhibition.service.FollowService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/follow")
public class FollowController {

    private final FollowService followService;
    private final JwtProvider jwtProvider;

    @PostMapping("/{authorName}")
    public ResponseEntity<?> followAuthor(@PathVariable String authorName,
                             @RequestHeader("Authorization") String token) {
        Long visitorId = jwtProvider.getAuthentication(token).getId();
        followService.follow(visitorId, authorName);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{authorName}")
    public ResponseEntity<?> unfollowAuthor(@PathVariable String authorName,
                             @RequestHeader("Authorization") String token) {
        Long visitorId = jwtProvider.getAuthentication(token).getId();
        followService.unfollow(visitorId, authorName);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/followers/{authorName}")
    public ResponseEntity<?> getFollowers(@PathVariable String authorName,
                                      final Pageable pageable) {
        Page<FollowerInfo> followers = followService.getFollowers(authorName, pageable);
        return ResponseEntity.ok(followers);
    }

    @GetMapping("/followings/{visitorName}")
    public ResponseEntity<?> getFollowings(@PathVariable String visitorName,
                                          final Pageable pageable) {
        Page<FollowingInfo> followings = followService.getFollowings(visitorName, pageable);
        return ResponseEntity.ok(followings);
    }
}
