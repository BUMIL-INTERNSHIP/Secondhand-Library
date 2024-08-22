package BUMIL.Secondhand_Library.service;

import BUMIL.Secondhand_Library.domain.member.entity.MemberEntity;
import BUMIL.Secondhand_Library.domain.member.repository.MemberRepository;
import BUMIL.Secondhand_Library.domain.message.dto.MessageDto;
import BUMIL.Secondhand_Library.domain.message.entity.MessageEntity;
import BUMIL.Secondhand_Library.domain.message.repository.MessageRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public MessageDto write(MessageDto messageDto) {
        MemberEntity receiver = memberRepository.findByMemberName(messageDto.getReceiverName());
        MemberEntity sender = memberRepository.findByMemberName(messageDto.getSenderName());

        MessageEntity message = new MessageEntity();
        message.setReceiver(receiver);
        message.setSender(sender);

        message.setTitle(messageDto.getTitle());
        message.setContent(messageDto.getContent());
        message.setDeletedByReceiver(false);
        message.setDeletedBySender(false);
        messageRepository.save(message);

        return MessageDto.toDto(message);
    }


    @Transactional
    public List<MessageDto> receivedMessage(MemberEntity member) {
        // 받은 편지함 불러오기
        // 한 명의 유저가 받은 모든 메시지
        List<MessageEntity> messages = messageRepository.findAllByReceiver(member);
        List<MessageDto> messageDtos = new ArrayList<>();

        for(MessageEntity message : messages) {
            // message 에서 받은 편지함에서 삭제하지 않았으면 보낼 때 추가해서 보내줌
            if(!message.isDeletedByReceiver()) {
                messageDtos.add(MessageDto.toDto(message));
            }
        }
        return messageDtos;
    }

    // 받은 편지 삭제
    @Transactional
    public Object deleteMessageByReceiver(Long id, MemberEntity member) {

        // Logger 설정
        Logger logger = LoggerFactory.getLogger(this.getClass());

        // 로그 출력
        logger.info("Received ID: " + id);

        MessageEntity message = messageRepository.findById(id).orElseThrow(() -> {
            return new IllegalArgumentException("메시지를 찾을 수 없습니다.");
        });

        if(member == message.getSender()) {
            message.deleteByReceiver(); // 받은 사람에게 메시지 삭제
            if (message.isDeleted()) {
                // 받은사람과 보낸 사람 모두 삭제했으면, 데이터베이스에서 삭제요청
                messageRepository.delete(message);
                return "양쪽 모두 삭제";
            }
            return "한쪽만 삭제";
        } else {
            return new IllegalArgumentException("유저 정보가 일치하지 않습니다.");
        }
    }



    @Transactional
    public List<MessageDto> sentMessage(MemberEntity member) {
        // 보낸 편지함 불러오기
        // 한 명의 유저가 받은 모든 메시지
        List<MessageEntity> messages = messageRepository.findAllBySender(member);
        List<MessageDto> messageDtos = new ArrayList<>();

        for(MessageEntity message : messages) {
            // message 에서 받은 편지함에서 삭제하지 않았으면 보낼 때 추가해서 보내줌
            if(!message.isDeletedBySender()) {
                messageDtos.add(MessageDto.toDto(message));
            }
        }
        return messageDtos;
    }


    // 보낸 편지 삭제
    @Transactional
    public Object deleteMessageBySender(Long id, MemberEntity member) {

        MessageEntity message = messageRepository.findById(id).orElseThrow(() -> {
            return new IllegalArgumentException("메시지를 찾을 수 없습니다.");
        });

        if(member == message.getSender()) {
            message.deleteBySender(); // 받은 사람에게 메시지 삭제
            if (message.isDeleted()) {
                // 받은사람과 보낸 사람 모두 삭제했으면, 데이터베이스에서 삭제요청
                messageRepository.delete(message);
                return "양쪽 모두 삭제";
            }
            return "한쪽만 삭제";
        } else {
            return new IllegalArgumentException("유저 정보가 일치하지 않습니다.");
        }


    }
}