## Урок 11. Spring Actuator. Настройка мониторинга с Prometheus и Grafana.

Задание: Используйте Spring Actuator для отслеживания метрик вашего приложения. Настройте визуализацию этих метрик
с использованием Prometheus и Grafana.

Задание выполнено для микросервиса `UserProjectService`

В [pom.xml](UserProjectService%2Fpom.xml) внедрены зависимости

```
<!-- https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-actuator -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>


<!-- https://mvnrepository.com/artifact/io.micrometer/micrometer-registry-prometheus -->
<dependency>
    <groupId>io.micrometer</groupId>
    <artifactId>micrometer-registry-prometheus</artifactId>
</dependency>
```

В настройках [application.yml](UserProjectService%2Fsrc%2Fmain%2Fresources%2Fapplication.yml) определены настройки

```angular2html
management:
  endpoints:
    web:
      exposure:
        include: health,metrics,prometheus
  endpoint:
    health:
      show-details: always
```

Prometheus и Grafana развернуты в Docker

Создан файл `docker-compose.yml`

```angular2html
services:
  prometheus:
    image: prom/prometheus:latest
    container_name: prometheus
    volumes:
      - ./prometheus.yml:/etc/prometheus/prometheus.yml
    ports:
      - "9090:9090"
    restart: always

  grafana:
    image: grafana/grafana:latest
    container_name: grafana
    ports:
      - "3000:3000"
    environment:
      - GF_SECURITY_ADMIN_USER=admin
      - GF_SECURITY_ADMIN_PASSWORD=admin
    restart: always
```

Создан файл `prometheus.yml`

```angular2html
global:
  
  scrape_interval: 15s

scrape_configs:
  - job_name: 'prometheus'

  - job_name: 'UserProjectService'
    metrics_path: 'actuator/prometheus'
    static_configs:
      - targets: ['host.docker.internal:8082']
```

В настройках Grafana прописываем путь к Prometheus в контейнере http://prometheus:9090

В корне размещены 2 скриншота с работающими серверами Grafana, Prometheus, Eurica, одной из страниц разработанного приложения

---

## Урок 10. Spring Testing. JUnit и Mockito для написания тестов.

```
Задание: Напишите тесты для основной логики вашего приложения с использованием Spring Testing, JUnit и Mockito.
```

В процессе выполнения задания для микросервиса [UserProjectService](UserProjectService) были разработаны: 

* класс [ProjectServiceUnitTest.java](UserProjectService%2Fsrc%2Ftest%2Fjava%2Forg%2Fexample%2Fservice%2FProjectServiceUnitTest.java), 
содержащий юнит-тест `updateProjectByIdUnitTest` метода `updateProjectById` сервиса [ProjectService.java](UserProjectService%2Fsrc%2Fmain%2Fjava%2Forg%2Fexample%2Fservice%2FProjectService.java)

* класс [ProjectServiceIntegrationTest.java](UserProjectService%2Fsrc%2Ftest%2Fjava%2Forg%2Fexample%2Fservice%2FProjectServiceIntegrationTest.java),
соддержащий интеграционные тесты методов сервиса [ProjectService.java](UserProjectService%2Fsrc%2Fmain%2Fjava%2Forg%2Fexample%2Fservice%2FProjectService.java)

---

## Урок 9. Spring Cloud. Микросервисная архитектура.

```
Задание: Создайте простую микросервисную архитектуру с использованием Spring Cloud. Ваша архитектура должна включать хотя бы два микросервиса и службу распределения.
```

Для выполнения задания была разработана простая микросервисная архитектура с использованием Spring Cloud, включающая несколько ключевых компонентов, которые обеспечивают эффективное взаимодействие между сервисами, а также управление их маршрутизацией и регистрацией.

Архитектура:

API Gateway (API Gateway) API Gateway выполняет роль единой точки входа для всех внешних запросов, управляя маршрутизацией запросов и обеспечивая обработку различных операций с сервисами. Он принимает запросы от пользователей и направляет их к соответствующим микросервисам, скрывая детали их реализации. Для реализации маршрутизации использован Spring Cloud Gateway, который предоставляет гибкие возможности для балансировки нагрузки и фильтрации запросов.

Eureka (Служба регистрации сервисов) В системе используется Eureka как служба регистрации для динамического обнаружения сервисов. Каждый микросервис регистрируется в Eureka Server, что позволяет API Gateway и другим сервисам находить и взаимодействовать друг с другом без необходимости жесткой настройки маршрутов. Это повышает масштабируемость и гибкость архитектуры.

WebService (Микросервис для взаимодействия с пользователем) WebService — это фронтенд-микросервис, который предоставляет пользователю интерфейс для работы с веб-формами и отправки запросов на другие микросервисы. Взаимодействие с другими сервисами осуществляется с помощью Feign, что позволяет WebService отправлять REST-запросы к микросервису UserProjectService и получать ответы. Feign упрощает клиентскую сторону взаимодействия, так как автоматически обрабатывает создание HTTP-запросов и парсинг ответов.

UserProjectService (Микросервис управления пользователями и проектами) UserProjectService — это основной бизнес-микросервис, отвечающий за управление пользователями и проектами. Он реализует CRUD операции для работы с базой данных, хранит информацию о пользователях и проектах, а также предоставляет REST API для взаимодействия с другими сервисами. Микросервис интегрирован с базой данных, что позволяет эффективно управлять данными и обеспечивать целостность информации.

Взаимодействие компонентов:

API Gateway принимает запросы от пользователей и, в зависимости от маршрута, направляет их либо в WebService, либо в UserProjectService.

WebService является посредником, обеспечивая представление веб-форм и отправку данных на UserProjectService с помощью Feign, который упрощает взаимодействие с REST API.

UserProjectService управляет всеми операциями с пользователями и проектами, включая создание, чтение, обновление и удаление данных, и предоставляет API для взаимодействия с другими сервисами.