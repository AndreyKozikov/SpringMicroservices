package org.example.WebMicroService.config;


import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.core.GenericTransformer;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.integration.file.FileWritingMessageHandler;
import org.springframework.integration.file.support.FileExistsMode;

import java.io.File;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Конфигурационный класс для настройки интеграции Spring Integration.
 * Этот класс конфигурирует каналы, преобразователи и обработчики сообщений для записи данных в файл.
 */
@Configuration
public class IntegrationConfig {

    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Создает и возвращает канал для входящих сообщений.
     *
     * @return канал для входящих сообщений
     */
    @Bean
    public MessageChannel inputMessageChannel() {
        return new DirectChannel();
    }


    /**
     * Создает и возвращает канал для исходящих сообщений.
     *
     * @return канал для исходящих сообщений
     */
    @Bean
    public MessageChannel outputMessageChannel() {
        return new DirectChannel();
    }

    /**
     * Преобразователь, который добавляет временную метку к текстовому содержимому сообщения.
     *
     * @return преобразователь для добавления временной метки
     */
    @Bean
    @Transformer(inputChannel = "inputMessageChannel", outputChannel = "outputMessageChannel")
    public GenericTransformer<String, String> genericTransformer() {
        return text -> {
            try {
               return LocalDateTime.now() + " " + text;
            } catch (Exception e) {
                throw new RuntimeException("Ошибка ", e);
            }
        };
    }

    /**
     * Обработчик сообщений, записывающий данные в файл.
     *
     * @return обработчик для записи данных в файл
     */
    @Bean
    @ServiceActivator(inputChannel = "outputMessageChannel")
    public FileWritingMessageHandler fileWritingMessageHandler() {
        FileWritingMessageHandler handler = new FileWritingMessageHandler(new File("Output"));
        handler.setExpectReply(false); // Ожидание ответа не требуется
        handler.setFileExistsMode(FileExistsMode.APPEND); // Добавление данных к существующему файлу
        handler.setCharset("UTF-8"); // Установка кодировки файла
        handler.setAppendNewLine(true); // Добавление новой строки после каждого записанного сообщения
        return handler;
    }
}
