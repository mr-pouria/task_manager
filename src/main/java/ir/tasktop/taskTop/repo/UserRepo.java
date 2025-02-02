package ir.tasktop.taskTop.repo;

import ir.tasktop.taskTop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    boolean existsUserByPhoneNumber(String username);
    List<User> getUserByPhoneNumber(String phoneNumber);

}
