package fiveguys.Tom.Cafeteria.Server.domain.board.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class PostPreviewDTO {
    private Long id;
    private String title;
    private String content;
    private String publisherName;
    private int likeCount;
    private LocalDateTime uploadTime;
    //DTO 수정하고 Controller, Service 수정해야함
}
