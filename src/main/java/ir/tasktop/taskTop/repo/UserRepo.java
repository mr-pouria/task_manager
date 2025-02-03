package ir.tasktop.taskTop.repo;

import ir.tasktop.taskTop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    boolean existsUserByPhoneNumber(String username);
    User getUserByPhoneNumber(String phoneNumber);
    @Query("UPDATE User  SET password = :password WHERE phoneNumber = :phone")
    @Modifying(clearAutomatically = true)
    void resetPassword(@Param("phone") String phoneNumber, @Param("password") String newPassword);

}
