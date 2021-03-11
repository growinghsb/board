package winfly.borad_2.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import winfly.borad_2.controller.dto.ContentsDto;

import java.util.List;

import static java.time.LocalDateTime.now;
import static java.time.format.DateTimeFormatter.ofPattern;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class ContentsServiceTest {

    @Autowired
    ContentsService service;

    @Test
    public void 저장_테스트() {
        //given (~ 가정했을 때)
        Long id = saveContent();

        //when (~ 할 때), then (그렇다면) 
        assertThat(id).isNotZero();

    }

    @Test
    public void 단건_조회_테스트() {
        //given (~ 가정했을 때)
        Long id = saveContent();

        //when (~ 할 때), then (그렇다면)
        ContentsDto dto = service.findOne(id);

        assertThat(dto).isNotNull();
        assertThat(dto.getWriter()).isEqualTo("한승범");
        assertThat(dto.getTitle()).isEqualTo("테스트");
        assertThat(dto.getContents()).isNotEmpty();
    }

    @Test
    public void 전체_조회_테스트() {
        //given (~ 가정했을 때)
        for (int i = 0; i < 5; i++) {
            saveContent();
        }
        List<ContentsDto> contentsDtos = service.findAll();
        //when (~ 할 때), then (그렇다면)

        assertThat(contentsDtos).isNotNull();
        assertThat(contentsDtos).isNotEmpty();
        assertThat(contentsDtos.size()).isEqualTo(5);
    }
    
    @Test
    public void 수정_테스트() {
        //given (~ 가정했을 때)
        Long id = saveContent();
        ContentsDto dto = service.findOne(id);
        dto.setWriter("승범");
        dto.setTitle("승범 테스트");
        dto.setContents("승범 승범");
        service.revise(dto);


        //when (~ 할 때), then (그렇다면)
        dto = service.findOne(id);

        assertThat(dto.getModifyTime()).isNotEqualTo("");
        //assertThat(dto.getModifyTime()).contains(now().format(ofPattern("yyyy-mm-dd"))); 이거 시간은 따로 해야 되는 거 같은데 공부하고 다시하자.
        assertThat(dto.getWriter()).isEqualTo("승범");
        assertThat(dto.getTitle()).isEqualTo("승범 테스트");
        assertThat(dto.getContents()).isEqualTo("승범 승범");
    }

    @Test
    public void 삭제_테스트() {
        //given (~ 가정했을 때)
        Long id = saveContent();
        List<ContentsDto> dtos = service.findAll();

        //when (~ 할 때), then (그렇다면)
        service.delete(id);

        assertThat(dtos.size()).isEqualTo(service.findAll().size() + 1);
    }

    private Long saveContent() {
        Long id = service.save(new ContentsDto("한승범", "테스트", "테스트 내용"));
        return id;
    }
}