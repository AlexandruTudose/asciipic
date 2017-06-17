Titlu: AsciiPic
==============
[![Requirements Status](https://requires.io/github/micumatei/ascipic/requirements.svg?branch=master)](https://requires.io/github/micumatei/ascipic/requirements/?branch=master)

### Descriere:


Inspirat din CoLiW. 
In mod normal, accesul la Web se realizeaza pe baza unui browser. 
Se doreste experimentarea unei interactiuni Web in linia de comandă, punandu-se la dispozitie o platforma 
extensibila capabila sa ofere suport pentru cautarea, manipularea, agregarea si exportarea imaginilor 
impreuna cu metadata ascociata acestora utilizand difierite API-uri publice ca Flickr, Instagram, etc.

Aplicatia va expune propriul API si va pune la dispozitie doua modalitati de interactiune cu acesta: 
  - una prin intermediul unei interfete web ce emuleaza o linie de comanda
  - iar a doua folosind un client, direct dintr-un terminal.

Printre functionalitati aplicatia permite:
  - cautarea si filtrarea imaginilor dupa tag-uri, data postarii, dimensiune etc.
  - vizualizarea imaginilor in format ascii(atat la linia de comanda cat si in interfata web);
  - exportarea url-urilor impreuna cu metadata asociata acestora in diverse formate(JSON, XML, etc.);
  - transformarea imaginilor astfel gasite(resize, aplicarea diverselor filtre);

Datorita faptului ca anumite request-uri pot fi costisitoare oferim posibilitatea postarii asincrone de job-uri 
care for fi executate de o arhitectura distribuita de work-eri.
Astfel putem interoga statusul job-ului sau la finalul lui putem opta pentru primirea unui e-mail cu informatiile cerute.


Am estimat proiectul ca fiind unul de tip S care ar avea nevoie de 4 persoane pentru o implementare optima in timpul acordat.

---

Website: https://asciipic.xyz/


Detalii de implementare
-----------
Aplicatia este scrisa in java si are la baza o arhitectura pe microservicii. Este impartia in 6 module principale:
 - Core - care actioneaza ca un gateway pentru toate celelalte si care comunica direct cu front-end-ul. In interiorul sau sunt preluate si parsate toate comenzile din terminal. Apoi sunt facute apelurile necesare pentru a obtine rezultate, care ulterior sunt afisate in terminal.
 - Login - avand o legatura directa doar cu crawl, modulul login este responsabil pentru inregistrarea, autentificarea, cofirmarea sesiunii si dezautentificarea utilizatorilor. In implementareaa sa am utilizat un token care este confirmat si reinoit la fiecare comanda scrisa in terminal. Astfel daca token-ul nu poate fi confirmat comenzile introduse nu sunt parsate. Exceptie fac comenzile "login" si "register".
 - Search - ofera functionalitatea principala a aplicatiei. Permite cautarea imaginilor dupa diverese criterii si totodata este responsabil de introducerea de noi imagini in baza de date. Aceasta operatie se face pe baza unui rest api ce accepta json-uri ce contin imagini si metadate asociate acestora. Deasemena ofera un api pentru conversia imaginilor in format ascii.
 - Crawl - permite aducerea de noi imagini in aplicatie. Interactioneaza cu api-ul de la flickr pentru a face posibila popularea bazei locale de date. Acesta are o arhitectura de tipul producer-consumer: sunt postate joburi de crawl, care apoi sunt preluate de workeri si rezolvate.
 - Filter - permite aplicarea de filtre peste imagini existente. Primeste si returneaza o imagine impreuna cu metadatele ei.
 - Journalize - permite jurnalizarea operatiilor ce au loc in aplicatie si generarea diverselor statistici pe baza acestora.
 
 In plus exista si modulul LocalTerminal care pune la dispozitie o alta metoda de interactiune cu aplicatia.


