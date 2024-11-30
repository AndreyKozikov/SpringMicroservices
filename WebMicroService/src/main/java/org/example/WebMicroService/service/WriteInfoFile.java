package org.example.WebMicroService.service;

import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.file.FileHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;

/**
 * Интерфейс для отправки сообщений в канал с целью записи данных в файл.
 * Используется для интеграции с Spring Integration.
 */
@MessagingGateway(defaultRequestChannel = "inputMessageChannel")
public interface WriteInfoFile {

    /**
     * Записывает переданные данные в указанный файл.
     *
     * @param fileName имя файла, в который должны быть записаны данные.
     *                 Значение извлекается из заголовков сообщения с использованием
     *                 FileHeaders.FILENAME.
     * @param string строка, содержащая данные, которые должны быть записаны в файл.
     */
    void writeToFile(@Header(FileHeaders.FILENAME) String fileName, String string);
}
