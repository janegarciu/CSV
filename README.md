# CSV
  
# Table of contents
  
  * [Task Description](#task-description)
  * [File List](#file-list)
  * [Implementation](#implementation)
  * [How to setup](#how-to-setup)
  
# Task Description
  *In this project I had to implement a SQLite in-memory database with the columns and data provided in the csv file namely _**Test.csv**_, which is located under the resources directory in main directory.
  Table had to include 10 columns which corresponded to csv file column headers. 
  
  *I had to verify whether the data in each row is valid data and does not contain any empty fields. 
  If so, this row should have been considered as a bad-data and be written to a <timestamp>.csv file(They are created under _**export**_ directory in resources directory). Elements with commas should have been double-quoted(Have not accomplished this one)
  
  *In the end I had to write number of:
  a. # of records received
  b. # of records successful
  c. # of records failed
  to a log file(Have stored it in a resources directory under _**logs**_ directory)
  
  *DDLs can be found in a _**DBConnector**_ under dbconnector package.
  
  
  
# File List
- _**DBConnector**_ under dbconnector package
- _**LogHandler**_.java under logger package
- _**CSVMapper**_.java under mapper package
- _**Person**_.java under model package
- _**CSVParser**_.java under service package
- _**CSVValidator**_.java under validator package
- _**Apllication**_.java located under com.testtask.csv


# Implementation 

Each package and class in it have their own responsibilities assigned through the methods they implement.
In this section I will properly define each class and its purpose.

---------------------

**DBConnector**

Its main responsibility is to contain a connection url to the in-memory sqlite db, which we will be using to store data and store methods
that interact with the DB, such as:
- openConnection() - methods which  opens connection to our database.
- closeConnection() - method which closes connection to our database.
- createNewTable() - method which creates a new table to store data in it, in my case I create table named *personal*. Here we define column names. I have named columns improvizing a bit on the 
context of given data, as if it was a real data stored somewhere in whichever company. Column names can be seen in this piece of code:

```java
                 public void createNewTable() {
                
                        // SQL statement for creating a new table
                        String sql = ("CREATE TABLE IF NOT EXISTS personal"
                                + "	(firstName TEXT,"
                                + "	lastName TEXT,"
                                + "	email TEXT,"
                                + "	gender TEXT,"
                                + "	image TEXT,"
                                + " price TEXT,"
                                + "	paymentMethod TEXT,"
                                + "	isAuthorized TEXT,"
                                + "	isEmployed TEXT,"
                                + "	country TEXT)");
                
                        try (Statement stmt = connection.createStatement()) {
                            // creates a new table
                            stmt.executeUpdate(sql);
                        } catch (SQLException e) {
                            System.out.println(e.getMessage());
                        }
                    }
```

- insert(Person person) - method which inserts Person object fields into the corresponding columns of database.

```java
                 public void insert(Person person) {
                         String sql = "INSERT INTO personal(firstName, lastName, email, gender, image, price, paymentMethod, isAuthorized, isEmployed, country) " +
                                 "VALUES(?,?,?,?,?,?,?,?,?,?)";
                         try {
                             PreparedStatement prepStmt = connection.prepareStatement(sql);
                             prepStmt.setString(1, person.getFirstName());
                             prepStmt.setString(2, person.getLastName());
                             prepStmt.setString(3, person.getEmail());
                             prepStmt.setString(4, person.getGender());
                             prepStmt.setBytes(5, person.getImage());
                             prepStmt.setString(6, person.getPrice());
                             prepStmt.setString(7, person.getPaymentMethod());
                             prepStmt.setBoolean(8, person.isAuthorized());
                             prepStmt.setBoolean(9, person.isEmployed());
                             prepStmt.setString(10, person.getCountry());
                             prepStmt.executeUpdate();
                         } catch (SQLException e) {
                             System.out.println(e.getMessage());
                             e.printStackTrace();
                         }
                 
                     }
```
- testDB() - method which can be used to verify whether successful rows are being inserted to a database
That's quite it on DBConnector.

 
 **LogHandler**
 
 A very simple class with a very simple responsibility. It creates a File Handler which will handle all the logs in the system and 
 write them into a file app.log located in main/resources/logs, currently it is not there, but when running the program it is automatically created.
 It is accessible from anywhere in the system, thus static.
 
```java
                     public static Logger createFileHandler() {
                            {
                                try {
                                    fileHandler = new FileHandler("C:\\project_test\\src\\main\\resources\\logs\\app.log", true);
                                    logger.addHandler(fileHandler);
                                    if (logger.isLoggable(Level.INFO)) {
                                        logger.info("Information message");
                                    }
                    
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                return logger;
                            }
                        }
```

--------------------
**CSVMapper**

A class which is responsible for mapping of a string received from the Test.csv file to a Person class-object model.
Contains only one method mapPerson() which accepts string array and returns _**Person**_ object:


```java
                 public static Person mapPerson(String[] row) {
                        Person person = new Person();
                        person.setId(UUID.randomUUID());
                        person.setFirstName(row[0]);
                        person.setLastName(row[1]);
                        person.setEmail(row[2]);
                        person.setGender(row[3]);
                        person.setImage(row[4].getBytes());
                        person.setPaymentMethod(row[5]);
                        person.setPrice(row[6]);
                        person.setAuthorized(Boolean.parseBoolean(row[7]));
                        person.setEmployed(Boolean.parseBoolean(row[8]));
                        person.setCountry(row[9]);
                        return person;
                    }

```


*Person*

Is a class which defines object model. It stores all the properties of a Person and defines getters and setters to access and modify those.
Fields of a Person object model are the following:


```java
                  private UUID id; //not used, but had in mind to include that(let's suppose in the next version of this project :D)
                  private String firstName;
                  private String lastName;
                  private String email;
                  private String gender;
                  private byte[] image;
                  private String paymentMethod;
                  private String price;
                  private boolean isAuthorized;
                  private boolean isEmployed;
                  private String country;

 
```

**CSVParser**

Probably the most important class that does all the magic. It defines four methods and one constructor with one parameter. It uses opencsv library to work with csv files.

- readPersonsFromCSV() - method which reads _**Test.csv**_ file that is passed as a parameter to this method.
Thanks to opencsv library and its functionality this line of code made my life easier by returning me parsed csv file in the format of list of string arrays:
```java
        List<String[]> allRows = csvReader.readAll();
```
Also here in this method I am defining the logic of how the data from csv file will be processed further by using _**CSVValidator**_ class functionality, about which I will 
talk further. All the magic is in lambda expressions which are filtering my list of string arrays and returning list of string arrays with invalid rows and list of Persons for valid rows.
```java
        List<String[]> invalidRows = allRows.stream().filter(row -> !csvRowValidator.isValid(row)).collect(Collectors.toList()); //validates and collects invalid rows

        List<Person> validRows = allRows.stream().filter(row -> csvRowValidator.isValid(row)).map(row -> CSVMapper.mapPerson(row)).collect(Collectors.toList()); //validates and collects valid rows
```
Valid rows are further inserted into DB and invalid rows are further processed by other methods.

- writeToCsvWriter() - method with two parameters of array of strings and index which is equal to processed row number.
This method calls getPath() method to get path to newly created .csv file for invalid rows to be inserted. It writes each invalid row to a new .csv file.

- getPath() - method accepts rowNumber parameter to be used in .csv file creation. .csv files have the following naming format:
rowNumber_dd_MM_yyyy_HH_mm_ss_nn.csv and are created under main/resources/export directory(Please remove example file from export directory before the program is runt).

**CSVValidator**

Validates each string in a row(which is String array) on an emptiness and returns boolean value:
 ```java
                  public boolean isValid(String[] row) {
              
                      return Arrays.stream(row).noneMatch(String::isBlank);
                  }
 ```

**Application**

Finally, _**Application**_ executable class defines main() method which runs the program. Now, when every method is familiar, it is a piece of cake to 
understand the sequence of actions defined in main method:


```java
          public static void main(String[] args) throws IOException, SQLException {
                 DBConnector connector = new DBConnector();
                 CSVParser parser = new CSVParser(connector);
                 connector.openConnection();
                 connector.createNewTable();
                 parser.readPersonsFromCSV("C:\\project_test\\src\\main\\resources\\Test.csv");
                 connector.closeConnection();
                 connector.testDB();
             }
```

# How to setup
- Make sure JDK 11.0.4 is set up
- Make sure pom.xml file is compiled and all the dependencies are present in External Libraries
- Make sure to copy path to File Handler defined in _**LogHandler**_ class, for this copy main/resources/logs directory's absolute path and paste it in here(ofc in LogHandler class):
```
          fileHandler = new FileHandler("C:\\project_test\\src\\main\\resources\\logs\\app.log", true);

```
- Make sure to copy path to main/resources/export directory defined in _**CSVParser**_ class, for this copy main/resources/export directory's absolute path and paste it in here(ofc in CSVParser class getPath() method):
```
        pathBuilder.append("C:\\project_test\\src\\main\\resources\\export\\");

```
- Make sure to copy path to main/resources/Test.csv file defined in _**Application**_ class, for this copy main/resources/Test.csv file's absolute path and paste it in here(ofc in Application class):
```
        parser.readPersonsFromCSV("C:\\project_test\\src\\main\\resources\\Test.csv");

```


  **By the way, not sure if relative path to those defined above will work, have not tried, but it might be the case**
  
## Author

* [**Garciu Eugenia**](https://github.com/janegarciu/CSV)
