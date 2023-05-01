IJA 22/23L
Project Pacman

Členovia tímu:
    Matej Horňanský
    Dávid Kán

Popis:
    Aplikácia umožní hrať hru typu Pac-Man, užívateľ ovláda panáčika pomocou WSAD alebo hore, dole, vlavo, vpravo šípkami. Panáčik sa pohybuje po bludisku z východzieho postavenia do cieľového.
    V bludisku sa samostatne pohybujú duchovia. Ak sa panáčik stretne s duchom, hra (level) končí neúspechom.
    Hra sa spusta medzerníkom a dá sa medzerníkom aj pozastaviť.

    Mapa bludiska sa načíta pri spustení aplikácie zo súboru (možno vybrať z viacerých uložených
    máp).

    Priebeh hry je možné logovať (ukladať) do súboru a následne prehrať.

Preklad a spustenie:
    Na preklad a spustenie sa používa Maven.

    Preklad:
        mvn install

    Spustenie:
        java -cp .\target\demo-1.0-SNAPSHOT.jar com.pacman.App

    Generovanie dokumentacie:
        mvn site
    Vygenerovaná dokumentácia sa nachádza v:
        target/site/apidocs/index.html
