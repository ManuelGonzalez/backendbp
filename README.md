# Backend para Gestión de Clientes y Movimientos Bancarios

## Descripción

Este proyecto es una API REST construida con Spring Boot que permite gestionar clientes y sus movimientos bancarios. La base de datos es auto-generativa, lo que significa que las tablas y relaciones se crean automáticamente al iniciar la aplicación.

## Funcionalidades

- Crear un nuevo cliente
- Actualizar datos de un cliente existente
- Eliminar un cliente
- Listar todos los clientes
- Crear un nuevo movimiento bancario
- Listar movimientos de un cliente en un rango de fechas

## Prerrequisitos

- Java 8 o superior
- Maven
- PostgreSQL

## Configuración de la Base de Datos

Antes de ejecutar la aplicación, necesitas crear una base de datos en PostgreSQL. La estructura de las tablas y relaciones se creará automáticamente al iniciar la aplicación.

1. Accede a PostgreSQL y crea una nueva base de datos:

   ```sql
   CREATE DATABASE datadpharma;
   ```
   
   2. Abre el archivo `application.properties` y modifica las siguientes propiedades:

   spring.datasource.url=jdbc:postgresql://localhost:5432/your_database_name
   spring.datasource.username=your_postgresql_username
   spring.datasource.password=your_postgresql_password
   spring.jpa.hibernate.ddl-auto=update

## Cómo Ejecutar el Proyecto

1. Clona el repositorio:
    ```
    git clone https://github.com/ManuelGonzalez/backendbp
    ```
2. Navega al directorio del proyecto:

    ```
    cd your-project-name
    ```
   
Compila y empaqueta el proyecto con Maven:

    ```
    mvn clean package
    ```

3. Ejecuta la aplicación:

    ```
    java -jar target/BackendBP-0.0.1-SNAPSHOT.jar
    ```

4. Alternativamente, puedes ejecutar la aplicación directamente usando Maven:

    ```
    mvn spring-boot:run
    ```

5. Una vez que la aplicación esté en ejecución, puedes acceder a la API a través de:

arduino
Copy code
http://localhost:8080/
(reemplaza 8080 con el puerto configurado en tu application.properties o application.yml si es diferente)

Documentación de la API
./src/main/resources/Backend - BP.postman_collection.json

Contacto
Si tienes alguna pregunta o comentario, por favor contacta a Manuel Gonzalez a través de m3gonzalez.cl@gmail.com.


