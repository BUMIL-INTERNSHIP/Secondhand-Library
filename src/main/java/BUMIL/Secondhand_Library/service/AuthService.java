package BUMIL.Secondhand_Library.service;

import BUMIL.Secondhand_Library.domain.member.dto.AuthLoginRes;
import BUMIL.Secondhand_Library.domain.member.entity.MemberEntity;
import BUMIL.Secondhand_Library.domain.member.repository.MemberRepository;
import BUMIL.Secondhand_Library.global.jwt.JwtTokenProvider;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    @Value("${kakao.auth.client_id}")
    private String client_id;

    @Value("${kakao.auth.redirect_uri}")
    private String redirect_uri;

    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthLoginRes login(String code) throws IOException {
        String kakaoAccessToken = getKakaoAccessToken(code);
        JsonElement element = getJsonElementByAccessToken(kakaoAccessToken);

        Long outhId = element.getAsJsonObject().get("id").getAsLong();
        JsonObject properties = element.getAsJsonObject().get("properties").getAsJsonObject();
        JsonObject kakao_account = element.getAsJsonObject().get("kakao_account").getAsJsonObject();

        String nickname = properties.getAsJsonObject().get("nickname").getAsString();
        String email = kakao_account.getAsJsonObject().get("email").getAsString();

        MemberEntity user = memberRepository.findByOuthId(outhId);
        if(user == null)
            return register(outhId, nickname, email);

        String accessToken = jwtTokenProvider.createAccessToken(outhId);
        String refreshToken = jwtTokenProvider.createRefreshToken(outhId);

        user.setRefreshToken(refreshToken);
        memberRepository.save(user);

        return new AuthLoginRes(accessToken, refreshToken);
    }

    public ResponseEntity<AuthLoginRes> refreshToken(Authentication authentication) {
        MemberEntity user = memberRepository.findByOuthId(Long.valueOf(authentication.getName()));
        if(user == null)
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        String accessToken = jwtTokenProvider.createAccessToken(user.getOuthId());
        return new ResponseEntity<>(new AuthLoginRes(accessToken, user.getRefreshToken()), HttpStatus.OK);
    }

    public ResponseEntity<HttpStatus> logout(Authentication authentication){
        MemberEntity member = memberRepository.findByOuthId(Long.valueOf(authentication.getName()));
        if(member == null)
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        member.setRefreshToken(null);
        memberRepository.save(member);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    public MemberEntity getUserInfo(Authentication authentication) {
        return memberRepository.findByOuthId(Long.valueOf(authentication.getName()));
    }

    private AuthLoginRes register(Long outhId, String nickname, String email){
        String accessToken = jwtTokenProvider.createAccessToken(outhId);
        String refreshToken = jwtTokenProvider.createRefreshToken(outhId);

        MemberEntity user = MemberEntity.builder()
                .outhId(outhId)
                .memberName(nickname)
                .email(email)
                .refreshToken(refreshToken)
                .build();

        memberRepository.save(user);

        return new AuthLoginRes(accessToken, refreshToken);
    }

    private JsonElement getJsonElementByAccessToken(String token) throws IOException {
        String reqUrl = "https://kapi.kakao.com/v2/user/me";
        URL url = new URL(reqUrl);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("GET");
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setRequestProperty("Authorization", "Bearer " + token);

        return getJsonElement(httpURLConnection);
    }

    public String getKakaoAccessToken (String authorize_code) throws UnsupportedEncodingException {
        String access_Token = "";
        String reqURL = "https://kauth.kakao.com/oauth/token";

        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code");
            sb.append("&client_id="+client_id);
            sb.append("&redirect_uri="+redirect_uri);
            sb.append("&code=" + authorize_code);
            bw.write(sb.toString());
            bw.flush();
            int responseCode = conn.getResponseCode();  
            log.info("responseCode : " + responseCode);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            StringBuilder result = new StringBuilder();

            while ((line = br.readLine()) != null) {
                result.append(line);
            }
            log.info("response body : " + result);

            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result.toString());

            access_Token = element.getAsJsonObject().get("access_token").getAsString();

            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return access_Token;
    }

    private JsonElement getJsonElement(HttpURLConnection httpURLConnection) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
        String line;
        StringBuilder result = new StringBuilder();

        while((line = bufferedReader.readLine()) != null){
            result.append(line);
        }

        bufferedReader.close();
        return JsonParser.parseString(result.toString());
    }
}
