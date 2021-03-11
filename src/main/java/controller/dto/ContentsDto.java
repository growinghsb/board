package controller.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContentsDto {

    public ContentsDto() {
    }

    // createContents, revise 용 생성자.
    public ContentsDto(String writer, String title, String contents, String time) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.time = time;
    }

    private Long id;
    private String writer;
    private String title;
    private String contents;
    private String time;
    private int views;
}
