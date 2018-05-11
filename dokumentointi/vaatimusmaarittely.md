# Vaatimusmäärittely

## Sovelluksen tarkoitus

Sovellukselle on kaksi tarkoitusta. Ensinnäkin sen tarkoitus on pitää kirjaa varastosta ja siinä olevista tuotteista. Toisekseen sovelluksen on tarkoitus pitää kirjaa tuotteita ostavista asiakkaista ja heidän saldosta sekä ostoshistoriastaan. Tähän käytetään sovelluksen yhteydessä käytettävää tietokantaa. Sovellus tarjoaa myös tilastotietoa asiakkaiden tekemistä ostoksista ja asiakkaiden saldosta. 

## Käyttäjät

Sovelluksella on toistaiseksi vain yksi käyttäjärooli. Erillistä admin-roolia ei tarvita, sillä ohjelma on ainoastaan em. varaston haltijan käytössä. Tulevaisuuden jatkokehityksessä sovellusta voidaan laajentaa siten, että asiakkaat voisivat käyttää sovellusta kirjautumalla sisään omilla tunnuksillaan. 

## Käyttöliittymäluonnos

<img src="https://raw.githubusercontent.com/Kallmark/otm-harjoitustyo/master/misc/kuvat/havainnekuva2.jpg" width="750">

Sovelluksessa on yhteensä seitsemän näkymää, joiden välillä sovellusta käytetään: 

-Aloitusnäkymä (Start): Aloitusnäkymässä käyttäjä valitsee, mitä haluaa tehdä. Aloitusnäkymästä voidaan siirtyä käyttäjän lisäämiseen, tuotteen lisäämiseen, ostoksen tekemiseen, aikanäkymään tai tilastonäkymään. 
-Käyttäjän lisäysnäkymä (Add user): Kirjanpidosta vastaava henkilö voi lisätä uusen käyttäjän antamalla sovellukselle käyttäjän nimen ja saldon. Saldo voi olla miinusmerkkinen, sillä asiakkaat voivat olla velkaa kaupan pitäjälle. 
-Tuotteen lisäysnäkymä (Add product): Sovellukseen voidaan lisätä uusia tuotteita antamalla sovellukselle tuotteen nimi, hinta, määrä ja tuotteen kuvaus (info).
-Ostosnäkymä (Make a purchase): Ostosnäkymässä voidaan valita kahdesta valmiista listasta asiakas ja tuote ja siten tehdä ostos. Sovellus päivittä tämän jälkeen asiakkaan saldon vähentämällä sitä tuotteen hinnan verran. Lisäksi sovellus vähentää tuotteen määrää varastossa. Sovellus tallentaa jokaisesta ostoksesta aikaleiman tietokantaan. 
-Aikanäkymä (Top users): Aikankäymässä sovellus näyttää annetulla aikavälillä (mikä on käyttäjän konnfiguroitavissa) käyttäjät, jotka ovat tehneet ostoksia. Sovellus järjestää käyttäjät järjestykseen tehtyjen ostosten määrän mukaan. Sovellus päivittää tätä taulukkoa jatkuvasti, joten taulukossa ei näy kuin valitun aikavälin aikana tehdyt ostokset. 
-Tilastonäkymä (Statistics): Käyttäjä voi tarkastella erillisessä tilastonäkymässä graafisesti ja numeraalisesti keskimääräistä käyttäjillä olevaa saldoa ja kaikkien tietokantaan lisättyjen käyttäjien yhteissaldoa. Tilastot on laskettu ulkoisen kirjaston avulla. 
-Käyttäjän tietonäkymä (View user information): Tietonäkymään ei siirrytä aloitusnäkymästä, vaan käyttäjien lisäämisnäkymästä. Valistemalla listasta tietyn käyttäjän, sovellus näyttää tämän käyttäjän koko ostoshistorian ja antaa mahdollisuuden muokata käyttäjän tietoja


## Jatkokehitysideoita

-Asiakkaille tehdään omat tunnukset, jotta myös he voivat käyttää kirjanpitojärjestelmää tarvittaessa omatoimisesti.
-Mahdollisuus poistaa käyttäjiä, tällä hetkellä ainaostaan muokkaaminen on mahdollista. 
-Mahdollisuus muokata tuotteita.
-Mahdollisuus poistaa tuotteita.
-Mahdollisuus ostaa useampi kappale yhtä tuotetta kerralla. 
-Tietokannan siirtäminen verkkoon jotta sovellusta voitaisiin käyttää useammalla eri laitteella. 
-Graafisen käyttöliittymän parantaminen.
-Tilastojen laajentaminen. Koska sovellus tallentaa kaikki tehdyt ostokset, voisi tietokannan dataa käyttää esimerkiksi yksittäisen tuotteen hintajouston määrittämiseen, kunhan dataa ensin kertyy riittävästi ajan saatossa. 

