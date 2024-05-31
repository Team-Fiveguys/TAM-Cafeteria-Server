package fiveguys.Tom.Cafeteria.Server.domain.board.dto;


import fiveguys.Tom.Cafeteria.Server.domain.board.entity.ReportType;
import lombok.Getter;

@Getter
public class ReportCreateDTO {
    private ReportType reportType;
    private String content;
}
