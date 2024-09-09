# App-Dokumentation für MossTodo

## Architektur:
In der App wurde versucht, das **Model-View-ViewModel (MVVM)**-Architekturmodell zu verwenden, wobei die Implementierung nicht vollständig erfolgreich war. Die Kommunikation zwischen der App und dem Backend wird hauptsächlich durch den **ApiClient** verwaltet. Die App verwendet Model-, ViewModel- und Activity-Klassen, um die Benutzeroberfläche und die Logik zu trennen.

## Funktionen der App:
1. **Login und Registrierung**:
   - Benutzer können sich mit ihrer E-Mail und ihrem Passwort anmelden oder registrieren.
   
2. **Task-Verwaltung**:
   - Benutzer können Aufgaben erstellen, bearbeiten, löschen und anzeigen.
   - Deadlines für Aufgaben werden unterstützt.
   - Die Implementierung von Erinnerungen wurde versucht, war jedoch nicht erfolgreich.

## Bekannte Probleme:
- **Datenbankzugriff**: Aufgrund eines Fehlers in der Umsetzung greifen derzeit alle Benutzer auf dieselben Datenbankeinträge zu und teilen sich die Aufgaben.
- **Edit Task**: Beim Bearbeiten von Aufgaben gab es Probleme, die vorübergehend mit einem **EditText** gelöst wurden. Diese Lösung funktioniert, ist jedoch nicht benutzerfreundlich.

## Technische Anforderungen:
- **JDK**: 17
- **Gradle**: 8.7
- **Kotlin**: 1.9.22
- **Min SDK**: 28
- **Max SDK**: 34
- **Backend**: C# REST API gehostet auf Azure

## Backend:
Das Backend der App ist eine **C# REST API**, die auf Microsoft Azure gehostet wird. Es bietet die Schnittstellen für die Aufgaben- und Benutzerverwaltung. Für weitere Details zur API siehe [Swagger-UI](https://mossrestapi-esdkf7hpc3fmadf4.germanywestcentral-01.azurewebsites.net//swagger/index.html).

