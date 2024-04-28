package fiveguys.Tom.Cafeteria.Server.domain.user.controller;


import fiveguys.Tom.Cafeteria.Server.apiPayload.ApiResponse;
import fiveguys.Tom.Cafeteria.Server.domain.user.service.UserCommandService;
import fiveguys.Tom.Cafeteria.Server.domain.user.service.UserQueryService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserQueryService userQueryService;
    private final UserCommandService userCommandService;
    @PostMapping("/notificationSet")
    @Operation(summary = "알림을 허용시 호출하는 API", description = "기기토큰을 저장시키고" +
            " general 토픽에 대해서 구독을 실시한다, 모든 알림 타입에 대해서도 on 상태")
    public ApiResponse<String> allowNotification(@RequestParam(name = "registrationToken") String token){
        userCommandService.initNotificationSet(token);
        return ApiResponse.onSuccess("알림이 활성화 되었습니다.");
    }

}
