package tk.marvelsas.engineeringProject.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tk.marvelsas.engineeringProject.model.ScoutOrder;


@Repository
public interface ScoutOrderRepository extends JpaRepository<ScoutOrder,Integer> {


    @Query(nativeQuery = true, value = "SELECT * FROM `scout_orders` WHERE order_type=:type and year(date)=:date  ORDER BY id DESC LIMIT 1")
    ScoutOrder findLast(@Param("type") int type, @Param("date") int date);



}
