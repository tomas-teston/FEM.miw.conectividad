# Frontend para Móviles - Persistencia de datos
> En este proyecto se gestiona la autenticación, persistencia y conexión con servicios remotos.
> #### [Máster en Ingeniería Web por la Universidad Politécnica de Madrid (miw-upm)](http://miw.etsisi.upm.es)
> #### Asignatura: *FEM (Frontend para móviles)*

## Tecnologías necesarias
* Android
* GitHub
* Firebase
* Retrofit
* Glind

## Enunciado.

Antiguamente, los marinos de guardia se encargaban de registrar en cuadernos las incidencias que 
sucedían durante los largos viajes en alta mar y sus coordenadas. Estos cuadernos se guardaban en 
la “bitácora”, un mueble empernado donde estaban resguardados de las inclemencias de alta mar. Los 
cuadernos de bitácora han ido evolucionando hasta nuestros días. En la actualidad los dispositivos 
móviles se utilizan en el campo de la logística para gestionar la trazabilidad de la paquetería, su 
localización, su hora de entrega, su temperatura, la conformidad de la entrega (firma) y otras 
variables que se pueden registrar mediante los sensores integrados en el propio teléfono (cámara de 
fotos, GPS, brújula, temperatura, biosensores, etc).

La presente tarea pretende abarcar los diferentes conceptos y técnicas estudiados en la asignatura. 
Para conseguir este objetivo se propone al alumno el desarrollo de un Cuaderno de Bitácora en Android.

Se pide realizar una aplicación móvil para una empresa de repartos (p.ej. deliveroo, la casa del 
libro, amazon, reparto de helados, etc…) que gestione la trazabilidad de sus productos, entregas y 
que cubra la siguiente funcionalidad:

* __Autentificación de usuarios__, es decir, autenticar los repartidores de la empresa. Para ello se 
empleará el servicio [Firebase Authentication](https://firebase.google.com/docs/auth/).

* __Acceso a un servicio externo__ (api restful Web service) para la obtención de información relevante 
para la aplicación escogida (por ejemplo: lista de productos, lista de ciudades de reparto, etc…). 
Se sugiere utilizar [ANY-API](https://any-api.com/) o [Mashape](https://market.mashape.com/explore) 
para identificar apis sobre cualquier temática. Se recomienda utilizar las librerías Volley o Retrofit.

* Gestión de repartos y trazabilidad de entregas. Registrar fecha/hora de inicio del reparto, 
registrar las localizaciones intermedias del producto, fecha/hora de recogida/entrega, y registrar 
incidencias en la ruta. Para alojar esta información se utilizará 
[Firebase Realtime Database](https://firebase.google.com/products/database/). 
Adicionalmente se valorará positivamente el registro de imágenes en 
[Firebase Storage](https://firebase.google.com/products/storage/): por ejemplo el 
código de barras (id paquete), fotografías para reportar sobre un paquete defectuoso, un accidente, etc.

Se adjuntará un documento de máximo 2 páginas describiendo la solución propuesta.

Se propone la arquitectura básica que se ilustra en la figura 1:

[![Arquitectura básica propuesta](https://github.com/tomas-teston/FEM.miw.conectividad/blob/master/chart.png)](https://github.com/tomas-teston/FEM.miw.conectividad/blob/master/chart.png)

## Propuesta de aplicación desarrollada - Book shop.

### Descripción de la idea
Se realiza como ejemplo una aplicación que gestiona pedidos de ejemplares de libros de una librería. 
Esta idea está enfocada a priori a que solo exista un administrador de la aplicación (en este caso 
será un librero).

### Esquema de la arquitectura y 
A continuación se detalla la estructura que se ha seguido en la aplicación y su correspondiente 
ejemplo de utilización:

* __Login (página inicial)__: En esta ventana se podrá utilizar usuario y contraseña para acceder
a la aplicación. Para esta demo se puede utilizar las siguientes credenciales:

    * Usuario: demo@hotmail.com
    * Contraseña: demo1234
    Nota: Si el usuario y contraseña son incorrectos nos aparecerá el siguiente aviso:
    
    <img src="https://github.com/tomas-teston/FEM.miw.conectividad/blob/master/errorLogin.gif" alt="error en el login" width="350"/>
    
* __Registrar pedido de libros__: Para poder pedir libros, primero tenemos que hacer login en la 
plataforma. Después, introducimos título y/o autor y pulsamos el botón buscar. La aplicación
buscará en los servidores de libros de Google y los mostrará en un listado. Debajo de cada libro, 
aparece un botón "pedir" que nos permite realizar pedidos de dicho ejemplar, nos aparecerá una
ventana "pop up" donde tendremos que introducir cuantos ejemplares queremos. Por tanto, los pasos
a seguir son:

    1- Login: Introducir usuario y contraseña.
    
    2- Buscar libros: Introducimos título y/o autores del libro que buscamos:
    
    <img src="https://github.com/tomas-teston/FEM.miw.conectividad/blob/master/buscarLibros.gif" alt="buscar libros" width="350"/>
    
    3- Pulsamos el botón "pedir": Debajo de cada libro existe un botón con el que podemos pedir
    un número de ejemplares.
    
    <img src="https://github.com/tomas-teston/FEM.miw.conectividad/blob/master/realizarPedido.gif" alt="pedir libros" width="350"/>
    
* __Seguimiento de pedidos y reclamaciones__: Cuando se realiza un pedido, la aplicación guarda un 
registro donde nos permite ver el seguimiento del mismo, como la fecha en la que se hizo o el estado
del mismo. Para acceder a este listado, pulsamos sobre el botón que aparece arriba a la derecha del 
todo (icono un papel con una lupa encima) y accedemos a la ventana que nos muestra dicho listado.
En este listado podemos realizar una reclamación. Para ello, tendremos que pulsar sobre el botón 
"Añadir foto relamación" y seleccionar una foto en el que aparezca el libro deteriorado. Este proceso
coloca el estado en modo relamación de forma automática.

    * Demostracción de acceso al listado:
    
    <img src="https://github.com/tomas-teston/FEM.miw.conectividad/blob/master/accesoListadoPedidos.gif" alt="acceso listado de libros pedidos" width="350"/>
    
    * Demostracción de creación de reclamación.
    
    <img src="https://github.com/tomas-teston/FEM.miw.conectividad/blob/master/registroReclamacion.gif" alt="abrir una reclamación" width="350"/>
    
    
    
    







