package tk.marvelsas.engineeringProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import tk.marvelsas.engineeringProject.model.ConfirmationToken;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken,Long> {

    Optional<ConfirmationToken> findByToken(String token);

    @Transactional
    @Modifying
    @Query("UPDATE ConfirmationToken c "+
            "SET c.confirmedAt = ?2 " +
            "WHERE c.token = ?1 ")
    int updateConfirmedAt(String token, LocalDateTime confirmedAt);


    @Transactional
    @Modifying
    @Query("UPDATE ConfirmationToken c "+
            "SET c.createAt = ?2 , c.expiredAt= ?3 , c.token = ?4 "+
            "WHERE c.token = ?1 ")
    int updateExpiredToken(String token,LocalDateTime createAT,LocalDateTime expiredAT,String newToken);
}
