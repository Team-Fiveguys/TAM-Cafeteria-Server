package fiveguys.Tom.Cafeteria.Server.apiPayload.code.status;

import lombok.AllArgsConstructor;
import lombok.Getter;
import fiveguys.Tom.Cafeteria.Server.apiPayload.code.BaseCode;
import fiveguys.Tom.Cafeteria.Server.apiPayload.code.ReasonDTO;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SuccessStatus implements BaseCode {
    // 일반적인 응답
    _OK(HttpStatus.OK, "COMMON200", "성공입니다.");
    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ReasonDTO getReasonHttpStatus(){
        return ReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(true)
                .httpStatus(httpStatus)
                .build();
    }
}
