package com.alexia.config;

import com.alexia.repository.BotCommandRepository;
import com.alexia.repository.TelegramMessageRepository;
import com.alexia.service.GrokService;
import com.alexia.service.TelegramService;
import com.alexia.telegram.AlexiaTelegramBot;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

/**
 * Configuración del bot de Telegram.
 * Inicializa y registra el bot en la API de Telegram.
 */
@Configuration
@RequiredArgsConstructor
@Slf4j
public class TelegramBotConfig {

    @Value("${telegram.bot.token}")
    private String botToken;

    @Value("${telegram.bot.username}")
    private String botUsername;

    private final TelegramService telegramService;
    private final BotCommandRepository botCommandRepository;
    private final TelegramMessageRepository telegramMessageRepository;
    private final GrokService grokService;

    @Bean
    public AlexiaTelegramBot alexiaTelegramBot() {
        return new AlexiaTelegramBot(botToken, botUsername, telegramService, 
                botCommandRepository, telegramMessageRepository, grokService);
    }

    @Bean
    public TelegramBotsApi telegramBotsApi(AlexiaTelegramBot alexiaTelegramBot) throws TelegramApiException {
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            
            // Registrar el bot
            botsApi.registerBot(alexiaTelegramBot);
            
            log.info("Bot de Telegram registrado exitosamente - username=@{}", 
                    alexiaTelegramBot.getBotUsername());
            
            return botsApi;
        } catch (TelegramApiException e) {
            log.error("Error al registrar bot de Telegram - exception={}, message={}", 
                    e.getClass().getSimpleName(), e.getMessage());
            log.error("Verifica que el token sea válido y que no haya un webhook configurado");
            throw new RuntimeException("No se pudo inicializar el bot de Telegram", e);
        }
    }
}
