package winfly.borad_2.controller.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContentsDto {

    public ContentsDto() {
    }

    // 전체 조회 시 모든 데이터 바인딩용 생성자
    public ContentsDto(Long id, String writer, String title, String contents, String writeTime, String modifyTime, int views) {
        this.id = id;
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.writeTime = writeTime;
        this.modifyTime = modifyTime;
        this.views = views;
    }

    // createContents, revise 테스트용 생성자.
    public ContentsDto(String writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;

    }

    // 조회용 생성자
    public ContentsDto(Long id, String writer, String title, String contents) {
        this.id = id;
        this.writer = writer;
        this.title = title;
        this.contents = contents;
    }

    private Long id;
    private String writer;
    private String title;
    private String writeTime;
    private String modifyTime;
    private String contents;
    private int views;
}
