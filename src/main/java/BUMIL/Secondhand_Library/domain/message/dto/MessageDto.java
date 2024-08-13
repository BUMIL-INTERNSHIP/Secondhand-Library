package BUMIL.Secondhand_Library.domain.message.dto;

import BUMIL.Secondhand_Library.domain.message.entity.MessageEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageDto {
    private String title;
    private String content;
    private String senderName;
    private String receiverName;

    public static MessageDto toDto(MessageEntity message) {
        return new MessageDto(
                message.getTitle(),
                message.getContent(),
                message.getSender().getMemberName(),
                message.getReceiver().getMemberName()
        );
    }
}