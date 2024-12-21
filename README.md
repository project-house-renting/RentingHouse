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
Beheert de huizen.

De woningen worden bijgehouden met volgende info:
- adres
- type woning
- beschrijving van de woning
- de huur prijs
- bouw jaar
- indient er onderhoudsverzoeken zijn worden die via een id hier ook bijgehouden


### Tenant Service
Beheert de huurders.

De huurders worden bijgehouden met volgende info:
- naam
- email
- een beschrijving over de persoon
- geslacht

### Payment Service
Beheert de maandelijkse betalingen van de huurders.

De maandelijkse betalingen worden bijgehouden met volgende info:
- bedrag
- methode
- transactie id
- datum
- de persoon
- het gehuurde huis

### Maintenance Service
Beheert alle onderhoudsverzoeken. Als er problemen zijn in een woning kan de huurder een onderhoudsverzoek indienen.

De onderhoudsverzoeken worden bijgehouden met volgende info:
- beschrijving van wat er stuk is
- datum
- urgency
- woning

### Contract Service
Deze service zorgt voor de verbinding tussen de huizen en de huurders. Aangezien een huis meerdere huurders kan hebben, en een huurder meerdere huizen kan huren, hebben we deze service toegevoegd. Dit maakt het mogelijk om een geschiedenis bij te houden.

Per contract houden we dan volgende info bij:
- woning
- huurder
- start datum
- eind datum  

### API gateway
De gateway met Google OAuth2 is een centrale poort die al het verkeer naar de achterliggende microservices beheert. Om de applicatie logischer te maken is hier een whitelist op geïmplementeerd. Zonder de whitelist zou iedereen dat zich met google aanmeld alle admin functies kunnen uitvoeren wat de bedoeling is.

### Web application (extra)
Voor de frontend hebben we het simpel gehouden met een SpringBoot web application met Thymeleaf. Wanneer de beheerders inloggen met google, kunnen ze alle admin functies uitvoeren. De gewone gebruiker kan alle huizen bekijken die te huur staan.

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
