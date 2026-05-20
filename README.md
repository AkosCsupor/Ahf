# Házi feladat specifikáció

Információk [itt](https://viauav21.github.io/laborok/hf)

## Androidalapú szoftverfejlesztés
### 2026-04-26
### Csupor Ákos - EYJ8PS
### akos.csupor@gmail.com
### Laborvezető: Németh Balázs

## Bemutatás

Az alkalmazás egy otthoni sörfőzést segítő mobilalkalmazás, amely támogatja a receptek kezelését, a főzési folyamat nyomon követését, valamint az elkészült sörök dokumentálását. Az ötlet onnan származik, hogy saját hobbim bizonyos alkoholok készítése. Jelenleg a sörfőzés a következő célpont. Ezzel az alkalmazással akár önmagamat segítve vagy ha publikálásra kerül sor akkor a hasonló hobbit űző embereket is segítheti.

## Főbb funkciók


### Receptek tárolása

Az alkalmazás lehetőséget biztosít sörreceptek elmentésére és kezelésére. Egy recept tartalmazza a nevét, főzési mennyiséget, hozzávalókat, elkészítési lépéseket és opcionálisan képet is.

### Alapanyaglista

A felhasználó megtekintheti az egyes receptekhez szükséges alapanyagokat részletesen. Az alapanyagok között szerepelhetnek maláták, komlók, élesztők, cukrok és egyéb adalékok mennyiséggel és mértékegységgel együtt.

### Saját recept létrehozása

A felhasználó új receptet hozhat létre saját paraméterekkel. Megadható a recept neve, stílusa, mennyisége, hozzávalói, valamint a főzési folyamat lépései és időzítései.

### Főzési lépések lépésről lépésre

Az alkalmazás a kiválasztott recept alapján végigvezeti a felhasználót a főzési folyamaton. Minden lépés külön jelenik meg leírással, például cefrézés, komló hozzáadás, forralás, hűtés vagy erjesztés indítása.


### Push notification fermentáció végén

A fermentáció elindításakor a felhasználó megadhatja az erjesztési időtartamot (például 7 vagy 14 nap). Az alkalmazás a folyamat végén push értesítést küld.

### Kép feltöltés kész sörről

A felhasználó fényképet készíthet vagy választhat a galériából, majd a képet hozzárendelheti az elkészült sörhöz. Így vizuálisan is dokumentálható a főzés eredménye.

### Brew Day mód

Egy külön főzési mód, amely valós időben vezeti végig a teljes főzési napon a felhasználót. A rendszer automatikusan mutatja a következő lépést, indítja az időzítőket és értesítéseket küld.

### Alkohol % kalkulátor

Az alkalmazás kiszámítja a sör várható alkoholtartalmát a megadott OG (Original Gravity) és FG (Final Gravity) értékek alapján.

### Recept skálázó

A felhasználó megadhat új főzési mennyiséget (például 20 liter helyett 10 litert), az alkalmazás pedig automatikusan újraszámolja az összes alapanyag mennyiségét.

### Készletkezelő

Az alkalmazás nyilvántartja az otthon elérhető alapanyagkészletet. Megjeleníti, hogy egy recept elkészítéséhez elegendő mennyiség áll-e rendelkezésre, illetve mi hiányzik.


## Választott technológiák:

### Kötelező technológiák

- Jetpack Compose UI – modern natív felhasználói felület kialakítására
- MVVM architektúra – jól elkülönített UI, üzleti logika és adatréteg
- Dependency Injection (Hilt) – komponensek kezelése és függőségek injektálása

### További technológiák

- Adatbáziskezelés : receptek, alapanyagok, készletadatok és brew history lokális tárolása
- WorkManager: hosszabb idejű háttérfeladatok és időzített értesítések kezelése
- Kamera/Galéria kezelés: képfeltöltés az elkészült sörről
- Navigation Compose: többképernyős alkalmazás navigációja
___

# Házi feladat dokumentáció

### [Alkalmazás neve]

<img src="./assets/icon.png" width="160">

**Legkésőbb a dokumentáció fázisban lecserélendő a saját ikonnal!**

## Bemutatás

Az alkalmazás rövid, 2-3 mondatos bemutatása. Honnan az ötlet, mi szülte az igényt, ki lehetne a célközönség.
A laboron és előadáson bemutatott alkalmazásokat nem lehet házi feladatnak választani.

## Főbb funkciók

Az alkalmazás minden funkciójára kiterjedő leírás. Legyen egyértelműen eldönthető, hogy az adott funkció implementálva van-e!
P.l.: Az alkalmazással lehetőség van térképen megjeleníteni az állomáspontokat és azok A,B,C,D tulajdonságai meg is jelennek (ha elérhetőek).


## Felhasználói kézikönyv

Az alkalmazás minden funkciójára kiterjedő, teljes körű felhasználói leírás. Az összes releváns képernyőhöz/funkcióhoz tartalmaznia kell képernyőképet!

A képernyőképekkel kapcsolatos követelmények:

- Android Device Art Generator-ral telefont/tabletet kell rajzolni a képernyő köré!
	- Mindegy, hogy melyik készüléket választod, de legyen egységes az egész dokumentumban!
	- Telefonra tervezett képernyőket valamelyik telefon skin-nel, tablet képernyőt (amennyiben készítettél külön) tablet skin-nel készítsünk!
- Álló képernyőket álló módban, fekvőket fekvő módban rakjuk be! (Értelemszerűen. Ha fekvő képernyőképet húzol be a generator-ba, akkor fekvő módban rajzolja köré az eszközt)
-	Minden képhez legyen képaláírás, ami leírja hogy mit kell nézni a képen!
-	A képeket úgy méretezzük, hogy álló telefon méretből kettő elférjen egymás mellett egy sorban (fekvő illetve tablet képeket ehhez viszonyítva nagyítsuk)!
-	Amennyiben gesztúra vezérlést akarunk bemutatni a képernyőn, jelezzük a gesztúrát is! (ld példa kép)
-	A képeket és a képaláírásokat középre igazítsuk!

<p align="center">
<img src="./assets/image1.png" width="320">
<img src="./assets/image2.png" width="320">

1. ábra: Gesztúrával és gombbal is navigálható képernyők, hasznos kényelmi funkció a felhasználónak ha több lehetőséget is biztosítunk a navigációra
</p>

## Felhasznált technológiák:

Itt kell felsorolni minden technológiát, technikát, külső könyvtárat, komplexebb algoritmust, ami növeli az alkalmazás értékét. Osztályzáskor ezt a fejezetet nézzük meg először.

Külső osztálykönyvtár használata esetén a könyvtár neve legyen link, ami annak elérhetőségére mutat.

A kulcsszavak legyenek **félkövér** betűtípussal szedve.
Például:

- •	Az X és Y képernyők optimalizáltak **álló és fekvő nézetre** is
- [YCharts](https://github.com/yml-org/YCharts) osztálykönyvtár használata a grafikonok rajzolására
- **Fused Location API** használata helymeghatározásra
- **SQLite** alapú adattárolás
- Implicit intent használata **QR kód beolvasáshoz** (telepített Barcode Scanner alkalmazás szükséges a futtatásához)
- A játék fizikáját a [Box2D](https://box2d.org/) motor biztosítja
- **Service** használata zenelejátszáshoz

## Fontosabb technológiai megoldások

**A számodra legnehezebb/legérdekesebb funkciót fejtsd ki kb.  10 mondatban, hogy mi volt a probléma és hogyan oldottad meg.**
