package BUMIL.Secondhand_Library.domain.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
//글 상세 페이지 전용
public class BoardResDto{

    private Long boardId;
    private Long memberOuthId;
    private String memberName;
    private String bookName;
    private String boardTitle;
    private String boardContent;
    private String boardImg;
    private String address;
    private Long price;
    private Long currentUserOuthId;
}