package ir.tasktop.taskTop.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginDto {
    @NotBlank(message = "پر کردن نام کاربری الزامی است")
    private String username;
    @NotBlank(message = "پر کردن رمز عبور الزامی است")
    private String password;
}
