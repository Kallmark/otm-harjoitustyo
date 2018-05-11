## Testausdokumentti 

Ohjelmaa on testattu automatisoidusti yksikkö -ja integraatiotasolla JUnit -testeillä sekä manuaalisesti testaamalla. 

## Yksikkö -ja integraatiotestaus

Yksikkötestaus perustuu pakkauksen [Domain](https://github.com/Kallmark/otm-harjoitustyo/tree/master/Kauppasovellus-otm/src/main/java/domain)
luokkien User, Product ja Purchase testaamiseen yksittäisillä testeillä. 

Integraatiotestaus tapahtuu sekä pakkauksen Domain Logic-luokan että pakkauksen [Database](https://github.com/Kallmark/otm-harjoitustyo/tree/master/Kauppasovellus-otm/src/main/java/database)
Dao-luokkien testaamiseen. Testauksessa on pyritty kattamaan sovelluksen eri käyttötilanteita ja niissä esimerkiksi
varmistetaan ostoksen tekemisen jälkeen, että käyttäjän saldo ja ostetun tavaran määrä vähenevät.

Testauksen ytimessä on kansiosta [dbTest](https://github.com/Kallmark/otm-harjoitustyo/tree/master/Kauppasovellus-otm/src/test/dbTest)
löytyvä testitietokanta testi.db, johon on esitallennettu hieman dataa. Tietokanta luodaan uudelleen ennen jokaista testiä, joten
sen sisältämä data on aina ennalta tiedossa olevaa ennen testin suoritusta. 

## Testauskattavuus

Sovelluksen testauskattavuudet ovat seuraavat:

*koko testauskttavuus*

<img src="https://raw.githubusercontent.com/Kallmark/otm-harjoitustyo/master/misc/kuvat/testi1.png" width="800">

*domain -pakkauksen testauskattavuus*

<img src="https://raw.githubusercontent.com/Kallmark/otm-harjoitustyo/master/misc/kuvat/testi2.png" width="800">

*database -pakkauksen testauskattavuus*

<img src="https://raw.githubusercontent.com/Kallmark/otm-harjoitustyo/master/misc/kuvat/testi3.png" width="800">

## Järjestelmätestaus

Sovelluksen järjestelmätestaus on tehty manuaalisesti.

## Asennus ja konfigurointi
Sovellus on haettu ja sitä on testattu käyttöohjeen kuvaamalla tavalla Linux-ympäristössä siten, että sovelluksen käynnistyshakemistossa on ollut käyttöohjeen kuvauksen
mukainen _config.properties_-tiedosto.

Sovellusta on testattu sekä tilanteissa, joissa käyttäjät ja työt tallettavat tiedostot ovat olleet olemassa. Oletuksena on ollut, että käyttäjä
käyttää sovelluksen mukana tulevaa tietokantaa ja pystyy vaihtamaan sitä tarvittaessa. Sovellus ei itse luo tietokantaa tai tallenna tietoa siihen.

## Sovellukseen jääneet laatuongelmat

Sovellus ei anna tällä hetkellä järkeviä virheilmoituksia esimerkiksi silloin, kun tietokantaa ei jostain syystä ole käytettävissä. 



