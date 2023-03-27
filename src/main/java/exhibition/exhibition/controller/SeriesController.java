package exhibition.exhibition.controller;

import exhibition.exhibition.dto.CreateSeries;
import exhibition.exhibition.security.JwtProvider;
import exhibition.exhibition.service.SeriesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
}
