# Bysykkel Oversikt


###Intro
------------------------------------
Denne appen henter data fra to endepunkter hos Oslo Bysykkel API. Disse blir aggregert og viser så brukeren en enkel oversikt over stasjoner, antall sykler og antall ledige låser på denne stasjonen. 
Hvert tiende sekund henter den ny data og overskriver dersom det har kommet endringer.

###Tekk
Skrevet i Kotlin til Android Native

Henter data med retrofit2

MVVM Arkitektur (Model — View — ViewModel) for å hente data og oppdatere UI når all dataen er hentet ut.

Jetpack Compose for å bygge UI komponenter med kode heller enn i gammel XML slik det var gjort i de første committene i prosjektet.


### Kjøre prosjektet
Åpne prosjektet i Android Studio 
Sync med gradle for å hente avhengigheter
Bygg prosjektet
Kjør på emulator
evt
Bygg APK fil. Overfør til mobil og enjoy