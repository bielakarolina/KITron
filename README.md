# KITron
KiTron to gra sieciowa wzorowana na grze Tron z przewidzianą rozbudową funkcjonalności o bonusy.

Warstwa sieciowa oparta jest na protokole UDP oraz TCP. 
Klient nasłuchuje na gnieździe UDP wiadomości dotyczących punktów skrętów, koloru linii graczy oraz bonusów. 
Następnie na podstawie otrzymanych danych rysuje z pomocą biblioteki Canvas trasy graczy.
Za pomocą połączenia TCP wysyłane są informacje o końcu rozgrywki (w wyniku zderzenia).
Serwer odpowiada za całą logikę programu. 


## Wskazówki do uruchomienia: 


#### 1. Pobierz jar klienta i serwera:
https://github.com/bielakarolina/KITron/tree/Goodversion/Jar

#### 2. Odpal serwer klikając dwukrotnie na pobrany jar.Nic się nie pojawi, ale serwer będzie działał w tle.

#### 3. Odpal klienta. Naciśnij "Play". Wprowadź adres IP hosta, na którym został odpalony serwer. Jeśli został odpalony na tym samym hoście wpisz "localhost". Dodatkowo wprowadź swoje imię/przezwisko. Naciśnj submit.

#### 4. W kolejnym oknie pod "Choose room" znajduje się symbol "+". Naciśj, w celu dodania pokoju bądź, jeśli już jakiś pokój został utworzony, wybierz go z listy poniżej w celu dołączenia do gry.

#### 5. Odpal drugiego klienta (bądź też dowolną ich liczbę) i powtórz kroki 3 i 4.

#### 6. Ciesz się grą!
