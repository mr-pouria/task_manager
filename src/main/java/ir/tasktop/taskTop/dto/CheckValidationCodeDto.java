package ir.tasktop.taskTop.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
@Data
public class CheckValidationCodeDto {
    @NotBlank(message = "کد اعتبارسنجی اشتباه است")
    @Size(min = 6 , max = 6 , message = "کد اعتبارسنجی اشتباه است")
    private String code;
    @NotBlank(message = "کد اعتبارسنجی اشتباه است")
    @Size(min = 11 , max = 11 , message = "شماره موبایل نامعتبر است")
    private String phoneNumber;
}
