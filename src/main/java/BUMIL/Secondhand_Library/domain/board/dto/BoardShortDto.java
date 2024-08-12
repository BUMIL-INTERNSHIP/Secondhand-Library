package BUMIL.Secondhand_Library.domain.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
//전체 글 전용
public class BoardShortDto {

    private Long boardId;
    private String memberName;
    private String bookName;
    private String boardTitle;
    private String boardImg;
    private String address;
    private Long price;
}
