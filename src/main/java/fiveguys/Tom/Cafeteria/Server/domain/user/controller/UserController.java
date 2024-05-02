package fiveguys.Tom.Cafeteria.Server.domain.user.controller;


import fiveguys.Tom.Cafeteria.Server.apiPayload.ApiResponse;
import fiveguys.Tom.Cafeteria.Server.domain.user.dto.UserRequestDTO;
import fiveguys.Tom.Cafeteria.Server.domain.user.dto.UserResponseDTO;
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

    @PutMapping("/notificationSet")
    @Operation(summary = "알림 항목을 조회하는 API", description = "현재 설정된 알림 항목에 대한 정보를 응답한다.")
    public ApiResponse<UserResponseDTO.QueryNotificationSet> queryNotification(){
        UserResponseDTO.QueryNotificationSet notificationSet = userQueryService.getNotificationSet();
        return ApiResponse.onSuccess(notificationSet);
    }

    @GetMapping("/notificationSet")
    @Operation(summary = "알림 항목을 업데이트 하는 API", description = "알림 항목에 대한 동의 여부를 받아서 저장시킨다. 구독과 항상 같이 이루어져야 한다.")
    public ApiResponse<String> modifyNotification(@RequestBody UserRequestDTO.UpdateNotificationSet dto){
        userCommandService.updateNotificationSet(dto);
        return ApiResponse.onSuccess("알림 항목이 수정 되었습니다.");
    }

    @PostMapping("/notifications/{notification-id}")
    @Operation(summary = "알림을 수신 받음을 서버에 알리는 API", description = "PathVariable으로 알림id를 전달하여" +
            "유저가 해당 알림에 대해서 제어할 수 있도록 한다.")
    public ApiResponse<String> receiveNotification(@PathVariable(name = "notification-id") Long id){
        userCommandService.receiveMessage(id);
        return ApiResponse.onSuccess("알림 수신 여부를 서버에 전달하였습니다.");
    }
    @GetMapping("/notifications")
    @Operation(summary = "수신받은 알림을 조회하는 API", description = "토큰에 있는 userId를 통해 유저를 식별하여 유저가" +
            "수신받은 알림 리스트를 응답한다. 추후에 한달이 지나면 자동으로 삭제하는 API 만들 것임")
    public ApiResponse<UserResponseDTO.QueryNotificationList> receiveNotification(){
        return ApiResponse.onSuccess(userQueryService.getNotifications());
    }

    @PatchMapping("/notifications/{notification-id}/read")
    @Operation(summary = "알림 하나를 읽음 처리 하는 API", description = "알림 id를 받아 알림을 조회하여 읽음 처리를 한다.")
    public ApiResponse<String> readNotification(@PathVariable(name = "notification-id") Long id){
        userCommandService.readNotification(id);
        return ApiResponse.onSuccess("해당 알림이 읽음 처리 되었습니다.");
    }

    @PatchMapping("/notifications/read")
    @Operation(summary = "유저의 모든 알림을 읽음 처리 하는 API", description = "모든 알림을 읽음 처리를 한다.")
    public ApiResponse<String> readNotifications(){
        userCommandService.readAllNotification();
        return ApiResponse.onSuccess("모든 알림들이 읽음 처리 되었습니다.");
    }

    @DeleteMapping("/notifications/{notification-id}")
    @Operation(summary = "알림 하나를 삭제 처리 하는 API", description = "알림 id를 받아 유저에게 보내진 알림을 삭제 한다.")
    public ApiResponse<String> deleteNotification(@PathVariable(name = "notification-id") Long id){
        userCommandService.deleteNotification(id);
        return ApiResponse.onSuccess("해당 알림이 삭제 처리 되었습니다.");
    }
    @DeleteMapping("/notifications")
    @Operation(summary = "알림 하나를 삭제 처리 하는 API", description = "모든 알림을 삭제 한다.")
    public ApiResponse<String> deleteNotifications(){
        userCommandService.deleteNotifications();
        return ApiResponse.onSuccess("모든 알림들이 삭제 되었습니다.");
    }
}
