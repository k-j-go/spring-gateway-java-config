### Implement spring gateway woth java config

- [reference](https://spring.io/projects/spring-cloud-gateway)

#### Create project
```shell
http -d https://start.spring.io/starter.zip type==maven-project \
language==java \
bootVersion==2.4.5 \
baseDir==gw2 \
groupId==com.azunitech.search \
artifactId==gw2 \
name==gw2 \
packageName==com.azunitech.search \
javaVersion==1.8 \
dependencies==web,webflux,lombok
```
#### Add dependent management
```xml
  <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-dependencies</artifactId>
                <version>${spring-cloud.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
            <dependency>
                <groupId>io.projectreactor</groupId>
                <artifactId>reactor-bom</artifactId>
                <version>2020.0.16</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>
```

#### Add and remove web 
```xml
	<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-gateway</artifactId>
    </dependency>
```
##### for M1
```xml
    <dependency>
        <groupId>io.netty</groupId>
        <artifactId>netty-resolver-dns-native-macos</artifactId>
        <version>4.1.90.Final</version>
        <classifier>osx-aarch_64</classifier>
    </dependency>
```

##### For X86
```xml
    <dependency>
        <groupId>io.netty</groupId>
        <artifactId>netty-resolver-dns-native-macos</artifactId>
        <version>4.1.90.Final</version>
        <classifier>osx-x86_64</classifier>
    </dependency>

```