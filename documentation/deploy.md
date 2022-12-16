# Instructivo para realizar updates en la aplicación
El repositorio se organiza con dos ramas principales permanentes: main y deploy. La rama main representa el ambiente de desarrollo, que siempre se correrá de forma local, y la rama deploy representa el ambiente productivo, y ahí es donde tiene utilidad este documento.

__Nota:__ Tener en cuenta que pueden haber más ramas en algún momento, pero estás ramas siempre deberán tener un tiempo de vida corto y deberán estar asociadas a una iteración particular del producto.

La aplicación desarrollada se encuentra deployada en los servidores de la facultad (UTN FRBA) a través de una PaaS (Platform as a Service) open source llamada Dokku.

Antes de poder hacer actualizaciones en la aplicación, hay que tener un servidor disponible de la facultad y establecer la conexión de forma segura. Para lograr esto, hay que generar una clave pública y privada y, luego, solicitar una aplicación. Cuando la petición sea aprobada, habrá que compartir la clave pública de forma que desde los servidores puedan autenticar los request y, entonces, detectar solicitudes inválidas y/o malisiosas.

Por lo que sigue, hay que hacer que las terminales encripten los mensajes hacia los destinos. Para esto, actualizaremos o cambiaremos el archivo ```~/.ssh/config```.

Por cada conexión, completaremos lo siguiente:
~~~
#Comentario (opcional)
Host [hosturi]
    HostName [hostname]
	PreferredAuthentications publickey
	IdentityFile ~/.ssh/[private_key_file_name]
~~~

[hosturi]: para el caso de __Dokku__, es `190.105.205.115`. Si es __GitHub__, `github.com`.
[hostname]: en el caso de __Dokku__ no hace falta, se puede eliminar la línea.
[private_key_file_name]: es el nombre del archivo donde está la clave privada. Si no se encuentra en `~/.ssh` se puede cambiar, pero no es recomendable.

Finalmente, hay que asociar los push de la rama deploy con los disparadores de despliegue. Para esto, utilizamos:

```powershell
git remote add dokku ....
```