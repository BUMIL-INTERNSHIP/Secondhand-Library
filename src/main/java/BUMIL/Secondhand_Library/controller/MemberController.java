package BUMIL.Secondhand_Library.controller;

import BUMIL.Secondhand_Library.domain.member.dto.AuthLoginRes;
import BUMIL.Secondhand_Library.domain.member.entity.MemberEntity;
import BUMIL.Secondhand_Library.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final AuthService authService;

    @GetMapping("/login/auth")
    public void login(@RequestParam String code, HttpServletResponse response) throws IOException {
        AuthLoginRes authLoginRes = authService.login(code);
        if (authLoginRes != null) {
            // 토큰 정보를 포함한 URL로 리다이렉션
            String redirectUrl = "http://localhost:8080/login/success?accessToken=" + authLoginRes.getAccessToken() + "&refreshToken=" + authLoginRes.getRefreshToken();
            response.sendRedirect(redirectUrl);
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
        }
    }

    @PatchMapping("/login/auth")
    public ResponseEntity<AuthLoginRes> refreshToken(Authentication authentication) {
        return authService.refreshToken(authentication);
    }

    @GetMapping("/api/userinfo")
    public ResponseEntity<MemberEntity> getUserInfo(Authentication authentication) {
        MemberEntity user = authService.getUserInfo(authentication);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @DeleteMapping("/logout/auth")
    public ResponseEntity<HttpStatus> logout(Authentication authentication){
        return authService.logout(authentication);
    }
}
