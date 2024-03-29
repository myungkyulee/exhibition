package exhibition.exhibition.service;

import exhibition.exhibition.domain.Author;
import exhibition.exhibition.domain.ImageFile;
import exhibition.exhibition.domain.Work;
import exhibition.exhibition.dto.CreateWork;
import exhibition.exhibition.dto.GetWorkInfo;
import exhibition.exhibition.exception.ErrorCode;
import exhibition.exhibition.exception.ExhibitionException;
import exhibition.exhibition.repository.AuthorRepository;
import exhibition.exhibition.repository.WorkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WorkService {
    private final AuthorRepository authorRepository;
    private final WorkRepository workRepository;

    @Transactional
    public CreateWork.Response createWork(Long visitorId, String title, String description, ImageFile image) {
        Author author = authorRepository.findByVisitorId(visitorId)
                .orElseThrow(() -> new RuntimeException("없는 작가입니다."));

        Work work = Work.builder()
                .title(title)
                .description(description)
                .author(author)
                .image(image)
                .build();

        return CreateWork.Response.fromEntity(workRepository.save(work));
    }

    public GetWorkInfo.Response getWorkInfo(Long workId) {
        Work work = workRepository.findById(workId)
                .orElseThrow(() -> new ExhibitionException(ErrorCode.NOT_FOUND_WORK));

        return GetWorkInfo.Response.fromEntity(work);
    }
}
