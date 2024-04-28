package fiveguys.Tom.Cafeteria.Server.domain.user.controller;


import fiveguys.Tom.Cafeteria.Server.apiPayload.ApiResponse;
import fiveguys.Tom.Cafeteria.Server.domain.user.service.UserCommandService;
import fiveguys.Tom.Cafeteria.Server.domain.user.service.UserQueryService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/notification/{notification-id}")
    @Operation(summary = "알림을 수신 받음을 서버에 알리는 API", description = "PathVariable으로 알림id를 전달하여" +
            "유저가 해당 알림에 대해서 제어할 수 있도록 한다.")
    public ApiResponse<String> receiveNotification(@PathVariable(name = "notification-id") Long id){
        userCommandService.receiveMessage(id);
        return ApiResponse.onSuccess("알림 수신 여부를 서버에 전달하였습니다.");
    }
}
