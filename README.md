![team.png](public%2Fteam%2Fteam.png)

# Talent Pulls (back-service as talentium)

###### by Sergey Pavlik, tg: @pavbatol

### Что это

#### Интерактивная платформа-сообщество для стажеров и участников молодежных карьерных проектов.  
Является решением по задаче [#16](https://leaders2023.innoagency.ru/task_16) для хакатона "Лидеры цифровой трансформации - 2023"

Дополнительное описание работы сервиса смотрите по ссылке:
[Описание работы сервиса.txt](public%2Fdocumentation%2F%D0%9E%D0%BF%D0%B8%D1%81%D0%B0%D0%BD%D0%B8%D0%B5%20%D1%80%D0%B0%D0%B1%D0%BE%D1%82%D1%8B%20%D1%81%D0%B5%D1%80%D0%B2%D0%B8%D1%81%D0%B0.txt)
### Возможности
- #### Сервис авторизации (t-auth)
    - Первоначальная регистрация с минимальным набором полей. Пользователь будет идентифицироваться в других сервисах по ID из базы данных этого сервиса.
    - Авторизация/аутентификация по токену

- #### Основной сервис (t-core)
  - Зоны доступа: public, private, admin. Зона private включает роли: CANDIDATE, INTERN, CURATOR, MENTOR, HH (компания).
  - Реальные пользователи (сущности) платформы: 
    - Студент - имеет несколько состояний: CANDIDATE, INVITEE, LEARNER, CONTESTANT, CHALLENGER, INTERN. Имеет уровень знаний: BEGINNER, INTERMEDIATE, ADVANCED. Имеет одного наставника и направление стажировки
    - Наставник - имеет градацию уровня знаний: BEGINNER, INTERMEDIATE, ADVANCED, CERTIFICATED. Связан с одним студентом, имеет принадлежность к компании
    - Куратор - имеет принадлежность к компании, в большинстве случаев обладает правами администратора
    - Компания - создает стажировки, содержит направления, адрес, контакты, ФИ ответственного
    - Стажировка - создается компанией. Содержит свойства:
      - Заголовок
      - Аннотация
      - Описание
      - Инициатор
      - Направление
      - Географические координаты
      - Возрастной диапазон
      - Лимит участников
      - Кол-во подтвержденных откликов
      - Дата создания
      - Дата публикации после модерации
      - Даты начала и окончания стажировки
      - Длительность рабочего дня (FULL, HALF)
      - Состояние: PENDING, PUBLISHED, CANCELED  - устанавливают участники с правами ADMIN или HH
    - Фидбек (не до конца реализовано) - содержит текст и оценку. Фидбек можно оставлять для Компании, Наставника, Студента. Эти участники имеют рейтинг.
    - Общее - **`При поиске Компании и в особенности поиске Стажировки реализован глубокий фильтр и сортировка по всем значимым полям`**

### Схемы

#### Сервис авторизации (t-auth)
![t-auth-er-diagram.png](public%2Fer-diagram%2Ft-auth-er-diagram.png)

  #### Основной сервис (t-core)
![t-core-er-diagram.png](public%2Fer-diagram%2Ft-core-er-diagram.png)

### Инструментарий
  - Микро-сервисная архитектура
  - SpringBoot, Spring Security
  - JPA, HIBERNATE
  - Postgresql, Redis
  - Maven
  - Docker-compose

### Open API
  - swagger-ui 
    - t-auth: host:8078/docs/swagger-ui.html
    - t-core: host:8080/docs/swagger-ui.html
  - api-docs
    - t-auth: host:8078/docs
    - t-core: host:8080/docs
  - specification
    - t-auth: [talentium-t-auth-openapi.yaml](public%2Fopen-api-specification%2Ftalentium-t-auth-openapi.yaml)
    - t-auth: [Посмотреть в PDF](public%2Fopen-api-specification%2FSwagger%20U_t-auth.pdf) 
    - t-core: [talentium-t-core-openapi.yaml](public%2Fopen-api-specification%2Ftalentium-t-core-openapi.yaml)
    - t-core: [Посмотреть в PDF](public%2Fopen-api-specification%2FSwagger%20U_t-core.pdf)

### Запуск
- Собрать проект командой: mvn clean package
- Запустить из каталога проекта под правами администратора:
  - docker-compose build
  - docker-compose up