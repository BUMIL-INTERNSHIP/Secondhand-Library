package BUMIL.Secondhand_Library.controller;

import BUMIL.Secondhand_Library.domain.member.dto.AuthLoginRes;
import BUMIL.Secondhand_Library.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final AuthService authService;

    @GetMapping("/login")
    public ResponseEntity<AuthLoginRes> login(String code) throws IOException {
        return authService.login(code);
    }

    //Authorization 헤더에 RefreshToken
    @PatchMapping("/login")
    public ResponseEntity<AuthLoginRes> login(Authentication authentication){
        return authService.login(authentication);
    }

    //서버에 남은 RefreshToken 삭제
    @DeleteMapping("/logout")
    public ResponseEntity<HttpStatus> logout(Authentication authentication){
        return authService.logout(authentication);
    }


}
