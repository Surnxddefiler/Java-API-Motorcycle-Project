package Garage.Motorcycle.db;

import Garage.Motorcycle.serviceRecordClass.ServiceRecordType;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Table(name = "service_record")
@Entity()
public class ServiceRecordEntity {
    @Id
    @Column(name = "service_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long serviceId;
    @Column(name = "service_time", nullable = false)
    private LocalDateTime serviceTime;
    @Enumerated(EnumType.STRING)
    @Column(name = "service_record_type", nullable = false)
    private ServiceRecordType serviceRecordType;
    @Column(name = "mileage", nullable = false)
    private int mileage;
    @Column(name = "comment")
    private String comment;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "motorcycle_id", nullable = false)// firstly this field was just called id, but for better understanding I made it motorcycle_id
    private MotorcycleEntity motorcycleEntity;

    public ServiceRecordEntity(){

    };
    public ServiceRecordEntity(Long serviceId, LocalDateTime serviceTime, ServiceRecordType serviceRecordType, int mileage, String comment, MotorcycleEntity motorcycleEntity){
        this.serviceId=serviceId;
        this.serviceTime=serviceTime;
        this.serviceRecordType=serviceRecordType;
        this.mileage=mileage;
        this.comment=comment;
        this.motorcycleEntity=motorcycleEntity;
    }

    public Long getId() {
        return serviceId;
    }

    public LocalDateTime getServiceTime() {
        return serviceTime;
    }

    public ServiceRecordType getServiceRecordType() {
        return serviceRecordType;
    }

    public int getMileage() {
        return mileage;
    }

    public String getComment() {
        return comment;
    }
    public MotorcycleEntity getMotorcycleEntity(){
        return motorcycleEntity;
    }

    public void setServiceTime(LocalDateTime serviceTime){this.serviceTime=serviceTime;}
}
