package tk.marvelsas.engineeringProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tk.marvelsas.engineeringProject.model.ActionQueueLine;
import tk.marvelsas.engineeringProject.model.Organization;

import java.util.List;
import java.util.Optional;

@Repository
public interface ActionQueueLineRepository extends JpaRepository<ActionQueueLine, Integer> {

    Optional<List<ActionQueueLine>> findAllByOrganizationAndArchived(Organization organization,Boolean archive);

}
