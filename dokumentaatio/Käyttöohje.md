## Käyttöohje

Lataa tiedosto [aurinkokuntasimulaattori.jar](https://github.com/leopekkas/ot-harjoitustyo/releases)

### Toiminnallisuudet

Sovelluksessa näkyy aluksi musta ruutu, jonka vasemmasta ylänurkasta löydät menuvalikon. 

#### Simulation-nappi

_Simulation_-menuvalikon alta voit ajaa valittua simulaatiota start-napilla, pysäyttää simulaation stop-napilla ja käydä yksittäisiä aika-askelia step-napilla.

#### Simulaation kappaleiden lisääminen ja poistaminen

#### Valmiiden simulaatiomallien ajaminen

_Presets_-menuvalikosta pääset käsiksi valmiiksi pyöritettäviin malleihin, jotka kuvaavat jotain oleellista kiertorataa tai vastaavaa.

#### Simulaation oma kustomisointi

_Custom_-napin alta löytyvä _clear_-nappi mahdollistaa yksittäisen planeetan poistamisen simulaatiosta. Planeetan saa poistettua klikkaamalla halutun planeetan nappia, jossa lukevat kys. kappaleen tunnistetiedot. Käytännön syistä poistettavien kappaleiden näkymässä lukee vain kappaleiden nimet, massat ja halkaisijat, joten Randomisoidun valmiin mallin kanssa kyseinen toiminnallisuus ei ole kovinkaan hyödyllinen (kaikki planeetat ovat näissä nimetty _null_-arvoisiksi). Omia simulaatioita kirjoittaessaan kannattanee siis nimetä planeetat järkevästi, jotta niiden poistaminen sujuu toiminnallisuutta tarvittaessa kivuttomasti. Kokonaisia uusia simulaatioita lisättäessä kannatta toiminnallisuus suorittaa tekstitiedoston avulla, josta tarkemmin alempana kohdassa _Tietojen tallennus_.

Halutessasi voit näyttää simulaation kappaleiden nimet osana simulaatiota _Show planet names_-napilla. Samasta napista voit piilottaa kyseisen toiminnallisuuden.

#### Simulaation skaalaus

_Timestep length_ -napista avautuu käyttöösi slideri, jolla voit muokata aika-askeleen pituutta, jolla simulaatio laskee kappaleiden väliset vuorovaikutukset. Aika-askeleen yksikkö simulaatiossa on päivä. Kovin suuren aika-askeleen käyttö voi johtaa kiertoratojen epästabiilisuuteen, kun taas liian pienen askeleen käyttö saa simulaation pyörimään turhan hitaasti. Suositeltava arvo riippuu simulaation mittakaavan suuruudesta, aurinkokunnan tapauksessa n. yli 10 päivän aika-askeleen käyttö voi tehdä sisäplaneettojen kiertoradoista hyvinkin epästabiileja, kun taas ulkoplaneettojen radat pysyvät hyvinkin stabiileina.

_Timestep unit_ -napista voit muuttaa aika-askeleen yksikköä, jos haluat päivien sijasta tarkastella simulaation toimintaa sekuntien, tuntien, tai jopa vuosien mittakaavoilla. 

_Scaling tool_ -työkalulla voit suurentaa tai pienentää simulaation skaalaa. Simulaation keskikohta pysyy koordinaatissa 0,0 skaalaa muutettaessa. Suurta skaalaa käytettäessä on suositeltavaa asettaa planeettojen nimitekstit päälle, jolloin keskuskoordinaatteihin nähden kaukana olevien planeettojen ratoja on helpompi seurata.

### Tietojen tallennus

_File_ menuvalikosta löydät Save ja Load -napit, joilla voit tallentaa simulaation senhetkisen tilan, tai ladata itse kirjoittamasi tai aiemmin tallentamasi tiedoston ajettavaksi.

_Save_-nappi avaa tiedostoselaimen, jonka avulla voit navigoida toivomaasi kansioon ja tekemään sinne haluamasi nimisen tekstitiedoston tallennetulle datalle. 

_Load_-napilla avaa näkymään myös tiedostoselaimen, josta voit valita haluamasi tiedoston ladattavaksi. Huomaathan, että tiedon tulee olle täsmälleen oikeassa muodossa, jotta simulaatio osaa sen lukea. Tekstin toivottu muoto tulee olla muodossa (kiinnitäthän oikeisiin yksiköihin huomiota):
- Planeetan nimi 
- Paikan x-koordinaatti (metreissä)
- y- koordinaatti (metreissä)
- Nopeusvektorin x-suuntainen komponentti (yksikkönä m/s)
- Nopeusvektorin y-suuntainen komponentti (yksikkönä ms/s)
- Planeetan massa (yksikkönä kg)
- Planeetan säde (Vapaavalintainen double-muuttuja, riippuu mittakaavoista mutta arvot 5-30 usein toimivia)

Kappaleen nimi on String-oliomuuttuja ja muut tiedot Double-muuttujia. Kappaleiden tiedot erotetaan rivinvaihdolla. 

Alla ladattavan esimerkkitiedoston sisältö:
```
Maa, 0, 1.496E11, 30000, 0, 5.972E24, 15.0
Aurinko, 0, 0, 0, 0, 2E30, 30.0
```

Projekti sisältää (tällä hetkellä) tyhjän _data_-kansion, joka toimii hyvin alustana tallennustietojen säilyttämiselle. Voit kuitenkin vapaasti valita haluamasi kansion tiedostoille.
