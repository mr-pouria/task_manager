package ir.tasktop.taskTop.controller;


import ir.tasktop.taskTop.dto.LoginDto;
import ir.tasktop.taskTop.dto.RegisterDto;
import ir.tasktop.taskTop.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    UserService userService;


    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterDto registerDto , BindingResult bindingResult) {
       return userService.register(registerDto , bindingResult);
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDto loginDto , BindingResult bindingResult) {

        return userService.login(loginDto , bindingResult);
    }


}
