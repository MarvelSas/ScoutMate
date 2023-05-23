package tk.marvelsas.engineeringProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tk.marvelsas.engineeringProject.model.ScoutRank;

@Repository
public interface ScoutRankRepository extends JpaRepository<ScoutRank,Integer> {
}
