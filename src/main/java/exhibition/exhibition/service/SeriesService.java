package exhibition.exhibition.service;

import exhibition.exhibition.domain.Author;
import exhibition.exhibition.domain.Series;
import exhibition.exhibition.domain.Work;
import exhibition.exhibition.dto.CreateSeries;
import exhibition.exhibition.exception.ErrorCode;
import exhibition.exhibition.exception.ExhibitionException;
import exhibition.exhibition.repository.AuthorRepository;
import exhibition.exhibition.repository.SeriesRepository;
import exhibition.exhibition.repository.WorkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SeriesService {

    private final AuthorRepository authorRepository;
    private final WorkRepository workRepository;
    private final SeriesRepository seriesRepository;

    @Transactional
    public CreateSeries.Response createSeries(CreateSeries.Request request, Long visitorId) {
        Author author = authorRepository.findByVisitorId(visitorId)
                .orElseThrow(() -> new ExhibitionException(ErrorCode.NOT_FOUND_AUTHOR));
        Work coverWork = workRepository.findById(request.getCoverWorkId())
                .orElseThrow(() -> new ExhibitionException(ErrorCode.NOT_FOUND_WORK));

        Series series = seriesRepository.save(Series.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .coverWork(coverWork)
                .author(author)
                .build());

        request.getWorkIdList().stream()
                .map(id -> workRepository.findById(id)
                        .orElseThrow(() -> new ExhibitionException(ErrorCode.NOT_FOUND_WORK)))
                .forEach(work -> work.setSeries(series));

        return CreateSeries.Response.fromEntity(series);
    }

}
