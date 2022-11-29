# Set de pruebas Diseño de Sistemas

Este documento sirve para mostrar el set de pruebas correspondientes a la entrega del TP Mi Huella de la asignatura Diseño de Sistemas.

## Datos Iniciales

Se definieron un conjunto de datos iniciales a partir de los cuales se pueden desarrollar las pruebas. Estos contienen:

- 3 usuarios
    - Se crearon usuarios iniciales para las tres organizaciones iniciales que se detallarán más adelante, para los agentes sectoriales _Carlos_ y _Esteban_ y para los miembros _manu_ (Ginobili), _fito_ (Páez) y _diego_ (Maradona).
- 3 organizaciones y cada organización con al menos 3 sectores
    - UTN - Campus. Sectores: Sistemas, Capital Humano y Administración.
    - UTN - Medrano. Sectores: Tesorería, Administración y Mantenimiento.
    - McDonald's del obelísco: Tesorería, Administración y Mantenimiento.
- Cada sector con al menos 3 empleados (son muchos, así que no vale la pena enumerarlos)
- Al menos 3 tipos de transporte distinto y al menos 2 colectivos con más de 4 paradas
    - Transportes: el colectivo 109, el colectivo 63, el Subte A, un taxi, dos autos particulares y una bici.
- De los empleados,
    - al menos 3 que no tengan cargado ningún trayecto: Carlos Bilardo, Lionel Messi, Diego Maradona, etc.
    - uno que llegue solo en bici: Alejandro Gómez.
    - uno que llegue solo en auto particular: Emanuel Ginobili.
    - uno que llegue en colectivo y caminando: Carlos García.
    - uno que llegue en auto y colectivo: Juan Pérez.
    - dos grupos con trayectos compartidos. El primero de 2 miembros (Carlos Bianchi y Martín Palermo) y el segundo de 3 miembros (Walter White, Jesse Pinkman y Saul Goodman).
    - al menos 2 personas tienen que ser miembros de 2 organizaciones distintas.
        - Diego Maradona: sector _Administración_ de _UTN - Campus_ y sector _Administración_ de _UTN - Medrano_.
        - Albert Einstein: sector _Mantenimiento_ de _UTN - Medrano_ y sector _Administración_ de _McDonald's_.
- Una organización tiene que tener cargados miembros y mediciones
dichas mediciones, al menos 20, tienen que tener al menos 3 tipos distintos
    - La organización con mediciones precargadas es _UTN - Campus_. Entre los tipos distintos, se pueden mencionar _Combustión Fija_, _Combustión Movil_ y _Electricidad_.
- La huella de carbono ya calculada: (poder explicar pq dio eso, algo que ayuda es cuando en el reporte, aparece lo que se fue sumando)
- Tener un json/csv listo para cargar un batch de mediciones, para cargar por API (tienen que saber cuanto es el resultado final)
    ```json
    {
        "mediciones": [{
            "categoria": { "actividad": "Combustion Fija", "tipoConsumo": "Diesel" },
            "unidad": "lt",
            "valor": 1.2,
            "periodo": { "periodicidad" : "M", "anio" : 2022, "mes" : 9 }
        },
        {
            "categoria": { "actividad": "Electricidad", "tipoConsumo": "Electricidad" },
            "unidad": "kw",
            "valor": 2,
            "periodo": { "periodicidad" : "M", "anio" : 2022, "mes" : 10 }
        },
        {
            "categoria": { "actividad": "Combustion Movil", "tipoConsumo": "Gasoil" },
            "unidad": "lt",
            "valor": 3.2,
            "periodo": { "periodicidad" : "A", "anio" : 2022, "mes" : 11 }
        }]
    }
    ```

## Pruebas Dinámicas

### 1. Seguridad

### 2.1. Creación y asignación de organizaciones y miembros

Se crea una organización Coca Cola. El objeto JSON que se envía a través del boby tiene dos partes:
- Información de la entidad: se define la razón social de la organización así como la ubicación y los sectores que tiene. A cada sector, además, se le pueden asignar los empleados que tiene directamente, si bien existe la posibilidad de agregarlos o eliminarlos mas adelante.
- Información del usuario: la creación de una organización no sirve de nada sin un usuario en el sistema que permita que las operaciones se autoricen adecuadamente.

En este caso, se crea una empresa inicializada con un sector Entretenimiento y con un empleado asignado a ese sector.

**Importante**: El empleado nunca se crea en el momento, debe estar previamente cargado en el sistema.

