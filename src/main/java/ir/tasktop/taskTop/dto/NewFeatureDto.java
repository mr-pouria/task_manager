package ir.tasktop.taskTop.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Date;

@Data
public class NewFeatureDto {
    @NotBlank(message = "لطفا فیلد های مورد نیاز را پر کنید")
    private String name;
    private String description;
    private Date releaseDate;
}
