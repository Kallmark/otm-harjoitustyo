# Arkkitehtuurikuvaus

## Pakkausrakenne

Ohjelman rakenne noudattaa kolmitasoista kerrosarkkitehtuuria. Koodin pakkasurakenne on kutakuinkin kyseistä kuvaa vastaava:

<img src= "https://raw.githubusercontent.com/Kallmark/otm-harjoitustyo/master/misc/kuvat/pakkauskaavio.jpg" widht="400">

Pakkauksista ui sisältää JavaFX:llä toteutetun käyttöliittymän, domain itse sovelluslogiikan ja database tietojen pysyväistallennusta vastaavan koodin.

## Käyttöliittymä

Käyttöliittymä sisältää seitsemän eri näkymää:

- Aloitusnäkymän.
- Käyttäjien lisäämiseen tarkoitetun näkymän.
- Tuotteiden lisäämiseen tarkoitetun näkymän.
- Ostosten tekemiseen tarkoitetun näkymän.
- Aikanäkymän käyttäjien käyttäjän tietyllä aikavälillä tehtyjen ostosten tarkastelua varten.
- Tilastonkäymän, joka tarjoaa tilastollista tietoa esimerkiksi käyttäjien saldosta. 
- Käyttäjän tietonkäymän, jossa käyttäjien tietoja voi muokata ja käyttäjän koko ostoshistoriaa tarkastella.

Kukin näkymistä on toteutettu omana Scene-oliona,, joista yksi kerrallaan on näkyvänä ja sijoitettuna sovelluksen stageen. Käyttöliittyä on erotettu itse sovelluslogiikasta ja ainoastaan kutsuu sovelluslogiikan metodeja. Sovelluslogiikka hoitaa myös kommunikoinnin tietojen pysyväistallenuksesta vastaavien luokkien välillä. 

## Sovelluslogiikka

Sovelluksen sisäinen logiikka rakentuu oheisen mallin perusteella:

<img src= "https://raw.githubusercontent.com/Kallmark/otm-harjoitustyo/master/misc/kuvat/luokkakaavio.jpg" widht="400">

