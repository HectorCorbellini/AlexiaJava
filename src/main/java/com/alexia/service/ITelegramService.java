package com.alexia.service;

import com.alexia.dto.TelegramMessageDTO;
import com.alexia.entity.TelegramMessage;

import java.util.List;

/**
 * Interfaz para operaciones de mensajes de Telegram.
 * Define el contrato para servicios de gestión de mensajes.
 * 
 * @author Alexia Team
 * @version 1.0
 * @since 2025-10-15
 */
public interface ITelegramService {
    
    /**
     * Guarda un mensaje de Telegram en la base de datos.
     *
     * @param dto DTO con la información del mensaje
     * @return El mensaje guardado
     */
    TelegramMessage saveMessage(TelegramMessageDTO dto);
    
    /**
     * Obtiene todos los mensajes de un chat específico.
     *
     * @param chatId ID del chat
     * @return Lista de mensajes
     */
    List<TelegramMessageDTO> getMessagesByChatId(Long chatId);
    
    /**
     * Obtiene todos los mensajes ordenados por fecha.
     *
     * @return Lista de todos los mensajes
     */
    List<TelegramMessageDTO> getAllMessages();
    
    /**
     * Cuenta el total de mensajes recibidos.
     *
     * @return Número total de mensajes
     */
    long getTotalMessageCount();
}
