package tk.marvelsas.engineeringProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tk.marvelsas.engineeringProject.model.AppUser;
import tk.marvelsas.engineeringProject.model.DTO.AppUserDetailsDTO;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser,Integer> {

    Optional<AppUser> findByEmail(String email);


    @Transactional
    @Modifying
    @Query("UPDATE AppUser a " +
            "SET a.enabled = TRUE WHERE a.email = ?1")
    int enableAppUser(String email);



    //Mapping AppUser to AppUserDetails to received necessary information
    @Query("SELECT new tk.marvelsas.engineeringProject.model.DTO.AppUserDetailsDTO(u.email, u.name, u.surname)  FROM AppUser u")
    Optional<List<AppUserDetailsDTO>> findAllAppUserDetails();

    @Query("SELECT new tk.marvelsas.engineeringProject.model.DTO.AppUserDetailsDTO(u.email, u.name, u.surname)  FROM AppUser u WHERE u.enabled=true ")
    Optional<List<AppUserDetailsDTO>> findAllAppUserDetailsEnabled();


}







