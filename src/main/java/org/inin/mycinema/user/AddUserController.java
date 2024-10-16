package org.inin.mycinema.user;


import jakarta.annotation.Resource;
import org.inin.mycinema.user.mapper.UserMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class AddUserController {

    @Resource
    UserMapper userMapper;

    /* 회원 가입 처리 */
    @GetMapping(value = "/addUser")
    public Map<String, String> addUser(@RequestParam String authCode) throws Exception {

        ReqKakaoController kakao = new ReqKakaoController();
        
        // 0. validation
        if (authCode == null || authCode.isEmpty()) {
            throw new Exception();
        }

        // 1. get tokens
        Map<String, String> kakaoTokenMap = kakao.reqAccessToken(authCode);
        // 2. get user info
        Map<String, String> kakaoUserInfoMap = kakao.reqUserInfo(kakaoTokenMap);

        // 3. insert into db
        Map<String, String> insertUserMapper = new HashMap<>();
        insertUserMapper.put("name", kakaoUserInfoMap.get("name"));
        insertUserMapper.put("email", kakaoUserInfoMap.get("email"));
        insertUserMapper.put("accessToken", kakaoTokenMap.get("accessToken"));
        insertUserMapper.put("refreshToken", kakaoTokenMap.get("refreshToken"));
        try {
            userMapper.insertUser(insertUserMapper);
        } catch (Exception e) {
            throw new Exception();
        }

        // 4. return response
        Map<String, String> respMap = new HashMap<>();
        respMap.put("userNo", insertUserMapper.get("userNo"));
        respMap.put("name", kakaoUserInfoMap.get("name"));
        respMap.put("email", kakaoUserInfoMap.get("email"));
        respMap.put("refreshToken", kakaoTokenMap.get("refresh_token"));

        return respMap;
    }

}
