# 🚀 Automatización de Pruebas con Serenity BDD & Grafana

Este proyecto es un framework de automatización de pruebas de interfaz de usuario (UI) utilizando el patrón **Screenplay** con Serenity BDD. Además, incluye una integración en tiempo real con **InfluxDB** y **Grafana** para el monitoreo de resultados.

## 🛠️ Tecnologías Utilizadas
* **Lenguaje:** Java 17
* **Framework de Pruebas:** Serenity BDD
* **Patrón de Diseño:** Screenplay
* **Gestor de Dependencias:** Maven
* **Base de Datos:** InfluxDB (Docker)
* **Visualización:** Grafana (Docker)
* **Pipeline:** GitHub Actions

## 📋 Requisitos Previos
Antes de ejecutar el proyecto, asegúrate de tener:
1. [JDK 17+](https://www.oracle.com/java/technologies/downloads/)
2. [Maven](https://maven.apache.org/download.cgi)
3. [Docker Desktop](https://www.docker.com/products/docker-desktop/)

## 🚀 Configuración del Entorno
1. **Levantar contenedores de Monitoreo:**
   ```bash
   docker-compose up -d

2- Acceder a las herramientas:

Grafana: http://localhost:3000 (Usuario: admin, Clave: admin)

InfluxDB: http://localhost:8086

🏃 Ejecución de Pruebas
Puedes ejecutar los tests localmente usando Maven.

Ejecutar todos los tests:

 ```Bash
mvn clean verify
```
Ejecutar por Tags (Ejemplo Login):
 ```Bash
mvn clean verify -Dcucumber.filter.tags="@Login"
 ```
📈 Dashboard de Resultados
Los resultados se envían automáticamente a InfluxDB. En Grafana, puedes visualizar:

Tiempo de ejecución por prueba.

Historial de éxitos y fallos (SUCCESS/FAILURE).

Detalle de cada escenario ejecutado.

⚙️ CI/CD (GitHub Actions)
Este proyecto cuenta con un Pipeline dinámico. Al ejecutar el workflow desde la pestaña Actions, podrás seleccionar qué Tag de Cucumber deseas ejecutar antes de disparar el proceso.

Creado por [Jhon Alejandro Tobon Arias] - QA Automation Engineer

