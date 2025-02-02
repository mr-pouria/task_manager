package ir.tasktop.taskTop.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "validation_code_users")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@SequenceGenerator(name = "sq_validation_code_users", sequenceName = "sq_validation_code_users", allocationSize = 1, initialValue = 100)
public class ValidationCodeUser {
    @Id
    @GeneratedValue(generator = "sq_validation_code_users" , strategy = GenerationType.IDENTITY)
    private Long validationCodeUserId;

    private Long validation_code_id;
    private Long user_id;
}
