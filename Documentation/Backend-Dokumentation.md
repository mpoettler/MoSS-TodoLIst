# Backend-Dokumentation für ToDoListApp

## Übersicht:
Das Backend der ToDoListApp wurde in C# entwickelt und ist auf Microsoft Azure gehostet. Es handelt sich um eine REST API, die eine mySQL-Datenbank verwendet, um Aufgaben und Benutzerdaten zu speichern und zu verwalten. Die API ist über zwei Controller verfügbar: **TasksController** und **UsersController**. Mithilfe der Swagger-Benutzeroberfläche können die API-Methoden visualisiert und getestet werden.

## Technologie-Stack:
- **Programmiersprache**: C#
- **Framework**: ASP.NET Core
- **Datenbank**: mySQL Flexible Server
- **Hosting**: Microsoft Azure (vorübergehend)
- **Swagger**: [Swagger-UI](https://mossrestapi-esdkf7hpc3fmadf4.germanywestcentral-01.azurewebsites.net//swagger/index.html)

## API-Endpunkte:

### TasksController (Verwaltung der Aufgaben)
1. **GET api/Tasks**  
   Ruft alle Aufgaben aus der Datenbank ab.

2. **GET api/Tasks/{id}**  
   Ruft eine bestimmte Aufgabe anhand der Aufgaben-ID ab.

3. **POST api/Tasks**  
   Erstellt eine neue Aufgabe in der Datenbank.

4. **PUT api/Tasks/{id}**  
   Aktualisiert eine bestehende Aufgabe mit der angegebenen ID.

5. **DELETE api/Tasks/{id}**  
   Löscht eine Aufgabe anhand der ID.

Die API verwendet das Entity Framework Core, um die Datenbankoperationen auszuführen. Es werden Logging-Mechanismen eingebaut, um die empfangenen JSON-Daten zu protokollieren.

### UsersController (Verwaltung der Benutzer)
1. **POST api/Users/login**  
   Führt die Authentifizierung durch, indem die E-Mail und das Passwort überprüft werden.

2. **GET api/Users**  
   Ruft alle Benutzer aus der Datenbank ab.

3. **GET api/Users/{id}**  
   Ruft einen bestimmten Benutzer anhand der Benutzer-ID ab.

4. **POST api/Users**  
   Erstellt einen neuen Benutzer.

5. **PUT api/Users/{id}**  
   Aktualisiert einen bestehenden Benutzer.

6. **DELETE api/Users/{id}**  
   Löscht einen Benutzer anhand der ID.

Die Passwörter werden aktuell im Klartext gespeichert, aber in einer realen Anwendung sollten sie gehasht und gesichert werden.


