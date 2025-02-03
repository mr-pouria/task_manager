package ir.tasktop.taskTop.dto;

import lombok.Data;

@Data
public class ResetPasswordDto {
    private String phoneNumber;
    private String hashCode;
    private String newPassword;
}
