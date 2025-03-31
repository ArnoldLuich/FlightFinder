# FlightFinder

## Ülevaade


## Rakenduse Käivitamine

### Samm 1: Klooni Projekt
```sh
git clone ....
```

### Samm 2: Kontrolli PostgreSQL Andmebaasi Õigsusi
Ava .env fail oma projekti kaustas.
Kontrolli POSTGRES_USER ja POSTGRES_PASSWORD väärtusi.
Veendu, et need ühtivad sinu PostgreSQL seadistusega.

### Samm 3: Ehita Backend

```sh
cd backend
```

```sh
./gradlew clean bootJar
```

### Samm 4: Käivita Rakendus Dockeriga

```sh
docker-compose up --build
```
See käsk:

Käivitab PostgreSQL andmebaasi konteineri

Käivitab Spring Boot backendi

Käivitab React frontendi

## Kulunud aega

## Keerulised koda lahensuses
