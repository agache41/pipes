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
And this proves to be easily to control. Testing can be done in isolation and most notably, annotations can be everywhere reused.
This degree of flexibility plays a key role when designing complex ORM solution and is actually the main reason behind this library.       

The library contains already defined annotation that can be used for CSV and Excel file parsing.
It also provides converters for all data types (String, Numeric, Date and Time) and functions for basic classes like string or file.

The library can be very easily extended to add more functionality by adding new Annotations, reusing and extending the provided components.

- [Quick start](#quick-start)
    - [CSV File](#csv-file)
    - [Bean](#bean)
    - [CSV Parsing](#csv-parsing)
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

### CSV File

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

### CSV Parsing

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
and ... you're pretty much done.

Complete code example [here](src/test/java/examples/csv/stringToBean/CSVTest.java).

For the **Parser** class the following combinations are already provided :
| Input Type|  Output a single Bean             | Output a Stream of Beans        |
|-----------|-----------------------------------|---------------------------------|
| String    |  [StringToBeanParser](src/main/java/io/github/agache41/ormpipes/pipes/base/parser/StringToBeanParser.java)    |  [StringToStreamOfBeansParser](src/main/java/io/github/agache41/ormpipes/pipes/base/parser/StringToStreamOfBeansParser.java)    |
| File      |  [FileToBeanParser](src/main/java/io/github/agache41/ormpipes/pipes/base/parser/FileToBeanParser.java)    |  [FileToStreamOfBeansParser](src/main/java/io/github/agache41/ormpipes/pipes/base/parser/FileToStreamOfBeansParser.java)    |


### File Parsing

The previous example had as input parameter a string providing the file name.
But what if we want to start directly with a file?
The first Annotation from the example (@TypeFile.NewResource) was responsible with reading/creating a file based on the resources default path.
By removing it, the Bean will be parsed with a File as input. This happens because the next Annotation (@IOStream.FileBased) works with an input of File Type, and at saving produces also a File.   

```java
@Data
@IOStream.FileBased
@IOEncoding.Automatic
@TypeString.IOStreamLines(separator = TypeString.IOStreamLines.CR)
@TypeString.Array(separator = ";")
@CSVFile(namingMethod = NamingMethod.JavaFieldNames, positionMethod = PositionMethod.Fields, model = Model.Fixed)
public class CSVFileBean {...}
```
This time the **Parser** will be extended 
from [FileToStreamOfBeansParser](src/main/java/io/github/agache41/ormpipes/pipes/base/parser/FileToStreamOfBeansParser.java):

```java
private final String testFileName = "csvFile.csv";
private final File file = this.getTestFile(testFileName);
private final List<CSVFileBean> beans = createBeans();

@Test
void fileToStreamOfBeansTest() throws Throwable {

    //given
    FileToStreamOfBeansParser<CSVFileBean> parser = new FileToStreamOfBeansParser<>(CSVFileBean.class);

    this.file.delete();
    assertFalse(this.file.exists());

    //when
    parser.write(this.file, this.beans.stream());

    //then
    assertTrue(this.file.exists());

    LinkedList<CSVFileBean> readout = parser.read(this.file)
                                            .collect(Collectors.toCollection(LinkedList::new));
    assertThat(this.beans).hasSameElementsAs(readout);
}

```
Complete code example [here](src/test/java/examples/csv/fileToBean/CSVFileTest.java).

But what if we want both scenarios? And do not wish to have two classes.
Even though this can be easily achieved through class extension, the framework offers an even better way : Views. 

### Views

The concept is simple : 

Every Annotation that does not have a specified view belongs to the default view. (This is valid for all the annotations from almost all the examples.)

Every Parser that does not have a specified view will use only the annotations in the default view. (This is also valid for the above examples and is generally the default case.)

When a view is specified to an annotation, the annotation will be available only when the view is selected.

When a view is specified to a parser, the parser will use all the annotations in the default view and the ones in the currently provided view.

Specified views do not impact the order of the annotations.

This allows very flexible combinations in which, for example, we can specify a view for files in the resource folder (for test purposes) and another view for files with absolute path in production use.
Or we can build complete separate hierarchies in different views on the very same Bean, allowing the same Bean to be parsed (or saved) in separate formats , for example CSV and Excel. 

Here is the previous example for a file:

```java
@Data
@TypeFile.NewResource(view = "fileName")
@IOStream.FileBased
@IOEncoding.Automatic
@TypeString.IOStreamLines(separator = TypeString.IOStreamLines.CR)
@TypeString.Array(separator = ";")
@CSVFile(namingMethod = NamingMethod.JavaFieldNames, positionMethod = PositionMethod.Fields, model = Model.Fixed)
public class CSVBeanWithView {...}
```
Extend your **Parser**
from [StringToStreamOfBeansParser](src/main/java/io/github/agache41/ormpipes/pipes/base/parser/StringToStreamOfBeansParser.java)
and add a view parameter:

```java
@Test
void stringToStreamOfBeansTestWithView() throws Throwable {

  //given
  StringToStreamOfBeansParser<CSVBeanWithView> parser = new StringToStreamOfBeansParser<>(CSVBeanWithView.class,"fileName");

  this.file.delete();
  assertFalse(this.file.exists());

  //when
  parser.write(this.testFileName, this.beans.stream());

  //then
  assertTrue(this.file.exists());

  LinkedList<CSVBeanWithView> readout = parser.read(this.testFileName)
                                              .collect(Collectors.toCollection(LinkedList::new));
  assertThat(this.beans).hasSameElementsAs(readout);
}
```
And the very same bean is also available for a file as input. This time no view parameter is needed, since all the needed annotations are still in the default view.
```java
private final String testFileName = "csvFile.csv";
private final File file = this.getTestFile(testFileName);
private final List<CSVFileBean> beans = createBeans();

@Test
void fileToStreamOfBeansTest() throws Throwable {

  //given
  FileToStreamOfBeansParser<CSVBeanWithView> parser = new FileToStreamOfBeansParser<>(CSVBeanWithView.class);

  this.file.delete();
  assertFalse(this.file.exists());

  //when
  parser.write(this.file, this.beans.stream());

  //then
  assertTrue(this.file.exists());

  LinkedList<CSVBeanWithView> readout = parser.read(this.file)
                                              .collect(Collectors.toCollection(LinkedList::new));
  assertThat(this.beans).hasSameElementsAs(readout);
}

```

Complete code example [here](src/test/java/examples/csv/stringToBeanWithView/CSVTestWithView.java).


### Excel

Now let's parse the same content from an Excel File.
Consider the following example :

```java
@Data
@TypeFile.NewResource
@IOStream.FileBased
@SpreadSheet.xlsx
@SpreadSheet.select(sheetName = "ExcelBean")
@SpreadSheet.sheet
public class ExcelFileBean {
  @Position(0)
  @SpreadSheet.Header(name = "string0", position = 0)
  @TypeString.cellValue
  @TypeString.nullable
  @Accessor
  private String string0;

  @Position(1)
  @SpreadSheet.Header(name = "integer1", position = 1, required = true)
  @TypeInteger.cellValue
  @Accessor
  private Integer integer1;

  @Position(2)
  @SpreadSheet.Header(name = "long2", position = 2)
  @TypeLong.cellValue
  @Accessor
  private Long long2;

  @Position(3)
  @SpreadSheet.Header(name = "double3", position = 3, required = true)
  @TypeDouble.cellValue
  @Accessor
  private Double double3;

  @Position(4)
  @SpreadSheet.Header(name = "float4", position = 4)
  @TypeFloat.cellValue
  @Accessor
  private Float float4;

  @Position(5)
  @SpreadSheet.Header(name = "localdate5", position = 5)
  @TypeLocalDate.cellValue
  @Accessor
  private LocalDate localdate5;

  @Position(6)
  @SpreadSheet.Header(name = "date6", position = 6)
  @TypeDate.cellValue
  @Accessor
  private Date date6;

  @Position(7)
  @SpreadSheet.Header(name = "list7", position = 7)
  @TypeString.cellValue
  @TypeString.List(separator = ",")
  @Accessor
  private List<String> list7;

}
```
Notice the re-used @TypeFile.NewResource and @IOStream.FileBased.
The other annotations are changed and the new ones, based on @SpreadSheet, are responsible for Spreadsheet parsing and saving.
Also on the Fields the annotations change to accommodate the new data format.
But some do actually remain (@Position,  @TypeString.nullable). I also plan a unified @Accessor annotation in the near future that will furthermore reduce the changes.  

Now let's parse it.

```java
private final String testFileName = "excelFile.xlsx";
private final File file = this.getTestFile(testFileName);
private final List<ExcelBean> beans = createBeans();

@Test
void stringToStreamOfBeansTest() throws Throwable {

  //given
  StringToStreamOfBeansParser<ExcelBean> parser = new StringToStreamOfBeansParser<>(ExcelBean.class);

  this.file.delete();
  assertFalse(this.file.exists());

  //when
  parser.write(this.testFileName, this.beans.stream());

  //then
  assertTrue(this.file.exists());

  LinkedList<ExcelBean> readout = parser.read(this.testFileName)
                                        .collect(Collectors.toCollection(LinkedList::new));
  assertThat(this.beans).hasSameElementsAs(readout);
}
  
```
The parser works in the very same way as the one for csv, and the code is practically 100% reused.
Complete code example [here](src/test/java/examples/excel/stringToBean/ExcelTest.java).
And the very same is also valid for File based parsing, see the example [here](src/test/java/examples/excel/fileToBean/ExcelFileTest.java).

### Excel with more than one sheet

But Excel files do have more sheets. How can I parse that?

It's even easier. Let's have a look at the following example:

```java
@Data
@TypeFile.NewResource
@IOStream.FileBased
@SpreadSheet.xlsx
@Field.forEachAnnotatedWith(Accessor.class)
public class ExcelTestMultiBean {

  @SpreadSheet.select(sheetName = "ExcelTestBean1")
  @Pipe.ofClass(ExcelSheetTestBean1.class)
  @Accessor
  private Stream<ExcelSheetTestBean1> sheet1;

  @Pipe.ofClass(ExcelSheetTestBean2.class)
  @Accessor
  private Stream<ExcelSheetTestBean2> sheet2;
}
```
Notice this class as it represents the main Excel File, and contains the two streams pertaining the two sheets of the Excel file.

Let's have a look at the first sheet. Notice the very same data fields from the previous example.

```java
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SpreadSheet.sheet
public class ExcelSheetTestBean1 {
  @Position(0)
  @SpreadSheet.Header(name = "string0", position = 0)
  @TypeString.cellValue
  @TypeString.nullable
  @Accessor
  private String string0;

  @Position(1)
  @SpreadSheet.Header(name = "integer1", position = 1, required = true)
  @TypeInteger.cellValue
  @Accessor
  private Integer integer1;

  @Position(2)
  @SpreadSheet.Header(name = "long2", position = 2)
  @TypeLong.cellValue
  @Accessor
  private Long long2;

  @Position(3)
  @SpreadSheet.Header(name = "double3", position = 3, required = true)
  @TypeDouble.cellValue
  @Accessor
  private Double double3;

  @Position(4)
  @SpreadSheet.Header(name = "float4", position = 4)
  @TypeFloat.cellValue
  @Accessor
  private Float float4;

  @Position(5)
  @SpreadSheet.Header(name = "localdate5", position = 5)
  @TypeLocalDate.cellValue
  @Accessor
  private LocalDate localdate5;

  @Position(6)
  @SpreadSheet.Header(name = "date6", position = 6)
  @TypeDate.cellValue
  @Accessor
  private Date date6;

  @Position(7)
  @SpreadSheet.Header(name = "list7", position = 7)
  @TypeString.cellValue
  @TypeString.List(separator = ",")
  @Accessor
  private List<String> list7;

}

```
And the second one:
```java
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SpreadSheet.select(sheetName = "ExcelTestBean2")
@SpreadSheet.sheet
public class ExcelSheetTestBean2 {

    @Position(0)
    @SpreadSheet.Header(name = "string0", position = 0)
    @TypeString.cellValue
    @TypeString.nullable
    @Accessor
    private String string0;

    @Position(1)
    @SpreadSheet.Header(name = "integer1", position = 1, required = true)
    @TypeInteger.cellValue
    @Accessor
    private Integer integer1;

    @Position(2)
    @SpreadSheet.Header(name = "long2", position = 2)
    @TypeLong.cellValue
    @Accessor
    private Long long2;

    @Position(3)
    @SpreadSheet.Header(name = "double3", position = 3, required = true)
    @TypeDouble.cellValue
    @Accessor
    private Double double3;

    @Position(4)
    @SpreadSheet.Header(name = "float4", position = 4)
    @TypeFloat.cellValue
    @Accessor
    private Float float4;

    @Position(5)
    @SpreadSheet.Header(name = "localdate5", position = 5)
    @TypeLocalDate.cellValue
    @Accessor
    private LocalDate localdate5;

    @Position(6)
    @SpreadSheet.Header(name = "date6", position = 6)
    @TypeDate.cellValue
    @Accessor
    private Date date6;

    @Position(7)
    @SpreadSheet.Header(name = "list7", position = 7)
    @TypeString.cellValue
    @TypeString.List(separator = ",")
    @Accessor
    private List<String> list7;

}
```
Now let's parse it:
```java
private final String testFileName = "excelMultiSheet.xlsx";
private final File file = this.getTestFile(testFileName);
private final ExcelTestMultiBean bean = createBean();

@Test
void test() throws Throwable {

  //given
  StringToBeanParser<ExcelTestMultiBean> parser = new StringToBeanParser<>(ExcelTestMultiBean.class);

  this.file.delete();
  assertFalse(this.file.exists());

  //when
  parser.write(this.testFileName, this.bean);

  //then
  assertTrue(this.file.exists());

  ExcelTestMultiBean readout = parser.read(this.testFileName);

  assertNotNull(readout);

  final ExcelTestMultiBean expected = createBean();

  assertThat(expected.getSheet1()
                     .collect(Collectors.toList())).hasSameElementsAs(readout.getSheet1()
                                                                             .collect(Collectors.toList()));

  assertThat(expected.getSheet2()
                     .collect(Collectors.toList())).hasSameElementsAs(readout.getSheet2()
                                                                             .collect(Collectors.toList()));

}
```
Complete code example [here](src/test/java/examples/excel/multiSheet/ExcelTest.java).


## Demo

А comprehensive example of using the library find in the **[examples](src/test/java/examples)** package.

## Requirements

The library works with Java 11+ and can be used in any container like Quarkus or Spring.

## Installation

Simply add  `io.github.agache41:functional.annotations` dependency to your project.

The current version today at sunset:

```xml

<functional.annotations.version>0.1.2</functional.annotations.version>
```

The dependency for the main jar:

```xml

<dependency>
    <groupId>io.github.agache41</groupId>
    <artifactId>functional.annotations</artifactId>
    <version>${functional.annotations.version}</version>
</dependency>
```

## Features

- Easy to install, use and extend.
- Provided examples in the test Folder
- Tested with Quarkus 3.6.7.

## Structure

The library is packaged as a single jar. An auxiliary test classifier jar is provided for test context.

## Dependencies

Execution dependencies consist in the needed packages for implementing the annotations functionality. 
The [JbossLogging](https://github.com/jboss-logging/jboss-logging) dependency ist not transitive and must be provided at runtime, that is if logging is to be used.

```xml
<dependencies>
  <dependency>
    <groupId>io.github.agache41</groupId>
    <artifactId>annotator</artifactId>
    <version>0.1.0</version>
  </dependency>
  <dependency>
    <groupId>org.apache.commons</groupId>
    <artifactId>commons-lang3</artifactId>
    <version>3.12.0</version>
  </dependency>
  <dependency>
    <groupId>commons-io</groupId>
    <artifactId>commons-io</artifactId>
    <version>2.16.1</version>
  </dependency>
  <dependency>
    <groupId>com.ibm.icu</groupId>
    <artifactId>icu4j</artifactId>
    <version>72.1</version>
  </dependency>
  <dependency>
    <groupId>org.codehaus.guessencoding</groupId>
    <artifactId>guessencoding</artifactId>
    <version>1.4</version>
  </dependency>
  <dependency>
    <groupId>org.apache.poi</groupId>
    <artifactId>poi</artifactId>
    <version>5.3.0</version>
  </dependency>
  <dependency>
    <groupId>org.apache.poi</groupId>
    <artifactId>poi-ooxml</artifactId>
    <version>5.3.0</version>
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
    <artifactId>junit-jupiter</artifactId>
    <version>5.9.2</version>
    <scope>test</scope>
  </dependency>
  <dependency>
    <groupId>org.apache.commons</groupId>
    <artifactId>commons-collections4</artifactId>
    <version>4.4</version>
    <scope>test</scope>
  </dependency>
  <dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <version>1.18.26</version>
    <scope>test</scope>
  </dependency>
  <dependency>
    <groupId>org.apache.logging.log4j</groupId>
    <artifactId>log4j-api</artifactId>
    <version>2.22.1</version>
    <scope>test</scope>
  </dependency>
  <dependency>
    <groupId>org.apache.logging.log4j</groupId>
    <artifactId>log4j-core</artifactId>
    <version>2.22.1</version>
    <scope>test</scope>
  </dependency>
  <dependency>
    <groupId>org.assertj</groupId>
    <artifactId>assertj-core</artifactId>
    <version>3.25.3</version>
    <scope>test</scope>
  </dependency>
</dependencies>
```
