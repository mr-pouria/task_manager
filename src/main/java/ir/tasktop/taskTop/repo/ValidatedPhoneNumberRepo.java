package ir.tasktop.taskTop.repo;

import ir.tasktop.taskTop.model.ValidatedPhoneNumber;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ValidatedPhoneNumberRepo extends JpaRepository<ValidatedPhoneNumber , Long> {
    boolean existsByPhoneNumber(String phoneNumber);
}
