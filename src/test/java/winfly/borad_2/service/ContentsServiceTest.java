package winfly.borad_2.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import winfly.borad_2.controller.dto.ContentsDto;

import javax.persistence.EntityManager;
import java.util.List;

import static java.time.LocalDateTime.now;
import static java.time.format.DateTimeFormatter.ofPattern;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class ContentsServiceTest {

    @Autowired
    ContentsService service;

    @Autowired
    EntityManager em;

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

    @Test
    public void 조회수_증가_테스트() {
        //given (~ 가정했을 때)
        Long id = saveContent();
        //assertThat(service.findOne(id).getId()).isEqualTo(1);

        //when (~ 할 때), then (그렇다면)
        service.viewsUp(id);

        /**
         * 테스트가 힘든데..이거 잘못짠건가?
         * 메서드 매개변수가 궂이 필요 없는데
         * 어떻게 조회를 해야 좋을까
         * 테스트 공부를 해야겠다.
         */



       /* assertThat(service.findOne(id).getViews()).isNotZero();
        assertThat(service.findOne(id).getViews()).isEqualTo(1);
        제대로 테스트가 안됬던 이유는 findOne 하면 dto를 반환하기 때문이다.
        테스트 시 타입에 대한 검증도 하자. Entity를 반환해야 하는데
        dto를 반환해 값이 반영이 안됬던 것이다.
        */
    }
    
    @Test
    public void 작성자_검색_테스트() {
        //given
        service.save(new ContentsDto("한승범", "테스트", "테스트 내용"));
        service.save(new ContentsDto("한승범", "테스트", "테스트 내용"));
        service.save(new ContentsDto("김승범", "테스트", "테스트 내용"));
        service.save(new ContentsDto("윤승범", "테스트", "테스트 내용"));
        service.save(new ContentsDto("윤승범", "테스트", "테스트 내용"));
        service.save(new ContentsDto("윤승범", "테스트", "테스트 내용"));
        service.save(new ContentsDto("윤승범", "테스트", "테스트 내용"));
        
        //when
        List<ContentsDto> 한승범 = service.writerSearch("한승범");
        List<ContentsDto> 김승범 = service.writerSearch("김승범");
        List<ContentsDto> 윤승범 = service.writerSearch("윤승범");
            
        //than
        assertThat(service.findAll().size()).isEqualTo(7);
        assertThat(한승범.size()).isEqualTo(2);
        assertThat(김승범).isNotEmpty();
        assertThat(윤승범).isNotNull();
    
    }

    @Test
    public void 키워드_검색_테스트() {
        //given
        service.save(new ContentsDto("한승범", "테스트1", "테스트 내용"));
        service.save(new ContentsDto("한승범", "테스트1", "테스트 내용"));
        service.save(new ContentsDto("김승범", "테스트2", "테스트 내용"));
        service.save(new ContentsDto("윤승범", "테스트2", "테스트 내용"));
        service.save(new ContentsDto("윤승범", "테스트2", "테스트 내용"));
        service.save(new ContentsDto("윤승범", "테스트3", "테스트 내용 이건 좀 다름"));
        service.save(new ContentsDto("윤승범", "테스트3", "테스트 내용"));


        //when
        List<ContentsDto> 테스트1 = service.keywordSearch("1");
        List<ContentsDto> 테스트2 = service.keywordSearch("2");
        List<ContentsDto> 테스트3 = service.keywordSearch("3");

        //than
        assertThat(테스트1).isNotNull();
        assertThat(테스트1).isNotEmpty();
        assertThat(테스트2.size()).isNotZero();
        assertThat(테스트2.size()).isEqualTo(3);
        assertThat(테스트3.get(0).getContents()).isEqualTo("테스트 내용 이건 좀 다름");


    }

    private Long saveContent() {
        Long id = service.save(new ContentsDto("한승범", "테스트", "테스트 내용"));
        return id;
    }
}