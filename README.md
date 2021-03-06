# Aurinkokuntasimulaattori

Sovelluksen avulla käyttäjä voi tarkastella gravitaation vaikutuksia aurinkokunnan suuruusluokan mittakaavoissa.

## Dokumentaatio ##

[Käyttöohje](https://github.com/leopekkas/ot-harjoitustyo/blob/master/dokumentaatio/K%C3%A4ytt%C3%B6ohje.md)

[Vaatimusmäärittely](https://github.com/leopekkas/ot-harjoitustyo/blob/master/dokumentaatio/vaatimusm%C3%A4%C3%A4rittely.md)

[Testausdokumentti](https://github.com/leopekkas/ot-harjoitustyo/blob/master/dokumentaatio/testaus.md)

[Arkkitehtuurikuvaus](https://github.com/leopekkas/ot-harjoitustyo/blob/master/dokumentaatio/arkkitehtuuri.md)

[Työaikakirjanpito](https://github.com/leopekkas/ot-harjoitustyo/blob/master/dokumentaatio/Työaikakirjanpito.md)

## Releaset

[Viikko 5](https://github.com/leopekkas/ot-harjoitustyo/releases)

[Viikko 6](https://github.com/leopekkas/ot-harjoitustyo/releases/tag/viikko6)

[Loppupalautus](https://github.com/leopekkas/ot-harjoitustyo/releases/tag/Loppupalautus)

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

### JavaDoc

JavaDoc generoidaan komennolla 
```
mvn javadoc:javadoc
```
JavaDocia voi tarkastella avaamalla selaimella tiedoston _target/site/apidocs/index.html_
