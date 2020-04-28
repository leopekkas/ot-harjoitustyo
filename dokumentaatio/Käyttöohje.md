## Käyttöohje

Lataa tiedosto [aurinkokuntasimulaattori.jar](https://github.com/leopekkas/ot-harjoitustyo/releases)

### Toiminnallisuudet

Sovelluksessa näkyy aluksi musta ruutu, jonka vasemmasta ylänurkasta löydät menuvalikon. 

Menusta ensimmäisenä vasemmalta löydät infonapin, josta saat tietoa sovelluksen toiminnallisuuksista. (Infotekstit ovat kuitenkin hyvin lyhyitä tällä hetkellä ja tämä toiminnallisuus luultavasti poistuu myöhemmissä versioissa)

#### Simulation-nappi

_Simulation_-menuvalikon alta voit ajaa valittua simulaatiota start-napilla, pysäyttää simulaation stop-napilla ja käydä yksittäisiä aika-askelia step-napilla.

#### Presets-nappi

_Presets_-menuvalikosta pääset käsiksi valmiiksi pyöritettäviin malleihin, jotka kuvaavat jotain oleellista kiertorataa tai vastaavaa.

#### Simulaation aika-askeleen muuttaminen

_Timestep length_ -napista avautuu käyttöösi slideri, jolla voit muokata aika-askeleen pituutta, jolla simulaatio laskee kappaleiden väliset vuorovaikutukset. Kovin suuren aika-askeleen käyttö voi johtaa kiertoratojen epästabiilisuuteen, kun taas liian pienen askeleen käyttö saa simulaation pyörimään turhan hitaasti. Suositeltava arvo on n. 0.3-0.6 välillä

### Tietojen tallennus

_File_ menuvalikosta löydät Save ja Load -napit, joilla voit tallentaa simulaation senhetkisen tilan, tai ladata itse kirjoittamasi tai aiemmin tallentamasi tiedoston ajettavaksi.

_Save_-nappi avaa tiedostoselaimen, jonka avulla voit navigoida toivomaasi kansioon ja tekemään sinne haluamasi nimisen tekstitiedoston tallennetulle datalle. 

_Load_-napilla avaa näkymään myös tiedostoselaimen, josta voit valita haluamasi tiedoston ladattavaksi. Huomaathan, että tiedon tulee olle täsmälleen oikeassa muodossa, jotta simulaatio osaa sen lukea. Tekstin toivottu muoto tulee olla muodossa Planeetan nimi, paikan x-koordinaatti, y- koordinaatti, nopeuden x-suuntainen vektori, nopeuden y-suuntainen vektori, kappaleen massa, kappaleen säde. Kappaleen nimi on String-oliomuuttuja ja muut tiedot Double-muuttujia. Kappaleiden tiedot erotetaan rivinvaihdolla. 

Alla ladattavan esimerkkitiedoston sisältö:
```
Maa, 100, 200, 5, -4.3, 10, 100
Kuu, 300, 400, 2, -8, 10.9, 110
```

Projekti sisältää (tällä hetkellä) tyhjän _data_-kansion, joka toimii hyvin alustana tallennustietojen säilyttämiselle. Voit kuitenkin vapaasti valita haluamasi kansion tiedostoille.
