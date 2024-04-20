package fiveguys.Tom.Cafeteria.Server.domain.user.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String socialId;

    @Enumerated(EnumType.STRING)
    private Sex sex;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private SocialType socialType;

    private String name;

    @Email
    private String email;
    private String password;
    private String appleRefreshToken;

    public static void setRoleAdmin(User user) {
        user.role = Role.ADMIN;
    }
    public static void setRoleMember(User user) {
        user.role = Role.MEMBER;
    }

    public static void setAppleRefreshToken(User user, String appleRefreshToken){
        user.setAppleRefreshToken(appleRefreshToken);
    }

    private void setAppleRefreshToken(String appleRefreshToken) {
        this.appleRefreshToken = appleRefreshToken;
    }
}
