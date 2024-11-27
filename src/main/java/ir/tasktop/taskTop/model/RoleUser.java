package ir.tasktop.taskTop.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "roles_user")
@SequenceGenerator(name = "seq_roles_user", sequenceName = "seq_roles_user"  , allocationSize = 1  , initialValue = 100)
public class RoleUser {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_roles_user")
    private Long id;

    private Long user_id;
    private Long role_id;
}
