# Käyttöohje

## Käynnistäminen 

Ohjelma käynnistetään komennolla 

```
java -jar Kauppasovellus-otm.jar

```

## Konfigurointi

Sovelluksen käyttäjä voi halutessaan konfiguroida sovellusta käyttämään eri tietokantaa, mutta oletuksena on että käyttäjä käyttäisi vain sovelluksen mukana tulevaa tietokantaa. Tällöin käyttäjän tulee itse luoda oma tietokantansa ja siihen kuluvat taulut. Taulujen luomisessa käytetyt sqlite-komennot löytyvät arkkitehtuurikuvauksesta. 

Konfiguroinnissa on myös mahdollista vaihtaa Top kayttajien tietojen tarkastelun aikaskaalaa. Tämä onnistuu vaihtamalla konfiguraatiotiodoston "time" kohtaa vastaamaan toivottua sekunttimäärää. Oletuksena määrä on 30 minuuttia eli 3600 sekuntia. 

Kaikki konfigurointi tapahtuu käyttämällä tiedostoa _config.properties_ Tiedoston muoto on seuraava:

```
database=jdbc:sqlite:db/kauppasovellus.db
time=3600

```

## Aloitusnäkymä

Ohjelma käynnistyy aloitusnäkymään, josta voi valita haluamansa näkymän.

## Käyttäjien lisääminen

Valitsemalla "Add users!" pääsee käyttäjien lisäämiseen tarkoitettuun näkymään ja lisäämään uusia käyttäjiä. Käyttäjän tulee syötää tietoja kenttiin ja painaa lopuksi käyttäjän lisäävää nappia. Sovellus näyttää viestin, jos lisäys on onnistunut. 

## Tavaroiden lisääminen

Valitsemalla "Add products!" pääsee tavaroiden lisäämiseen tarkoitettuun näkymään ja lisäämään uusia tavaroita. Käyttäjän tulee syötää tietoja kenttiin ja painaa lopuksi tavaran lisäävää nappia. Sovellus näyttää viestin, jos lisäys on onnistunut. 

## Ostosten tekeminen

Valitsemalla "Make a purchase!" pääsee ostosten tekemiseen tarkoitettuun näkymään ja tekemään käyttäjäkohtaisia ostoksia. Valitsemalla valikosta oikean käyttäjän ja halutun tuotteen sekä painamalla lopuksi "Purchase" nappia ohjelma rekisteröi ostoksen. 

## Top käyttäjät

Ostostilastonäkymästä voi halutessaan tarkastella viimeisimpiä tehtyjä ostoksia. Sovellus päivittää näkymän automaattisesti siten, että näkyvillä olevassa listassa ensimmäisenä on käyttäjä, jolla ostoksia on eniten.

## Käyttäjäkohtaiset tiedot

Käyttäjäkohtaisia tietoja voi tarkastella menemällä ensin käyttäjien lissämisnäkymään ja valitsemalla dropdown-menusta tietyn käyttäjän sekä painamalla tämän jälkeen "Show user information" -nappia. Uudessa näkymässä on mahdollista päivittää käyttäjän kaikkia tietoja, onnistuneesta päivityksestä sovellus lähettää viestin. Samalla sovellus näyttää käyttäjän koko ostoshistorian. 

## Tilastonäkymä

Painamalla "View Statistics" -nappia pääsee katsomaan tilastoja sovelluksen toiminnasta. 

