
Maven-Client-ProjectCalidad
========================================================================================================================================
### Obtener la documentación
Para acceder a la documentación primero hay que acceder a la carpeta  *../src/main/resources* donde se encuentra el fichero *Doxyfile*. Después, hay que escribir el siguiente comando:

      doxygen
Entonces, doxygen creara la documentación en formato html en la carpeta  *target/doxygen/* .

Nota: Para que la documentación sea generada tiene que existir la carpeta target. Es decir, que si se ha ejecutado ***mvn clean*** habra desaparecido la carpeta target y por lo tanto habrá que ejecutar ***mvn compile*** para crearla de nuevo.

### Ejecutar Test

Para ejecutar los test hace falta que se ejecute:  

      mvn test
Nota: Para que funcione bien primero habría que ejecutar ***mvn clean***, sino como las classes habran pasado por ***mvn datanucleus:enhance***, mockito mostraria codigo que ha insertado datanucleus en nuestra clase como **NO TESTADO**. Para ver la cobertura de los test hay que hacer click en  *index.html*  que se encuentra en  *target>site>jacoco* 

### Ejecutar el programa por primera vez

Si es la primera vez que se ejecuta el programa en este ordenador primero hay que abrir el workbench de MySQL y desde el root ejecutar todas las sentencias MySQL del fichero "create-ikea.sql". El fichero se encuentra dentro del proyecto en la carpeta "sql".

En caso de haberlo hecho así tambien habra que añadir ciertos datos. Para ello habra que seguir los siguientes pasos:
1-Clean limpia las clases anteriormente compiladas.  

      mvn clean

2-Compila todo de nuevo.

      mvn compile

3-Para crear la base de datos primero hay que hacer enhance.  

      mvn datanucleus:enhance

4-Despues, el siguiente comando termina de crear la base de datos ikeadb con sus entidades que se crean en base a los JDO.

      mvn datanucleus:schema-create

5-Por ultimo hay que ejecutar el comando ***pruebas*** que carga datos en el servidor

      mvn exec:java -Ppruebas

6-Pone en marcha el servidor (Puedes saltarle los pasos anteriores si ya tienes el codigo compilado y no has hecho ningun cambio en el codigo de antes)

      mvn jetty:run

7-Ejecuta el cliente en un nuevo cmd (Se puede ejecutar más de un cliente a la vez pero siempre que se quiera ejecutar un cliente hay que esperar a que el servidor este completamente operativo)

      mvn exec:java -Pclient

Nota: Para detener el servidor o el cliente hay que pulsar **Ctrl+C** y introducir una **S**. No obstante, el cliente tambien se puede detener cerrando todas las ventanas.

### Ejecutar Programa a partir de la primera vez

Habría que ejecutar (En cmd):

1-Clean limpia las clases anteriormente compiladas.  

      mvn clean

2-Compila todo de nuevo.

      mvn compile

3-Para crear la base de datos primero hay que hacer enhance.  

      mvn datanucleus:enhance

4-Despues, el siguiente comando termina de crear la base de datos ikeadb con sus entidades que se crean en base a los JDO.

      mvn datanucleus:schema-create

5-Pone en marcha el servidor (Puedes saltarle los pasos anteriores si ya tienes el codigo compilado y no has hecho ningun cambio en el codigo de antes)

      mvn jetty:run

6-Ejecuta el cliente en un nuevo cmd (Se puede ejecutar más de un cliente a la vez pero siempre que se quiera ejecutar un cliente hay que esperar a que el servidor este completamente operativo)

      mvn exec:java -Pclient

Nota: Para detener el servidor o el cliente hay que pulsar **Ctrl+C** y introducir una **S**. No obstante, el cliente tambien se puede detener cerrando todas las ventanas.

### Ejecutar Test de Integración
Al contrario que en los test unitarios en estos si que hay que hacer ***datanucleus enhance*** antes de probarlos.
Los primeros pasos son:

      mvn compile
      mvn datanucleus:enhance
      mvn datanucleus:schema-create

Estos pasos preparan el programa para ejecutar los test de integración. Por ultimo, hay que ejecutar el siguiente comando relacionado a un perfil que se encarga de ejecutar los test de integración.

      mvn verify -Pintegration-tests


### Ejecutar Test de Rendimiento
Se ejecutan de manera muy parecida a los test de integración.
Los primeros pasos son:

      mvn compile
      mvn datanucleus:enhance
      mvn datanucleus:schema-create

Por ultimo, hay que ejecutar el siguiente comando relacionado a un perfil que se encarga de ejecutar los test de rendimiento.

      mvn verify -Pperformance-tests
      
Nota: Para ver las graficas de los performance test hay que hacer click en  *report.html*  que esta en *target>junitperf*







