#Процедура запуска автотестов

##Перед запуском проверить наличие программ или при остутсвии установить:
- Git
- Браузер Google Chrome
- IntelliJ IDEA (+ плагин Allure, Docker, Lombok)
- Docker Desktop
- chromeDriver 

#Запуск:
1. Склонировать репозиторий https://github.com/hoodsey/DIPLOMA через git clone
2. Открыть склонированный проект в IntelliJ IDEA
3. Запустить Docker Desktop
3. В терминале IntelliJ IDEA запустить контейнеры Docker командой: docker-compose up --build
Для БД MySQL
5. Запустить приложение в новом терминале командой: java "-Dspring.datasource.url=jdbc:mysql://localhost:3306/app" -jar ./artifacts/aqa-shop.jar
6. Ввести в браузере Сhrome URL http://localhost/8080, откроется страница приложения.
7. Запустить тесты с БД MySQL  в новом терминале командой: ./gradlew clean test "-Ddb.url=jdbc:mysql://localhost:3306/app"
8. После прохождения сгенерировать отчет командой в новом терминале:
./gradlew allureServe
Автоматически откроется отчет в браузере
9. Остановить allureServe командой в терминале: CTRL + C
10. Завершить выполнение терминала с командой из пункта 5
11. Запустить приложение в новом терминале с БД PostgresSQL: java "-Dspring.datasource.url=jdbc:postgresql://localhost:5432/app" -jar ./artifacts/aqa-shop.jar
12. Запустить в новом терминале тесты командой: ./gradlew clean test "-Ddb.url=jdbc:postgresql://localhost:5432/app"
13. После прохождения сгенерировать отчет командой в новом терминале:
./gradlew allureServe
Автоматически откроется отчет в браузере
14. Остановить allureServe командой в терминале: CTRL + C
15. Завершить выполнение терминала с командой из пункта 5
16. Завершить выполнение терминала с командой из пункта 3


