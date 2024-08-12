package BUMIL.Secondhand_Library.domain.board.dto;

import BUMIL.Secondhand_Library.global.CategoryEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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