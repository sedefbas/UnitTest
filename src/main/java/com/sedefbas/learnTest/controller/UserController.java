package com.sedefbas.learnTest.controller;

import com.sedefbas.learnTest.dto.UserDto;
import com.sedefbas.learnTest.model.User;

import com.sedefbas.learnTest.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
   private UserService userService;

    @PostMapping
    public ResponseEntity<User> create(@RequestBody UserDto request){
        Optional<User> userOptional = userService.create(request);

        if(!userOptional.isPresent()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(userOptional.get(), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAll(){
        final List<UserDto> userDtoList = userService.findAll();

        return new ResponseEntity<>(userDtoList, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<UserDto> getById(@PathVariable long id){
        final UserDto userDto = userService.findById(id);
        if(userDto == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteById(@PathVariable long id){
        try{
            userService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (EntityNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
