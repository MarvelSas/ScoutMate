package tk.marvelsas.engineeringProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tk.marvelsas.engineeringProject.model.ForgetPasswordToken;

import java.util.Optional;

@Repository
public interface ForgetPasswordTokenRepository extends JpaRepository<ForgetPasswordToken,Long> {

    Optional<ForgetPasswordToken>findByToken(String Token);
}
