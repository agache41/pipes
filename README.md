# Functional Annotations

## Intrigue

What would you say the following code snippet does ?
```java
@TypeFile.New
@IOStream.FileBased
@Zip.Archive
@Zip.Entry(fileName = "data.csv")
@IOEncoding.Automatic
@TypeString.IOStreamLines(separator = TypeString.IOStreamLines.CR)
@TypeString.Array(separator = ";")
@CSVFile(positionMethod = PositionMethod.Fields)
public class CSVTestBean { ... }
```
Well, if you guessed that it creates a file, then opens a IOStream on it, reads it as a Zip archive, seeks and streams the File Entry named "data.csv", identifies the used encoding and opens a Reader on it, reads the content line by line, reads each line in a String Array and finally parses the csv content in a Stream of Java Beans, than you are probably right. And to be accurate it also does the complete reverse process when saving is called.
This is achieved by simply chaining the used annotations as they were functions. Or functional operators. Or functional annotations.

Let's have a look on the following example that uses well known annotations :
```java
@Temporal(TemporalType.TIMESTAMP)
@Column                                                                               
private Date myDate;
.....
@Convert(converter = MyConverter.class)
@Column
private MyData myData;
.....
@Getter
@Column
private myInfo;
```
The Annotations @Temporal , @Convert and @Getter are being used here to enhance the behaviour of the given fields and each of them introduce a change.
That is, they either add or chain a function to an already existing getter or setter and thus modify the resulting value.
But if we try to look at these examples from a functional programming perspective we can say (more or less) that to those fields (or getters) functional operators were applied (or chained). 

This library is trying to answer the question "How can we generalize this ?"

## Introduction

Annotation as a functional operator

**Functional Annotations** is a java Library that aims at implementing data processes by decorating Object Relational Mapping Patterns with Annotations.

The idea behind it is that just by adding Annotations on an already existing POJO Bean, functional Methods will be chained and the Bean will enhance its functionality. 
The main area of applicability for these types of patterns lies in ORM based implementations for data parsing and saving.

But the most notable advantage of the library is that it can offer the possibility of a very clean implementations for more enhanced data parsing frameworks. 
Who ever tried to implement an annotation driven solution to a data model saw that the more annotations are being used,
the more complex and difficult to maintain the code becomes.
On the other side when using annotation as a function (or a functional operator) the interaction only occurs between the two neighboring functions.
This is just like using functional methods chaining to a Stream.
And this proves to be much easily to control. Testing can be done in isolation and most notably, annotations can be easily reused.
This degree of flexibility plays a key role when designing complex ORM solution and is actually the main reason behind this library.       

The library contains already defined annotation that can be used for CSV and Excel file parsing.
It also provides converters for all data types (String, Numeric, Date and Time) and functions for basic classes like string or file.

The library can be very easily extended to add more functionality by adding new Annotations, reusing and extending the provided components.

