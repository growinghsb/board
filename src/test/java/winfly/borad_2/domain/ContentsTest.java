package winfly.borad_2.domain;

import org.junit.jupiter.api.Test;
import winfly.borad_2.controller.dto.ContentsDto;

import static java.time.LocalDateTime.*;
import static java.time.format.DateTimeFormatter.*;
import static org.assertj.core.api.Assertions.*;

/**
 * Entity 내부에 있는 비즈니스 메서드 테스트
 */
class ContentsTest {

    @Test
    public void contents_생성_테스트() {
        //given (~ 가정했을 때)
        Contents contents = createContent();

        //when (~ 할 때), then (그렇다면)
        assertThat(contents).isNotNull();
        assertThat(contents.getWriter()).isEqualTo("한승범");
    }
    
    @Test
    public void contents_수정_테스트() {
        //given (~ 가정했을 때)
        Contents contents = createContent();
        ContentsDto dto = new ContentsDto("바뀐 한승범", "바뀐 테스트 글", "바뀐 테스트 테스트 테스트");

        //when (~ 할 때), then (그렇다면)
        Contents.revise(contents, dto);

        assertThat(contents.getWriter()).isEqualTo("바뀐 한승범");
        assertThat(contents.getTitle()).isNotEqualTo("테스트 글");
    }
    
    @Test
    public void 조회수_증가_테스트() {
        //given (~ 가정했을 때)
        Contents contents = createContent();
        
        //when (~ 할 때), then (그렇다면) 
        Contents.viewsUp(contents);

        assertThat(contents.getViews()).isNotZero();
        assertThat(contents.getViews()).isEqualTo(1);
    }

    private Contents createContent() {
        ContentsDto dto = new ContentsDto("한승범", "테스트 글", "이건 테스트 테스트 테스트");
        Contents contents = Contents.createContents(dto);
        return contents;
    }
}