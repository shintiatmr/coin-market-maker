# Coin Market Maker

| document  | url |
|:---------:|:---:|
|    PRD    |  -  |
| Tech Spec |  -  |
| Api Spec  |  -  |

## Requirements

For building and running the application you need:

- [JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Maven 3](https://maven.apache.org)

## Running the application locally

There are several ways to run this application on your local machine. One way is to execute
the `main` method in the `com.ajaib.coin.market.maker.Application` class from your IDE.

Alternatively you can use
the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html)
like so:

```shell
mvn spring-boot:run
```

## Deploying the application to Server

The easiest way to deploy the sample application to Server is to use
the [Jenkins](https://jenkins.app.noc.ajaib.tech/). You can merge your code to target branch to
apply your change Server

| branch  | environment | jenkins | consul |
|:-------:|:-----------:|:-------:|:------:|
| develop |     dev     |    -    |   -    |
|   stg   |     stg     |    -    |   -    |
|  main   |    prod     |    -    |   -    |

If you don't have an access to Jenkins pleas contact devops team to grant your account to open and
run the Jenkins

## Copyright

Released under the Ajaib