# Aplikacja do organizacji czasu

## Projekt semestralny z przedmiotu Programowanie Obiektowe

### Informatyka, 3 semestr, Rzeszów 2024
<img src="https://github.com/JustynaToczek/Aplikacja-do-organizacji-czasu/assets/113525212/98861275-57df-4b53-a553-a0576f68ad8e" width="700">

### Spis treści
1.	Opis założeń projektu</br>
1.1.	Wstęp</br>
1.2.	Cele i założenia projektu
2.	Specyfikacja wymagań</br>
2.1.	Wymagania funkcjonalne</br>
2.2.	Wymagania niefunkcjonalne
3.	Opis struktury projektu
4.	Harmonogram realizacji projektu
5.	Prezentacja warstwy użytkowej projektu
6.	Podsumowanie
7.	Literatura



### 1.	Opis założeń projektu</br>
#### 1.1.	Wstęp</br>
Każdy człowiek, na pewnym etapie swojego życia spotyka się z wieloma obowiązkami. Poza obowiązkami, każdy ma swoje prywatne życia, hobby, cele i marzenia do których dąży krok po kroku. Natłok wszystkich aktywności, obowiązków, prac, czy planów do zrealizowania potrafi przytłaczać. Brak organizacji swoich planów przyczynia się do ciągłego pośpiechu, braku czasu na odpoczynek, a nawet wypalenia zawodowego. Z tego powodu organizacja i planowanie jest kluczowym elementem produktywnego i zbilansowanego dnia. Dzięki planowaniu możliwe jest znalezienie czasu na obowiązki oraz odpoczynek, o którym tak często się zapomina, lub właśnie nie znajduje się na niego czasu. Dzięki planowaniu znajdowanie czasu na odpoczynek i hobby jest dużo łatwiejsze. Ponadto planowanie swojego dnia i konsekwentne odnotowywanie zrealizowanych prac może przyczynić się do samozadowolenia z siebie, co może motywować do dalszej realizacji celów. Z taką właśnie myślą została stworzona aplikacja do organizacji czasu.</br>
#### 1.2. Cele i założenia projektu</br>
Celem projektu jest stworzenie programu pomocnego w organizacji codziennych zdań.
Rozwiązywanym problemem jest trudność w organizowaniu swoich obowiązków oraz trudność  znajdowania czasu na odpoczynek. Podstawowym źródłem problemu jest natłok codziennych obowiązków. Problem dotyka większości osób, co czyni go ważnym zagadnieniem. Niezbędnym krokiem do rozwiązania tego problemu jest jego uświadomienie i zrozumienie. Nie każdy jest świadomy, że tak prosta rzecz, jak dobra organizacja czasu może przynieść mnóstwo korzyści i rozwiązać niejeden problem związany z brakiem czasu. Jednym z rozwiązań problemu jest dostarczenie udogodnienia w postaci aplikacji, która będzie mogła wizualizować ilość obowiązków i organizować wprowadzane zadania. Założenia działania aplikacji zamieszczono poniżej.
Funkcje aplikacji są dostępne dla każdego zarejestrowanego użytkownika. Po zalogowaniu, użytkownik ma możliwość wyboru przejścia do kalendarza lub do notatek. Wybierając pierwszą opcję, ukazuje się okno kalendarza, w którym możliwy jest wybór dowolnej daty. Po wybraniu daty, ukazuje się okno,  którym użytkownik może dodawać swoje cele do zrealizowania na dany dzień. Możliwe jest także zarządzanie tymi celami poprzez przesuwanie suwaka przyjmującego wartości od 0% do 100%, które reprezentują stopień realizacji danego zadania. Możliwe jest również zaznaczenie pola wyboru przy danym zadaniu, co jest równoznaczne z wartością 100% na suwaku, czyli w pełni zrealizowaniu wybranego zadania. Wprowadzane plany można także usuwać. Takie funkcje programu są dostępne dla każdej wybranej daty. Użytkownik ma również możliwość do przejścia do okna notatek, w którym możliwe jest wprowadzanie i modyfikowanie swoich notatek.

