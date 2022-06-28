# Kevops Academy - Microservicio ZIPI

## Introducción

Ejemplo de microservicio para el path formativo Kevops Academy

## Requisitos

- Máquina Linux/Mac. Windows: máquina virtual con Ubuntu (ejemplo: [multipass](https://multipass.run/docs/installing-on-windows))
- [Docker](https://docs.docker.com/engine/install/ubuntu/) + [Docker Compose](https://docs.docker.com/compose/install/) / [Rancher Desktop](https://docs.rancherdesktop.io/getting-started/installation/)
- Bash
- Java 11
- Maven

Para generar versiones semánticas:

- [Conventional Changelog](https://www.npmjs.com/package/conventional-changelog)

## Uso

```shell
$ bin/devcontrol.sh
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

- Levantamos la base de datos Postgres de test con docker-compose, y anotamos el puerto expuesto, en este caso el 49180

```shell
$ docker-compose -f docker-compose.test.yaml up -d
Creating network "zipi_default" with the default driver
Creating zipi_db_1 ... done
$ docker-compose -f docker-compose.test.yaml ps
  Name                 Command                      State                             Ports                   
--------------------------------------------------------------------------------------------------------------
zipi_db_1   docker-entrypoint.sh postgres   Up (health: starting)   0.0.0.0:49180->5432/tcp,:::49180->5432/tcp
```

- Arrancamos la aplicaciíon Springboot con el puerto 49180

```shell
$ SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:49180/zipi mvn spring-boot:run
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

## Changelog

Para mantener el changelog:

- Echamos un vistazo a la version actual

```shell
$ conventional-changelog -p eslint
# [](https://github.com/kevops-acme/zipi/compare/v1.0.0...v) (2022-04-23)


### build

* build docker image stage on pull requests and main branch ([e0178ae](https://github.com/kevops-acme/zipi/commit/e0178aeb0f78b30fc3f9899143e604c0313ae050))
* disable commit validation ([139c3dc](https://github.com/kevops-acme/zipi/commit/139c3dc0cdd1ed2f4c575eb21d0a9bb25f64082e))
* fix typo in anyOf usage ([31d1a82](https://github.com/kevops-acme/zipi/commit/31d1a8244b9d9934e4b7481516ef0463d33de60d))

### docs

* add changelog ([3a2519b](https://github.com/kevops-acme/zipi/commit/3a2519b9661b8095bc57067d3a5397f856dc05ba))

### feat

* use dynamic db port for docker-compose test ([62e80aa](https://github.com/kevops-acme/zipi/commit/62e80aacfc9b7c15f63e6628de0967671993ba9d))

```

- Creamos un nuevo tag basado en nuestros cambios y generamos el changelog definitivo sobreescribiendo CHANGELOG.md

```console
$ git tag v1.1.0 -m "Release v1.1.0"
$ git tag -n1
v1.0.0          Release v1.0.0
v1.1.0          Release v1.1.0
$ conventional-changelog -p eslint -r 0 |tail -n +6 > CHANGELOG.md
$ git diff CHANGELOG.md
diff --git a/CHANGELOG.md b/CHANGELOG.md
index f3699cb..8d9dd18 100644
--- a/CHANGELOG.md
+++ b/CHANGELOG.md
@@ -1,3 +1,22 @@
+# [1.1.0](https://github.com/kevops-acme/zipi/compare/v1.0.0...v1.1.0) (2022-04-23)
+
+
+### build
+
+* build docker image stage on pull requests and main branch ([e0178ae](https://github.com/kevops-acme/zipi/commit/e0178aeb0f78b30fc3f9899143e604c0313ae050))
+* disable commit validation ([139c3dc](https://github.com/kevops-acme/zipi/commit/139c3dc0cdd1ed2f4c575eb21d0a9bb25f64082e))
+* fix typo in anyOf usage ([31d1a82](https://github.com/kevops-acme/zipi/commit/31d1a8244b9d9934e4b7481516ef0463d33de60d))
+
+### docs
```

- Hacemos commit con los cambios en el changelog y subimos ese commit y el tag

```shell
$ git add CHANGELOG.md 
$ git commit -m "docs: update changelog with v1.1.0 info"
[feature/dynamic-db-test-port b8ee889] docs: update changelog with v1.1.0 info
 1 file changed, 19 insertions(+)
$ git push
Enumerating objects: 5, done.
Counting objects: 100% (5/5), done.
Delta compression using up to 16 threads
Compressing objects: 100% (3/3), done.
Writing objects: 100% (3/3), 1.31 KiB | 1.31 MiB/s, done.
Total 3 (delta 2), reused 0 (delta 0)
remote: Resolving deltas: 100% (2/2), completed with 2 local objects.
To github.com:kevops-acme/zipi.git
   31d1a82..b8ee889  feature/dynamic-db-test-port -> feature/dynamic-db-test-port
$ git push --tags
Enumerating objects: 1, done.
Counting objects: 100% (1/1), done.
Writing objects: 100% (1/1), 172 bytes | 172.00 KiB/s, done.
Total 1 (delta 0), reused 0 (delta 0)
To github.com:kevops-acme/zipi.git
 * [new tag]         v1.1.0 -> v1.1.0
```

This is a test
