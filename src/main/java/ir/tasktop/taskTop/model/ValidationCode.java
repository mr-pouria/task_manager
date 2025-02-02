package ir.tasktop.taskTop.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;

@Table(name = "validation_codes")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@SequenceGenerator(name = "sq_validation_codes", sequenceName = "sq_validation_codes", allocationSize = 1, initialValue = 100)
public class ValidationCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "sq_validation_codes")
    private Long validationCodeId;
    private String code;
    private String postDate;
    private String notRegisteredPhoneNumber;




}