### 2.	Specyfikacja wymagań </br>
#### 2.1.	Wymagania funkcjonalne
•	Korzystanie z aplikacji jest możliwe dla każdego wcześniej zarejestrowanego i zalogowanego użytkownika.</br>
•	Przy pomocy wbudowanego kalendarza możliwe jest wprowadzanie planów na każdy dzień.</br>
•	Użytkownik może zarządzać planami, wprowadzając stopień ich realizacji.</br>
•	Użytkownik może usuwać wcześniej wprowadzone przez siebie plany.</br>
•	Możliwe jest korzystanie z notatnika, do którego można wprowadzać dane, modyfikować je, lub usuwać.</br>
#### 2.2.	Wymagania niefunkcjonalne</br>
•	Program korzysta z połączenia z bazą danych, co umożliwia zapis, usuwanie i modyfikację danych. Stan danych będzie zapisany w sposób, jaki użytkownik o tym zdecyduje, bez utraty danych po wylogowaniu. </br>
•	Używanie programu wymaga zainstalowanego XAMPP z serwerem Apache oraz bazą danych MySQL dostępną przez phpMyAdmin na localhost.</br>
•	Przed uruchomieniem programu wymagane jest, aby mieć włączone moduły Apache oraz MySQL w XAMPP. Należy przejść na stronę internetową http://localhost/phpmyadmin/ i stworzyć nową bazę danych o nazwie „TimeApplication”. Należy także wykonać zapytania SQL do ww. bazy danych o treści: </br>
CREATE TABLE IF NOT EXISTS users (id INT(10) NOT NULL PRIMARY KEY AUTO_INCREMENT, username VARCHAR(200) NOT NULL, email VARCHAR(200) NOT NULL UNIQUE, password VARCHAR(200) NOT NULL);</br>
CREATE TABLE IF NOT EXISTS date (id_date INT(10) NOT NULL PRIMARY KEY AUTO_INCREMENT, user_id INT, FOREIGN KEY (user_id) REFERENCES users(id), date VARCHAR(200) NOT NULL);</br>
CREATE TABLE IF NOT EXISTS progress (id_date INT, FOREIGN KEY (id_date) REFERENCES date(id_date), to_do VARCHAR(200) NOT NULL, percentage INT(10), ischeckboxselected BOOLEAN);</br>
CREATE TABLE IF NOT EXISTS notes (user_id INT, FOREIGN KEY (user_id) REFERENCES users(id), notes VARCHAR(255));</br>
•	Aplikacja tworzona jest w języku Java z użyciem biblioteki Swing.</br>
•	Program korzysta także z zewnętrznej biblioteki JCalendar, dostarczającej gotowe komponenty do wybierania daty i obsługi kalendarza. Biblioteka została pobrana ze strony internetowej: https://toedter.com/jcalendar/. </br>

