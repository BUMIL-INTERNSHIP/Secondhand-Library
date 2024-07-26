package BUMIL.Secondhand_Library.domain.board.dto;

import BUMIL.Secondhand_Library.global.CategoryEnum;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardCreateDto {

    private String boardTitle;
    private String bookName;
    private String boardContent;
    private String address;
    private Long price;
    private CategoryEnum category;
}