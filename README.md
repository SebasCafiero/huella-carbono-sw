## Grupo 6:
- Antezana, Cristian
- Brandariz, Nicolás
- Cafiero, Sebastián
- Galli, Patricio
- Ruival, Julián
- Sequeira, Raúl

---

## Enunciado
https://docs.google.com/document/d/1JrrfGSMhQSedRqcdFXGG4duWqic4m-Tc/edit

---
---

### Entrega 2:

Template:
https://docs.google.com/document/d/1pUVb02YbHfMvoH5zJ72DC-YCKAuCczcU/edit

Informe:


Diagramas:
https://app.diagrams.net/#G1AMS_ks9sLerY8oX13eJWu418mVdCjgH4

---

### Entrega 1: 

Template:  
https://docs.google.com/document/d/1UEdUMCm31Sfp0nceks9aOZpcRrID02CI/edit

Informe:  
https://docs.google.com/document/d/19oey-oeZJOeRzqx62GqKQ8EOAW9A_ozD/edit

Diagramas:  
https://app.diagrams.net/#G1og6DMqGm5OKGTSFRNgNep-xY4xzlv7zP

Útiles:  
https://github.com/dds-utn/2022-ma-no-utiles-entrega1

- Ejemplo de uso entrega 1:
```
mvn compile  exec:java -Dexec.mainClass="CalculadorHU" -Dexec.args="-p ./src/test/resources/propiedades.csv -m src/test/resources/mediciones.csv"
```
- El main se encargará de interpretar con las funcionalidades del Perser las propiedades y mediciones correspondientes.
- Se generaran los factores de emision y se utilizarán asociados a las mediciones para obtener la HuellaCarbon.
- Ejemplo:
```
propiedades.csv:
"Combustion Fija", "Gas Natural", "m3", "1"
"Combustion Fija", "Diesel", "ls", "2"
"Combustion Movil", "Gasoil", "ls", "3"
"Electricidad", "Electricidad", "kwh", "4"

mediciones.csv:
"Combustion Fija", "Gas Natural", "m3", "10", "A", "2019"
"Combustion Movil", "Gasoil", "ls", "100", "M", "03/2021"
"Electricidad", "Electricidad", "kwh", "10000", "M", "10/2020"

Se usan los factores de emisión para cada propiedad (por ejemplo 3 para la categoría Combustión Movil - Gasoil) para calcular la huella según las mediciones:
(10 x 1) + (100 x 3) + (10000 x 4) = 40310.
```
---