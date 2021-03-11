package winfly.borad_2.service;

import winfly.borad_2.controller.dto.ContentsDto;
import winfly.borad_2.domain.Contents;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import winfly.borad_2.repository.ContentsRepository;

import java.util.ArrayList;
import java.util.List;

import static java.time.LocalDateTime.*;
import static java.time.format.DateTimeFormatter.*;

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
        List<Contents> contents = repository.findAll();
        List<ContentsDto> contentsDto = new ArrayList<>();
        for (Contents content : contents) {
            contentsDto.add(new ContentsDto(content.getId()
                    , content.getWriter()
                    , content.getTitle()
                    , content.getContents()
                    , content.getWriteTime()
                    , content.getModifyTime()
                    , content.getViews()));
        }
        return contentsDto;
    }

    public void revise(ContentsDto dto) {
        Contents.revise(repository.findOne(dto.getId()), dto);
    }

    public void delete(Long id) {
        repository.delete(repository.findOne(id));
    }


}


