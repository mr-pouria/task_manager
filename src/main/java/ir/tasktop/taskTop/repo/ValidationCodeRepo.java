package ir.tasktop.taskTop.repo;

import ir.tasktop.taskTop.model.ValidationCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;

import java.util.List;
import java.util.Optional;

public interface ValidationCodeRepo extends JpaRepository<ValidationCode, Long> {
    @Query("SELECT codes.postDate FROM ValidationCode codes INNER JOIN ValidationCodeUser codeuser ON codes.validationCodeId = codeuser.validation_code_id INNER JOIN User user ON user.userId = codeuser.user_id WHERE user.phoneNumber = :phone AND codes.postDate IN (SELECT max(codes.postDate) FROM ValidationCode codes INNER JOIN ValidationCodeUser codeuser ON codes.validationCodeId = codeuser.validation_code_id INNER JOIN User user ON user.userId = codeuser.user_id WHERE user.phoneNumber = :phone)")
    String findLatestRecord(@Param("phone") String phoneNumber);
    @Query("SELECT codes.postDate FROM ValidationCode codes WHERE codes.notRegisteredPhoneNumber = :phone AND codes.postDate IN (SELECT MAX(codes.postDate) FROM ValidationCode codes WHERE codes.notRegisteredPhoneNumber= :phone)")
    String findLatestRecordByNotRegisteredPhoneNumber(@Param("phone") String phoneNumber);
    @Query("SELECT codes.code FROM ValidationCode codes WHERE codes.notRegisteredPhoneNumber = :phone AND codes.postDate IN (SELECT MAX(codes.postDate) FROM ValidationCode codes WHERE codes.notRegisteredPhoneNumber= :phone)")
    String findLatestByNotRegisteredPhoneNumber(@Param("phone") String phoneNumber);
    @Query("SELECT codes.code FROM ValidationCode codes INNER JOIN ValidationCodeUser codeuser ON codes.validationCodeId = codeuser.validation_code_id INNER JOIN User user ON user.userId = codeuser.user_id WHERE user.phoneNumber = :phone AND codes.postDate IN (SELECT max(codes.postDate) FROM ValidationCode codes INNER JOIN ValidationCodeUser codeuser ON codes.validationCodeId = codeuser.validation_code_id INNER JOIN User user ON user.userId = codeuser.user_id WHERE user.phoneNumber = :phone)")
    String findLatestRecordWithCode(@Param("phone") String phoneNumber);
    boolean existsByNotRegisteredPhoneNumber(String notRegisteredPhoneNumber);
}
