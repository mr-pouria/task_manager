package ir.tasktop.taskTop.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginDto {
    @NotBlank(message = "شماره موبایل الزامی است")
    private String phoneNumber;
    @NotBlank(message = "رمز عبور الزامی است")
    private String password;
}
