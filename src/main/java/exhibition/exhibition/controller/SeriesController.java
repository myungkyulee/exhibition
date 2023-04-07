package exhibition.exhibition.controller;

import exhibition.exhibition.domain.Work;
import exhibition.exhibition.dto.CreateSeries;
import exhibition.exhibition.security.JwtProvider;
import exhibition.exhibition.service.SeriesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/series")
public class SeriesController {

    private final SeriesService seriesService;
    private final JwtProvider jwtProvider;

    @PostMapping
    public ResponseEntity<CreateSeries.Response> createSeries(
            @RequestBody @Valid CreateSeries.Request request,
            @RequestHeader("Authorization") String token) {
        Long visitorIdForAuthor = jwtProvider.getAuthentication(token).getId();

        CreateSeries.Response series = seriesService.createSeries(request, visitorIdForAuthor);
        return ResponseEntity.ok(series);
    }
    //TODO: 시리즈 보여주는 기능 구현

    @GetMapping("/series/{seriesNum}")
    public ResponseEntity<?> getSeries(@PathVariable Long seriesNum,
                                       @RequestHeader("Authorization") String token){
        List<Work> series = seriesService.getSeries(seriesNum);
        return ResponseEntity.ok(series);
    }
}
