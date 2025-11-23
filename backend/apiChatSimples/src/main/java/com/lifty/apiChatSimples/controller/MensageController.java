package com.lifty.apiChatSimples.controller;


import com.lifty.apiChatSimples.dtos.mensage.MensageRequestDTO;
import com.lifty.apiChatSimples.dtos.mensage.MensageResponseDTO;
import com.lifty.apiChatSimples.repository.MensageRepository;
import com.lifty.apiChatSimples.service.MensageService;
import jakarta.persistence.Entity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mensage")

public class MensageController {

    private MensageService mensageService;

    public MensageController(MensageService mensageService){
        this.mensageService = mensageService;
    }

    @PostMapping
    public ResponseEntity<MensageResponseDTO> sendMensage(@RequestBody MensageRequestDTO mensageRequestDTO) {
       MensageResponseDTO mensageResponseDTO  =  mensageService.sendMensage(mensageRequestDTO);
       return ResponseEntity.ok(mensageResponseDTO);
    }








}
