package ir.tasktop.taskTop.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class SaveFileDto {
    @NotNull(message = "فایل ارسالی نامعتبر است")
    @Max(value = 20000 , message = "فایل ارسالی باید کوچک تر از 20 مگابایت باشد")
    private MultipartFile file;
    @NotBlank(message = "فایل ارسالی نامعتبر است")
    private String docName;
    @NotBlank(message = "فایل ارسالی نامعتبر است")
    private Long docIdentifier;
}
