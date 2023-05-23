package tk.marvelsas.engineeringProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tk.marvelsas.engineeringProject.model.CategoryMeritBadge;


@Repository
public interface CategoryMeritBadgeRepository extends JpaRepository<CategoryMeritBadge,Integer> {
}
