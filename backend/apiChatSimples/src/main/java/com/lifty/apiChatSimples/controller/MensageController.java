package com.lifty.apiChatSimples.controller;


import com.lifty.apiChatSimples.dtos.mensage.MensageRequestDTO;
import com.lifty.apiChatSimples.dtos.mensage.MensageResponseDTO;
import com.lifty.apiChatSimples.repository.MensageRepository;
import com.lifty.apiChatSimples.service.MensageService;
import jakarta.persistence.Entity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mensage")

public class MensageController {

    private MensageService mensageService;

    public MensageController(MensageService mensageService){
        this.mensageService = mensageService;
    }

    @GetMapping("/conversation/{id}/mensages")
    public ResponseEntity<List<MensageResponseDTO>> listMensagesByConversationId(@PathVariable Long id){
        List<MensageResponseDTO> mensageResponseDTOS = mensageService.listMensagesByConversationId(id);
        return ResponseEntity.ok(mensageResponseDTOS);
    }

    @GetMapping("/list-mensages")
    public ResponseEntity<List<MensageResponseDTO>> listAllMensages(){
       List<MensageResponseDTO> mensageResponseDTOS = mensageService.listAllMensages();
       return ResponseEntity.ok(mensageResponseDTOS);
    }

    @PostMapping
    public ResponseEntity<MensageResponseDTO> sendMensage(@RequestBody MensageRequestDTO mensageRequestDTO) {
       MensageResponseDTO mensageResponseDTO = mensageService.sendMensage(mensageRequestDTO);
       return ResponseEntity.ok(mensageResponseDTO);
    }



}