~~~
POST -> /api/organizacion
~~~
```json
{
    "usuario": {
        "username": "nombre_usuario",
        "password": "contraseña"
    },
    "entidad": {
        "nombre": "Coca Cola",
        "ubicacion": {
            "direccion": {
                "municipio": "CIUDAD DE BUENOS AIRES",
                "localidad": "VILLA CRESPO",
                "calle": "Maestro Marcelo Lopez",
                "numero": 10
            },
            "coordenadas": {
                "latitud": 201.4123,
                "longitud": 201.4323
            }
        },
        "clasificacion": "Empresa",
        "tipo": "EMPRESA",
        "sectores": [
            {
                "nombre": "Entretenimiento",
                "miembros": [
                    {
                        "tipoDocumento": "DNI",
                        "documento": 34432237
                    }
                ]
            }
        ]
    }
}
```

La respuesta del servidor contendrá el resultado de la solicitud. En caso de éxito, los id del usuario y la organización creadas; en caso de error, la descripción del mismo.

### 2.2. Asignar mediciones a la huella
En este punto se requiere agregar mediciones a la organización. Para poder autenticarse y que el servidor autorice la solicitud, se deberá pasar un header extra 'Authorization' con el id del usuario.
~~~
POST -> /api/organizacion/:id_organizacion/batch
Header: Authorization -> :id_usuario (organización Coca Cola)
~~~
```json
{
    "mediciones": [{
        "categoria": { "actividad": "Combustion Fija", "tipoConsumo": "Diesel" },
        "unidad": "lt",
        "valor": 1.2,
        "periodo": { "periodicidad" : "M", "anio" : 2022, "mes" : 9 }
    },
    {
        "categoria": { "actividad": "Electricidad", "tipoConsumo": "Electricidad" },
        "unidad": "kw",
        "valor": 2,
        "periodo": { "periodicidad" : "M", "anio" : 2022, "mes" : 10 }
    },
    {
        "categoria": { "actividad": "Combustion Movil", "tipoConsumo": "Gasoil" },
        "unidad": "lt",
        "valor": 3.2,
        "periodo": { "periodicidad" : "A", "anio" : 2022, "mes" : 11 }
    }]
}
```

Con la nueva información, se procede a obtener la huella de carbono de la organización. Nótese que las mediciones varían en período y no todas terminan perteneciendo al período de interés; se tomó esta decisión para mostrar que se toman en cuenta sólo los medibles del período solicitado. El período es el año 2022.

A diferencia de los pasos anteriores, esta prueba se desarrolla a través de las pantallas. El proceso entonces es: iniciar sesión en `/home`, ir a la sección de reportes, elegir un período y disparar la consulta.

Para el período anual 2022, el resultado esperado del cálculo es:
- Medición 1: DA: 1.2 y FE: 2.0 => Consumo: 2.4
- Medición 2. DA: 2.0 y FE: 4.0 => Consumo: 8.0
- Medición 3: DA: 3.2. FE: 3. => Consumo: 9.6

Total: 20.0

Para los períodos mensuales 9/2022, 10/2022 y 11/2022, el valor es igual al consumo de huella en de la medición que corresponda. Para los períodos que no contienen mediciones, el consumo de huella vale 0.
 
### 2.3. A esa a esa misma organización agregarle una persona
Por último, se agrega otro miembro a la organización. Esto se hace por API. Para mostrar que puede realizarse todo desde 0, vamos a asignarle un miembro nuevo. Por eso, realizamos un request previo que sirve para crear ese miembro.

~~~
POST -> /api/miembro
~~~

```json
{
    "usuario": {
        "username": "nombre_usuario",
        "password": "contraseña"
    },
    "entidad": {
        "nombre" : "Damián",
        "apellido" : "Weigandt",
        "tipoDocumento" : "DNI",
        "documento" : 87644332
    }
}
```

~~~
POST -> /api/organizacion/:id_organizacion/sector/:id_sector/miembro
Header: Authorization -> :id_usuario (organización Coca Cola)
~~~

```json
{
    "tipoDocumento" : "DNI",
    "documento" : 87644332
}
```

### 3. A otra organizacion que ya tenga mediciones agregar un batch de mediciones, hacer consultas por la API y calcular huella de carbono

Para este punto, se elige a la organización _UTN - Campus_ como víctima para agregar más mediciones.

~~~
POST -> /api/organizacion/:id_organizacion/batch
Header: Authorization -> :id_usuario (organización UTN - Campus)
~~~
```json
{
    "mediciones": [{
        "categoria": { "actividad": "Combustion Fija", "tipoConsumo": "Gas Natural" },
        "unidad": "m3",
        "valor": 5.2,
        "periodo": { "periodicidad" : "M", "anio" : 2022, "mes" : 9 }
    }, {
        "categoria": { "actividad": "Electricidad", "tipoConsumo": "Electricidad" },
        "unidad": "kw",
        "valor": 3.6,
        "periodo": { "periodicidad" : "M", "anio" : 2022, "mes" : 10 }
    }, {
        "categoria": { "actividad": "Combustion Fija", "tipoConsumo": "Diesel" },
        "unidad": "kw",
        "valor": 2.1,
        "periodo": { "periodicidad" : "M", "anio" : 2022, "mes" : 10 }
    }, {
        "categoria": { "actividad": "Combustion Movil", "tipoConsumo": "Gasoil" },
        "unidad": "lt",
        "valor": 0.4,
        "periodo": { "periodicidad" : "A", "anio" : 2022, "mes" : 11 }
    }]
}
```

