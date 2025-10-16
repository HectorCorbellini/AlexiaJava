package com.alexia.telegram;

import com.alexia.constants.BotCommands;
import com.alexia.constants.Messages;
import com.alexia.dto.TelegramMessageDTO;
import com.alexia.entity.BotCommand;
import com.alexia.repository.BotCommandRepository;
import com.alexia.repository.TelegramMessageRepository;
import com.alexia.service.GrokService;
import com.alexia.service.TelegramService;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Bot de Telegram para Alexia.
 * Recibe mensajes de usuarios y responde con comandos y funcionalidad de eco.
 */
@Slf4j
public class AlexiaTelegramBot extends TelegramLongPollingBot {
    private final TelegramService telegramService;
    private final BotCommandRepository botCommandRepository;
    private final TelegramMessageRepository telegramMessageRepository;
    private final GrokService grokService;
    private final String botUsername;

    public AlexiaTelegramBot(String botToken, String botUsername, 
                            TelegramService telegramService,
                            BotCommandRepository botCommandRepository,
                            TelegramMessageRepository telegramMessageRepository,
                            GrokService grokService) {
        super(botToken);
        this.botUsername = botUsername;
        this.telegramService = telegramService;
        this.botCommandRepository = botCommandRepository;
        this.telegramMessageRepository = telegramMessageRepository;
        this.grokService = grokService;
        log.info("Bot de Telegram inicializado con Grok AI - username=@{}", botUsername);
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            processTextMessage(update);
        }
    }

    /**
     * Procesa un mensaje de texto recibido.
     */
    private void processTextMessage(Update update) {
        Long chatId = update.getMessage().getChatId();
        String messageText = update.getMessage().getText();
        User user = update.getMessage().getFrom();

        log.info("Mensaje recibido - chatId={}, userName={}, firstName={}, messageLength={}", 
                chatId, user.getUserName(), user.getFirstName(), messageText.length());
        log.debug("Contenido del mensaje - chatId={}, text={}", chatId, messageText);

        String response;
        
        // Verificar si es un comando
        if (messageText.startsWith("/")) {
            response = handleCommand(chatId, user, messageText);
        } else {
            // Generar respuesta con Grok AI para mensajes normales
            response = generateGrokResponse(chatId, messageText);
        }

        // Guardar mensaje en base de datos
        saveMessageToDatabase(chatId, user, messageText, response);

        // Enviar respuesta al usuario
        sendTextMessage(chatId, response);
    }

    /**
     * Maneja los comandos del bot.
     */
    private String handleCommand(Long chatId, User user, String commandText) {
        String command = commandText.split(" ")[0].toLowerCase();
        
        log.info("Comando recibido - chatId={}, command={}, userName={}", 
                chatId, command, user.getUserName());
        
        // Guardar comando en base de datos
        saveCommandToDatabase(chatId, user, command);
        
        // Procesar comando
        return switch (command) {
            case BotCommands.START -> BotCommands.START_MESSAGE;
            case BotCommands.HELP -> BotCommands.HELP_MESSAGE;
            case BotCommands.STATUS -> generateStatusMessage();
            default -> BotCommands.UNKNOWN_COMMAND_MESSAGE;
        };
    }

    /**
     * Genera el mensaje de estado del bot.
     */
    private String generateStatusMessage() {
        long totalMessages = telegramMessageRepository.count();
        long totalCommands = botCommandRepository.count();
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
        
        return String.format(BotCommands.STATUS_MESSAGE_TEMPLATE, 
                totalMessages, totalCommands, timestamp);
    }

    /**
     * Guarda el comando ejecutado en la base de datos.
     */
    private void saveCommandToDatabase(Long chatId, User user, String command) {
        try {
            BotCommand botCommand = BotCommand.builder()
                    .chatId(chatId)
                    .command(command)
                    .userName(user.getUserName())
                    .firstName(user.getFirstName())
                    .createdAt(LocalDateTime.now())
                    .build();

            botCommandRepository.save(botCommand);
            log.debug("Comando guardado en BD - chatId={}, command={}", chatId, command);
        } catch (Exception e) {
            log.error("Error al guardar comando en BD - chatId={}, command={}, exception={}, message={}", 
                    chatId, command, e.getClass().getSimpleName(), e.getMessage(), e);
        }
    }

    /**
     * Genera una respuesta usando Grok AI o eco como fallback.
     */
    private String generateGrokResponse(Long chatId, String messageText) {
        try {
            // Intentar obtener respuesta de Grok AI
            String grokResponse = grokService.getResponse(chatId, messageText);
            
            // Si Grok AI responde correctamente, retornar
            if (grokResponse != null && !grokResponse.isEmpty()) {
                log.info("Respuesta de Grok AI generada - chatId={}, responseLength={}", 
                        chatId, grokResponse.length());
                return grokResponse;
            }
        } catch (Exception e) {
            log.error("Error al obtener respuesta de Grok AI - chatId={}, error={}", 
                    chatId, e.getMessage());
        }
        
        // Fallback: usar respuesta eco
        log.warn("Grok AI no disponible, usando respuesta eco - chatId={}", chatId);
        return Messages.TELEGRAM_ECHO_PREFIX + messageText;
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
            log.debug("Mensaje guardado en BD - chatId={}, userName={}", chatId, user.getUserName());
        } catch (Exception e) {
            log.error("Error al guardar mensaje en BD - chatId={}, exception={}, message={}", 
                    chatId, e.getClass().getSimpleName(), e.getMessage(), e);
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
            log.info("Respuesta enviada - chatId={}, responseLength={}", chatId, text.length());
        } catch (TelegramApiException e) {
            log.error("Error al enviar mensaje - chatId={}, exception={}, message={}", 
                    chatId, e.getClass().getSimpleName(), e.getMessage(), e);
        }
    }
}
