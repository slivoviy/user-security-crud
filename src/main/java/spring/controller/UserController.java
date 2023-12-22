package spring.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.model.Usr;

import java.security.Principal;

@RestController
@RequestMapping("api/user/")
public class UserController {

    @GetMapping("")
    public Usr getUser(Principal principal) {
        return (Usr) principal;
    }
}
