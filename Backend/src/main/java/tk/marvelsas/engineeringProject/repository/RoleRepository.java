package tk.marvelsas.engineeringProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tk.marvelsas.engineeringProject.model.AppUser;
import tk.marvelsas.engineeringProject.model.Organization;
import tk.marvelsas.engineeringProject.model.Role;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Optional;
@Repository
public interface RoleRepository extends JpaRepository<Role,Integer> {

    Optional<Role> findByName(String name);
    Optional<Role>findByAppUserRoleAndOrganizationRole(AppUser appUser,Organization organization);
    @Transactional
    long deleteByAppUserRoleAndOrganizationRole(AppUser appUser,Organization organization);


    Optional<ArrayList<Role>> findAllByOrganizationRole(Organization organization);
    Optional<ArrayList<Role>>findAllByAppUserRole(AppUser user);

    @Query("select r FROM Role r where r.name='ADMIN'")
    Optional<ArrayList<Role>>allAdmins();


}
