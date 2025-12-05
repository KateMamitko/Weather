# Weather App

Красивий Android-додаток для перегляду погоди в різних містах.

## ✨ Можливості

- Перегляд погоди у вибраних містах
- Додавання обраних локацій
- Автовизначення місцезнаходження
- Детальна інформація про погоду
- Ефектна UI-частина зі світлими градієнтами
- Збереження улюблених міст у локальній базі (Room)

## 📸 Скріншоти

### Головний екран

<img src="/screenshots/home.jpg" width="280"/>
<img src="/screenshots/favorites.jpg" width="280"/>

### Пошук

<img src="/screenshots/search.jpg" width="280"/>

### Деталі погоди

<img src="/screenshots/details.jpg" width="280"/>

## 🛠 Використані технології

- **Kotlin**
- **Jetpack Compose**
- **MVI (Orbit MVI)**
- **Decompose Navigation**
- **Hilt (DI)**
- **Room (local storage)**
- **Retrofit + OkHttp**
- **Coroutines + Flow**
- **Glide Compose**

## Інструкції по збірці проекта

В файл gradle.properties потрібно добавити свій API KEY в такому форматі:

apikey=YOUR_API_KEY
