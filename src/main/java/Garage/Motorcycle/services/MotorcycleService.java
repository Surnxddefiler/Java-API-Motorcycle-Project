package Garage.Motorcycle.services;

import Garage.Motorcycle.MotocycleClass.MotorcycleRequest;
import Garage.Motorcycle.MotocycleClass.MotorcycleResponse;
import Garage.Motorcycle.MotorcycleMapper;
import Garage.Motorcycle.customExeptions.InvalidPageSize;
import Garage.Motorcycle.customExeptions.InvalidYear;
import Garage.Motorcycle.customExeptions.MotorcycleNotFoundException;
import Garage.Motorcycle.customExeptions.UserNotFoundException;
import Garage.Motorcycle.db.MotorcycleEntity;
import Garage.Motorcycle.db.MotorcycleRepository;
import Garage.Motorcycle.db.UserRepository;
import Garage.Motorcycle.db.UsersEntity;
import Garage.Motorcycle.domain.MotorcycleFilters;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
@Service
public class MotorcycleService {

    private final MotorcycleRepository motorcycleRepository;
    private final MotorcycleMapper motorcycleMapper;
    private final UserRepository userRepository;

    //spring boot beans
    public MotorcycleService(MotorcycleRepository motorcycleRepository, MotorcycleMapper motorcycleMapper, UserRepository userRepository){
        this.motorcycleMapper= motorcycleMapper;
        this.motorcycleRepository=motorcycleRepository;
        this.userRepository=userRepository;
    }
    //getting all Motocycles
    public Page<MotorcycleResponse> getAllMotorcycles(String email, MotorcycleFilters filters){
        //checking if the user exists
        //checking by email that we get from jwt token
        UsersEntity user =userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);

        //page size of motorcycles
        int pageSize=filters.pageSize()!=null ? filters.pageSize() : 3;
        //current page
        int currentPage=filters.currentPage()!=null? filters.currentPage() : 0;
        //logic for handler
        if (pageSize<=0 || pageSize>20){
            throw new InvalidPageSize(pageSize);
        }

        //sort Logic
        Sort sort=Sort.unsorted();
        if (filters.motorcycleOrderBy()!=null){
            sort=Sort.by(filters.motorcycleOrderBy().getField());
        }
        //creating paging
        Pageable pageable= PageRequest.of(currentPage,pageSize,sort);
        //getting entities from db by repository interface
        Page<MotorcycleEntity> entityList=motorcycleRepository.searchAllByFilters(user.getId(), filters.motorcycleType(), filters.mark(), pageable, filters.minYear(), filters.maxYear(), filters.minCc(), filters.maxCc());
        return entityList.map(motorcycleMapper::toResponse);
    }
    //getting one bike id
    public MotorcycleResponse getMotocycleById(String email, Long id){

        //checking if the user exists
        UsersEntity user =userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
        //calling repository
        MotorcycleEntity motorcycleEntity=motorcycleRepository.findByIdAndUsersEntityId(id, user.getId()).orElseThrow(()-> new MotorcycleNotFoundException(id));
        return motorcycleMapper.toResponse(motorcycleEntity);
    }
    //creating a bike
    public MotorcycleResponse postMotorcycle(Long userId , MotorcycleRequest motorcycle){
        //error checking
        validateYear(motorcycle.year());

        UsersEntity user =userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        //to entity
        MotorcycleEntity toEntity=motorcycleMapper.toEntity(motorcycle, user);
        //saving
        var motorcycleToSave=motorcycleRepository.save(toEntity);
        return motorcycleMapper.toResponse(motorcycleToSave);
    };
    //editing a bike
    public MotorcycleResponse editMotorcycle (Long userId,Long id, MotorcycleRequest motorcycle){
        //error checking
        validateYear(motorcycle.year());

        UsersEntity user =userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        //searching for a bike in user
        MotorcycleEntity motorcycleEntity=motorcycleRepository.findByIdAndUsersEntityId(id, userId).orElseThrow(()->new MotorcycleNotFoundException(id));
        //to entity
        MotorcycleEntity toEntity=motorcycleMapper.toEntity(motorcycle, user);
        toEntity.setId(motorcycleEntity.getId());
        //saving
        var motorcycleToSave=motorcycleRepository.save(toEntity);
        return motorcycleMapper.toResponse(motorcycleToSave);
    };

    public void deleteMotorcycle(Long userId, Long id) {
        //finding bike
        MotorcycleEntity motorcycleEntity=motorcycleRepository.findByIdAndUsersEntityId(id, userId).orElseThrow(()->new MotorcycleNotFoundException(id));
        motorcycleRepository.delete(motorcycleEntity);
    }
    private void validateYear(int year){
        if (year> LocalDateTime.now().getYear()){
            throw new InvalidYear(year);
        }
    }
}
