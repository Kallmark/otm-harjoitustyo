<h1>Arkkitehtuurikuvaus<h1>

<h2>Pakkausrakenne<h2>

Sovelluksen pakkaus on rakennettu kutakuinkin kyseistä kuvaa vastaavalla tavalla:

<img src= "https://raw.githubusercontent.com/Kallmark/otm-harjoitustyo/master/misc/kuvat/pakkauskaavio.jpg" widht="400">

Pakkauksista ui sisältää JavaFX:llä toteutetun käyttöliittymän, domain sovelluslogiigan ja database tietojen pysyväistallennusta vastaavan koodin.

<h2>Käyttöliittymä<h2>

Käyttöliittymä sisältää neljä eri näkymää:

-aloitusnäkymän
-käyttäjien lisäämiseen tarkoitetun näkymän
-tuotteiden lisäämiseen tarkoitetun näkymän
-ostosten tilastojen tarkkailuun tarkoitetun näkymän

Kukin näistä on toteutettu omana Scene-oliona,, joista yksi kerrallaan on näkyvänä ja sijoitettuna sovelluksen stageen. Käyttöliittyä on erotettu itse sovelluslogiikasta ja ainoastaan kutsuu sovelluslogiikan ja pysyväistallentamisen metodeja. 

<h2> Sovelluslogiikka<h2>


Sovelluksen sisäinen logiikka rakentuu oheisen mallin perusteella:
<img src= "https://raw.githubusercontent.com/Kallmark/otm-harjoitustyo/master/misc/kuvat/luokkakaavio.jpg" widht="400">

Kolme luokkaa (Kayttaja, Tuote, Ostos) vaikuttavat keskenään siten, että käyttäjät voivat ostaa tuotteita, joista jää historiamerkintä tallennetun Ostos-olion muodossa. Tämä logiikka on integroituna Dao-suunnittelumallin avulla SQLlite-tietokantaan, jonka päälle itse sovellus on rakennettu. 

<h2>Päätoiminnallisuudet<h2>

<b>käyttäjän lisääminen<b> tapahtuu oiheisen kuvan osoittamalla tavalla:
  
<img src= "https://raw.githubusercontent.com/Kallmark/otm-harjoitustyo/master/misc/kuvat/sekvenssikaavio.png" widht="400">

Kuvassa käyttäjä lisää uusen kayttäjä-olion käyttöliittymästä käsin, jolloin käyttöliittymä kutsuu Kayttajaluokkaa. Luodulla kayttaja-oliolla kutsutaan KayttajaDao:n tallennusmetodia, jolla kyseinen käyttäjä tallennetaan sovelluksen käyttämään tietokantaan. 



