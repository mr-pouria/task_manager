package ir.tasktop.taskTop.dto;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterDto {
    @NotBlank(message = "پر کردن نام کاربری الزامی است")
    @Size(min =  3 , max = 15, message = "برای نام کاربری حداقل 3 کرکتر و حداکثر 15 کرکتر مجاز است")
    private String username;
    @NotBlank(message = "پر کردن رمز عبور الزامی است")
    private String password;
    @NotBlank(message = "پر کردن نام الزامی است")
    @Size(min =  3 , max = 20, message = "برای نام حداقل 3 کرکتر و حداکثر 20 کرکتر مجاز است")
    private String firstName;
    @NotBlank(message = "پر کردن نام خانوادگی الزامی است")
    @Size(min = 3 , max = 20, message = "برای نام خانوادگی حداقل 3 کرکتر و حداکثر 20 کرکتر مجاز است")
    private String lastName;
}
