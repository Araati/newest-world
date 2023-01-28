package com.newestworld.scheduler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// TODO: 27.01.2023 Обеспечивает ли реббит гарантированную доставку сообщений? Если да, то бесконечное посылания таймаутов в случае неработоспособности остальных сервисов приведет к проблемам
@SpringBootApplication
public class SchedulerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SchedulerApplication.class, args);
    }

}
