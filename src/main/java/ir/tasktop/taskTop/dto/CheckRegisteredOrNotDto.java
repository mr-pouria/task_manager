package ir.tasktop.taskTop.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CheckRegisteredOrNotDto {
    @NotBlank(message = "کد اعتبارسنجی اشتباه است")
    @Size(min = 11 , max = 11 , message = "شماره موبایل نامعتبر است")
    private String phoneNumber;
}
