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
- Lietotājs izvēlas `1. Pievienot spēlētāju`
- Programma prasa ievadīt:
  - Vārdu
  - Spēlētāja numuru (unikāls)
  - Pozīciju (uzbrucējs, aizsargs, vārtsargs)

### 🔹 Spēles reģistrēšana
- Lietotājs izvēlas `2. Reģistrēt spēli`
- Ievada:
  - Datumu
  - Pretinieka komandu
  - Rezultātu (piem. 5:2)

### 🔹 Statistikas ievade
- Lietotājs izvēlas `3. Ievadīt statistiku`
- Izvēlas spēlētāju un spēli
- Ievada statistikas datus (vārti, piespēles, sodi)

### 🔹 Statistikas apskate
- Izvēloties `4. Skatīt spēlētāju statistiku`, lietotājs var:
  - Apskatīt kopējo statistiku
  - Meklēt spēlētājus pēc vārda vai numura

### 🔹 Datu saglabāšana / ielāde
- `5. Saglabāt datus` – saglabā `JSON` failā
- `6. Ielādēt datus` – ielādē datus no iepriekš saglabāta faila

---

Programma veidota atbilstoši objektorientētai pieejai, katram galvenajam objektam (Spēlētājs, Spēle, Statistika) ir sava klase. Tiek izmantotas datu filtrēšanas un meklēšanas metodes, kā arī tiek nodrošināta datu uzglabāšana ārējos failos.

