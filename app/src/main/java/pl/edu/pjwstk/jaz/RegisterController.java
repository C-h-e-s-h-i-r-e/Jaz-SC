package pl.edu.pjwstk.jaz;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class RegisterController {
    @PostMapping("/register")
    public void register(@RequestBody RegisterRequest registerRequest){
        //
    }
}
