package winfly.borad_2.domain;

import winfly.borad_2.controller.dto.ContentsDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static java.time.LocalDateTime.now;
import static java.time.format.DateTimeFormatter.ofPattern;
import static javax.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

/**
 * 생성자를 막아두는 이유는 따로 생성 비즈니스 메서드가 있기 때문에
 * 해당 메서드에서만 Entity 생성을 제한하고자 함이다.
 * 기본 생성자로 막 생성할 수 없도록 protected로 막아두었다.
 *
 * 비즈니스 메서드가 Entity 내부에 있는 이유는
 * Entity는 독립적이며 자율적이기 때문에 자신의 상태에 대한
 * 책임을 질 수 있다. 그렇기에 수정과 조회수 추가를
 * 해당 Entity가 변경할 책임을 부여한 것이다.
 *
 * 이렇게 함으로써 응집도를 높힐 수 있다.
 * 객체가 해야 할 행동을 객체 내부에 명시 함으로써
 * 외부에서는 객체에게 요청만 할 수 있게 되었다.
 */

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Contents {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "contents_id")
    private Long id;

    private String writer;
    private String title;
    private String contents;
    private String writeTime;
    private String modifyTime;
    private int views;

    public static Contents createContents(ContentsDto dto) {
        Contents contents = new Contents();
        contents.writer = dto.getWriter();
        contents.title = dto.getTitle();
        contents.contents = dto.getContents();
        contents.writeTime = currentTime();
        contents.modifyTime = "";
        contents.views = 0;

        return contents;
    }

    public static void revise(Contents contents, ContentsDto dto) {
        contents.writer = dto.getWriter();
        contents.title = dto.getTitle();
        contents.contents = dto.getContents();
        contents.modifyTime = currentTime();
    }

    public static void viewsUp(Contents contents) {
        contents.views = contents.getViews() + 1;
    }

    private static String currentTime() {
        return now().format(ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
