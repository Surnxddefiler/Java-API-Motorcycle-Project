package Garage.Motorcycle.db;

import Garage.Motorcycle.serviceRecordClass.ServiceRecordType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ServiceRecordRepository extends JpaRepository<ServiceRecordEntity, Long> {

    //getting service list from db
    public Optional<ServiceRecordEntity> findByServiceIdAndMotorcycleEntityId(Long serviceId, Long id);


    //getting last one for checking if the maintenance is needed
    public Optional<ServiceRecordEntity> findFirstByMotorcycleEntityIdAndServiceRecordTypeOrderByServiceTimeDesc(Long motorcycleId, ServiceRecordType serviceRecordType);

    //searching records by motorcycle
    @Query("""
SELECT m FROM ServiceRecordEntity m
    WHERE
        (:serviceRecordType IS NULL OR m.serviceRecordType = :serviceRecordType)
    AND (m.motorcycleEntity.id = :motorcycleId)
""")
    public List<ServiceRecordEntity> searchAllByFilters(@Param("motorcycleId") Long motorcycleId, @Param("serviceRecordType")ServiceRecordType serviceRecordType, Pageable pageable);
}
