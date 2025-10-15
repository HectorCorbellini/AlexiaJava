package com.alexia.config;

import com.alexia.telegram.AlexiaTelegramBot;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    private final AlexiaTelegramBot alexiaTelegramBot;

    @Bean
    public TelegramBotsApi telegramBotsApi() {
        try {
            // Crear la API sin intentar limpiar webhooks automáticamente
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            
            // Registrar el bot
            botsApi.registerBot(alexiaTelegramBot);
            
            log.info("✓ Bot de Telegram registrado exitosamente");
            log.info("✓ Bot username: @{}", alexiaTelegramBot.getBotUsername());
            
            return botsApi;
        } catch (TelegramApiException e) {
            log.error("✗ Error al registrar bot de Telegram: {}", e.getMessage());
            log.error("✗ Verifica que el token sea válido y que no haya un webhook configurado");
            throw new RuntimeException("No se pudo inicializar el bot de Telegram", e);
        }
    }
}
