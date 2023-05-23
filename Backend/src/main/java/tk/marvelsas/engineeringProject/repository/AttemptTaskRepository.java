package tk.marvelsas.engineeringProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tk.marvelsas.engineeringProject.model.AttemptTask;

@Repository
public interface AttemptTaskRepository extends JpaRepository<AttemptTask,Integer> {
}
