# FlightFinder

## Ülevaade
### Rakenduse funktsionaalsused
1) Lennuplaani vaatamine ja filtreerimine
2) Lennu valimine
3) Istekohtade soovitamine
4) Random lennu lisamine 

## Rakenduse Käivitamine
### Eeldused:
- [Docker](https://docs.docker.com/desktop/setup/install/windows-install/)
- [PostgreSQL](https://www.postgresql.org/)
- [Java 21](https://www.oracle.com/ee/java/technologies/downloads/#java21)


### Samm 1: Klooni Projekt
```sh
git clone https://github.com/ArnoldLuich/FlightFinder.git
```

### Samm 2: Kontrolli PostgreSQL Andmebaasi Õigsusi
   1. Ava .env fail oma projekti juur kaustas.
   2. Kontrolli POSTGRES_USER ja POSTGRES_PASSWORD väärtusi.
   3. Veendu, et need ühtivad sinu PostgreSQL seadistusega.

### Samm 3: Backend'i ettevalmistamine esimeseks käivituseks
See samm on vajalik esimeseks käivituseks, kuna see loob vajaliku kaustastruktuuri, mida Docker hiljem kasutab.

```sh
cd .\Backend\
```

```sh
./gradlew clean bootJar
```

### Samm 4: Käivita Rakendus Dockeriga
Kui oled eelnevalt ette valmistanud backend'i, käivita rakendus Dockeris juurkaustas järgmise käsu abil

```sh
docker-compose up --build
```

## Probleemid ja lahendused
Suurimaks väljakutseks osutus istekohtade soovitamise algoritm. Peamine keerukus seisnes selles, kuidas määrata parim võimalik istekoht vastavalt kasutaja eelistustele ja olemasolevale istekohtade paigutusele.
### Lahendus:
Kuna algoritmi loomine osutus keeruliseks, kasutasin selle väljatöötamisel ChatGPT abi. 
   1. Analüüsib lennuki istekohtade plaani ridade kaupa.
   2. Kontrollib, kas reas on piisavalt vabu kohti, et mitu reisijat saaksid istuda kõrvuti.
   3. Kui sobiv koht on leitud, valitakse parim variant kasutaja määratud eelistuste põhjal (näiteks aknaäärne koht, rohkem jalaruumi, väljapääsu lähedus).
   4. Kui ühes reas ei ole sobivat kohta, otsib algoritm võimalikult lähedasi istekohti mitmel real, jälgides kasutaja seatud eelistusi.


## Ajakulu ja arenduse etapid
Selle projekti arendamiseks kulus kokku 5 päeva, mille jooksul töötasin ligikaudu 32 tundi.

### 1. Planeerimine ja jooniste loomine
   1. Analüüsisin ülesannet ja määratlesin vajalikud funktsionaalsused.
   2. Joonistasin skeemi, et paremini mõista, milliseid komponente on vaja
   ![CGI_proovitöö](https://github.com/user-attachments/assets/5ea12dc3-5e1a-403e-aadd-a3e398d7a7b7)

### 2. Backend arendus
   1. Lõin Spring Boot projektistruktuuri ja seadistasin andmebaasi.
   2. Töötasin välja REST API-d otsimiseks ja filtreerimiseks.
   3. Rakendasin istekohtade soovitamise loogika ning genereerisin juhuslikult juba hõivatud kohad.
### 3. Frontend arendus
   1. Lõin kasutajaliidese põhiraamistiku ja kujunduse.
   2. Integreerisin back-endi API-d kasutajaliidesega.
   3. Lisasin filtreerimise ja istekohtade kuvamise lennuki plaanil.
### 4. Funktsionaalsuste täiustamine ja koodi puhastamine
   1. Viimase kahe päeva jooksul täiendasin olemasolevaid funktsionaalsusi.
   2. Parandasin vigu, optimeerisin koodi ja täiustasin kasutajakogemust.
   3. Dokumenteerisin projekti
## Kasutatud ressursid



