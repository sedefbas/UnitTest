package com.sedefbas.learnTest.service;

import com.sedefbas.learnTest.dto.UserDto;
import com.sedefbas.learnTest.exceptions.IdentificationNumberNotValidException;
import com.sedefbas.learnTest.model.User;
import com.sedefbas.learnTest.repository.UserRepository;
import com.sedefbas.learnTest.service.validators.IdentificationNumberValidator;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final IdentificationNumberValidator identificationNumberValidator;

    @Transactional
    public Optional<User> create(UserDto request){

        boolean isValidIdentificationNumber = identificationNumberValidator.
                test(request.getIdentificationNumber());

        if (!isValidIdentificationNumber) {
            throw new IdentificationNumberNotValidException("Identification number not valid.");
        }

        Optional<User> userOptional = userRepository.findByIdentificationNumber(request.getIdentificationNumber());

        if(userOptional.isPresent()){
            throw new EntityExistsException("This user already exist!");
        }

        User newUser = new User();
        newUser.setFirstName(request.getFirstName());
        newUser.setLastName(request.getLastName());
        newUser.setPhoneNumber(request.getPhoneNumber());
        newUser.setIdentificationNumber(request.getIdentificationNumber());

        return Optional.of(userRepository.save(newUser));
    }



    @Transactional(readOnly = true)
    public List<UserDto> findAll(){
        final List<UserDto> userDtoList = new ArrayList<UserDto>();
        userRepository.findAll()
                .forEach(user -> {

                    UserDto userDto = new UserDto();
                    if(user != null){
                        userDto.setFirstName(user.getFirstName());
                        userDto.setLastName(user.getLastName());
                        userDto.setIdentificationNumber(user.getIdentificationNumber());
                        userDto.setPhoneNumber(user.getPhoneNumber());
                    }

                    userDtoList.add(userDto);

                });

        return userDtoList;
    }

    @Transactional(readOnly = true)
    public UserDto findById(long id){
        final User user =  userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException());

        UserDto userDto = new UserDto();
        if(user != null){
            userDto.setFirstName(user.getFirstName());
            userDto.setLastName(user.getLastName());
            userDto.setIdentificationNumber(user.getIdentificationNumber());
            userDto.setPhoneNumber(user.getPhoneNumber());
        }

        return userDto;
    }

    @Transactional
    public void deleteById(long id){
        final User user =  userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException());
        userRepository.deleteById(id);
    }


}
