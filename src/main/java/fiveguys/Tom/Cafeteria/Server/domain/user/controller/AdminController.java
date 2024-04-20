package fiveguys.Tom.Cafeteria.Server.domain.user.controller;


import fiveguys.Tom.Cafeteria.Server.apiPayload.ApiResponse;
import fiveguys.Tom.Cafeteria.Server.domain.user.service.UserCommandService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final UserCommandService userCommandService;

    @Operation(summary = "관리자 권한 부여 API",description = "일반 회원에게 관리자 권한을 부여합니다.")
    @PatchMapping("/users/{user-id}/role/admin")
    public ApiResponse<String> grantAdminRole(@PathVariable(value = "user-id") Long userId){
        userCommandService.grantAdmin(userId);
        return ApiResponse.onSuccess(userId + "번 회원 관리자 권한 부여");
    }
    @Operation(summary = "관리자 권한 회수 API",description = "지정된 회원의 관리자 권한을 회수합니다.")
    @PatchMapping("/users/{user-id}/role/user")
    public ApiResponse<String> depriveAdminRole(@PathVariable(value = "user-id") Long userId){
        userCommandService.depriveAdmin(userId);
        return ApiResponse.onSuccess(userId + "번 회원 관리자 권한 회수");
    }
}
