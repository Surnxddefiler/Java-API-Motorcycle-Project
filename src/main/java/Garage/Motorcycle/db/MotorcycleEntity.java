package Garage.Motorcycle.db;

import Garage.Motorcycle.MotocycleClass.MotorcycleType;
import jakarta.persistence.*;


//creating table
@Table(
        name = "motorcycle",
        indexes = {
                @Index(name = "idx_motorcycle_main", columnList = "user_id, motorcycle_type, engine_cc, year"),
                @Index(name = "idx_motorcycle_user_mark", columnList = "user_id, mark")
        }
)
@Entity()
public class MotorcycleEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="mark", nullable=false)
    private String mark;
    @Column(name="model", nullable=false)
    private String model;
    @Column(name="year", nullable=false)
    private int year;
    @Column(name = "mileage", nullable=false)
    private int mileage;
    @Column(name="engine_cc", nullable = false)
    private int engineCc;
    @Enumerated(EnumType.STRING)
    @Column(name="motorcycle_type", nullable = false)
    private MotorcycleType motorcycleType;
    //adding user to motorcycle
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UsersEntity usersEntity;
    public MotorcycleEntity(){

    }

    public MotorcycleEntity(Long id,  String mark, String model, int year, int mileage, int engineCc, MotorcycleType motorcycleType, UsersEntity usersEntity){
        this.id=id;
        this.mark=mark;
        this.model=model;
        this.year=year;
        this.mileage=mileage;
        this.engineCc=engineCc;
        this.motorcycleType=motorcycleType;
        this.usersEntity=usersEntity;

    }
    //getters for retransforming
    public MotorcycleType getMotorcycleType() {
        return motorcycleType;
    }
    public String getMark() {
        return mark;
    }
    public Long getId() {
        return id;
    }
    public String getModel() {
        return model;
    }

    public int getYear() {
        return year;
    }
    public int getMileage() {
        return mileage;
    }
    public int getEngineCc() {
        return engineCc;
    }
    public UsersEntity getUsersEntity(){return usersEntity;}
    //setter

    public void setId(Long id) {
        this.id = id;
    }
    public void setMileage(int mileage){this.mileage=mileage;}
}
