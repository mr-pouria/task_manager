package ir.tasktop.taskTop.service;


import ir.tasktop.taskTop.dto.*;
import ir.tasktop.taskTop.model.User;
import ir.tasktop.taskTop.model.ValidatedPhoneNumber;
import ir.tasktop.taskTop.model.ValidationCode;
import ir.tasktop.taskTop.model.ValidationCodeUser;
import ir.tasktop.taskTop.repo.UserRepo;
import ir.tasktop.taskTop.repo.ValidatedPhoneNumberRepo;
import ir.tasktop.taskTop.repo.ValidationCodeRepo;
import ir.tasktop.taskTop.repo.ValidationCodeUserRepo;
import ir.tasktop.taskTop.security.Jwt;
import ir.tasktop.taskTop.utils.EncryptionHandler;
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

import java.security.Key;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

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


    @Autowired
    ValidationCodeRepo validationCodeRepo;

    @Autowired
    ValidationCodeUserRepo validationCodeUserRepo;


    @Autowired
    ValidatedPhoneNumberRepo validatedPhoneNumberRepo;

    Key key;

    public ResponseEntity<?> register(RegisterDto registerDto, BindingResult result) throws Exception {


        if (result.hasErrors()) {
            return responseHandler.responseBack(null, null, result.getFieldError().getDefaultMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        }

        String[] hash = EncryptionHandler.decrypt(registerDto.getHashCode() , key).split("\\|");
        Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());
        Long diff = (timestamp.getTime() - Long.parseLong(hash[1])) / 1000;
        if (!registerDto.getPhoneNumber().equals(hash[0]) || diff >= 120) {
            return responseHandler.responseBack(null, null, "خطا در احراز هویت , مجدد تلاش کنید", HttpStatus.BAD_REQUEST);
        }


        if (userRepo.existsUserByPhoneNumber(registerDto.getPhoneNumber())) {
            return responseHandler.responseBack(null, null, Messages.U_ALREADYEXISTS.toString(), HttpStatus.BAD_REQUEST);
        }

        User user = new User();
        user.setUsername(registerDto.getPhoneNumber());
        user.setPhoneNumber(registerDto.getPhoneNumber());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setFirstName(registerDto.getFirstName());
        user.setLastName(registerDto.getLastName());
        try {
            userRepo.save(user);
            Map<String, String> map = new HashMap<>();
            map.put("token", jwt.generateToken(user.getUsername()));
            return responseHandler.responseBack(map, Messages.WELLCOME.toString(), null, HttpStatus.OK);

        } catch (Exception e) {

            return responseHandler.responseBack(null, null, e.getMessage(), HttpStatus.BAD_REQUEST);

        }
    }

    public ResponseEntity<?> login(LoginDto loginDto, BindingResult result) {

        if (result.hasErrors()) {
            return responseHandler.responseBack(null, null, result.getFieldError().getDefaultMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        }
        try {
            if (!userRepo.existsUserByPhoneNumber(loginDto.getPhoneNumber())) {
                return responseHandler.responseBack(null, null, Messages.UP_WRONG.toString(), HttpStatus.BAD_REQUEST);
            }
            Map<String, String> map = new HashMap<>();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getPhoneNumber(), loginDto.getPassword()));
            map.put("token", jwt.generateToken(loginDto.getPhoneNumber()));
            return responseHandler.responseBack(map, Messages.WELLCOME.toString(), null, HttpStatus.OK);
        } catch (Exception e) {
            if (e instanceof BadCredentialsException) {
                return responseHandler.responseBack(null, null, Messages.UP_WRONG.toString(), HttpStatus.BAD_REQUEST);
            }
            return responseHandler.responseBack(null, null, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> checkRegisteredOrNot(CheckRegisteredOrNotDto checkRegisteredOrNotDto, BindingResult result) {
        if (result.hasErrors()) {
                return responseHandler.responseBack(null, null, result.getFieldError().getDefaultMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        }
        if (userRepo.existsUserByPhoneNumber(checkRegisteredOrNotDto.getPhoneNumber())) {
            Map<String, Boolean> map = new HashMap<>();
            map.put("isRegistered", true);
            return responseHandler.responseBack(map, null, null, HttpStatus.OK);
        } else {
            Map<String, Boolean> map = new HashMap<>();
            map.put("isRegistered", false);
            return responseHandler.responseBack(map, null, null, HttpStatus.OK);
        }
    }

    public ResponseEntity<?> sendCode(SendCodeDto sendCodeDto, BindingResult result) {
        if (result.hasErrors()) {
            return responseHandler.responseBack(null, null, result.getFieldError().getDefaultMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        }
        if (!userRepo.existsUserByPhoneNumber(sendCodeDto.getPhoneNumber())) {
            String postDate = validationCodeRepo.findLatestRecordByNotRegisteredPhoneNumber(sendCodeDto.getPhoneNumber());
            if (postDate != null) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime localDateTime = LocalDateTime.now();
                LocalDateTime localPostDate = LocalDateTime.parse(postDate, formatter);
                Duration duration = Duration.between(localPostDate, localDateTime);
                if (duration.toSeconds() < 120) {
                    return responseHandler.responseBack(null, Messages.SENT_BEFORE.toString(),null, HttpStatus.OK);
                }
            }


            ValidationCode validationCode = new ValidationCode();
            Random random = new Random();
            int number = 100000 + (int) (Math.random() * (999999 - 100000));
            validationCode.setCode(String.valueOf(number));
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter nowFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            validationCode.setPostDate(now.format(nowFormatter));
            validationCode.setNotRegisteredPhoneNumber(sendCodeDto.getPhoneNumber());
            try {
                ValidationCode vCode = validationCodeRepo.save(validationCode);
                return responseHandler.responseBack(null,  "کد تایید به شماره " + sendCodeDto.getPhoneNumber() + " ارسال شد , این کد تنها 2 دقیقه اعتبار دارد",null, HttpStatus.OK);
            } catch (Exception e) {
                return responseHandler.responseBack(null, null,  Messages.INTERNAL_SERVER_ERROR.toString() , HttpStatus.INTERNAL_SERVER_ERROR);
            }

        }


        String postDate = validationCodeRepo.findLatestRecord(sendCodeDto.getPhoneNumber());
        if (postDate == null) {
            ValidationCode validationCode = new ValidationCode();
            ValidationCodeUser validationCodeUser = new ValidationCodeUser();
            Long userId = userRepo.getUserByPhoneNumber(sendCodeDto.getPhoneNumber()).getUserId();
            Random random = new Random();
            int number = 100000 + (int) (Math.random() * (999999 - 100000));

            validationCode.setCode(String.valueOf(number));
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter nowFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            try {
                validationCode.setPostDate(now.format(nowFormatter));
                ValidationCode vCode = validationCodeRepo.save(validationCode);
                validationCodeUser.setValidation_code_id(vCode.getValidationCodeId());
                validationCodeUser.setUser_id(userId);
                validationCodeUserRepo.save(validationCodeUser);
                return responseHandler.responseBack(null,  "کد تایید به شماره " + sendCodeDto.getPhoneNumber() + " ارسال شد , این کد تنها 2 دقیقه اعتبار دارد", null ,HttpStatus.OK);
            } catch (Exception e) {
                return responseHandler.responseBack(null, null,  Messages.INTERNAL_SERVER_ERROR.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDateTime localPostDate = LocalDateTime.parse(postDate, formatter);
        Duration duration = Duration.between(localPostDate, localDateTime);
        if (duration.toSeconds() < 120) {
            return responseHandler.responseBack(null,  Messages.SENT_BEFORE.toString(), null , HttpStatus.OK);
        }
        ValidationCode validationCode = new ValidationCode();
        ValidationCodeUser validationCodeUser = new ValidationCodeUser();
        Long userId = userRepo.getUserByPhoneNumber(sendCodeDto.getPhoneNumber()).getUserId();
        Random random = new Random();
        int number = 100000 + (int) (Math.random() * (999999 - 100000));

        validationCode.setCode(String.valueOf(number));
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter nowFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        try {
            validationCode.setPostDate(now.format(nowFormatter));
            ValidationCode vCode = validationCodeRepo.save(validationCode);
            validationCodeUser.setValidation_code_id(vCode.getValidationCodeId());
            validationCodeUser.setUser_id(userId);
            validationCodeUserRepo.save(validationCodeUser);
            return responseHandler.responseBack(null,  "کد تایید به شماره " + sendCodeDto.getPhoneNumber() + " ارسال شد , این کد تنها 2 دقیقه اعتبار دارد", null ,HttpStatus.OK);
        } catch (Exception e) {
            return responseHandler.responseBack(null, null,  Messages.INTERNAL_SERVER_ERROR.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public ResponseEntity<?> resetPassword(ResetPasswordDto resetPasswordDto, BindingResult result) throws Exception {
        String[] hashCode = EncryptionHandler.decrypt(resetPasswordDto.getHashCode(),key).split("\\|");
        Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());
        Long diff = (Long.parseLong(hashCode[1]) - timestamp.getTime()) / 1000;

        if (!resetPasswordDto.getPhoneNumber().equals(hashCode[0]) || diff >= 10) {
            return responseHandler  .responseBack(null, null, "خطا در احراز هویت , مجدد تلاش کنید", HttpStatus.BAD_REQUEST);
        }
        String newPassword = passwordEncoder.encode(resetPasswordDto.getNewPassword());
        userRepo.resetPassword(resetPasswordDto.getPhoneNumber() , newPassword);
        return responseHandler.responseBack(null, "رمز عبور با موفقیت تغییر یافت", null, HttpStatus.OK);
    }

    public ResponseEntity<?> checkValidationCode(CheckValidationCodeDto checkValidationCodeDto, BindingResult result) throws Exception {
        if (result.hasErrors()) {
            return responseHandler.responseBack(null, null, result.getFieldError().getDefaultMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        }
        if (!userRepo.existsUserByPhoneNumber(checkValidationCodeDto.getPhoneNumber())) {
            if (validationCodeRepo.existsByNotRegisteredPhoneNumber(checkValidationCodeDto.getPhoneNumber())) {
                String vCode = validationCodeRepo.findLatestByNotRegisteredPhoneNumber(checkValidationCodeDto.getPhoneNumber());
                String postDate = validationCodeRepo.findLatestRecordByNotRegisteredPhoneNumber(checkValidationCodeDto.getPhoneNumber());
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime localDateTime = LocalDateTime.now();
                LocalDateTime localPostDate = LocalDateTime.parse(postDate, formatter);
                Duration duration = Duration.between(localPostDate, localDateTime);

                if (duration.toSeconds() > 120) {
                    return responseHandler.responseBack(null, null, Messages.V_CODE_EXPIRED.toString(), HttpStatus.BAD_REQUEST);
                }
                else if (checkValidationCodeDto.getCode().equals(vCode)) {
                    LocalDateTime now = LocalDateTime.now();
                    DateTimeFormatter nowFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    ValidatedPhoneNumber validatedPhoneNumber = new ValidatedPhoneNumber();
                    validatedPhoneNumber.setPhoneNumber(checkValidationCodeDto.getPhoneNumber());
                    validatedPhoneNumber.setCreated_at(now.format(nowFormatter));
                    try {
                        Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());
                        String feed = String.valueOf(checkValidationCodeDto.getPhoneNumber()) + "|" + String.valueOf(timestamp.getTime());
                        String hashCode = EncryptionHandler.encrypt(feed);
                        key = EncryptionHandler.key;
                        Map<String, String> map = new HashMap<>();
                        map.put("hashCode", hashCode);
                        return responseHandler.responseBack(map, null, null, HttpStatus.OK);
                    } catch (Exception e) {
                        return responseHandler.responseBack(null, "", Messages.INTERNAL_SERVER_ERROR.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
                    }
                } else {
                    return responseHandler.responseBack(null, null, Messages.V_CODE_EXPIRED.toString(), HttpStatus.BAD_REQUEST);
                }

            } else {
                return responseHandler.responseBack("", "", Messages.INTERNAL_SERVER_ERROR.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        String postDate = validationCodeRepo.findLatestRecord(checkValidationCodeDto.getPhoneNumber());
        if (postDate != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime localDateTime = LocalDateTime.now();
            LocalDateTime localPostDate = LocalDateTime.parse(postDate, formatter);
            Duration duration = Duration.between(localPostDate, localDateTime);
            if (duration.toSeconds() > 120) {
                return responseHandler.responseBack(null, null, Messages.V_CODE_EXPIRED.toString(), HttpStatus.BAD_REQUEST);
            }
            String vCode = validationCodeRepo.findLatestRecordWithCode(checkValidationCodeDto.getPhoneNumber());
            if (checkValidationCodeDto.getCode().equals(vCode)) {
                Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());
                String feed = String.valueOf(checkValidationCodeDto.getPhoneNumber()) + "|" + String.valueOf(timestamp.getTime());
                String hashCode = EncryptionHandler.encrypt(feed);
                key = EncryptionHandler.key;
                Map<String, String> map = new HashMap<>();
                map.put("hashCode", hashCode);
                return responseHandler.responseBack(map, null, null, HttpStatus.OK);
            }
        } else {
            return responseHandler.responseBack(null, null,  Messages.INTERNAL_SERVER_ERROR.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseHandler.responseBack(null, null,  Messages.INTERNAL_SERVER_ERROR.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
