# Тестовое задание на CaseLab Java

### Для реализации микросервиса была выбрана трехслойная архитектура 
1. core - пакет с pojo
2. service - пакет с сервисами
3. application - пакет с контроллерами

### Примечание
* Для миграций бд используется Flyway
* Для взаимодействия с бд используется Hibernate
* Реализована пагинация и сортировка по дате создания файла
* Написаны тесты
* Приложение можно упаковать в docker-контейнер

### API
* Примеры тестовых запросов находятся в [файле](requests.http)
* Доступна [документация Api](http://localhost:8080/swagger-ui/index.html#/) (только при запущенном приложении)

### Инструкция по запуску
#### Вне docker-контейнера
* Запустить [compose файл для бд](src/main/java/docker-compose.yml)
* Запустить [приложение](src/main/java/org/itmo/caselab/CaseLabApplication.java)
#### В docker-контейнере
* Запаковать приложение: mvn clean package
* Запустить [compose файл для приложения](docker-compose.yml) (бд будет внутри)