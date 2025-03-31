# FlightFinder

## Ülevaade


## Rakenduse Käivitamine
### Eeldused:
- [Docker](https://docs.docker.com/desktop/setup/install/windows-install/)
- [PostgreSQL](https://www.postgresql.org/)
- [Java 21](https://www.oracle.com/ee/java/technologies/downloads/#java21)


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

## Probleemid ja lahendused


## Ajakulu ja arenduse etapid
Selle projekti arendamiseks kulus kokku 5 päeva, mille jooksul töötasin ligikaudu 32 tundi.

### 1. Planeerimine ja jooniste loomine
	Analüüsisin ülesannet ja määratlesin vajalikud funktsionaalsused.

	Joonistasin skeemi, et paremini mõista, milliseid komponente on vaja

### 2. Backend arendus

### 3. Frontend arendus

### 4. Funktsionaalsuste täiustamine ja koodi puhastamine
