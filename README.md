# <h1>Kauppasovellus<h1>

Sovelluksen tarkoituksena on toimia kirjanpitovälineenä kaupalle, jolla varastossa tavaroita ja asiakkaita. Sovelluksen avulla käyttäjä saa tietoa mm. asiakkaiden saldoista ja varastossa olevista tavaroista. 

<h2>Dokumentaatio<h2>

[Vaatimusmäärittely](https://github.com/Kallmark/otm-harjoitustyo/blob/master/dokumentointi/vaatimusmaarittely.md)

[Työaikakirjanpito](https://github.com/Kallmark/otm-harjoitustyo/blob/master/dokumentointi/tyoaikakirjanpito.md)

[Arkkitehtuuri](https://github.com/Kallmark/otm-harjoitustyo/blob/master/dokumentointi/arkkitehtuuri.md)

[Käyttöohje](https://github.com/Kallmark/otm-harjoitustyo/blob/master/dokumentointi/kaytto-ohje.md)

[Testausdokumentti](https://github.com/Kallmark/otm-harjoitustyo/blob/master/dokumentointi/testaus.md)

<h2>Releaset<h2>

[Loppupalautus](https://github.com/Kallmark/otm-harjoitustyo/releases/tag/Loppupalautus)

<h2>Komentorivitoiminnot<h2>

### Testaus

Testit suoritetaan komennolla

```
mvn test
```

Testikattavuusraportti luodaan komennolla

```
mvn jacoco:report
```

Kattavuusraporttia voi tarkastella avaamalla selaimella tiedosto otm-harjoitustyo/Kauppasovellus-otm/target/site/jacoco/index.html

### Suoritettavan jarin generointi

Komento

```
mvn package
```

generoi hakemistoon _target_ suoritettavan jar-tiedoston Kauppasovellus-otm-1.0-SNAPSHOT.jar


### Checkstyle

Tiedostoon [checkstyle.xml](https://github.com/Kallmark/otm-harjoitustyo/blob/master/Kauppasovellus-otm/checkstyle.xml) määrittelemät tarkistukset suoritetaan komennolla

```
 mvn jxr:jxr checkstyle:checkstyle
```

Mahdolliset virheilmoitukset selviävät avaamalla selaimella tiedosto otm-harjoitustyo/Kauppasovellus-otm/target/site/checkstyle.html
