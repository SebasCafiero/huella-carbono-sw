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

### Entrega 3:

Template:
https://docs.google.com/document/d/1cGvChcpcLVE2ruhLU7w-j8DMQ2ovKsIA/edit

Informe:

Diagramas:
https://app.diagrams.net/#G1AMS_ks9sLerY8oX13eJWu418mVdCjgH4

Útiles:
https://github.com/ezequieljsosa/tp-dds-2022-entrega3
https://docs.google.com/document/d/1mwcgcQ7C9YBsvr4qzfAUhJpGXad_CtjhDA7dvnL-9Aw/edit

---

### Entrega 2:

Template:
https://docs.google.com/document/d/1pUVb02YbHfMvoH5zJ72DC-YCKAuCczcU/edit

Informe:
https://docs.google.com/document/d/1-EXs81Jb4hMn90SCQ70BmBl10zFl4__7/edit

Diagramas:
https://app.diagrams.net/#G1AMS_ks9sLerY8oX13eJWu418mVdCjgH4

- Ejemplo de uso entrega 2:
```
mvn compile  exec:java -Dexec.mainClass="TrayectosHC" -Dexec.args="-o src/main/resources/entrada_TrayectosHC/organizaciones.json -t src/main/resources/entrada_TrayectosHC/trayectos.csv -T src/main/resources/entrada_TrayectosHC/transportes.json"
```
- El main se encargará de interpretar con las funcionalidades del Perser las organizaciones, trayectos y transportes correspondientes.
- Se registrará cada trayecto en las organizaciones correspondientes a sus miembros.
- Se registrarán las mediciones de cada organización (por el momento referenciadas con distintos archivos).
- Se cargarán los factores de emisión genéricos para todas las organizaciones.
- Se podrá obtener la Huella de Carbono para cada organización con diferentes especificaciones:
  - Filtrando por periodicidad.
  - Calcular el impacto de los trayectos respecto a la HC total.
  - Calcular el impacto de cada miembro según sus trayectos involucrados.
- Ejemplo *(EN CONSTRUCCION)*:
```
propiedades.csv:
"Combustion Fija", "Gas Natural", "m3", "10"
"Combustion Fija", "Diesel", "ls", "20"
"Combustion Movil", "Gasoil", "ls", "30"
"Electricidad", "Electricidad", "kwh", "40"
"Traslado de Miembros a la Organizacion", "Transporte Privado" - "GNC", "lts", "100"
"Traslado de Miembros a la Organizacion", "Transporte Privado" - "NAFTA", "lts", "150"
"Traslado de Miembros a la Organizacion", "Transporte Privado" - "ELECTRICO", "kmh", "15"
"Traslado de Miembros a la Organizacion", "Transporte Publico" - "TREN", "kmh", "200"
"Traslado de Miembros a la Organizacion", "Transporte Publico" - "SUBTE", "kmh", "250"

mediciones.csv:
organizaciones.json:
transportes.json:
trayectos.csv:
```
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