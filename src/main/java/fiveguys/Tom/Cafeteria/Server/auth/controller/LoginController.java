package fiveguys.Tom.Cafeteria.Server.auth.controller;


import fiveguys.Tom.Cafeteria.Server.apiPayload.ApiResponse;
import fiveguys.Tom.Cafeteria.Server.auth.converter.LoginConverter;
import fiveguys.Tom.Cafeteria.Server.auth.dto.LoginRequestDTO;
import fiveguys.Tom.Cafeteria.Server.auth.dto.LoginResponseDTO;
import fiveguys.Tom.Cafeteria.Server.auth.feignClient.TokenResponse;
import fiveguys.Tom.Cafeteria.Server.auth.feignClient.kakao.dto.KakaoResponseDTO;
import fiveguys.Tom.Cafeteria.Server.auth.jwt.JwtToken;
import fiveguys.Tom.Cafeteria.Server.auth.jwt.service.JwtUtil;
import fiveguys.Tom.Cafeteria.Server.auth.jwt.service.TokenProvider;
import fiveguys.Tom.Cafeteria.Server.auth.service.KakaoLoginService;
import fiveguys.Tom.Cafeteria.Server.domain.user.UserConverter;
import fiveguys.Tom.Cafeteria.Server.domain.user.entity.User;
import fiveguys.Tom.Cafeteria.Server.domain.user.service.UserCommandService;
import fiveguys.Tom.Cafeteria.Server.domain.user.service.UserQueryService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {
    private final KakaoLoginService kakaoLoginService;
    private final UserQueryService userQueryService;
    private final UserCommandService userCommandService;
    private final TokenProvider tokenProvider;
    private final JwtUtil jwtUtil;
    @Value("${kakao.redirect-uri}")
    private String redirectUri;
    @Value("${kakao.client-id}")
    private String kakaoClientId;


    /*
  테스트용 API, CORS 때문에 직접 호출하지 않고 redirect
   */
    @GetMapping("/oauth2/authorize/kakao")
    public String login(){
        String toRedirectUri = UriComponentsBuilder.fromUriString("https://kauth.kakao.com/oauth/authorize")
                .queryParam("response_type", "code")
                .queryParam("client_id", kakaoClientId)
                .queryParam("redirect_uri", redirectUri)
                .toUriString();
        return "redirect:" + toRedirectUri;
    }

    @Operation(summary = "카카오 소셜 토큰 검증 API",description = "추가정보와 ID토큰을 받으면 ID토큰을 검증하고 통과 시" +
            "서버에서 발급한 토큰을 받습니다. 회원가입을 하지 않은 사용자의 경우 회원가입을 시킵니다.")
    @ResponseBody
    @PostMapping("/oauth2/kakao/token/validate")
    public ApiResponse<LoginResponseDTO.LoginDTO> validateKakoToken(@RequestBody @Valid LoginRequestDTO.KakaoTokenValidateDTO requestDTO)  {
        // identity 토큰 검증
        // kakaoLoginService.validate(requestDTO.getIdentityToken());
        // ok -> 유저 정보 가져오기
        KakaoResponseDTO.UserInfoResponseDTO userInfo;
        userInfo = kakaoLoginService.getUserInfo(requestDTO.getAccessToken());
        // 유저 정보에 DB 조회하고 정보 있으면 응답만, 없으면 저장까지, 추가정보 입력 여부에 따라서 응답 다르게
        String socialId = userInfo.getSocialId();
        User user;
        if( userQueryService.isExistBySocialId(socialId)){
            user = userQueryService.getUserBySocialId(socialId);
        }
        else{
            user = UserConverter.toUser(userInfo);//, requestDTO.getAdditionalInfo());
            user = userCommandService.create(user);
        }
        // 응답본문에 토큰 추가
        JwtToken token = jwtUtil.generateToken(String.valueOf(user.getId()));
        return ApiResponse.onSuccess(LoginConverter.toLoginDTO(token.getAccessToken(), token.getRefreshToken()));
    }
    /*
    테스트용 API
     */
    @GetMapping("/oauth2/login/kakao")
    @ResponseBody
    public ResponseEntity<String> getAccessToken(@RequestParam(name = "code") String code){
        TokenResponse tokenResponse = kakaoLoginService.getAccessTokenByCode(code);
        return ResponseEntity.ok("accessToken="+ tokenResponse.getAccessToken() +
                "\n\nidToken=" + tokenResponse.getIdToken());
    }
}