- [Quick start](#quick-start)
    - [Entity](#entity)
    - [Resource Service](#resource-service)
    - [Updating](#updating)
    - [Data Access](#data-access)
    - [Resource Service again](#resource-service-again)
    - [Testing](#testing)
    - [Testing my own methods](#testing-my-own-methods)
- [Demo](#demo)
- [Requirements](#requirements)
- [Installation](#installation)
- [Features](#features)
- [Structure](#structure)

## Quick start

Let's start with a csv file and a java bean.

### File

```text

string0;integer1;long2;double3;float4;localdate5;date6;list7
[NULL];;;;;;;[NULL]
;-2147483648;-9223372036854775808;4.9E-324;1.4E-45;1900-01-01;1900-01-01;1,2,3
string0;1;2;3.0;4.0;2022-02-22;2022-02-22;a,b,c,d,e,f,g,h,i,j

```

### Bean

```java
@Data
@TypeFile.NewResource
@IOStream.FileBased
@IOEncoding.Automatic
@TypeString.IOStreamLines(separator = TypeString.IOStreamLines.CR)
@TypeString.Array(separator = ";")
@CSVFile(namingMethod = NamingMethod.JavaFieldNames, positionMethod = PositionMethod.Fields, model = Model.Fixed)
public class CSVBean {
    @Position(0)
    @TypeString.nullable
    @CSVAccessor(name = "string0", position = 0)
    private String string0;
    
    @Position(1)
    @TypeInteger.New(simple = true)
    @CSVAccessor(name = "integer1", position = 1, required = true)
    private Integer integer1;
    
    @Position(2)
    @TypeLong.New(simple = true)
    @CSVAccessor(name = "long2", position = 2)
    private Long long2;
    
    @Position(3)
    @TypeDouble.New(simple = true)
    @CSVAccessor(name = "double3", position = 3, required = true)
    private Double double3;

    @Position(4)
    @TypeFloat.New(simple = true)
    @CSVAccessor(name = "float4", position = 4)
    private Float float4;

    @Position(5)
    @TypeLocalDate.New(simple = true)
    @CSVAccessor(name = "localdate5", position = 5)
    private LocalDate localdate5;

    @Position(6)
    @TypeDate.New(format = "yyyy-MM-dd")
    @CSVAccessor(name = "date6", position = 6)
    private Date date6;

    @Position(7)
    @TypeString.nullable
    @TypeString.List(separator = ",")
    @CSVAccessor(name = "list7", position = 7)
    private List<String> list7;
}
```

Notice the used @Data annotation from [Lombok](https://projectlombok.org/).

### Parsing

Extend your **Parser**
from [StringToStreamOfBeansParser](src/main/java/io/github/agache41/ormpipes/pipes/base/parser/StringToStreamOfBeansParser.java):

```java

private final String testFileName = "csvFile.csv";
private final File file = this.getTestFile(testFileName);
private final List<CSVBean> beans = createBeans();

@Test
void test() throws Throwable {

  //given
  StringToStreamOfBeansParser<CSVBean> parser = new StringToStreamOfBeansParser<>(CSVBean.class);

  this.file.delete();
  assertFalse(this.file.exists());

  //when
  parser.write(this.testFileName, this.beans.stream());

  //then
  assertTrue(this.file.exists());

  LinkedList<CSVBean> readout = parser.read(this.testFileName)
                                      .collect(Collectors.toCollection(LinkedList::new));
  assertThat(this.beans).hasSameElementsAs(readout);
}
```
and ... you're pretty much done. Complete code example [Here](src/test/java/examples/csv/CSVTest.java):

For the **Parser** class the following combinations are available :
|-----------|-----------------------------------|---------------------------------|
| Input Type|  Output a single Bean             | Output a Stream of Beans        |
|-----------|-----------------------------------|---------------------------------|
| String    |  [StringToBeanParser](src/main/java/io/github/agache41/ormpipes/pipes/base/parser/StringToBeanParser.java)    |  [StringToStreamOfBeansParser](src/main/java/io/github/agache41/ormpipes/pipes/base/parser/StringToStreamOfBeansParser.java)    |
| File      |  [FileToBeanParser](src/main/java/io/github/agache41/ormpipes/pipes/base/parser/FileToBeanParser.java)    |  [FileToStreamOfBeansParser](src/main/java/io/github/agache41/ormpipes/pipes/base/parser/FileToStreamOfBeansParser.java)    |
|-----------|-----------------------------------|---------------------------------|



### Updating

What does the [@Update](src/main/java/io/github/agache41/generic/rest/jpa/update/Update.java) annotation do ?

The Resource Service uses the entity as both [DAO](https://en.wikipedia.org/wiki/Data_access_object)
and [DTO](https://en.wikipedia.org/wiki/Data_transfer_object). Upon update though it is important to be able to
configure which fields participate in the update process and how null values impact that.

When a field is annotated, it will be updated from the provided source during a PUT or POST operation.

When used on the class, all fields will be updated, except the ones annotated
with [@Update.excluded](src/main/java/io/github/agache41/generic/rest/jpa/update/Update.java) annotation.

If a field is not annotated, it will not participate in the update process. That is general the case for the id field
and for our last field in the example (age).

By default, during the update the value can not be set to null, so if a null value is received, it will be skipped.
Exception can be enforced with @Update(notNull = false) but only when @NotNull is not used on the field.
This is only recommended to be used when the update source transfer object is always complete.

Every entity participating in this update process must implement
the [Updatable](src/main/java/io/github/agache41/generic/rest/jpa/update/Updatable.java) interface.
The root entity must also implement
the [PrimaryKey](src/main/java/io/github/agache41/generic/rest/jpa/dataAccess/PrimaryKey.java) interface and provide a
unique id field.
If the primary key of the table is composed of several database
columns, [@EmbeddedId](https://jakarta.ee/specifications/persistence/3.2/apidocs/jakarta.persistence/jakarta/persistence/embeddedid)
must be used.

### Data Access

Extending the [DAO](https://en.wikipedia.org/wiki/Data_access_object) layer

In complex cases the **Data Access** of the entity must be extended, by adding the new data methods.
Let's start by extending [DataAccess](src/main/java/io/klebrit/generic/api/dataAccess/DataAccess.java).

```java

@Dependent
public class ModellDataAccess extends DataAccess<Modell, Long> {
    @Inject
    public ModellDataAccess() {
        super(Modell.class, Long.class);
    }

    public List<Modell> getAllModellsOver100() {
        CriteriaBuilder criteriaBuilder = em().getCriteriaBuilder();
        CriteriaQuery<Modell> query = criteriaBuilder.createQuery(type);
        Root<Modell> entity = query.from(type);
        return em().createQuery(query.select(entity)
                                     .where(criteriaBuilder.greaterThan(entity.get("age"), 100L)))
                   .getResultList();
    }

}

```

The method getAllModellsOver100() uses the underlining em() method to access
the available [EntityManager](https://docs.oracle.com/javaee%2F7%2Fapi%2F%2F/javax/persistence/EntityManager.html) and
builds the query using
the [CriteriaBuilder](https://docs.jboss.org/hibernate/stable/entitymanager/reference/en/html/querycriteria.html).

Or if a [JPQL](https://en.wikibooks.org/wiki/Java_Persistence/JPQL) approach is to be considered :

```java

@Dependent
public class ModellDataAccess extends DataAccess<Modell, Long> {
    @Inject
    public ModellDataAccess() {
        super(Modell.class, Long.class);
    }

    public List<Modell> getAllModellsOver100() {
        return em().createQuery(" select t from Modell where t.age > 100")
                   .getResultList();
    }
}

```

### Resource Service again

Now let's use this newly ModellDataAccess in our Resource Service:

```java

@Getter
@Path("/modell")
@Transactional

public class ModellResourceService extends AbstractResourceServiceImpl<Modell, Long> {

    @Inject
    ModellDataAccess dataAccess;

    /**
     * Finds and returns all the models over 100
     *
     * @return the models list.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/over/100")
    public List<Modell> getAllModellsOver100() {
        return this.getDataAccess()
                   .getAllModellsOver100();
    }
}

```

and we're ready to go:

- [GET] /modell/over/100 - Finds and returns all the models over 100

Please do notice the this.getDataAccess() method that gets overridden behind the scenes
with [Lombok](https://projectlombok.org/)

### Testing

So far so good. But how can I be sure that the generated services do really work on my platform or with my entities ?
Not to mention that there are already 17 methods in the service, and that goes for each entity.

Let's start by creating the **TestUnit** by
extending  [AbstractResourceServiceImplTest](src/main/java/io/github/agache41/generic/rest/jpa/resourceService/AbstractResourceServiceImpl.java).

```java

@QuarkusTest
@Transactional
public class ModellResourceServiceTest extends AbstractResourceServiceImplTest<Modell, Long> {

    static final String path = "/modell";
    private static final String stringField = "stringVal";
    private static final Producer<Modell> producer;
    private static final List<Modell> insertData;
    private static final List<Modell> updateData;

    static {
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
    }

    static {
        producer = Producer.ofClass(Modell.class)
                           .withList(LinkedList::new)
                           .withMap(LinkedHashMap::new)
                           .withSize(Config.collectionSize);
        insertData = producer.produceList();
        updateData = producer.changeList(insertData);
    }

    public ModellResourceServiceTest() {
        super(Modell.class, //
              path, //
              insertData, //
              updateData,
              stringField); //
    }
}

```

Notice the use of the [Producer](src/test/java/io/github/agache41/generic/rest/jpa/producer/Producer.java) class that
generates automatically complete lists with instance objects for tests.

The test goes through all the provided methods :

![ModelResourcesServiceTest](/readme.res/ModelResourcesServiceTest.png)

Notice that the actual entities used in the test are omitted for simplicity from the example.

### Testing my own methods

The ModellResourceServiceTest is a UnitTest where test methods can be further added :

```java

@QuarkusTest
public class ModellResourceServiceTest extends AbstractResourceServiceImplTest<Modell, Long> {
    public ModellResourceServiceTest() {
        /// .....
    }

    @Test
    @Order(1000)
    void testGetAllModellsOver100() {

        /// your favorite method gets tested here 
    }

}

```

Notice the use of the @Order(1000) annotation, this will ensure the correct order of running.

### Generating Front End model classes and services.

My application grows steadily and every day I add new entities. It's time to present the resource services to my clients
in a
ready to code manner.
The smallrye-openapi open API setting ensures the generation of the open API yaml file.

```properties
quarkus.smallrye-openapi.store-schema-directory=openapi/api
quarkus.smallrye-openapi.open-api-version=3.0.3
```

Then the `org.openapitools:openapi-generator-maven-plugin:7.0.1` plugin will generate the classes for the front end.
Here is an example for [Angular](https://angular.io/) using [Typescript](https://www.typescriptlang.org/).

```xml

<profile>
    <id>generate</id>
    <activation>
        <property>
            <name>generate</name>
        </property>
    </activation>
    <build>
        <plugins>
            <plugin>
                <groupId>org.openapitools</groupId>
                <artifactId>openapi-generator-maven-plugin</artifactId>
                <!-- RELEASE_VERSION -->
                <version>7.0.1</version>
                <!-- /RELEASE_VERSION -->
                <executions>
                    <execution>
                        <phase>package</phase>
                        <goals>
                            <goal>generate</goal>
                        </goals>
                        <configuration>
                            <inputSpec>${project.basedir}/openapi/api/openapi.yaml</inputSpec>
                            <generatorName>typescript-angular</generatorName>
                            <configOptions>
                                <sourceFolder>src/gen/java/main</sourceFolder>
                            </configOptions>
                            <output>${project.basedir}/openapi/generated</output>
                            <verbose>true</verbose>
                            <cleanupOutput>true</cleanupOutput>
                        </configuration>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>
</profile>
```

Here ist an example of the generated files:

![OpenapiGeneration](/readme.res/OpenapiGeneration.png)

## Demo

А comprehensive example of using the library with JPA database you can find in the *
*[demo](https://github.com/agache41/modell_quarkus)** module.

## Requirements

The library works with Java 11+, Quarkus 3.4.3+, JPA 2+

## Installation

Simply add  `io.github.agache41:generic-rest-jpa.version` dependency to your project.

The current version today at sunset:

```xml

<generic-rest-jpa.version>0.2.7</generic-rest-jpa.version>
```

The dependency for the main jar:

```xml

<dependency>
    <groupId>io.github.agache41</groupId>
    <artifactId>generic-rest-jpa</artifactId>
    <version>${generic-rest-jpa.version}</version>
</dependency>
```

For the test context the tests-classified jar is needed:

```xml

<dependency>
    <groupId>io.github.agache41</groupId>
    <artifactId>generic-rest-jpa</artifactId>
    <version>${generic-rest-jpa.version}</version>
    <classifier>tests</classifier>
    <type>test-jar</type>
    <scope>test</scope>
</dependency>
```

## Features

- Easy to install, use and extend.
- Test coverage provided on the fly.
- Works with both [Jackson](https://github.com/FasterXML/jackson) and [JSONB](https://javaee.github.io/jsonb-spec/). No
  support yet for reactive mode.
- Tested with Quarkus 3.6.7.

## Structure

The library is packaged as a single jar. An auxiliary test classifier jar is provided for test context.

## Dependencies

Execution dependencies consist in the needed packages from [Jakarta](https://jakarta.ee/)
and [JbossLogging](https://github.com/jboss-logging/jboss-logging) and are not transitive.

```xml

<dependencies>
    <dependency>
        <groupId>jakarta.enterprise</groupId>
        <artifactId>jakarta.enterprise.cdi-api</artifactId>
        <version>4.0.1</version>
        <scope>provided</scope>
    </dependency>
    <dependency>
        <groupId>jakarta.persistence</groupId>
        <artifactId>jakarta.persistence-api</artifactId>
        <version>3.1.0</version>
        <scope>provided</scope>
    </dependency>
    <dependency>
        <groupId>jakarta.ws.rs</groupId>
        <artifactId>jakarta.ws.rs-api</artifactId>
        <version>3.1.0</version>
        <scope>provided</scope>
    </dependency>
    <dependency>
        <groupId>jakarta.validation</groupId>
        <artifactId>jakarta.validation-api</artifactId>
        <version>3.0.2</version>
        <scope>provided</scope>
    </dependency>
    <dependency>
        <groupId>org.jboss.logging</groupId>
        <artifactId>jboss-logging</artifactId>
        <version>3.5.3.Final</version>
        <scope>provided</scope>
    </dependency>
</dependencies>
```

Testing dependencies are listed here. Please note the Lombok is used only in the test context.

```xml

<dependencies>
    <dependency>
        <groupId>org.junit.jupiter</groupId>
        <artifactId>junit-jupiter-engine</artifactId>
        <version>5.10.1</version>
        <scope>test</scope>
    </dependency>
    <dependency>
        <groupId>io.rest-assured</groupId>
        <artifactId>rest-assured</artifactId>
        <version>5.4.0</version>
        <scope>test</scope>
    </dependency>
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <version>1.18.30</version>
        <scope>test</scope>
    </dependency>
    <dependency>
        <groupId>org.hibernate.orm</groupId>
        <artifactId>hibernate-core</artifactId>
        <version>6.4.1.Final</version>
        <scope>test</scope>
    </dependency>
    <dependency>
        <groupId>com.h2database</groupId>
        <artifactId>h2</artifactId>
        <version>2.2.224</version>
        <scope>test</scope>
    </dependency>
</dependencies>
```
