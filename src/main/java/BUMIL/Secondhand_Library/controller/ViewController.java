package BUMIL.Secondhand_Library.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ViewController {

    @GetMapping("/login")
    public String login() {
        return "login"; // login.html 파일 반환
    }

    @GetMapping("/login/success")
    public String loginSuccess(@RequestParam String accessToken, @RequestParam String refreshToken, Model model) {
        model.addAttribute("accessToken", accessToken);
        model.addAttribute("refreshToken", refreshToken);
        return "loginSuccess"; // login_success.html로 연결
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }

    @GetMapping("/mypage")
    public String mypage() {
        return "mypage";
    }
}
