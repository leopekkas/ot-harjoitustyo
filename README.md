# Aurinkokuntasimulaattori

Sovelluksen avulla käyttäjä voi tarkastella gravitaation vaikutuksia aurinkokunnan suuruusluokan mittakaavoissa.

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

### Suoritettavan jarin generointi 

Komento

```
mvn package
```

generoi hakemistoon _target_ suoritettavan jar-tiedoston _Aurinkokuntasimulaattori-1.0-SNAPSHOT.jar_


### Checkstyle

Tiedostoon [checkstyle.xml](https://github.com/leopekkas/ot-harjoitustyo/blob/master/Aurinkokuntasimulaattori/checkstyle.xml) määrittelemät tarkistukset suoritetaan komennolla
```
mvn jxr:jxr checkstyle:checkstyle
```
Mahdolliset virheilmoitukset selviävät avaamalla selaimella tiedoston _target/site/checkstyle.html_
