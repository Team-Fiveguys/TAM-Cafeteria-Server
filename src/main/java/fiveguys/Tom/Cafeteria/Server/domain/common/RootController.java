package fiveguys.Tom.Cafeteria.Server.domain.common;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RootController {
    @GetMapping("/health")
    public String healthCheck(){
        return "i'm healthy!!!";

    }
}
