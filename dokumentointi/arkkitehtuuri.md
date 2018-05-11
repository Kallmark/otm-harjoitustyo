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

## Päätoiminnallisuudet

*käyttäjän lisääminen tapahtuu oiheisen kuvan osoittamalla tavalla:*
  
<img src= "https://raw.githubusercontent.com/Kallmark/otm-harjoitustyo/master/misc/kuvat/sekvenssikaavio.png" widht="400">

Kuvassa käyttäjä lisää uusen kayttäjä-olion käyttöliittymästä käsin, jolloin käyttöliittymä kutsuu Kayttajaluokkaa. Luodulla kayttaja-oliolla kutsutaan KayttajaDao:n tallennusmetodia, jolla kyseinen käyttäjä tallennetaan sovelluksen käyttämään tietokantaan. 