### 3.	Opis struktury projektu </br>
•	Środowisko programistyczne Javy: java version "17.0.6"</br>
•	Środowisko programistyczne: IntelliJ IDEA 2023.2.1 (Ultimate Edition)</br>
•	Urządzenie z system operacyjnym Windows 11</br>
•	Aplikacja była projektowana na urządzeniu HP laptop 15-dw1xxx</br>
![image](https://github.com/JustynaToczek/Aplikacja-do-organizacji-czasu/assets/113525212/d970e12e-c260-4aaa-ad58-6ef8503115c7)
Rysunek 1. Diagram klas aplikacji


Baza danych składa się z czterech tabel. W tabeli users przechowywane są dane użytkowników (username, email oraz hasło). Tabela posiada klucz główny id, który identyfikuje każdego zarejestrowanego użytkownika. Tabela date przechowuje informacje o konkretnych datach wybranych przez danego użytkownika. Id_date jest kluczem głównym tej tabeli i przechowuje informacje o tym, który użytkownik wybrał którą datę. User_id jest w tej tabeli kluczem obcym, który odwołuje się do klucza głównego id w tabeli users. Tabela posiada także kolumnę date. Z kolei tabela progress posiada klucz obcy id_date, który odwołuje się do klucza głównego id_date w tabeli date. Tabela progress posiada także kolmnny: to_do, percentage, ischeckboxselected. Tabela notes przechowuje klucz obcy user_id odwołujący się do klucza głównego id w tabeli users. Posiada także kolumnę notes. Opisywane tabele i relacje między nimi zostały zwizualizowane na schemacie ERD na rysunku 2.
<img src="https://github.com/JustynaToczek/Aplikacja-do-organizacji-czasu/assets/113525212/7efa39b9-1d98-40a9-9234-63a636571653" width="700"> </br>
Rysunek 2.Diagram ERD bazy danych

### 4.	Harmonogram realizacji projektu</br>
Projekt zrealizowano w ciągu 11 dni. Kroki realizacji projektu to kolejno: analiza wymagań, zaprojektowanie interfejsu użytkownika, stworzenie bazy danych i logiki relacji, połączenie bazy danych z programem, stworzenie logiki działania programu, testowanie aplikacji, stworzenie dokumentacji. Stworzono diagram Gantta przestawiający harmonogram realizacji projektu, przedstawiony na rysunku 3. </br>
<img src="https://github.com/JustynaToczek/Aplikacja-do-organizacji-czasu/assets/113525212/fa605cb0-9b65-4084-a141-6f4391ae59ff" width="400"> </br>
Rysunek 3. Diagram Gantta

### 5.	Prezentacja warstwy użytkowej projektu
Na rysunku 5 przedstawiono okno logowania do aplikacji, które ukazuje się po uruchomieniu programu. </br>
<img src="https://github.com/JustynaToczek/Aplikacja-do-organizacji-czasu/assets/113525212/6461bc45-ae3c-4f68-8ea8-3200b09bbed6" width = "400"> </br>
Rysunek 5. Okno logowania aplikacji

Dla użytkowników pierwszy raz korzystających z aplikacji, stworzono okno rejestracji (rysunek 6), do którego można przejść za za pomocą przycisku „Go to register”. </br>
<img src="https://github.com/JustynaToczek/Aplikacja-do-organizacji-czasu/assets/113525212/3398e560-0598-431b-8251-393e3ace9b5e" width="400"> </br>
Rysunek 6. Okno rejestracji </br>

Po udanej rejestracji i/lub logowaniu, ukazuje się panel wyboru (rysunek 7). Zalogowany użytkownik może wybrać, czy chce przejść do notatek, czy do kalendarza. </br>
<img src="https://github.com/JustynaToczek/Aplikacja-do-organizacji-czasu/assets/113525212/850355a7-afc7-47fc-9a64-46deff1fbc11" width="400"> </br>
Rysunek 7. Panel wyboru

Wybierając opcję przejścia do notatek, następuje wyświetlenie okienka, w którym użytkownik może dodawać, modyfikować i usuwać swoje notatki (rysunek 8). </br>
<img src="https://github.com/JustynaToczek/Aplikacja-do-organizacji-czasu/assets/113525212/8dc39317-dd95-498c-b53e-79d385a9b6b8" width="400"> </br>
Rysunek 8. Okno notatek

Wybierając opcję przejścia do kalendarza, wyświetla się kalendarz użytkownika, przedstawiony na rysunku 9. W tym oknie użytkownik może wybrać datę, do której chciałby przypisać swoje plany, zmodyfikować je, lub sprawdzić te wcześniej wprowadzone. </br>
<img src="https://github.com/JustynaToczek/Aplikacja-do-organizacji-czasu/assets/113525212/52648a65-705e-4bce-b126-cacf397f490d" width="300"> </br>
Rysunek 9. Okno kalendarza

Po kliknięciu w przycisk „OK” i wybraniu daty, która interesuje użytkownika, program przełącza się do okna planowania, przedstawionego na rysunku 10. W tym oknie możliwe jest wprowadzanie swoich planów na wcześniej wybrany dzień, poprzez pojedyncze wprowadzenie ich w pole tekstowe i kliknięcie w przycisk „Enter to list”. Wówczas wprowadzone plany zapisywane są na liście. Istnieje także możliwość ich usuwania za pomocą przycisku „Remove from list”. </br>
<img src="https://github.com/JustynaToczek/Aplikacja-do-organizacji-czasu/assets/113525212/33c4fb57-2194-4959-9fba-2376a6d8248d" width="400"> </br>
Rysunek 10. Okno planowania

Na rysunku 11 przedstawione są udogodnienia okna planowania. Po wprowadzeniu planowanych wydarzeń oraz po kliknięciu na wybrany z element z listy, w dolnej części okienka pokazuje się panel, w którym możliwe jest wprowadzenie progresu realizacji danego celu. Stopień realizacji danego zadania jest reprezentowany poprzez wartości 0% - 100% występujące na suwaku. W celu zwizualizowania zakończonego zadania, oprócz przesunięcia suwaka na wartość 100%, można także zaznaczyć pole wyboru znajdujące się w lewym dolnym rogu okna, które sprawi, że suwak będzie wskazywał wartość 100%. W celu uniknięcia utraty danych, każdy progres realizacji danego zadania należy zapisać za pomocą przycisku „Save progress”. </br>
<img src="https://github.com/JustynaToczek/Aplikacja-do-organizacji-czasu/assets/113525212/05a06888-a679-4df0-96ce-09102338fd30" width="400"> </br>
Rysunek 11. Udogodnienia okna planowania

### 6.	Podsumowanie
Realizacja projektu przebiegła pomyślnie. Stworzony program działa zgodnie z założeniami. Aplikację można rozbudować o dodatkowe funkcje, takie jak np.: </br>
•	W oknie notatnika można by było dodać zakładki notatek, w ten sposób, żeby istniała możliwość przechowywania wielu notatek na różne tematy. </br> 
•	W oknie wyboru można by było dodać opcję przejścia do okna „your goals”, które zawierałoby listę ogólnych celów, nie tylko na wybrane dni. Realizacja tych celów mogłaby być także przedstawiana za pomocą suwaka przyjmującego wartości od 0% do 100%. </br>

### 7.	Literatura
1.	https://stackoverflow.com/questions/15269507/how-to-validate-a-jtextfield-of-email-id-with-a-regex-in-swing-code (data dostępu: 18.01.2024)
2.	https://toedter.com/jcalendar/ (data dostępu: 20.01.2024)
3.	https://bulldogjob.pl/readme/adding-an-external-library-into-a-project-why-use-a-build-tool-like-maven (data dostępu 20.01.2024)

