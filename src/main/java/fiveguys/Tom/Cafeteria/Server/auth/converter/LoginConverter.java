package fiveguys.Tom.Cafeteria.Server.auth.converter;

import fiveguys.Tom.Cafeteria.Server.auth.dto.LoginResponseDTO;

public class LoginConverter {

    public static LoginResponseDTO.LoginDTO toLoginDTO(String accessToken, String refreshToken){
        return LoginResponseDTO.LoginDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();

    }
}
