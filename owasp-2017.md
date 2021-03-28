# Vulnerability Analysis
## A1:2017 Injection
### Description
Injection betekent het invoegen van scripts (meestal SQL) in data die naar de server worden gestuurd.

### Risk
Binnen dit project is het risico klein, omdat de data van de applicatie niet veel waard is.

Als er authenticatie aan de applicatie wordt toegevoegd, komt er een nieuw risico bij van het lekken van wachtwoorden/gebruikersnamen. Goed gemaakte authentication maakt gebruik van password hashing, dus dit zal geen groot risico geven.
### Counter-measures
De applicatie ontvangt alleen integers en strings, waarbij de strings altijd gechecked worden tegen de lijst met bekende woorden. Pas als het woord in de woordenlijst staat kan hij worden opgeslagen.

De conversie naar integers of strings wordt door Spring Web gedaan, waarvan ik aanneem dat het dat veilig doet.
 
## A8:2017 Insecure Deserialization
### Description
Deserialization is het omzetten van ruwe data naar objects van bijvoorbeeld java.  
Insecure deserialization betekent het niet-checken van objects voordat ze ge-deserialized worden.

### Risk
Remote code execution is het belangrijkste risico. Dit is voor deze applicatie een groot risico, omdat er op de deployment server verschillende andere applicaties draaien.

### Counter-measures
Om de impact van remote-code execution te verlagen draait de applicatie als een aparte user met geen permissions.  
Om het volledige risico te limiteren wordt niet gebruik gemaakt van deserialization.

## A9:2017 Using Components with Known Vulnerabilities
### Description
Programma's die libraries/dependencies gebruiken met beveiligingsproblemen kunnen deze problemen overnemen.

### Risk
Hoog risicio. de impact hiervan kan heel hoog zijn, met remote code execution.

### Counter-measures
gebruik van dependabot, voor waarschuwingen over beveiligingsproblemen/out-of-date dependencies.  
Java, tomcat en alle andere programma's op de server worden dagelijks automatisch geupdated.