Kolme luokkaa ([User](https://github.com/Kallmark/otm-harjoitustyo/blob/master/Kauppasovellus-otm/src/main/java/domain/User.java), [Product](https://github.com/Kallmark/otm-harjoitustyo/blob/master/Kauppasovellus-otm/src/main/java/domain/Product.java) ja [Purchase](https://github.com/Kallmark/otm-harjoitustyo/blob/master/Kauppasovellus-otm/src/main/java/domain/Purchase.java)) vaikuttavat keskenään siten, että käyttäjät voivat ostaa tuotteita, joista jää historiamerkintä tallennetun Ostos-olion muodossa. 

Sovelluksen toiminnallisuuksista vastaa pääosin luokan [Logic](https://github.com/Kallmark/otm-harjoitustyo/blob/master/Kauppasovellus-otm/src/main/java/domain/Logic.java) ainoa olio. Logic-luokka on integroituna Dao-suunnittelumallin ja sitä toteuttavien luokkien avulla SQLlite-tietokantaan. Itse tietokanta on konfiguroitu siten, että käyttäjä voi halutessaan vaihtaa sovelluksen käyttämää tietokantaa. Se myös hyödyntää erillistä [Statistics](https://github.com/Kallmark/otm-harjoitustyo/blob/master/Kauppasovellus-otm/src/main/java/domain/Statistics.java) luokkaa, joka perustuu uloisen kirjaston tarjoamiin toiminnallisuuksiin. Statistics -luokan oliolla Logic -luokan ainoa olio saa tilastollista dataa tietokannasta. 

Logic -luokan tarjoamia metodeita ovat esimerkiksi: 

- public boolean saveUser(String id, String name, String balance)
- public boolean saveProduct(String id, String name, String price, String amount, String info)
- public Map findUsersPurchaseHistory(Integer id)
- public boolean saveOrUpdatePurchase(Purchase purchase)

## Tietojen pysyväistalletus 

Pakkauksen database luokat UserDao, ProductDao ja PurchaseDao huolehtivat tietojen tallentamisesta ja noutamisesta tietokantaa käyttäen. Käyttäjä voi halutessaan vaihtaa käytettävää tietokantaa [konfiguraariotiedoston](https://github.com/Kallmark/otm-harjoitustyo/blob/master/Kauppasovellus-otm/config.properties) avulla.

SQLite-tietokanta on luotu seuraavilla komennoilla:

>CREATE TABLE Tuote (
tuote_id integer PRIMARY KEY,
nimi varchar(100),
hinta integer,
maara integer,
info varchar (100)
);
CREATE TABLE Kayttaja (
kayttaja_id integer PRIMARY KEY, 
nimi varchar(100), 
saldo integer
);
CREATE TABLE OSTOS (
kayttaja_id integer,
tuote_id integer, 
date integer,
FOREIGN KEY (kayttaja_id) REFERENCES Kayttaja (kayttaja_id),
FOREIGN KEY (tuote_id) REFERENCES Tuote (tuote_id)
>);

Sovelluslogiikan testaamisessa hyödynnetään erillistä testitietokantaa, johon on esitallennettu valmiiksi dataa testien suorittamiseksi.

## Päätoiminnallisuudet

Ohessa selvennetään muutamien sovelluksen päätoiminnallisuuksien toimintaa. Esimerkeiksi on valittu tiedon tallentaminen käyttäjän muodossa (muu tallentaminen ja muokkaainen muistuttaa paljolti samaa logiikkaa), aikanäkymän taulukon toiminta ja tilastotoiminnallisuuden tilastotiedon haku. 

*käyttäjän lisääminen tapahtuu oiheisen kuvan osoittamalla tavalla:*
  
<img src= "https://raw.githubusercontent.com/Kallmark/otm-harjoitustyo/master/misc/kuvat/sekvenssikaavio1.png" widht="400">

Kuvassa käyttäjä lisää uusen kayttäjä-olion käyttöliittymästä käsin, jolloin käyttöliittymä kutsuu Logiikkaluokkaa. Logiikkaluokka luo User -olion tarkastettuaan, että käyttöliittymästä saadut merkkijonot voidaan kääntää User-olion paremetreiksi. Näin voidaan välttyä erroreilta silloin, kun käyttäjä syöttää esimerkiksi saldoksi jonkin merkkijonon liukuluvun sijaan. Luodulla kayttaja-oliolla kutsutaan KayttajaDao:n tallennusmetodia, jolla kyseinen käyttäjä tallennetaan sovelluksen käyttämään tietokantaan. Mikäli tallennus onnistuu, Logic -luokka lähettää varmistuksena käyttöliittymälle "true" ja käyttöliittymä näyttää sovelluksen käyttäjälle viestillä (oikeasti tämä ei tapahdu tulostuksella, vaan tieto tallennetaan Labeliin) tallennuksen onnistuneen.

*Top käyttäjien tiedon saaminen*

<img src= "https://raw.githubusercontent.com/Kallmark/otm-harjoitustyo/master/misc/kuvat/sekvessikaavio2.png" widht="400">

Tämä toiminnallisuus on hieman erikoinen, sillä käyttöliittymä kutsuu logiika-luokkaa ilman sovelluksen käyttäjän erillistä käskyä (esimerkiksi napin painainen). Logiikkaluokka taas kutsuu UserDao -luokkaa, joka palauttaa LinkedMapin, johon on listattu avaimina käyttäjät ja arvoina käyttäjien tekemien ostosten määrä järjestyksessä eniten ostoksia tehneestä käyttäjästä vähiten ostoksia tehneeseen käyttäjään. Käyttöliittymässä on AnimationTimer -olio, joka päivittää jatkuvalla syötöllä Top käyttäjien -listaa. Itse lista tosin tulee sovelluksen käyttäjälle näkyviin vasta, kun käyttäjä valitsee kyseisen näkymän. 

*Tilastojen saamien*

<img src= "https://raw.githubusercontent.com/Kallmark/otm-harjoitustyo/master/misc/kuvat/sekvenssikaavio4.png" widht="400">

Tilastojen saaminen muodostaa samaa logiikkaa kuin Top käyttäjien tietojen saaminen, paitsi että tässä tapauksessa Logic-luokka kommunikoi myös Statistics -luokan kanssa. Ensin Logic -luokka kutsuu UserDao-luokkaa, joka antaa Logic-luokalle listan kaikista käyttäjistä. Logic-luokka välittää parametrina tämän Statistics-luokalle, joka laskee listan käyttäjä-olioiden keskimääräisen saldon. Logic-luokka välittää arvon käyttöliittymälle, jossa AnimationTimer -olio päivittää tulosta jatkuvalla syötöllä.

