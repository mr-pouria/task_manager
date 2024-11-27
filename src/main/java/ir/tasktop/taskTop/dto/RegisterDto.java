package ir.tasktop.taskTop.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterDto {
    @NotBlank(message = "پر کردن نام کاربری الزامی است")
    private String username;
    @NotBlank(message = "پر کردن رمز عبور الزامی است")
    private String password;
    @NotBlank(message = "پر کردن نام الزامی است")
    private String firstName;
    @NotBlank(message = "پر کردن نام خانوادگی الزامی است")
    private String lastName;
}
