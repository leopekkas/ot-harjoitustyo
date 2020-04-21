# Kesken! LateXin math mode ei oletuksellisesti toimi perus MarkDown tekstissä, joten etsin keinon kaavojen embeddaamiseen.

## Simulaation fysikaalinen premissi

Tämänhetkisessä tilassaan projekti toimii vain mielenkiintoisena tapana tarkastella kappaleiden vuorovaikutusta ja niiden välistä laskentaa eri aika-askeleiden pituuksilla. Itse fysikaaliset arvot eivät vastaa totuutta, sillä etäisyyden tarkasteleminen javafx:n sisällä on osoittautunut haasteelliseksi ja jätänkin hienosäädön simulaation "todenmukaisuudessa" myöhemmille viikoille.

### Newtonin gravitaatioteoria

Aurinkokunnan mittakaavoilla mallinnettaessa tarkastellaan usein Newtonin gravitaatioteoriaa tai Keplerin lakeja, sillä näin pienillä mittakaavoilla Einsteinin suhteellisuusteorian vaikutukset ovat häviävän pieniä (paitsi Merkuriuksen kiertoradan tapauksessa, jos aihe kiinnostaa, löytyy englanninkielisestä wikipediasta lyhyt kuvaus efektistä: [Perihelion precession of Mercury](https://en.wikipedia.org/wiki/Tests_of_general_relativity#Perihelion_precession_of_Mercury)). 

Tässä simulaatiossa olen toteuttanut (ja pyrin toteuttamaan) laskennan Newtonilaisen fysiikan pohjalta.

Newton arvioi 1600-luvun loppupuolella planeettojen kiertoratojen olevan ennustettavissa ja niiden ratojen noudattavan lakeja, jotka riippuvat vuorovaikuttavien kappaleiden massoista ja etäisyyksistä toisiinsa nähden seuraavanlaisesti:

\begin{equation}
F = G*\frac{m_1*m_2}{r^2} \textrm{, jossa r on kappaleiden välinen etäisyys ja m kappaleiden massat. G on universaali gravitaatiovakio.}
\end{equation}

Kaavasta voimme johtaa kätevästi esimerkiksi kappaleiden kiertoratojen pituuksia tai vaikkapa monen kappaleen keskinäistä vuorovaikutusta toistensa kesken. 

Koska simulaatiossa tarkastelemme _n_ lukumäärää kappaleita, tulee laskennasta hyvin raskasta jo muutamalla sadalla kohteella näiden kaikkien vaikuttaessaan keskenään (_brute force_ -laskennan aikavaativuutta mitataan luokassa $O(n^n)$). Simulaation toimintaa pyritään siis rajaamaan suhteellisen pieniin kokonaisuuksiin, mutta esimerkiksi oman aurinkokuntamme planeettojen mallinnuksessa laskenta tapahtuu suhteellisen kätevästi.

Tarkastellaan vielä, miten saamme johdettua vaikkapa Maan ja Auringon välise vuorovaikutuksen ja tästä maan radan nopeuden:

\begin{equation}
F_maa = G * \frac{m_maa*m_aurinko}{r^2} \textrm{, ja tästä johdettuna maan keskeiskiihtyvyys Aurinkoa kohti:}

a_maa = G * \frac{m_aurinko}{r^2} \textrm{, jossa $a_maa$ on Maahan kohdistuva kiihtyvyys Auringon suuntaan, tästä edelleen saamme:}

\frac{v_t^2/r = G * \frac{m_aurinko}{r^2}} \textrm{, jossa $v_t$ on Maan tangentiaalinen nopeus (Huom! tässä tehdään oletus, että Maan koko nopeusvektori on tangentin suuntainen, mikä on hyvä approksimaatio)}

\frac{v_t = \sqrt[2]{G * \frac{m_aurinko}{r}}} \textrm{, jossa $v_t$ on Maan tangentiaalinen nopeus}

\end{equation}

Kun yhtälöön sijoitetaan lukuarvot (jätän sen nyt WolframAlphalle), saadaan Maan kiertoradan nopeudeksi Auringon ympärillä jonain tiettynä ajanhetkenä n. $29 800 \frac{m}{s}$. 
Nyt laskettuamme kiihtyvyyden muutoksen planeettaan, voimme päivittää sen nopeusvektoria aika-askeleen vaikuttaman ajan verran ja paikkavektoria nopeuden vaikuttaman määrän aika-askeleen pituudella. Tätä laskemalla saamme päivitettyä kappaleiden kulkua avaruudessa hetki hetkeltä. Jätän kuitenkin tarkemmat perustelut myöhempää varten, mutta tässä dokumentissa on nyt alkuun alustava katsaus aiheeseen.