La HC deberia dar 57,4(32,4 del batch anterior + 25 de este) 

### 4. Verificar que si una persona ya forma parte de la organización y se intenta agregar en otro sector, el sistema expone el error

Para graficar este ejemplo, se elije al miembro _fito_ que ya es empleado en _UTN - Medrano_ en el sector de Tesorería. Cuando se intente asignarle un trabajo nuevo a fito en el sector de _Administración_ en esa misma organización, el sistema no lo permitirá.

~~~
POST -> /api/organizacion/:id_organizacion/sector/:id_sector/miembro
Header: Authorization -> :id_usuario (organización UTN - Medrano)
~~~

```json
{
    "tipoDocumento" : "DNI",
    "documento" : 34432237
}
```

### 5. Modificar uno de los parámetros / pesos y ver cómo se modifica la huella de carbono

En este caso, se elije cambiar el factor de emisión de un medible de tipo trayecto, en particular, el generado por los trasportes particulares que usan como combustible el Gasoil.

```json
{
    "categoria": {
        "actividad": "Traslado de Miembros",
        "tipoConsumo": "Particular - GASOIL"
    },
    "unidad": "km",
    "valor": 10
}
```

Dentro de la organizacion _UTN - Campus_, lo usa Ginobili, por lo tanto impactará en la huella de carbono que este genera. A partir del cambio, su impacto se reducirá 8 veces, ya que pasa de 80 a 10 el valor del parámetro. El impacto el la HC de la organización pasará a ser 85.15201. REVISAR

### 6. Cambiar / agregar trayectos y ver cómo se modifica la huella de carbono

Desde la aplicación web, hay que le agregarle a Manu un trayecto en el colectivo 109 desde Av. Córdoba 1531 hasta Av. Córdoba 3800. Entonces, si el medible se desarrollara en el mes de noviembre del 2022, por ejemplo, el impacto de la huella en Campus en el período anual del 2022 aumentaría.

El valor de este trayecto se verá afectado por dos variables: el dato de la actividad y el factor de emisión. El factor de emisión está previamente cargado, pero el dato de actividad se obtiene desde la API Distacias que es externa. De está manera, el impacto real no se puede preveer y el correcto cálculo se validará en el momento.

El cálculo de la API se debería realizar así, tomando el caso del período 2022:
- `+` Impacto del trayecto de Juan Pérez
    1. Viaje en auto: DA * 130
    2. Viaje en colectivo: (Distancia = 0.6 + 1.3 + 0.95 + 1.1) 3.95 * 200 = 790
    3. Viaje en subte: (Distancia = 1.3 + 1.5 + 0.6 + 0.5) 3.9 * 125 = 487.5
    4. Viaje caminando: DA * 0 = 0
    - Total: 1277.5 + (DA auto * 130)
- `+` Impacto del trayecto anterior de Manu Ginobili:
    1. Viaje en auto: DA * 130
    - Total: (DA auto * 130)
- `+` Impacto del trayecto del Papu Gómez:
    1. Viaje en bici: DA * 0
    - Total: 0
- `+` Impacto de las mediciones:
    1. Combustion Fija + Gas Natural    -> 3 * 1    = 3
    2. Combustion Fija + Gas Natural    -> 5 * 1    = 3
    3. Electricidad + Electricidad      -> 4.7 * 4  = 18.8
    4. Electricidad + Electricidad      -> 10 * 4   = 40
    5. Combustion Fija + Diesel         -> 4 * 2    = 8
    6. Electricidad + Electricidad      -> 22 * 4   = 88
    7. Combustion Movil + Gasoil        -> 2.2 * 3  = 6.6
    8. Combustion Fija + Gas Natural    -> 4.6 * 1  = 4.6
    9. Combustion Fija + Gas Natural    -> 2 * 1    = 2
    10. Electricidad + Electricidad     -> 4 * 4    = 16
    11. Electricidad + Electricidad     -> 14 * 4   = 56
    12. Combustion Fija + Diesel        -> 4.7 * 2  = 9.4
    13. Electricidad + Electricidad     -> 23.6 * 4 = 94.4
    14. Combustion Movil + Gasoil       -> 24 * 3   = 72
    - Total: 423.8
- `+` Impacto del nuevo trayecto de Manu:
    1. Viaje en colectivo: (Distancia = 1.2 + 0.5 + 0.6) 2.3 * 200 = 460.0
    - Total: 460.0

En resumen, el nuevo impacto es superior en 460 unidades.

### 7. Hacer que 2 miembros que tenían un recorrido distinto, ahora compartan un trayecto


