package tk.marvelsas.engineeringProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tk.marvelsas.engineeringProject.ENUMS.ATTEMPT_STATUS;
import tk.marvelsas.engineeringProject.ENUMS.ATTEMPT_TYPE;
import tk.marvelsas.engineeringProject.model.AppUser;
import tk.marvelsas.engineeringProject.model.Attempt;

import java.util.List;
import java.util.Optional;

@Repository
public interface AttemptRepository extends JpaRepository<Attempt,Integer> {


    Optional<List<Attempt>> findAllByCreatorId(AppUser appUser);
    Optional<List<Attempt>> findAllByApplicantId(AppUser appUser);
    Optional<List<Attempt>> findAllByApplicantIdAndSTATUS(AppUser appUser, ATTEMPT_STATUS status);

}
