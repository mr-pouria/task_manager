package ir.tasktop.taskTop.dto;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SendCodeDto {
    @NotNull(message = "شماره موبایل نامعتبر است")
    @Size(min = 11 , max = 11 , message = "شماره موبایل نامعتبر است")
    private String phoneNumber;
}
