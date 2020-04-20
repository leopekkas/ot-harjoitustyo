# Aurinkokuntasimulaattori

Sovelluksen avulla käyttäjä voi tarkastella gravitaation vaikutuksia aurinkokunnan suuruusluokan mittakaavoissa.

Lisää kuvausta tulossa projektin edetessä!

## Dokumentaatio ##

[Vaatimusmäärittely](https://github.com/leopekkas/ot-harjoitustyo/blob/master/dokumentaatio/vaatimusm%C3%A4%C3%A4rittely.md)

[Työaikakirjanpito](https://github.com/leopekkas/ot-harjoitustyo/blob/master/dokumentaatio/Työaikakirjanpito.md)

[Arkkitehtuurikuvaus](https://github.com/leopekkas/ot-harjoitustyo/blob/master/dokumentaatio/arkkitehtuuri.md)

## Komentorivitoiminnot 

### Testaus

Testit suoritetaan komennolla
```
mvn test
```
Testikattavuusraportti luodaan komennolla 
```
mvn jacoco:report
```
Kattavuusraporttia voi tarkastella avaamalla selaimella tiedosto _target/site/jacoco/index.html_

### Ohjelman ajaminen 

Ohjelman voi ajaa komentoriviltä komennolla
```
mvn compile exec:java -Dexec.mainClass=aurinkokuntasimulaattori.domain.Main
```

### Checkstyle

Tiedostoon checkstyle.xml määrittelemät tarkistukset suoritetaan komennolla
```
mvn jxr:jxr checkstyle:checkstyle
```
