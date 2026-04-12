package Garage.Motorcycle.db;

import Garage.Motorcycle.serviceRecordClass.ServiceRecordAnalytics;
import Garage.Motorcycle.serviceRecordClass.ServiceRecordType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
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
    public Page<ServiceRecordEntity> searchAllByFilters(@Param("motorcycleId") Long motorcycleId, @Param("serviceRecordType")ServiceRecordType serviceRecordType, Pageable pageable);

    //getting custom analytics table
    //we don't need an Entity for this, we can use default class
    @Query("""
SELECT new Garage.Motorcycle.serviceRecordClass.ServiceRecordAnalytics(
    COALESCE(AVG(s.price), 0),
    COALESCE(MAX(s.price), 0),
    COALESCE(MIN(s.price), 0)
)
FROM ServiceRecordEntity s
WHERE s.motorcycleEntity.id = :motorcycleId
AND (:startDate IS NULL OR s.serviceTime >= :startDate)
AND (:endDate IS NULL OR s.serviceTime <= :endDate)
""")
    ServiceRecordAnalytics getAnalytics(@Param("motorcycleId") Long motorcycleId, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
}
