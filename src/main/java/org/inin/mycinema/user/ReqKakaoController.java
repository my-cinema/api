package org.inin.mycinema.user;

import org.inin.mycinema.util.Utility;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

public class ReqKakaoController {

    @Value("${kakao.api_key}")
    private String KAKAO_API_KEY;

    /* 카카오 토큰 요청 */
    @RequestMapping("/reqKakaoAccessToken")
    public Map<String, String> reqAccessToken(String authCode) throws Exception {
        RestTemplate restTemplate = new RestTemplate();

        // 0. validation
        if (authCode == null || authCode.isEmpty()) {
            throw new Exception();
        }

        // 1. make http header
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // 2. make http body
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", KAKAO_API_KEY);
        body.add("redirect_uri", "http://localhost:8080/login"); // todo [내 애플리케이션] > [카카오 로그인] > [Redirect URI]에서 등록
        body.add("code", authCode);

        // 3. make http entity
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, headers);

        // 4. send request
        String response = restTemplate.postForObject(
                "https://kauth.kakao.com/oauth/token"
                , requestEntity
                , String.class);

        if (response == null) {
            throw new Exception();
        }

        // 3. json string response -> map
        Map<String, String> responseMap = new Utility().jsonStrToMap(response);

        // 5. return response
        Map<String, String> kakaoTokenMap = new HashMap<>();
        kakaoTokenMap.put("accessToken", responseMap.get("access_token"));
        kakaoTokenMap.put("refreshToken", responseMap.get("refresh_token"));
        return kakaoTokenMap;
    }

    /* 카카오 사용자 정보 요청 */
    public Map<String, String> reqUserInfo(Map<String, String> tokenMap) throws Exception {
        RestTemplate restTemplate = new RestTemplate();

        // 1. make http header
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(tokenMap.get("accessToken"));
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // 2. make http entity
        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(new HashMap<>(), headers);

        // 3. send request
        String response = restTemplate.postForObject(
                "https://kapi.kakao.com/v2/user/me"
                , requestEntity
                , String.class
        );

        if (response == null) {
            throw new Exception();
        }

        // 3. json string response -> map
        Map<String, String> responseMap = new Utility().jsonStrToMap(response);

        return responseMap;
    }

//    @RequestMapping("/reqKakaoUnlink")
//    public String reqKakaoUnlink() {
//        // kakaoUnlink
//        return "";
//    }

}
