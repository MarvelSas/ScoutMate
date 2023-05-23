package tk.marvelsas.engineeringProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tk.marvelsas.engineeringProject.model.Organization;

import java.util.Collection;
import java.util.Optional;
@Repository
public interface OrganizationRepository extends JpaRepository<Organization,Integer> {



}
