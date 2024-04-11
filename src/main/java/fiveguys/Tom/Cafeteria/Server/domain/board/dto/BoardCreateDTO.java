package fiveguys.Tom.Cafeteria.Server.domain.board.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoardCreateDTO {
    private String title;
    private String content;
    private String boardType; // 예시를 단순화하기 위해 String 사용, 실제로는 Enum 타입을 사용할 수 있음
    private boolean isAdminPick;

    @Override
    public String toString() {
        return "BoardCreateDto{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", boardType='" + boardType + '\'' +
                ", isAdminPick=" + isAdminPick +
                '}';
    }
}
