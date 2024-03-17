# Процедура запуска автотестов

## Перед запуском проверить наличие программ или при остутсвии установить:
- Git
- Браузер Google Chrome
- IntelliJ IDEA (+ плагин Allure, Docker, Lombok)
- Docker Desktop
- chromeDriver 

# Запуск:
1. Склонировать репозиторий https://github.com/hoodsey/DIPLOMA через git clone
2. Открыть склонированный проект в IntelliJ IDEA
3. Запустить Docker Desktop
3. В терминале IntelliJ IDEA запустить контейнеры Docker командой: docker-compose up --build
Для БД MySQL
5. Запустить приложение в новом терминале командой:
   java "-Dspring.datasource.url=jdbc:mysql://localhost:3306/app" -jar ./artifacts/aqa-shop.jar
7. Ввести в браузере Сhrome URL http://localhost/8080, откроется страница приложения.
8. Запустить тесты с БД MySQL  в новом терминале командой:
    ./gradlew clean test "-Ddb.url=jdbc:mysql://localhost:3306/app"
10. После прохождения сгенерировать отчет командой в новом терминале:
./gradlew allureServe
Автоматически откроется отчет в браузере
11. Остановить allureServe командой в терминале: CTRL + C
12. Завершить выполнение терминала с командой из пункта 5
13. Запустить приложение в новом терминале с БД PostgresSQL:
    java "-Dspring.datasource.url=jdbc:postgresql://localhost:5432/app" -jar ./artifacts/aqa-shop.jar
15. Запустить в новом терминале тесты командой:
    ./gradlew clean test "-Ddb.url=jdbc:postgresql://localhost:5432/app"
17. После прохождения сгенерировать отчет командой в новом терминале:
./gradlew allureServe
Автоматически откроется отчет в браузере
18. Остановить allureServe командой в терминале: CTRL + C
19. Завершить выполнение терминала с командой из пункта 5
20. Завершить выполнение терминала с командой из пункта 3


