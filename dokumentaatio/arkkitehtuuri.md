## Sovelluslogiikka

Sovelluksen loogisen datamallin muodostavat luokat Kappale ja Aurinkokunta, jotka kuvaavat simulaatiossa pyöriviä kappaleita suhteessa aurinkokuntaansa.

![Kuva sovelluslogiikasta](https://github.com/leopekkas/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/sovelluslogiikka.png)

## Käyttöliittymä 

Käyttöliittymä sisältää ainakin kolme erillistä näkymää, joita voi tarkastella päänäkymän ohessa. Näkymät voidaan jakaa seuraavanlaisesti

- Päänäkymä
- Infonäkymät 
- Tiedostonselausnäkymä
- Uuden planeetan lisäyksen tarjoama ikkuna (ei toiminnallisuutta vielä)

Näistä kaikki paitsi tiedostojen selaukseen tarkoitettu näkymä ovat toteutettu omina Scene-olioinaan. Näkymät ovat sijoitettuina omiin stageihinsa, eli niitä voi siirrellä ja käyttää ohessa päänäkymän kanssa.

Käyttöliittymä on pyritty eristämään täysin sovelluslogiikasta ja kaikki käytännön laskelmat suoritetaan _SimulationPhysics_ -luokan alla. Samoin tiedostojen tallennus ja lataaminen toteutuu käyttöliittymässä vain tarjoten selausnäkymän tiedoston valintaa varten. Itse toiminnallisuudet toimivat _Saver_ ja _Loader_ luokkien alla, joista alempana tarkemmin.

## Tietojen pysyväistallennus

Pakkauksen _aurinkokuntasimulaattori.domain_ luokat _Saver_ ja _Loader_ tarjoavat toiminnallisuuden simulaation datan saamiseen tiedostoista ja niiden tallentamisesta vanhaan tai uuteen tiedostoon. 

### Tiedostot

Sovellus mahdollistaa simulaation datan tallentamisen käyttäjän itse valitsemaan tekstitiedostoon, joka voi olla jokin aiemmin käytetty tiedosto, tai täysin uusi. Tiedon voi halutessaan tallentaa projektin ulkopuoliseen kansioon.

Ladattaessa tiedostoa samantyyppisesti tieto voidaan noutaa käyttäjän itse valitsemasta kansiosta, jonka ei vältttämättä tarvitse olla juuri projektikansion sisällä. Projekti kuitenkin sisältää (tällä hetkellä) tyhjän kansion _aurinkokuntasimulaattori.data_, joka esimerkiksi toimii hyvänä paikkana sijoittaa tallennettuja tiedostoja.

Tiedostojen tallennustoiminto tallettaa tiedoston muotoon, josta sen lataaminen tapahtuu suoraviivaisesti _Loader_ luokan näkökulmasta. Käyttäjän itse tehdessään ladattavaa tiedostoa tulee hänen kiinnittää erityisesti huomiota tiedon oikeaan muotoon [käyttöohjeiden](https://github.com/leopekkas/ot-harjoitustyo/blob/master/dokumentaatio/K%C3%A4ytt%C3%B6ohje.md) mukaisesti. 

Tiedostojen talletusmuodon formaatti on seuraavanlainen:
```
nimi, 10, 10, 5, -3.5, 100, 15 
```
Jossa tiedot järjestyksessä kuvaavat kappaleen ominaisuuksia seuraavanlaisesti: 
- Nimi
- x-koordinaatti
- y-koordinaatti
- Nopeusvektorin x-komponentti
- Nopeusvektorin y-komponentti
- Sassa
- Säde
Tietojen kentät on eroteltuina pilkulla ja välillä. Kappaleen nimi on String-olio ja muut ominaisuudet Double-muuttujia. 

## Päätoiminnallisuudet

Kuvataan sovelluksen toimintalogiikkaa havainnollistavalla sekvenssikaaviolla.

### Valmiin mallin ajaminen (yksinkertaisuuden vuoksi tässä on vain Auringon ja Maan välinen simulaatio)

![Sekvenssikaavio toiminnallisuudesta](https://github.com/leopekkas/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/sekvenssikaaviomalli.png)

Malliesimerkissä käyttäjä siis ensin lisää mallisimulaation, jonka jälkeen hän ajaa sen start-napista ja lopuksi pysäyttää stop-napista.
