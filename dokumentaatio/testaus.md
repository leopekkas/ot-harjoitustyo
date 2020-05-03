# Testausdokumentti

Ohjelmalle on rakennettu JUnit testaus, jolla voidaan tarkastella ohjelman testauskattavuutta.

## Yksikkö- ja integraatiotestaus

### Sovelluslogiikka

Sovelluslogiikkaa, eli pakkausten [aurinkokuntasimulaattori.domain](https://github.com/leopekkas/ot-harjoitustyo/tree/master/Aurinkokuntasimulaattori/src/main/java/aurinkokuntasimulaattori/domain) ja [aurinkokuntasimulaattori.math](https://github.com/leopekkas/ot-harjoitustyo/tree/master/Aurinkokuntasimulaattori/src/main/java/aurinkokuntasimulaattori/math) luokkia testaavat integraatiotestit [KappaleTest ja SimPhysicsTest](https://github.com/leopekkas/ot-harjoitustyo/tree/master/Aurinkokuntasimulaattori/src/test/java/aurinkokuntasimulaattori), jotka nimiään vastaavasti testaavat domain pakkauksen eri luokkia., sekä [VectorTest](https://github.com/leopekkas/ot-harjoitustyo/blob/master/Aurinkokuntasimulaattori/src/test/java/aurinkokuntasimulaattori/math/VectorTest.java), jolla testataan laskennassa käytettävien vektoreiden toimivuutta.

### Pysyväistallennus

Pysyväistallennuksessa käytettäviä [Saver](https://github.com/leopekkas/ot-harjoitustyo/blob/master/Aurinkokuntasimulaattori/src/main/java/aurinkokuntasimulaattori/domain/Saver.java) ja [Loader](https://github.com/leopekkas/ot-harjoitustyo/blob/master/Aurinkokuntasimulaattori/src/main/java/aurinkokuntasimulaattori/domain/Loader.java) -luokkia testataan [SaverTest ja LoaderTest](https://github.com/leopekkas/ot-harjoitustyo/tree/master/Aurinkokuntasimulaattori/src/test/java/aurinkokuntasimulaattori) hyödyntämällä testien kanssa samaan kansioon tehtyjen testitiedostojen kautta.

### Asennus ja konfigurointi

Sovellus on haettu ja sitä on testattu [käyttöohjeen](https://github.com/leopekkas/ot-harjoitustyo/blob/master/dokumentaatio/K%C3%A4ytt%C3%B6ohje.md) mukaisella tavalla Linux-ympäristössä.

### Toiminnallisuudet

Kaikki [määrittelydokumentin](https://github.com/leopekkas/ot-harjoitustyo/blob/master/dokumentaatio/vaatimusm%C3%A4%C3%A4rittely.md) ja käyttöohjeen listaamat toiminnallisuudet on käyty läpi.

## Sovellukseen jääneet laatuongelmat

Sovellus ei anna järkeviä virheilmoituksia seuraavissa tilanteissa:
- Käyttäjä painaa _Load_ tai _Save_-nappia tiedoston latausta varten, mutta keskeyttää toiminnon _Cancel_ tai _X_-napeista. Myös simulaation senhetkinen konteksti tyhjentyy kyseisessä tapauksessa.

Sovelluslogiikan virheitä:
- Simulaation senhetkinen konteksti tyhjentyy, kun käyttäjä painaa _Load_-nappia, mutta keskeyttää toiminnon ennenaikaisesti.
- _Timestep unit_ napista avautuvia ikkunoita voi avata rajatta (Ei ehkä suoraan virhe, vaan enemmänkin ominaisuus)

 


