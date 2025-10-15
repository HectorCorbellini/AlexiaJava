package com.alexia.telegram;

import com.alexia.constants.Messages;
import com.alexia.dto.TelegramMessageDTO;
import com.alexia.service.TelegramService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 * Bot de Telegram para Alexia.
 * Recibe mensajes de usuarios y responde con funcionalidad de eco.
 */
@Component
@Slf4j
public class AlexiaTelegramBot extends TelegramLongPollingBot {

    private final TelegramService telegramService;
    private final String botUsername;

    public AlexiaTelegramBot(
            @Value("${telegram.bot.token}") String botToken,
            @Value("${telegram.bot.username:AlexiaBot}") String botUsername,
            TelegramService telegramService) {
        super(botToken);
        this.botUsername = botUsername;
        this.telegramService = telegramService;
        log.info("Bot de Telegram inicializado: @{}", botUsername);
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            Long chatId = update.getMessage().getChatId();
            String messageText = update.getMessage().getText();
            User user = update.getMessage().getFrom();

            log.info("Mensaje recibido de {} ({}): {}", 
                    user.getFirstName(), chatId, messageText);

            // Generar respuesta eco
            String response = generateEchoResponse(messageText);

            // Guardar mensaje en base de datos
            saveMessageToDatabase(chatId, user, messageText, response);

            // Enviar respuesta al usuario
            sendTextMessage(chatId, response);
        }
    }

    /**
     * Genera una respuesta eco para el mensaje recibido.
     */
    private String generateEchoResponse(String messageText) {
        return "Recibí tu mensaje: " + messageText;
    }

    /**
     * Guarda el mensaje en la base de datos.
     */
    private void saveMessageToDatabase(Long chatId, User user, String messageText, String response) {
        try {
            TelegramMessageDTO dto = TelegramMessageDTO.builder()
                    .chatId(chatId)
                    .userName(user.getUserName())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .messageText(messageText)
                    .botResponse(response)
                    .build();

            telegramService.saveMessage(dto);
        } catch (Exception e) {
            log.error("Error al guardar mensaje en BD: {}", e.getMessage(), e);
        }
    }

    /**
     * Envía un mensaje de texto al usuario.
     */
    private void sendTextMessage(Long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId.toString());
        message.setText(text);

        try {
            execute(message);
            log.info("Respuesta enviada a chat {}", chatId);
        } catch (TelegramApiException e) {
            log.error("Error al enviar mensaje: {}", e.getMessage(), e);
        }
    }
}
