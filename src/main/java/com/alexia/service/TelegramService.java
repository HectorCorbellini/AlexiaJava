package com.alexia.service;

import com.alexia.dto.TelegramMessageDTO;
import com.alexia.entity.TelegramMessage;
import com.alexia.repository.TelegramMessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementación del servicio para gestionar mensajes de Telegram.
 * Maneja la lógica de negocio relacionada con la persistencia de mensajes.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class TelegramService implements ITelegramService {

    private final TelegramMessageRepository telegramMessageRepository;

    /**
     * Guarda un mensaje de Telegram en la base de datos.
     *
     * @param dto DTO con la información del mensaje
     * @return El mensaje guardado
     */
    @Override
    @Transactional
    public TelegramMessage saveMessage(TelegramMessageDTO dto) {
        try {
            TelegramMessage message = TelegramMessage.builder()
                    .chatId(dto.getChatId())
                    .userName(dto.getUserName())
                    .firstName(dto.getFirstName())
                    .lastName(dto.getLastName())
                    .messageText(dto.getMessageText())
                    .botResponse(dto.getBotResponse())
                    .createdAt(LocalDateTime.now())
                    .build();

            TelegramMessage saved = telegramMessageRepository.save(message);
            log.info("Mensaje de Telegram guardado. ID: {}, Chat: {}, Usuario: {}", 
                    saved.getId(), saved.getChatId(), dto.getFullName());
            
            return saved;
            
        } catch (Exception e) {
            log.error("Error al guardar mensaje de Telegram: {}", e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Obtiene todos los mensajes de un chat específico.
     *
     * @param chatId ID del chat
     * @return Lista de mensajes
     */
    @Override
    public List<TelegramMessageDTO> getMessagesByChatId(Long chatId) {
        return telegramMessageRepository.findByChatIdOrderByCreatedAtDesc(chatId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Obtiene todos los mensajes ordenados por fecha.
     *
     * @return Lista de todos los mensajes
     */
    @Override
    public List<TelegramMessageDTO> getAllMessages() {
        return telegramMessageRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Cuenta el total de mensajes recibidos.
     *
     * @return Número total de mensajes
     */
    @Override
    public long getTotalMessageCount() {
        return telegramMessageRepository.count();
    }

    /**
     * Convierte una entidad a DTO.
     */
    private TelegramMessageDTO convertToDTO(TelegramMessage entity) {
        return TelegramMessageDTO.builder()
                .chatId(entity.getChatId())
                .userName(entity.getUserName())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .messageText(entity.getMessageText())
                .botResponse(entity.getBotResponse())
                .timestamp(entity.getCreatedAt())
                .build();
    }
}
