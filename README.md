## Grupo 6:
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
https://docs.google.com/document/d/1PSw249i9DkLQTf3nQcVpy_uSC7G6Y2KL/edit

Diagramas:
https://app.diagrams.net/#G1AMS_ks9sLerY8oX13eJWu418mVdCjgH4

Útiles:
https://github.com/ezequieljsosa/tp-dds-2022-entrega3
https://docs.google.com/document/d/1mwcgcQ7C9YBsvr4qzfAUhJpGXad_CtjhDA7dvnL-9Aw/edit

#### API

- URL Base:
- Rutas con Métodos HTTP:
- Ejemplo de uso:
  - EN CONSTRUCCION

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
- Ejemplo:
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
"Combustion Fija", "Gas Natural", "m3", "10", "A", "2019"
"Combustion Movil", "Gasoil", "ls", "100", "M", "03/2021"
"Electricidad", "Electricidad", "kwh", "10000", "M", "10/2020"

organizaciones.json:
[
  {
    "organizacion": "UTN",
    "ubicacion" : "Buenos Aires",
    "clasificacion" : "Universidad",
    "latitud" : 146.4,
    "longitud" : 146.4,
    "tipo" : "INSTITUCION",
    "sectores": [
      {
        "nombre" : "Sistemas",
        "miembros" : [
          {
            "nombre" : "Juan",
            "apellido" : "Pérez",
            "tipoDocumento" : "DNI",
            "documento" : 1,
            "ubicacion" : "Cordoba",
            "latitud" : 312.4,
            "longitud" : 43.4
          },
          {
            "nombre" : "Fernando",
            "apellido" : "Cópola",
            "tipoDocumento" : "PASAPORTE",
            "documento" : 2,
            "ubicacion" : "Cordoba",
            "latitud" : 312.4,
            "longitud" : 43.4
          }
        ]
      },
      {
        "nombre" : "Recursos Humanos",
        "miembros" : [
          {
            "nombre" : "Sebastián",
            "apellido" : "Sosa",
            "tipoDocumento" : "DNI",
            "documento" : 3,
            "ubicacion" : "Cordoba",
            "latitud" : 312.4,
            "longitud" : 43.4
          },
          {
            "nombre" : "Martín",
            "apellido" : "Tagliafico",
            "tipoDocumento" : "DNI",
            "documento" : 4,
            "ubicacion" : "Cordoba",
            "latitud" : 312.4,
            "longitud" : 43.4
          }
        ]
      }
    ]
  }
]

transportes.json:
[
  {
    "transporte": "Tachero",
    "tipo" : "contratado",
    "subtipo" : "TAXI"
  },
  {
    "transporte": "El 47",
    "tipo" : "publico",
    "subtipo" : "COLECTIVO",
    "linea" : "47",
    "paradas" : [
      {
        "latitud" : 123.4,
        "longitud" : 123.4,
        "distanciaAnterior" : 0.0,
        "distanciaProxima" : 7.0
      },
      {
        "latitud" : 129.0,
        "longitud" : 129.0,
        "distanciaAnterior" : 7.0,
        "distanciaProxima" : 8.0
      },
      {
        "latitud" : 135.4,
        "longitud" : 135.4,
        "distanciaAnterior" : 8.0,
        "distanciaProxima" : 0
      }
    ]
  },
  {
    "transporte": "El tren",
    "tipo": "publico",
    "subtipo": "TREN",
    "linea": "sarmiento",
    "paradas": [
      {
        "latitud": 120.4,
        "longitud": 120.4,
        "distanciaAnterior": 0.0,
        "distanciaProxima": 25.0
      },
      {
        "latitud": 135.4,
        "longitud": 135.4,
        "distanciaAnterior": 25.0,
        "distanciaProxima": 15.0
      },
      {
        "latitud": 146.4,
        "longitud": 146.4,
        "distanciaAnterior": 15.0,
        "distanciaProxima": 25.3
      },
      {
        "latitud": 160.4,
        "longitud": 160.4,
        "distanciaAnterior": 25.3,
        "distanciaProxima": 0
      }
    ]
  },
  {
    "transporte": "El tío Alberto",
    "tipo" : "particular",
    "subtipo" : "AUTOMOVIL",
    "combustible" : "GNC"
  },
  {
    "transporte": "Forest Gump",
    "tipo" : "ecologico",
    "subtipo" : "BICICLETA"
  }
]

trayectos.csv:
TrayId, MiembroDNI, Compartido, LatIni, LongIni,    LatFin, LongIni,    Tipo,       Atrb1,      Atrb2,      Periodo,  Fecha
1,      1,          0,          123.4,  123.4,      135.4,  135.4,      publico,    colectivo,  47,         "M",      "10/2020"
1,      1,          0,          135.4,  135.4,      146.4,  146.4,      publico,    tren,       sarmiento,  "M",      "10/2020"
2,      1,          2,          146.4,  146.4,      123.4,  123.4,      contratado, Taxi,       ,           "M",      "10/2020"
3,      2,          0,          200.4,  200.4,      205.4,  205.4,      ecologico,  bicicleta,  ,           "M",      "10/2020"
3,      2,          0,          205.4,  205.4,      300.9,  300.9,      particular, automovil,  GNC,        "M",      "10/2020"
4,      3,          0,          123.4,  123.4,      135.4,  135.4,      publico,    colectivo,  47,         "M",      "10/2020"
4,      3,          0,          135.4,  135.4,      146.4,  146.4,      publico,    tren,       sarmiento,  "M",      "10/2020"
0,      2,          2


Se recopila la información de cada archivo en repositorios runtime (almacenamos en memoria simulando DB).
Se obtiene la HC y el aporte de cada miembro o sector, filtrando los trayectos/mediciones según corresponda:
HCtotal(8140+22090+5150) = HCtotal(35380)
- Respecto a miembros:
  t1: 7x200 + 15x250 = 5150
  t2: 46x130 = 5980
  t3: 10x0 + 191x100 = 19100
  t4: 7x200 + 15x250 = 5150
  
  m1: (t1 + t2/2) / HCtotal = 8140/35380 = 23.01%
  m2: (t3 + t2/2) / HCtotal = 22090/35380 = 62.44%
  m3: t4 / HCtotal = 5150/35380 = 14.56%
  m4: 0 / HCtotal = 0/281 = 0%
- Respecto a sectores:
  s1: m1 y m2 => 85.45%
  s2: m3 y m4 => 14.56%
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