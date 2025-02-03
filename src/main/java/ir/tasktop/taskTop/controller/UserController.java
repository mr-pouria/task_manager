package ir.tasktop.taskTop.controller;


import ir.tasktop.taskTop.dto.*;
import ir.tasktop.taskTop.service.UserService;
import ir.tasktop.taskTop.utils.ResponseHandler;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin()
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    ResponseHandler responseHandler;


    @PostMapping("/sendCode")
    public ResponseEntity<?> sendCode(@Valid @RequestBody SendCodeDto sendCodeDto, BindingResult bindingResult) {
        return userService.sendCode(sendCodeDto , bindingResult);
    }


    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterDto registerDto , BindingResult bindingResult) throws Exception {
       return userService.register(registerDto , bindingResult);
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDto loginDto , BindingResult bindingResult) {

        return userService.login(loginDto , bindingResult);
    }

    @PostMapping("/checkRegisterOrNot")
    public ResponseEntity<?> checkRegisteredOrNot(@Valid @RequestBody CheckRegisteredOrNotDto checkRegisteredOrNotDto , BindingResult bindingResult) {
        return userService.checkRegisteredOrNot(checkRegisteredOrNotDto , bindingResult);
    }

    @PostMapping("/checkValidationCode")
    public ResponseEntity<?> checkValidationCode(@Valid @RequestBody CheckValidationCodeDto checkValidationCodeDto, BindingResult bindingResult) throws Exception {
        return userService.checkValidationCode(checkValidationCodeDto , bindingResult);
    }

    @Transactional()
    @PostMapping("/resetPassword")
    public ResponseEntity<?> resetUserPassword(@Valid @RequestBody ResetPasswordDto resetPasswordDto , BindingResult bindingResult) throws Exception {
        return userService.resetPassword(resetPasswordDto , bindingResult);
    }

}
