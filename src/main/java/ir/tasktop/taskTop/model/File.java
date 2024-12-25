package ir.tasktop.taskTop.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "files")
@SequenceGenerator(name = "seq_files",sequenceName = "seq_files" , initialValue = 100 , allocationSize = 1)
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE , generator = "seq_files")
    private Long id;
    @Column(nullable = false)
    private String path;
    @Column(nullable = false)
    private String fileExtension;
    @Column(nullable = false)
    private String fileName;
    @Column(nullable = false)
    private Long fileSize;
    @Column(nullable = false)
    private String docName;
    @Column(nullable = false)
    private Long docIdentifier;
    @Column(nullable = false)
    @JoinColumns(@JoinColumn(table = "users" , name = "id"))
    private Long createdBy;
}
