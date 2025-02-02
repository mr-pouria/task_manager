package ir.tasktop.taskTop.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "roles")
@NoArgsConstructor
@AllArgsConstructor
@Data
@SequenceGenerator(name = "seq_roles", sequenceName = "seq_roles",  allocationSize=1 , initialValue = 100)
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_roles")
    private Long roleId;
    private String title;

}
