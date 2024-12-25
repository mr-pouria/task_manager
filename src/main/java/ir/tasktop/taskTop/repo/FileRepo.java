package ir.tasktop.taskTop.repo;

import ir.tasktop.taskTop.model.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepo extends JpaRepository<File,Long> {
}
