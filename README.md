# Prueba Técnica – API Fibonacci

**API REST en Java 17 + Spring Boot 3.5.5 que calcula el n-ésimo número de Fibonacci, almacena resultados intermedios en una BD relacional (cache) y mantiene estadísticas de consultas. Proyecto pensado para ser claro, eficiente y testeable.**

-------

## Qué implementa (resumen técnico)

- **Endpoint principal**: GET /api/fibonacci/{n} → devuelve el valor de Fibonacci para n.

	- Devuelve JSON simple con el valor (ej.: 55).

- **Endpoint de estadísticas**: GET /api/fibonacci/stats → lista las entradas más consultadas.

- **Cache en BD relacional**: resultados intermedios se guardan en la tabla fibonacci_result para evitar recalcular valores ya resueltos.

- **Estadísticas**: cada consulta incrementa el contador en fibonacci_stat para saber qué n se consultan más.

- **Algoritmo**: enfoque iterativo y/o incremental: parte desde el mayor n cacheado y calcula solo los términos faltantes (evita recursión exponencial).

- **Tests**: unitarios (servicio), controller (MockMvc) y repository (@DataJpaTest con H2).

-------

## Tecnologías

- Java 17
- Spring Boot 3.5.5
- Spring Data JPA (ORM)
- H2 (base relacional en memoria) — usada como cache y para pruebas locales
- JUnit 5, Mockito (test)
- Maven
- Docker (imagen para despliegue)

###### Nota: H2 se usó por simplicidad y porque la consigna permite una BD relacional. En producción se recomienda PostgreSQL/MySQL para persistencia real.

-------

## Despliegue público

La aplicación fue dockerizada y desplegada en Render. URLs públicas:

- Estadísticas:
	- https://fibonacci-omz1.onrender.com/api/fibonacci/stats

- Cálculo:
	- https://fibonacci-omz1.onrender.com/api/fibonacci/{n}
	- ejemplo: https://fibonacci-omz1.onrender.com/api/fibonacci/10 → 55

###### Nota: la instancia en Render usa la configuración actual (H2 en memoria). Los datos (cache + stats) son volátiles y se pierden al reiniciar el contenedor. Para persistencia, cambiar spring.datasource a una BD externa. Tener en cuenta que Render en su version gratuita, deja en reposos los servicios mientras que no se utilizan, por lo que es posible que en el primer intento de consumir el sevricio, demore unos minutos en levantar la imagen y comenzar a responder los servicios. Se recomienda consumir el servicio desde un Navegador, para ver el proceso que ejecuta render al levantar los servicios.

-------

## Cómo probar / validar (local)

- **Correr localmente**: mvn spring-boot:run
- **Calcular un número**: curl http://localhost:8080/api/fibonacci/10
- **Ver estadísticas**: curl http://localhost:8080/api/fibonacci/stats
- **Inspeccionar BD** (H2 console, local)
	- **Abrir**: http://localhost:8080/h2-console
		- **JDBC URL**: jdbc:h2:mem:fibonacciDB — user sa, sin password
		- **Consultas útiles**:
			- SELECT * FROM fibonacci_result;
				- SELECT * FROM fibonacci_stat ORDER BY count DESC;
		
-------

## Tests & cobertura

- **Ejecutar**: mvn test


- **Se incluyen**:
	- **FibonacciServiceTes** (cache hit/miss, validaciones, stats)
		- **FibonacciControllerTest** (endpoints)
		- **FibonacciRepositoryIT** (@DataJpaTest con H2)
		
-------

## Notas Generales
- Diseño orientado a eficiencia: evita recomputar y opera incrementalmente usando la cache en BD.
- Tests cubren caminos felices, errores (valores inválidos) y la integración con JPA.
- Elección H2: simplifica evaluación y CI; se recomienda DB externa para producción real.
- Despliegue en Render mediante Docker; URLs públicas arriba.


