# Kevops Academy - Microservicio ZIPI

## Introducción

Ejemplo de microservicio para el path formativo Kevops Academy

## Requisitos

- Máquina Linux/Mac. Windows: máquina virtual con Ubuntu (ejemplo: [multipass](https://multipass.run/docs/installing-on-windows))
- [Docker](https://docs.docker.com/engine/install/ubuntu/) + [Docker Compose](https://docs.docker.com/compose/install/) / [Rancher Desktop](https://docs.rancherdesktop.io/getting-started/installation/)
- Bash
- Java 11
- Maven

## Uso

```shell
pedro.rodriguez@pedro-kairos:~/workspace/kevops-acme/zipi$ bin/devcontrol.sh
Kevops Academy - Zipi (c) 2022

Usage:
    - bin/devcontrol.sh help            # This information page
    - bin/devcontrol.sh build-docker    # Build zipi docker image
    - bin/devcontrol.sh build-jar       # Build zipi app jar package
    - bin/devcontrol.sh compile         # Compile zipi app
    - bin/devcontrol.sh deploy          # Deploy to production
    - bin/devcontrol.sh destroy         # Production environment destroy
    - bin/devcontrol.sh logs            # Show logs
    - bin/devcontrol.sh run-bash-linter # Run bash linter
    - bin/devcontrol.sh start           # Production environment start
    - bin/devcontrol.sh status          # Production environment status
    - bin/devcontrol.sh stop            # Production environment stop
    - bin/devcontrol.sh test            # Test zipi app

Use 'bin/devcontrol.sh help <action>' to display his help (e.g. bin/devcontrol.sh help build-docker)
```

Para desarrollar / probar en local:

- Preparamos archivo `.env`:

```shell
$ cp .env.dist .env
```

- Levantamos la base de datos Postgres de test con docker-compose

```shell
pedro.rodriguez@pedro-kairos:~/workspace/kevops-acme/zipi$ docker-compose -f docker-compose.test.yaml up -d
Creating network "zipi_default" with the default driver
Creating zipi_db_1 ... done
```

- Arrancamos la aplicaciíon Springboot

```shell
$ SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/zipi mvn spring-boot:run
WARNING: An illegal reflective access operation has occurred
WARNING: Illegal reflective access by com.google.inject.internal.cglib.core.$ReflectUtils$1 (file:/usr/share/maven/lib/guice.jar) to method java.lang.ClassLoader.defineClass(java.lang.String,byte[],int,int,java.security.ProtectionDomain)
WARNING: Please consider reporting this to the maintainers of com.google.inject.internal.cglib.core.$ReflectUtils$1
WARNING: Use --illegal-access=warn to enable warnings of further illegal reflective access operations
WARNING: All illegal access operations will be denied in a future release
[WARNING] 
[WARNING] Some problems were encountered while building the effective settings
[WARNING] Unrecognised tag: 'url' (position: START_TAG seen ...</id>\n           <url>... @8:17)  @ /home/pedro.rodriguez/.m2/settings.xml, line 8, column 17
[WARNING] 
[INFO] Scanning for projects...
[INFO] 
[INFO] ---------------------------< com.acme:zipi >----------------------------
[INFO] Building zipi 0.0.1-SNAPSHOT
[INFO] --------------------------------[ jar ]---------------------------------

[...]

2022-04-23 19:16:08.710 DEBUG 389509 --- [  restartedMain] .m.m.a.ExceptionHandlerExceptionResolver : ControllerAdvice beans: 0 @ExceptionHandler, 1 ResponseBodyAdvice
2022-04-23 19:16:08.821  INFO 389509 --- [  restartedMain] o.s.b.d.a.OptionalLiveReloadServer       : LiveReload server is running on port 35729
2022-04-23 19:16:08.860  INFO 389509 --- [  restartedMain] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8080 (http) with context path ''
2022-04-23 19:16:08.882  INFO 389509 --- [  restartedMain] com.acme.zipi.ZipiApplication            : Started ZipiApplication in 4.867 seconds (JVM running for 5.488)
```

El proceso se queda arrancado. Podemos probar abriendo un navegador y poniendo la url <http://localhost:8080>, o ejecutando en otra consola:

```shell
$ curl localhost:8080; echo
Hello World!
$
```
