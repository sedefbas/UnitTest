package com.sedefbas.learnTest.service;
import com.sedefbas.learnTest.dto.UserDto;
import com.sedefbas.learnTest.exceptions.IdentificationNumberNotValidException;
import com.sedefbas.learnTest.model.User;
import com.sedefbas.learnTest.repository.UserRepository;
import com.sedefbas.learnTest.service.validators.IdentificationNumberValidator;
import jakarta.persistence.EntityExistsException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    UserRepository mockUserRepository;

    @Mock
    IdentificationNumberValidator mockIdentificationNumberValidator;

    @InjectMocks
    UserService underTest;

// ilk testteki koşullar 1-tc doğru düzende olması , tc daha önce veritabanına kaydedilmemiş olması, 3- vritabanına kaydetme
    @Test
    void create_shouldCreateNewUser(){
       //given kısmı
        User expected =new User();
        expected.setIdentificationNumber("22414885888");
        expected.setPhoneNumber("052525252");

        UserDto request =new UserDto();


        when(mockIdentificationNumberValidator.test(any())).
                thenReturn(true);

        when(mockUserRepository.findByIdentificationNumber(any())).
                thenReturn(Optional.empty());
        //herhangi user kaydedilğinde expected dönecek.
        when(mockUserRepository.save(any())).thenReturn(expected);

        User actual = underTest.create(request).get();

        assertAll(
                ()-> assertNotNull(actual),
                ()-> assertEquals(expected,actual),
                ()-> assertEquals(expected.getIdentificationNumber() , actual.getIdentificationNumber()),
                () -> assertEquals(expected.getPhoneNumber(), actual.getPhoneNumber())
        );
    }

    @Test
    void create_shouldThrowExpection_WhenIdentificationNumberNotValid(){
        UserDto request = new UserDto();
       // herhangi bir parametrede false dönme seneryosu
        when(mockIdentificationNumberValidator.test(any())).thenReturn(false);
        Executable executable = ()-> underTest.create(request).get();
        assertThrows(IdentificationNumberNotValidException.class,executable);
    }

    @Test
    void create_shouldThrowException_whenCustomerIsAlreadyExist(){
        User expected =new User();
        expected.setIdentificationNumber("22414885888");
        expected.setPhoneNumber("052525252");

        UserDto request = new UserDto();

        when(mockIdentificationNumberValidator.test(any())).
                thenReturn(true);

        when(mockUserRepository.findByIdentificationNumber(any())).
                thenReturn(Optional.of(expected));

        Executable executable = ()-> underTest.create(request).get();
        assertThrows(EntityExistsException.class,executable);
    }


}