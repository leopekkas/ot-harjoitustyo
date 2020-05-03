# Vaatimusmäärittely #

## Sovelluksen tarkoitus ##

* Sovelluksen avulla käyttäjä pystyy tarkastelemaan gravitaation vaikutusta aurinkokunnan suuruusluokan mittakaavoilla Newtonin lakien pohjalta.
* Sovellusta käytetään aina laitekohtaisesti, joten pääkäyttäjän lisäksi muita käyttäjiä siinä ei sinällään ole.

## Käyttöliittymä ##

* Sovellus aukeaa kirjautumisnäkymään, josta voi valita uuden täysin oman simulaation luomisen tai jonkin valmiin tilanteen ajamisen.
* Valittuaan halitsemansa simulaatiotyppin käyttäjä pääsee näkymään, jossa planeetat käyttäytyvät simulaatiossa fysiikan lakien mukaisesti yksinkertaisessa 2D näkymässä.
* Käyttöliittymä sisältää menukentän, jossa on simulaation muokkaukseen, tallennukseen, lataamiseen ja skaalaamiseen tarvittavat toiminnallisuudet.
* Käyttöliittymässä näkyy tietokenttä, joka kertoo simulaation laskemien askeleiden lukumäärän, sekä simulaation näkökulmasta kuluneen ajan päivissä ja vuosissa.
* Käyttäjä voi tarkastella kappaleita pelkästään ''ympyröinä'', tai voi halutessaan näyttää ruudulla reaaliaikaisesti joka kappaleen nimitiedot vastaavan kappaleen alla.

## Perusversion tarjoama toiminnallisuus ##

### Simulaation initialisointi ###

* Käyttäjä voi itse luoda uusia kappaleita, tai ladata tiedoston, joka sisältää esim. aiemmin käsiteltyjä tai tallennettuja simulaatioita. Käyttäjä voi myös itse kirjoittaa omaan tiedostoon haluamansa tilanteen simulaatiolle.

### Simulaation toiminnallisuudet initialisoinnin jälkeen ###

* Käyttäjä pystyy tarkastella nykyisten kappaleiden käyttäytymistä yksinkertaisessa simulaatiossa.
* Käyttäjä voi lisätä simulaatioon uusia kappaleita ja antamaan niille ominaisuuksia, kuten massan, halkaisijan, nopeuden ja tunnistetietona nimen.
* Käyttäjä pystyy tallentamaan nykyisen simulaation sisältämät kappaleet tiedostoon, jonka pystyy myöhemmin ajamaan uudestaan sovelluksessa.
* Käyttäjä pystyy testailemaan valmiiksi luotuja tilanteita, esim. aurinkokuntaa vastaavaa järjestelmää. 
* Käyttäjä voi muuttaa simulaation laskemisessa käytettävän aika-askeleen pituuksia ja yksiköitä.
* Simulaatio kertoo laskemiensa askeleiden lukumäärän, sekä simulaation näkökulmasta kuluneen ajan päivissä ja vuosissa.

## Jatkokehitysideoita

Perusversion jälkeen sovelluksen toimintaa voidaan laajentaa esim. seuraavanlaisilla toiminnallisuuksilla:

* Värien lisääminen yhdeksi osaksi kappaleiden tietoja
* Mahdollisuus syöttää kappaleiden tietoja eri yksiköissä
* Metodi pyöreän kiertoradan laskemiseen, jolloin kys. kappaleilla voitaisiin jättää nopeudet ilmoittamatta syötetiedostossa
* Käyttöliittymän siirtäminen hiirellä, näkymän kohdistus yksittäisiin kappaleisiin
* Käyttöliittymän kaunistamista

