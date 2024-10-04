# Aplicación de Encuestas

## Descripción
Esta es una aplicación simple que permite realizar encuestas. Permite a los usuarios seleccionar entre 2 tipos de encuestas predefinidas, responder preguntas y guardar las respuestas en una base de datos MySQL.

## Características
- Interfaz gráfica de usuario intuitiva
- Soporte para múltiples encuestas
- Almacenamiento de respuestas en base de datos MySQL

## Requisitos
- Java JDK 8 o superior
- MySQL Server
- Conector MySQL para Java

## Configuración
1. Crear una base de datos MySQL llamada `encuestas_db`
2. Ejecutar el siguiente script SQL para crear las tablas necesarias:
3. Modificar la clase ConexionBD con los detalles de tu base de datos:
URL
Nombre de usuario
Contraseña
4. Uso
Seleccionar una encuesta del menú desplegable
Responder las preguntas que se muestran
Hacer clic en "Siguiente" para avanzar o "Finalizar" para terminar la encuesta

   ```sql
   CREATE TABLE encuestas (
       id INT AUTO_INCREMENT PRIMARY KEY,
       nombre VARCHAR(255) NOT NULL
   );

   CREATE TABLE preguntas (
       id INT AUTO_INCREMENT PRIMARY KEY,
       encuesta_id INT,
       pregunta TEXT,
       FOREIGN KEY (encuesta_id) REFERENCES encuestas(id)
   );

   CREATE TABLE respuestas (
       id INT AUTO_INCREMENT PRIMARY KEY,
       nombre_encuesta VARCHAR(255),
       pregunta TEXT,
       respuesta TEXT
   );
