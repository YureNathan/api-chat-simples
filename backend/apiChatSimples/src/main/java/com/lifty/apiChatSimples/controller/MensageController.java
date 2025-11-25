package com.lifty.apiChatSimples.controller;


import com.lifty.apiChatSimples.dtos.mensage.MensageRequestDTO;
import com.lifty.apiChatSimples.dtos.mensage.MensageResponseDTO;
import com.lifty.apiChatSimples.service.MensageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mensage")

public class MensageController {

    private static final Logger logger = LoggerFactory.getLogger(MensageController.class);

    private MensageService mensageService;

    public MensageController(MensageService mensageService){
        this.mensageService = mensageService;
    }

    @GetMapping("{id}/mensages")
    public ResponseEntity<List<MensageResponseDTO>> listMensagesByConversationId(@PathVariable Long id){
        logger.info("Requisição para listar mensagens da conversa ID: {}", id);
        try{
        List<MensageResponseDTO> mensageResponseDTOS = mensageService.listMensagesByConversationId(id);
            logger.info("Total de mensagens encontradas para a conversa {}: {}", id, mensageResponseDTOS.size());
            return ResponseEntity.ok(mensageResponseDTOS);
    }catch (Exception e) {
            logger.error("Erro ao listar mensagens da conversa {}: {}", id, e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/list-mensages")
    public ResponseEntity<List<MensageResponseDTO>> listAllMensages(){
        logger.info("Requisição recebida para listar todas as mensagens");
        try {
            List<MensageResponseDTO> mensageResponseDTOS = mensageService.listAllMensages();
            logger.info("Total de mensagens encontradas: {}", mensageResponseDTOS.size());
            return ResponseEntity.ok(mensageResponseDTOS);
        } catch (Exception e) {
            logger.error("Erro ao listar todas as mensagens: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping
    public ResponseEntity<MensageResponseDTO> sendMensage(@RequestBody MensageRequestDTO mensageRequestDTO) {
        logger.info("Requisição para enviar nova mensagem");
        logger.debug("Payload recebido: {}", mensageRequestDTO);
        try{
            MensageResponseDTO mensageResponseDTO = mensageService.sendMensage(mensageRequestDTO);
            logger.info("Mensagem enviada com sucesso. ID gerado: {}", mensageResponseDTO != null ? mensageResponseDTO.idMensage() : "null");
            return ResponseEntity.status(201).body(mensageResponseDTO);
        } catch (Exception e) {
            logger.error("Erro ao enviar mensagem: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }



    }



}
