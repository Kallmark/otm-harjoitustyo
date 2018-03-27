# Vaatimusmäärittely

## Sovelluksen tarkoitus

Sovellukselle on kaksi tarkoitusta. Ensinnäkin sen tarkoitus on pitää kirjaa varastosta ja siinä olevista tuotteista. Toisekseen sovelluksen on tarkoitus pitää kirjaa tuotteita ostavista asiakkaista ja heidän ostoshistoriastaan. Tähän käytetään verkossa olevaa tietokantaa.

## Käyttäjät

Sovelluksella on tarkoitus olla aluksi vain yksi käyttäjärooli. Erillistä admin-roolia ei tarvita, sillä ohjelma tulee ainoastaan em. varaston haltijan kirjanpitovälineeksi.

## Käyttöliittymäluonnos sanallisesti

Sovelluksessa on neljä näkymää: Aloitusnäkymä (valitse näkymä), Ostonäkymä (jossa näytetään varaston tuotteet nimeltä ja merkitään tietty tuote tietyn asiakkaan ostettavaksi), Varastonäkymä (näyttää varastossa olevat tuotteet ja niiden tiedot, antaa mahdollisuuden olemassa olevan tuotteen tietojen muokkaamiseen) ja Asiakasnäkymä (näyttää listan asiakkaista ja heidän ostoshistoriastaan, antaa mahdollisuuden muokata asiakkaiden tietoja).

## Perusversion tarjoama toiminnallisuus

Aloitusnäkymä:

-valitse joko Ostosnäkymä, Varastonäkymä tai Asiakasnäkymä.

Ostosnäkymä:

-Käyttäjä valitsee asiakkaan listasta

-Käyttäjä voi lisätä valitulle asiakkaalle tuotteen, jonka asiakas haluaa ostaa

Varastonäkymä:

-Käyttäjä näkee varastossa olevat tuotteet ja niiden tiedot

-Käyttäjä voi muokata em. tuotteiden tietoja.

Asiaksanäkymä:

-Käyttäjä näkee asiakkaiden tiedot ja voi muokata niitä.

## Jatkokehitysideoita

-Sovellukseen sisällytetään erillinen Live-näkymä (näyttää annetulla aikajaksolla 5 eniten tuotteita ostanutta käyttäjää).

-Asiakkaille tehdään omat tunnukset, jotta myös he voivat käyttää kirjanpitojärjestelmää tarvittaessa.

-Järjestelmä näyttää asiakasnäkymässä valitun asiakkaan ostoshistorian.