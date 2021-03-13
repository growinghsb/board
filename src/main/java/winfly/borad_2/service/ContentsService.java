package winfly.borad_2.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import winfly.borad_2.controller.dto.ContentsDto;
import winfly.borad_2.domain.Contents;
import winfly.borad_2.repository.ContentsRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Service class도 interface를 통해서 test나 controller가
 * 추상화를 바라볼 수 있도록 하는게 사이드이펙트를 줄이는데
 * 도움이 될 거 같다.
 */

@Service
@Transactional
@RequiredArgsConstructor
public class ContentsService {

    private final ContentsRepository repository;

    public Long save(ContentsDto dto) {
        return repository.save(Contents.createContents(dto));
    }

    public ContentsDto findOne(Long id) {
        Contents contents = repository.findOne(id);
        return new ContentsDto(contents.getId(), contents.getWriter(), contents.getTitle(), contents.getContents());
    }

    public List<ContentsDto> findAll() {
        return getContentsDtos(repository.findAll());
    }

    public void revise(ContentsDto dto) {
        Contents.revise(repository.findOne(dto.getId()), dto);
    }

    public void delete(Long id) {
        repository.delete(repository.findOne(id));
    }

    public void viewsUp(Long id) {
        Contents.viewsUp(repository.findOne(id));
    }

    public List<String> findAllWriter() {
        return repository.findAllWriter();
    }

    public List<ContentsDto> keywordSearch(String keyword) {
        return getContentsDtos(repository.keywordSearch(keyword));
    }

    public List<ContentsDto> writerSearch(String writer) {
        return getContentsDtos(repository.writerSearch(writer));
    }

    private List<ContentsDto> getContentsDtos(List<Contents> contents) {
        List<ContentsDto> contentsDtos = new ArrayList<>();
        for (Contents content : contents) {
            contentsDtos.add(new ContentsDto(content.getId()
                    , content.getWriter()
                    , content.getTitle()
                    , content.getContents()
                    , content.getWriteTime()
                    , content.getModifyTime()
                    , content.getViews()));
        }
        return contentsDtos;
    }
}


