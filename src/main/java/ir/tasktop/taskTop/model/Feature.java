package ir.tasktop.taskTop.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "features")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "seq_feature", sequenceName = "seq_feature"  , allocationSize = 1  , initialValue = 100)
public class Feature {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE , generator = "seq_feature")
    private Long id;
    @Column(nullable = false , unique = true)
    private String name;
    private String description;
    private Date releaseDate;
}
