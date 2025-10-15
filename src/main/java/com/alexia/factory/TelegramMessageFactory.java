package com.alexia.factory;

import com.alexia.dto.TelegramMessageDTO;
import com.alexia.entity.TelegramMessage;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Factory para crear instancias de TelegramMessage.
 * Centraliza la lógica de creación y conversión de mensajes de Telegram.
 * 
 * @author Alexia Team
 * @version 1.0
 * @since 2025-10-15
 */
@Component
public class TelegramMessageFactory {
    
    /**
     * Crea una entidad TelegramMessage desde un DTO.
     * 
     * @param dto DTO con la información del mensaje
     * @return Nueva instancia de TelegramMessage
     */
    public TelegramMessage createFromDTO(TelegramMessageDTO dto) {
        return TelegramMessage.builder()
                .chatId(dto.getChatId())
                .userName(dto.getUserName())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .messageText(dto.getMessageText())
                .botResponse(dto.getBotResponse())
                .createdAt(LocalDateTime.now())
                .build();
    }
    
    /**
     * Crea una entidad TelegramMessage con parámetros individuales.
     * 
     * @param chatId ID del chat
     * @param userName Username del usuario
     * @param firstName Nombre del usuario
     * @param lastName Apellido del usuario
     * @param messageText Texto del mensaje
     * @param botResponse Respuesta del bot
     * @return Nueva instancia de TelegramMessage
     */
    public TelegramMessage create(Long chatId, String userName, String firstName, 
                                   String lastName, String messageText, String botResponse) {
        return TelegramMessage.builder()
                .chatId(chatId)
                .userName(userName)
                .firstName(firstName)
                .lastName(lastName)
                .messageText(messageText)
                .botResponse(botResponse)
                .createdAt(LocalDateTime.now())
                .build();
    }
    
    /**
     * Crea un DTO desde una entidad TelegramMessage.
     * 
     * @param entity Entidad TelegramMessage
     * @return DTO con la información del mensaje
     */
    public TelegramMessageDTO createDTO(TelegramMessage entity) {
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
