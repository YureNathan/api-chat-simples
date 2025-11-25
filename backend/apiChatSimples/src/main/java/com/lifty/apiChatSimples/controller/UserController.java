package com.lifty.apiChatSimples.controller;

import com.lifty.apiChatSimples.dtos.user.UserRequestDTO;
import com.lifty.apiChatSimples.dtos.user.UserResponseDTO;
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
    public ResponseEntity<List<UserResponseDTO>> listAllUsers(){
      List<UserResponseDTO> userResponseDTOS = userService.listAllUsers();
      return ResponseEntity.ok(userResponseDTOS);
   }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> listUserId(@PathVariable Long id){
        UserResponseDTO userResponseDTO = userService.findUserById(id);
        return ResponseEntity.ok(userResponseDTO);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateUser(@PathVariable Long id, @RequestBody UserRequestDTO userRequestDTO){
        userService.updateUser(id, userRequestDTO);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
