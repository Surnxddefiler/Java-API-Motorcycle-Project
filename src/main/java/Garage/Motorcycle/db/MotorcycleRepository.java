package Garage.Motorcycle.db;

import Garage.Motorcycle.MotocycleClass.MotorcycleType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

//we made also
@Repository
public interface MotorcycleRepository extends JpaRepository<MotorcycleEntity, Long> {

    //for getting motorcycle by user
    Optional<MotorcycleEntity> findByIdAndUsersEntityId(Long id, Long userId);


    @Query("""
    SELECT m FROM MotorcycleEntity m
    WHERE
        (:motorcycleType IS NULL OR m.motorcycleType = :motorcycleType)
    AND (:mark IS NULL OR m.mark = :mark)
    AND (:userId IS NULL OR m.usersEntity.id = :userId)
""")
    public List<MotorcycleEntity> searchAllByFilters(@Param("userId") Long userId,@Param("motorcycleType")MotorcycleType motorcycleType,@Param("mark")String mark, Pageable pageable);
}
