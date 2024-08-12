package BUMIL.Secondhand_Library.domain.book.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MemberSelectionDTO {
    private String sex;
    private String age;
    private String location;
    private String Interest;
}