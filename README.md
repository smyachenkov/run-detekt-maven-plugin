# __run-detekt__

**run-detekt** is the Maven plugin which allows to use [detekt](https://github.com/detekt/detekt) code analysis tool from Maven.

## How to use
```xml
            <plugin>
                <groupId>com.smyachenkov</groupId>
                <artifactId>run-detekt-maven-plugin</artifactId>
                <version>1.9.1</version>
                <executions>
                    <execution>
                        <goals>
                            <goal>detekt</goal>
                        </goals>
                    </execution>
                </executions>
                <configuration>
                    <buildUponDefaultConfig>true</buildUponDefaultConfig>
                </configuration>
            </plugin>
```

Version of run-detekt plugin is the same as version of detekt starting with 1.4.0.

## How to build
```
mvn install
```
