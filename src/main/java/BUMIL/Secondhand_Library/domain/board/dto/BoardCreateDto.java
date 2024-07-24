package BUMIL.Secondhand_Library.domain.board.dto;

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
}