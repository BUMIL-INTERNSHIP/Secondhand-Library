package BUMIL.Secondhand_Library.domain.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardResDto{

    private Long boardId;
    private String memberName;
    private String boardTitle;
    private String boardContent;
    private String boardImg;
    private String address;
    private Long price;
}