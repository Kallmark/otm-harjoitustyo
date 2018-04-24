<h1>Arkkitehtuurikuvaus<h1>

<h2>Pakkausrakenne<h2>

Sovelluksen pakkaus on rakennettu kutakuinkin kyseistä kuvaa vastaavalla tavalla:

<img src= "https://raw.githubusercontent.com/Kallmark/otm-harjoitustyo/master/misc/kuvat/pakkauskaavio.jpg" widht="400">

<h2> Sovellusrakenne<h2>


Sovelluksen sisäinen logiikka rakentuu oheisen mallin perusteella:
<img src= "https://raw.githubusercontent.com/Kallmark/otm-harjoitustyo/master/misc/kuvat/luokkakaavio.jpg" widht="400">

Kolme luokkaa (Kayttaja, Tuote, Ostos) vaikuttavat keskenään siten, että käyttäjät voivat ostaa tuotteita, joista jää historiamerkintä tallennetun Ostos-olion muodossa. Tämä logiikka on integroituna Dao-suunnittelumallin avulla SQLlite-tietokantaan, jonka päälle itse sovellus on rakennettu. 

<h2>Päätoiminnallisuudet<h2>

<b>käyttäjän lisääminen<b> tapahtuu oiheisen kuvan osoittamalla tavalla:
  
<img src= "https://raw.githubusercontent.com/Kallmark/otm-harjoitustyo/master/misc/kuvat/sekvenssikaavio.png" widht="400">



