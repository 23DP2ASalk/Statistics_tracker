# Spēlētāju statistikas pārvaldnieks

## Apraksts

Šī ir Java konsoles programma, kas paredzēta sporta komandas spēlētāju statistikas uzskatei un pārvaldībai. Programmas mērķis ir atvieglot trenera vai komandas vadītāja darbu, nodrošinot iespēju vienkārši pievienot spēlētājus, reģistrēt spēles un analizēt uzkrāto statistiku.

Galvenās funkcijas:
- Pievienot spēlētājus ar informāciju par vārdu, numuru un pozīciju.
- Reģistrēt spēles pret dažādiem pretiniekiem ar rezultātu.
- Ievadīt un saglabāt statistiku katram spēlētājam (vārti, piespēles, sodi).
- Attēlot statistiku un meklēt spēlētājus pēc dažādiem kritērijiem.

## Lietotāja interfeisa apraksts

Programma darbojas konsoles režīmā ar teksta izvēlnēm. Pēc programmas palaišanas lietotājs redz galveno izvēlni ar darbību sarakstu:

Lietotājs izvēlas vēlamo darbību, ievadot attiecīgo numuru. Katrs izvēlnes punkts aizved uz nākamo soli ar detalizētiem norādījumiem.

## 3. Funkciju apraksts

### 🔹 Spēlētāju pievienošana
- Lietotājs izvēlas `1. Add Player Stats`
- Programma prasa ievadīt:
  - Spēlētāja numuru (unikāls)
  - Vārdu
  - Punktu skaitu(golus un piespēles)
  - Spēļu skaitu

### 🔹 Statistikas apskate
- Izvēloties `2. Show Players`, lietotājs var:
  - Apskatīt kopējo statistiku

### 🔹 Statistikas dzēšana
- Lietotājs izvēlas `3. Delete Player`, lietotājs var:
- Dzēst spēlētaju no tabulas ievadot viņa numuru

### 🔹 Statistikas apskate
- Izvēloties `4. Exit`, lietotājs var:
  - Saglabā datus
  - Beidz prorammu

### 🔹 Datu saglabāšana / ielāde
- `5. Saglabāt datus` – saglabā `JSON` failā
- `6. Ielādēt datus` – ielādē datus no iepriekš saglabāta faila

---

Programma veidota atbilstoši objektorientētai pieejai, katram galvenajam objektam (Spēlētājs, Spēle, Statistika) ir sava klase. Tiek izmantotas datu filtrēšanas un meklēšanas metodes, kā arī tiek nodrošināta datu uzglabāšana ārējos failos.

