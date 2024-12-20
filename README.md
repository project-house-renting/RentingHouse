# projectAdvancedProgrammingTopics

| Team leden |
| --------- |
| Sander Jannes |
| Cisse Vandeweyer | 

## Project beschrijving

Onderwerp: Vastgoedplatform

Onze applicatie is een vastgoedplatform bestaande uit 4 hoofdentiteiten: Home, Tenant, Payment en Maintenance. Er zijn twee rollen, de gewone gebruiker van de applicatie (huurder) en de oprichters van het vastgoedplatform (verhuurder). De admins kunnen extra functionaliteiten uitvoeren op de website zoals het toevoegen, updaten en deleten van huizen. Het inloggen gebeurd met Google Authenticatie.  

Microservices:
-   Woning beheer
-   Verhuur beheer
-   Onderhoud beheer
-   Betaling beheer
-   Contract beheer

## Componenten
![](https://github.com/project-house-renting/RentingHouse/blob/main/images/schema.png)

### Home Service

### Tenant Service

### Payment Service

### Maintenance Service

### Contract Service
Deze service zorgt voor de verbinding tussen de huizen en de huurders. Aangezien een huis meerdere huurders kan hebben, en een huurder meerdere huizen kan huren, hebben we deze service toegevoegd. Dit maakt het mogelijk om een geschiedenis bij te houden.  


### API gateway
De gateway met Google OAuth2 is een centrale poort die al het verkeer naar de achterliggende microservices beheert.

### Web application (extra)


## Testing (Postman)

### Alle beschikbare huizen (niet secured)
![](https://github.com/project-house-renting/RentingHouse/blob/main/images/available.png)

### Alle huizen (secured)
![](https://github.com/project-house-renting/RentingHouse/blob/main/images/all.png)

### Details van een huis (secured)
![](https://github.com/project-house-renting/RentingHouse/blob/main/images/details.png)

### Huidig contract van een huis (secured)
![](https://github.com/project-house-renting/RentingHouse/blob/main/images/contract_current.png)

### Alle contracten van een huis (secured)
![](https://github.com/project-house-renting/RentingHouse/blob/main/images/contract_all.png)

### Huis toevoegen
![](https://github.com/project-house-renting/RentingHouse/blob/main/images/addhome.png)

### Huis updaten
![](https://github.com/project-house-renting/RentingHouse/blob/main/images/updatehome.png)

### Huis verwijderen
![](https://github.com/project-house-renting/RentingHouse/blob/main/images/deletehome.png)

