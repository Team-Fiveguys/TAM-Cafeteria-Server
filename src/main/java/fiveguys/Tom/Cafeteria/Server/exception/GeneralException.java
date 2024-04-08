package fiveguys.Tom.Cafeteria.Server.exception;

import fiveguys.Tom.Cafeteria.Server.apiPayload.code.BaseCode;
import fiveguys.Tom.Cafeteria.Server.apiPayload.code.ReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;



@Getter
@AllArgsConstructor
public class GeneralException extends RuntimeException{
    private BaseCode code;

    public ReasonDTO getErrorReasonHttpStatus(){
        return this.code.getReasonHttpStatus();
    }
}