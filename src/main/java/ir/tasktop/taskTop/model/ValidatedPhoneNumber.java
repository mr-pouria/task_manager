package ir.tasktop.taskTop.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "validated_phone_number")
@Entity
@SequenceGenerator(name = "sq_validated_phone_number" , sequenceName = "sq_validated_phone_number" , allocationSize = 1 , initialValue = 100)
public class ValidatedPhoneNumber {
    @Id
    @GeneratedValue(generator = "sq_validated_phone_number" , strategy = GenerationType.IDENTITY)
    private Long validatedPhoneNumberId;
    @Column(nullable = false)
    private String phoneNumber;
    @Column(nullable = false)
    private String created_at;
}
