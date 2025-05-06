# Spēlētāju statistikas pārvaldnieks

## Apraksts

Šī ir Java konsoles programma, kas paredzēta sporta komandas spēlētāju statistikas uzskatei un pārvaldībai. Programmas mērķis ir atvieglot trenera vai komandas vadītāja darbu, nodrošinot iespēju vienkārši pievienot spēlētājus, reģistrēt viņu statistiku un veikt dažādas analīzes.

Galvenās funkcijas:
- Pievienot spēlētājus ar informāciju par vārdu, numuru un statistiku.
- Reģistrēt un uzskaitīt gūtos vārtus, piespēles un spēlētās spēles.
- Kārtot datus pēc vairākiem kritērijiem.
- Meklēt un filtrēt spēlētājus pēc dažādiem parametriem.
- Veikt statistisko analīzi (vidējie rādītāji, skaitīšana diapazonā).

## Lietotāja interfeisa apraksts

Programma darbojas konsoles režīmā ar teksta izvēlnēm. Pēc programmas palaišanas lietotājs redz galveno izvēlni ar darbību sarakstu:

Lietotājs izvēlas vēlamo darbību, ievadot attiecīgo numuru. Katrs izvēlnes punkts aizved uz nākamo soli ar detalizētiem norādījumiem.

## Funkciju apraksts

### 🔹 Spēlētāju pievienošana un rediģēšana
- Lietotājs izvēlas `1. Add Player Stats`
- Programma prasa ievadīt:
  - Spēlētāja numuru (unikāls)
  - Vārdu (ja spēlētājs vēl nav sistēmā)
  - Punktu skaitu (golus un piespēles)
  - Spēļu skaitu

### 🔹 Statistikas apskate
- Izvēloties `2. Show Players`, lietotājs var:
  - Apskatīt kopējo statistiku par visiem spēlētājiem

### 🔹 Statistikas dzēšana
- Lietotājs izvēlas `3. Delete Player`, lietotājs var:
  - Dzēst spēlētāju no tabulas ievadot viņa numuru

### 🔹 Datu kārtošana
- Izvēloties `4. Sort Players`, lietotājs var:
  - Kārtot pēc viena kritērija (numurs, vārds, goli, piespēles, spēles, kopējie punkti)
  - Kārtot pēc diviem kritērijiem (primārais un sekundārais)
  - Izvēlēties kārtošanas virzienu (augoši vai dilstoši)

### 🔹 Datu filtrēšana
- Izvēloties `5. Filter Players`, lietotājs var:
  - Meklēt pēc vārda (daļēja atbilstība)
  - Meklēt pēc numura
  - Filtrēt pēc skaitliskajiem kritērijiem (goli, piespēles, spēles, kopējie punkti)
  - Izmantot dažādus salīdzināšanas operatorus (=, >, <, >=, <=)

### 🔹 Statistikas analīze
- Izvēloties `6. Show Statistics`, lietotājs var:
  - Skaitīt spēlētājus ar vērtībām noteiktā diapazonā
  - Apskatīt vidējos rādītājus (goli, piespēles, spēles, kopējie punkti)

### 🔹 Programmas beigšana
- Izvēloties `7. Exit`, lietotājs var:
  - Saglabāt datus un beigt programmu

### 🔹 Datu saglabāšana / ielāde
- Dati automātiski tiek saglabāti `JSON` failā pēc izmaiņām
- Dati automātiski tiek ielādēti, palaižot programmu

---

##  Projekta struktūra

<pre lang="markdown">  Statistics_tracker/
  ├── .devcontainer/
  │   └── devcontainer.json
  │
  ├── .github/
  │   └── dependabot.yml
  ├── .mvn/
  │   └── wrapper
  │       └── maven-wrapper.properties
  ├── data/
  │   └── players.json
  ├── src/
  │   ├── main/
  │   │   └── java/
  │   │       └── lv/
  │   │           └── rvt/
  │   │               ├── tools/
  │   │               │   └── Helper.java
  │   │               ├── Color.java
  │   │               ├── ConsoleUI.java
  │   │               ├── Main.java
  │   │               ├── Player.java
  │   │               └── PlayerService.java
  │   └── test/
  │       └── java/
  │           └── lv/
  │               └── rvt/
  │                   └── AppTest.java
  ├── .gitignore
  ├── mvnw
  ├── mvnw.cmd
  ├── pom.xml
  └── README.md</pre>

---

## Tehniskās īpatnības

Programma veidota atbilstoši objektorientētai pieejai, katram galvenajam objektam ir sava klase. 

### Datu apstrāde
- **Daudzpusīga kārtošana**: Iespēja kārtot pēc jebkura kritērija vai vairākiem kritērijiem vienlaicīgi
- **Elastīga filtrēšana**: Meklēšana un filtrēšana pēc dažādiem kritērijiem, izmantojot dažādus operatorus
- **Statistiskā analīze**: Datu apkopošana, vidējo rādītāju aprēķināšana, datu skaitīšana diapazonā

### Kodu organizācija
- **Enums**: Izmantoti uzskaitījuma tipi (SortCriteria, FilterCriteria, NumericFilterCriteria, ComparisonOperator)
- **Java Streams API**: Izmantots efektīvai datu apstrādei un filtrēšanai
- **Komparatoru ķēdes**: Iespēja kombinēt vairākus kārtošanas kritērijus
- **Datu persistēšana**: Automātiska datu saglabāšana un ielāde JSON formātā

Programma demonstrē modernas Java valodas iespējas un struktūras, kas atvieglo datu pārvaldīšanu un analīzi.