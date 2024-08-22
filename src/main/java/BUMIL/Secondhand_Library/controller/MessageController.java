package BUMIL.Secondhand_Library.controller;

import BUMIL.Secondhand_Library.domain.member.entity.MemberEntity;
import BUMIL.Secondhand_Library.domain.member.repository.MemberRepository;
import BUMIL.Secondhand_Library.domain.message.dto.MessageDto;
import BUMIL.Secondhand_Library.service.MessageService;
import BUMIL.Secondhand_Library.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class MessageController {

    private final MessageService messageService;
    private final MemberRepository memberRepository;
    private final AuthService authService;

    @PostMapping("/messages")
    public ResponseEntity<?> sendMessage(@RequestBody MessageDto messageDto, Authentication authentication) {
        MemberEntity member = authService.getUserInfo(authentication);
        messageDto.setSenderName(member.getMemberName());

        messageService.write(messageDto);
        return new ResponseEntity<>("쪽지를 보냈습니다.", HttpStatus.CREATED);
    }

    @GetMapping("/messages/received")
    public ResponseEntity<?> getReceivedMessage(Authentication authentication) {
        MemberEntity member = authService.getUserInfo(authentication);
        return new ResponseEntity<>(messageService.receivedMessage(member), HttpStatus.OK);
    }

    @DeleteMapping("/messages/received/{id}")
    public ResponseEntity<?> deleteReceivedMessage(@PathVariable("id") Long id, Authentication authentication) {
        MemberEntity member = authService.getUserInfo(authentication);
        messageService.deleteMessageByReceiver(id, member);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/messages/sent")
    public ResponseEntity<?> getSentMessage(Authentication authentication) {
        MemberEntity member = authService.getUserInfo(authentication);
        return new ResponseEntity<>(messageService.sentMessage(member), HttpStatus.OK);
    }

    @DeleteMapping("/messages/sent/{id}")
    public ResponseEntity<?> deleteSentMessage(@PathVariable("id") Long id, Authentication authentication) {
        MemberEntity member = authService.getUserInfo(authentication);
        messageService.deleteMessageBySender(id, member);
        return ResponseEntity.noContent().build();
    }
}
