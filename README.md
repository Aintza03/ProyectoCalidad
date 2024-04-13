
Maven-Client-ProjectCalidad
========================================================================================================================================

Si es la primera vez que se ejecuta el programa en este ordenador primero hay que abrir el workbench de MySQL y desde el root ejecutar todas las sentencias MySQL del fichero "create-ikea.sql". El fichero se encuentra dentro del proyecto en la carpeta "sql".

En cualquier caso, sea la primera vez que se ejecuta en este ordenador o no habría que ejecutar (En cmd):

1-Clean limpia las clases anteriormente compiladas.  

      mvn clean

2-Compila todo de nuevo.

      mvn compile

2-Crea la base de datos ikeadb con sus entidades que se crean en base a los JDO.

      mvn datanucleus:schema-create

3-Pone en marcha el servidor (Puedes saltarle los pasos anteriores si ya tienes el codigo compilado y no has hecho ningun cambio en el codigo de antes)

      mvn jetty:run

4-Ejecuta el cliente en un nuevo cmd (Se puede ejecutar más de un cliente a la vez pero siempre que se quiera ejecutar un cliente hay que esperar a que el servidor este completamente operativo)

      mvn exec:java -Pclient

Nota: Para detener el servidor o el cliente hay que pulsar **Ctrl+C** y introducir una **S**. No obstante, el cliente tambien se puede detener cerrando todas las ventanas.

