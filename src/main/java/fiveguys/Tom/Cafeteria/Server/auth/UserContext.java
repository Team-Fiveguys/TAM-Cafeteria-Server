package fiveguys.Tom.Cafeteria.Server.auth;

public class UserContext {
    private static final ThreadLocal<Long> authenticatedUser = new ThreadLocal<>();

    public static void setUserId(Long userId){
        authenticatedUser.set(userId);
    }
    public static Long getUserId(){
        return authenticatedUser.get();
    }
    public static void clearUserId(){
        authenticatedUser.remove();
    }
}