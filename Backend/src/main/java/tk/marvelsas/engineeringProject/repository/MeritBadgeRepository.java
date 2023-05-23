package tk.marvelsas.engineeringProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tk.marvelsas.engineeringProject.model.MeritBadge;

@Repository
public interface MeritBadgeRepository extends JpaRepository<MeritBadge,Integer> {
}
