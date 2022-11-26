# Set de pruebas Diseño de Sistemas

Este documento sirve para mostrar el set de pruebas correspondientes a la entrega del TP Mi Huella de la asignatura Diseño de Sistemas.

## Datos Iniciales


## Pruebas Dinámicas

### 1. Seguridad

### 2. Creación y asignación de organizaciones y miembros

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

### 3. Asignar mediciones a la huella
En este punto se requiere agregar mediciones a la organización. Para poder autenticarse y que el servidor autorice la solicitud, se deberá pasar un header extra 'Authorization' con el id del usuario.
~~~
POST -> /api/organizacion/:id_organizacion/batch
Header: Authorization -> :id_usuario
~~~
```json
{
    "mediciones": [
        {
            "categoria": {
                "actividad": "Combustion Fija",
                "tipoConsumo": "Diesel"
            },
            "unidad": "lt",
            "valor": 1.2,
            "periodo": {
                "periodicidad": "M",
                "anio": 2022,
                "mes": 9
            }
        },
        {
            "categoria": {
                "actividad": "Electricidad",
                "tipoConsumo": "Electricidad"
            },
            "unidad": "kw",
            "valor": 2,
            "periodo": {
                "periodicidad": "M",
                "anio": 2022,
                "mes": 10
            }
        },
        {
            "categoria": {
                "actividad": "Combustion Movil",
                "tipoConsumo": "Gasoil"
            },
            "unidad": "lt",
            "valor": 3.2,
            "periodo": {
                "periodicidad": "A",
                "anio": 2022,
                "mes": 11
            }
        }
    ]
}
```

Con la nueva información, se procede a obtener la huella de carbono de la organización. Notese que las mediciones varían en período y no todas terminan perteneciendo al período de interés; se tomó esta decisión para mostrar que se toman en cuenta sólo los medibles del período solicitado.

A diferencia de los pasos anteriores, esta prueba se desarrolla a través de las pantallas. El proceso entonces es: iniciar sesión en `/home`, ir a la sección de reportes, elegir un período y disparar la consulta.
 
### 4. A esa a esa misma organización agregarle una persona
Por último, se agrega otro miembro a la organización. Esto se hace por API.

~~~
POST -> /api/organizacion/:id_organizacion/sector/:id_sector/miembro
Header: Authorization -> :id_usuario
~~~

### 5. A otra organizacion que ya tenga mediciones agregar un batch de mediciones, hacer consultas por la API y calcular huella de carbono

### 6. Verificar que si una persona ya forma parte de la organización, si se agrega en otro sector, el sistema no lo permite

### 7. Modificar uno de los parámetros / pesos y ver cómo se modifica la huella de carbono

### 8. Cambiar / agregar trayectos y ver cómo se modifica la huella de carbono

### 9. Hacer que 2 miembros que tenían un recorrido distinto, ahora compartan un trayecto


