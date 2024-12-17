package ac.kyungnam.book.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "책 ID", example = "1")
    private Long id;

    @Column(nullable = false, length = 45)
    @Schema(description = "책 이름", example = "자바의 정석")
    private String title;

    @Column(nullable = false, length = 45)
    @Schema(description = "책 부제", example = "자바 기초 입문서")
    private String subTitle;

    @Column(length = 45)
    @Schema(description = "저자", example = "남궁성")
    private String author;

    @Column(length = 45)
    @Schema(description = "출판사", example = "도우출판")
    private String publisher;

    @Enumerated(value = EnumType.STRING)
    @Schema(description = "책 상태", example = "AVAILABLE")
    private Status status;

    public enum Status {
        BORROWED,
        AVAILABLE
    }
}
