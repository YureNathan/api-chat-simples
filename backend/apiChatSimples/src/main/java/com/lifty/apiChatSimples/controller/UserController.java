package com.lifty.apiChatSimples.controller;

import com.lifty.apiChatSimples.dtos.UserRequestDTO;
import com.lifty.apiChatSimples.dtos.UserResponseDTO;
import com.lifty.apiChatSimples.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")

public class UserController {

    public final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

   @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserRequestDTO userRequestDTO) {
       UserResponseDTO userResponseDTO = userService.createUser(userRequestDTO);
       return ResponseEntity.ok(userResponseDTO);
   }

   @GetMapping
    public ResponseEntity<List<UserResponseDTO>> listAllUser(){
      List<UserResponseDTO> userResponseDTOS = userService.listAllUser();
      return ResponseEntity.ok(userResponseDTOS);
   }

}
