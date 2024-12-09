package ir.tasktop.taskTop.service;


import ir.tasktop.taskTop.dto.LoginDto;
import ir.tasktop.taskTop.dto.RegisterDto;
import ir.tasktop.taskTop.model.User;
import ir.tasktop.taskTop.repo.UserRepo;
import ir.tasktop.taskTop.security.Jwt;
import ir.tasktop.taskTop.utils.Messages;
import ir.tasktop.taskTop.utils.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRepo userRepo;

    @Autowired
    Jwt jwt;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    ResponseHandler responseHandler;


    public ResponseEntity<?> register(RegisterDto registerDto, BindingResult result) {


        if (result.hasErrors()) {
            return responseHandler.responseBack(null, null, result.getFieldError().getDefaultMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        }
        if (userRepo.existsByUsername(registerDto.getUsername())) {
            return responseHandler.responseBack(null, null, Messages.U_ALREADYEXISTS.toString(), HttpStatus.BAD_REQUEST);
        }
        User user = new User();
        user.setUsername(registerDto.getUsername());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        try {
            userRepo.save(user);
            Map<String, String> map = new HashMap<>();
            map.put("token", jwt.generateToken(user.getUsername()));
            return responseHandler.responseBack(map, Messages.WELLCOME.toString(), null, HttpStatus.OK);

        } catch (Exception e) {

            return responseHandler.responseBack(null, null, e.getMessage(), HttpStatus.BAD_REQUEST);

        }
    }

    public ResponseEntity<?> login(LoginDto loginDto , BindingResult result) {

        if (result.hasErrors()) {
            return responseHandler.responseBack(null, null, result.getFieldError().getDefaultMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        }
        try {
            if (!userRepo.existsByUsername(loginDto.getUsername())) {
                return responseHandler.responseBack(null, null, Messages.UP_WRONG.toString(), HttpStatus.BAD_REQUEST);
            }
            Map<String, String> map = new HashMap<>();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
            map.put("token", jwt.generateToken(loginDto.getUsername()));
            return responseHandler.responseBack(map, Messages.WELLCOME.toString(), null, HttpStatus.OK);
        } catch (Exception e) {
            if (e instanceof BadCredentialsException) {
                return responseHandler.responseBack(null, null, Messages.UP_WRONG.toString(), HttpStatus.BAD_REQUEST);
            }
            return responseHandler.responseBack(null, null, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
