# Hibernate ORM Repository

## Introduction

Hibernate is a powerful Object-Relational Mapping (ORM) tool used in the data layer of applications. It simplifies the interaction between Java applications and relational databases by mapping Java objects to database tables. Hibernate implements the Java Persistence API (JPA) specification, making it a popular choice for managing database operations.

## Problems Solved by Hibernate

### 1. Mapping Member Variables to Columns
   - **Problem:** Traditional databases require explicit mapping between Java objects and database tables, leading to repetitive and error-prone code.
   - **Solution:** Hibernate automates this mapping through annotations, reducing the need for manual configuration.

### 2. Mapping Relationships
   - **Problem:** Establishing and managing relationships between entities can be complex with direct SQL queries.
   - **Solution:** Hibernate simplifies relationship mapping, providing annotations to define associations, making it more intuitive and less error-prone.

### 3. Handling Data Types
   - **Problem:** Different databases may have varying data types, leading to compatibility issues.
   - **Solution:** Hibernate abstracts these differences, allowing developers to work with Java data types, and it handles the translation to the appropriate database types.

### 4. Managing Changes to Object State
   - **Problem:** Tracking changes to object state and updating the database accordingly can be tedious.
   - **Solution:** Hibernate automatically detects changes to object states and synchronizes them with the database, streamlining the update process.

## Saving Without Hibernate

Before Hibernate, saving objects to a database involved:

1. **JDBC Database Configuration:**
   - Configuring the database connection using JDBC.

2. **Model Object:**
   - Creating a model object representing the entity to be persisted.

3. **Service Method to Create Model Object:**
   - Implementing a service method that constructs the model object and prepares SQL queries for saving data.

4. **Database Design:**
   - Defining the database schema based on the model object.

5. **DAO Method Using SQL Queries:**
   - Writing Data Access Object (DAO) methods to execute SQL queries for saving the object.

## The Hibernate Way

With Hibernate, the process becomes more streamlined:

1. **Hibernate Configuration File (`hibernate.cfg.xml`):**
   - Configure the Hibernate settings, eliminating the need for complex JDBC configurations.

2. **Model Object with Annotations:**
   - Annotate the Java class with Hibernate annotations, removing the requirement for a separate database design.

3. **Service Method Using Hibernate API:**
   - Leverage Hibernate API in service methods, simplifying the creation and persistence of model objects.

4. **No Direct SQL Queries in DAO:**
   - Hibernate eliminates the need to write explicit SQL queries in DAO methods for basic CRUD operations.
  

## Getting Started

Setting up a new Hibernate project involves several steps, including configuring dependencies, creating Hibernate configuration files, defining entity classes, and integrating Hibernate into your application. Below is a step-by-step guide to help you set up a new Hibernate project:

### 1. **Step 1: Create a New Java Project**
   - Start by creating a new Java project using your preferred IDE (Integrated Development Environment) such as Eclipse, IntelliJ, or any other. Configure the project settings according to your preferences.

### 2. **Step 2: Add Hibernate Dependencies**
   - Include the Hibernate dependencies in your project. You can use a build tool like Maven or Gradle to manage dependencies. Add the following dependencies to your project's pom.xml file if you are using Maven:
   - Maven Dependency for Hibernate:
     
```xml
<dependencies>

        <!-- https://mvnrepository.com/artifact/org.hibernate/hibernate-core -->
        <dependency>
            <groupId>org.hibernate</groupId>
            <artifactId>hibernate-core</artifactId>
            <version>6.4.1.Final</version>
        </dependency>


        <!-- https://mvnrepository.com/artifact/org.postgresql/postgresql -->
        <dependency>
            <groupId>org.postgresql</groupId>
            <artifactId>postgresql</artifactId>
            <version>42.7.1</version>
        </dependency>

 </dependencies>

```

### 3. **Step 3: Create Hibernate Configuration File**
   - Create a Hibernate configuration file named `hibernate.cfg.xml.` This file contains information about the database connection, dialect, and other settings. Place this file in the `src` or `resources `directory of your project.
     
  ```xml
<!-- src/main/resources/hibernate.cfg.xml -->
<hibernate-configuration>

    <session-factory>

    <!-- Database connection settings -->
    <property name="hibernate.connection.driver_class">com.mysql.cj.jdbc.Driver</property>
    <property name="hibernate.connection.url">jdbc:mysql://localhost:3306/your_database</property>
    <property name="hibernate.connection.username">your_username</property>
    <property name="hibernate.connection.password">your_password</property>

    <!-- Dialect for the chosen database -->
    <property name="hibernate.dialect">org.hibernate.dialect.MySQLDialect</property>

    <!-- Echo all executed statements to stdout -->
    <property name="hibernate.show_sql">true</property>

    <!-- Drop and re-create the database schema on startup -->
    <property name="hibernate.hbm2ddl.auto">update</property>

    <!-- Mention annotated class locations -->
    <mapping class="com.example.YourEntityClass"/>

    </session-factory>

</hibernate-configuration>

```

### `hbm2ddl` Configuration :

#### `hibernate.hbm2ddl.auto` Property
The `hibernate.hbm2ddl.auto` property controls the behavior of Hibernate regarding the database schema. Common values include:

- `update:` Update the schema if necessary when the session factory is created.
- `create:` Create the schema, destroying previous data.
- `validate:` Validate the schema, but do not make any changes.

### 4. **Step 4: Create A Simple Entity Class**
Create a Java class representing your entity (table) and annotate it with Hibernate annotations. This class will be mapped to a database table.

```java
@Entity
public class Employee {
    @Id
    private Long id;
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
```

### Name Annotations

#### `@Entity` Annotation

The `@Entity` annotation is used to mark a Java class as an entity. This means it will be persisted in the database. Example:

```java
@Entity
public class Employee {
    @Id
    private Long id;
    // Other fields and methods...
}
```

#### `@Table` Annotation

The `@Table` annotation is used to specify the details of the database table to which an entity is mapped. Example:


```java
@Entity
@Table(name = "employee_table")
public class Employee {
    @Id
    private Long id;
    // Other fields and methods...
}
```

#### `@Column` Annotation

The `@Column` annotation is used to specify the details of a database column to which a field or property is mapped. Example:

```java
@Entity
public class Employee {
    @Id
    private Long id;

    @Column(name = "employee_name", length = 100, nullable = false)
    private String name;

    @Column(name = "salary")
    private double salary;
    // Other fields and methods...
}

```



### 5. **Step 5: Hibernate Utility Class**
Now, you can use Hibernate in your application to perform database operations. Create a Hibernate session, and use it to save, update, and query your entities.

```java
public class HibernateUtil {

    private static final SessionFactory sessionFactory;

    static {
        try {
            // Create the SessionFactory from hibernate.cfg.xml
            sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
```

### 6. **Step 6: Use Hibernate in Your Application**
Now, you can use Hibernate in your application to perform database operations. Create a Hibernate session, and use it to save, update, and query your entities.

 *Using the Hibernate Api :*
 
  1. create a session factory
  2. create a session from the session factory
  3. use the session to save model objects
        

```java
public class HibernateTest {
    public static void main(String[] args) {

//      Take an instance from Employee Entity and set initial values
        Employee employee=new Employee();
        employee.setId(1L);
        employee.setName("Mahmoud Matar");

//        Using the Hibernate Api

//        1. create a session factory
//        2. create a session from the session factory
//        3. use the session to save model objects

        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(employee);
        session.getTransaction().commit();

    }
}
```

### 7. **Step 7: Run and Test**
Run your application and test the Hibernate setup by performing CRUD operations on your entity.



## More Annotations

### `@Temporal` Annotation
The `@Temporal` annotation is used to declare the type of a temporal field (date, time, timestamp). It allows you to specify whether the field should be mapped as `DATE`, `TIME`, or `TIMESTAMP`. Example:

```java
@Entity
public class Employee {
    @Id
    private Long id;

    private String name;

    @Temporal(TemporalType.DATE)
    private Date joinDate;

    // Other fields and methods...
}
```
In this example, the `joinDate` field will be mapped as a DATE in the database.

### `@Lob` Annotation
The `@Lob` annotation is used to map a large object (LOB) field to a database-supported large object type. It is often used for mapping `CLOB` (Character Large Object) and `BLOB` (Binary Large Object) fields. Example:

```java
@Entity
public class Employee {
    @Id
    private Long id;

    private String name;

    @Lob
    private byte[] photo;

    // Other fields and methods...
}
```
In this example, the `photo` field will be mapped as a BLOB in the database.


### `@Transient` Annotation
The `@Transient` annotation is used to mark a field as not persistable. Fields marked with `@Transient` will not be stored in the database. Example:

```java
@Entity
public class Employee {
    @Id
    private Long id;

    private String firstName;
    private String lastName;

    @Transient
    private String fullName; // This field will not be stored in the database

    // Other fields and methods...
}

```
In this example, the `fullName` field will not be persisted in the database.


## Retrieving Objects using `session.get`

In Hibernate, the `session.get` method is used to retrieve an object from the database based on its primary key. This method is particularly useful when you want to fetch an entity by its unique identifier. Here's an example demonstrating the usage of `session.get`:

```java
// Retrieving Objects using session.get
employee = null;
session = HibernateUtil.getSessionFactory().openSession();
session.beginTransaction();
employee = session.get(Employee.class, 1);
System.out.println("Employee Name Retrieved From Database: " + employee.getName());
```

In this example:

`Employee.class:` Specifies the entity type you want to retrieve, in this case, the` Employee` class.
`1:` Represents the `primary key` value for the desired employee. Adjust this value based on the `primary key` of the entity you are retrieving.

After retrieving the object using `session.get`, you can then access its properties or perform additional operations. It's important to note that if the object is not found in the database, the method returns `null.`

Remember to commit the transaction and close the session after completing your database operations:

```java
session.getTransaction().commit();
session.close();
```

## Primary Keys and `@GeneratedValue` Annotation

In a relational database, a primary key uniquely identifies each record in a table. In Hibernate, primary keys are crucial for maintaining the uniqueness and integrity of data. The `@GeneratedValue` annotation is commonly used to specify how primary key values are generated.

### Using `@GeneratedValue`

The `@GeneratedValue` annotation is used to indicate that the database should automatically generate values for the annotated field, typically used for primary keys. Here's an example of using `@GeneratedValue` with an `@Id` annotated field:

```java
@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Other fields and methods...
}
```
In this example:

- `@Id`: Marks the field as the primary key.
- `@GeneratedValue`: Specifies the generation strategy for the primary key.
- `strategy = GenerationType.IDENTITY`: Indicates that the primary key values are generated by the database identity column.

### Common Generation Strategies
Hibernate supports various strategies for generating primary key values. Some common strategies include:

- `GenerationType.IDENTITY`: Uses an identity column in the database (auto-increment in MySQL, serial in PostgreSQL).
- `GenerationType.SEQUENCE`: Uses a database sequence.
- `GenerationType.AUTO`: Lets Hibernate choose the appropriate strategy based on the underlying database.


## Value Types and Embedding Objects

In Hibernate, value types and embedding objects allow you to model complex data structures in your entities. These concepts are essential for creating flexible and normalized database schemas.

### Value Types

A value type is an object that represents a descriptive aspect of the domain with no conceptual identity. Value types are owned by an entity and are used to model attributes of that entity. Hibernate allows you to define custom value types using the `@Embeddable` annotation.

Here's an example of using a value type with `@Embeddable`:

```java
@Embeddable
public class Address {
    private String street;
    private String city;
    private String zipCode;

    // Getters and setters...
}

@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Embedded
    private Address address;

    // Other fields and methods...
}
```

In this example, the Address class is marked with @Embeddable, and it is then embedded in the Employee entity using the @Embedded annotation.

### Embedding Objects

Embedding objects involve including the properties of one class into another. This is useful when you want to group related fields together. Hibernate provides the `@Embeddable` and `@Embedded` annotations for handling embedded objects.

Here's an example of embedding an object in an entity:
```java
@Embeddable
public class ContactInformation {
    private String email;
    private String phoneNumber;

    // Getters and setters...
}

@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Embedded
    private ContactInformation contactInformation;

    // Other fields and methods...
}
```
In this example, the ContactInformation class is marked with` @Embeddable`, and it is embedded in the Customer entity using the `@Embedded` annotation.


## AttributeOverrides and Embedded Object Keys

In Hibernate, `AttributeOverrides` and embedded object keys allow you to customize the mapping of attributes within an embedded object. This is particularly useful when you need to handle scenarios where the same embedded type is used in multiple places with different attribute mappings.

### AttributeOverrides

The `@AttributeOverrides` annotation in Hibernate allows you to override the mapping of attributes defined in an embedded object when it is used within an entity. This can be handy when you want to reuse an embedded object but need different column names or configurations in different contexts.

Here's an example of using `@AttributeOverrides`:

```java
@Embeddable
public class Address {
    private String street;
    private String city;
    private String zipCode;

    // Getters and setters...
}

@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "street", column = @Column(name = "home_street")),
        @AttributeOverride(name = "city", column = @Column(name = "home_city")),
        @AttributeOverride(name = "zipCode", column = @Column(name = "home_zip_code"))
    })
    private Address homeAddress;

    // Other fields and methods...
}
```
In this example, the Employee entity embeds an Address object (homeAddress) and uses @AttributeOverrides to customize the column names for the street, city, and zipCode attributes.

### Embedded Object Keys
When using an embedded object as part of the primary key of an entity, you can leverage @EmbeddedId to declare an embedded identifier. This is useful when you want to create composite primary keys using an embedded object.

Here's an example of using `@EmbeddedId`:

```java
@Embeddable
public class EmployeeId implements Serializable {
    private String department;
    private Long employeeNumber;

    // Getters and setters...
}

@Entity
public class Employee {
    @EmbeddedId
    private EmployeeId employeeId;

    private String name;

    // Other fields and methods...
}
```
In this example, the Employee entity uses an embedded identifier (EmployeeId) as part of its primary key.



## Saving Collections

In Hibernate, you often need to model relationships between entities where one entity has a collection of another entity. This section explores how to save and manage collections in Hibernate entities.

### Saving a Collection of Elements

### One-to-Many Relationship

In a one-to-many relationship, an entity can have multiple instances of another entity. Hibernate provides the `@OneToMany` annotation to represent this relationship.

Here's an example:

```java
@Entity
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Employee> employees = new ArrayList<>();

    // Other fields and methods...
}

@Entity
@Table(name = "employee_table")
public class Employee {
    @Id
    private Long id;

    @Column(name = "employee_name", length = 100, nullable = false)
    private String name;
    
    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    // Other fields and methods...
}
```
In this example, the Department entity has a one-to-many relationship with Employee. The employees field in the Department entity represents the collection of employees in that department. The @OneToMany annotation specifies the mapping, and cascade = CascadeType.ALL ensures that changes to the collection are cascaded to the database.

Here Test Calss
```java
public class HibernateTest {
    public static void main(String[] args) {

//      Take an instance from Employee Entity and set initial values
        Employee employee=new Employee();
        employee.setId(1L);
        employee.setName("Mahmoud Matar");

//      Take an instance from Department Entity and set initial values
        Department department=new Department();
        department.setName("Human Resource");

//      Set the employee department to employee in One To Many Relationship
        employee.setDepartment(department);
        department.getEmployees().add(employee);


//        Using the Hibernate Api

        // create a session factory and create a session from the session factory
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        // save model objects
        session.save(department);
        session.save(employee);
        session.getTransaction().commit();
        session.close();

        // Retrieving Objects using session.get
        employee=null;
        session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        employee=session.get(Employee.class,1);
        session.getTransaction().commit();
        session.close();
        System.out.println("Employee Name Retrieved From Database : "+employee.getName());
        System.out.println(employee.getName()+ " in department: "+employee.getDepartment().getName());
        System.out.println(department.getName()+" has First employee : "+department.getEmployees().get(0).getName());

    }
}
```


### Many-to-Many Relationship

In a many-to-many relationship, entities can have multiple instances of each other. Hibernate provides the @ManyToMany annotation to represent this relationship.

Here's an example:
```java
@Entity
public class Manger {
    @Id
    private Long id;
    private String Name;

    @ManyToMany
    @JoinTable(
            name = "manger_employee",
            joinColumns = @JoinColumn(name = "manger_id"),
            inverseJoinColumns = @JoinColumn(name = "employee_id")
    )
    private List<Employee> employeeList=new ArrayList<>();

    // setter and getter
}

@Entity
@Table(name = "employee_table")
public class Employee {
    @Id
    private Long id;
    @Column(name = "employee_name", length = 100, nullable = false)
    private String name;

    @ManyToMany(mappedBy = "employeeList", cascade = CascadeType.ALL)
    private List<Manger> mangerList=new ArrayList<>();

    // setter and getter
}

``` 

### One to One Relationship
In a one-to-one relationship, each record in a table is related to only one record in another table. Hibernate provides the @OneToOne annotation to represent this relationship.

Here's an example:

```java
@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;

    // Other fields and methods...
}

@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String street;

    // Other fields and methods...
}
```
In this example, the Employee entity has a one-to-one relationship with the Address entity. The address field in the Employee entity represents the associated address, and @OneToOne(cascade = CascadeType.ALL) ensures that changes to the address are cascaded to the database.



## Implementing Inheritance

Inheritance is a powerful concept in object-oriented programming, and Hibernate provides support for implementing inheritance in your entity mappings. In this tutorial section, we'll explore how to model inheritance using Hibernate.

### Table Per Hierarchy (Single Table Strategy)

Table Per Hierarchy is a strategy where all classes in an inheritance hierarchy are mapped to a single database table. Discriminator column differentiates between different types of entities in the table.

### Example:

```java
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "employee_type", discriminatorType = DiscriminatorType.STRING)
public abstract class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    
    // Common fields and methods...
}

@Entity
@DiscriminatorValue("FT_EMPLOYEE")
public class FullTimeEmployee extends Employee {
    private BigDecimal salary;
    
    // Additional fields and methods specific to FullTimeEmployee...
}

@Entity
@DiscriminatorValue("PT_EMPLOYEE")
public class PartTimeEmployee extends Employee {
    private BigDecimal hourlyRate;
    
    // Additional fields and methods specific to PartTimeEmployee...
}
```
In this example, `Employee` is an abstract class, and `FullTimeEmployee` and `PartTimeEmployee` are concrete subclasses. The discriminator column `(employee_type)` is used to differentiate between the types.


### Table Per Class Hierarchy (Joined Table Strategy)

Table Per Class Hierarchy is a strategy where each class in an inheritance hierarchy is mapped to its own database table. There is a common table for shared attributes.

### Example :

```java
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    
    // Common fields and methods...
}

@Entity
public class FullTimeEmployee extends Employee {
    private BigDecimal salary;
    
    // Additional fields and methods specific to FullTimeEmployee...
}

@Entity
public class PartTimeEmployee extends Employee {
    private BigDecimal hourlyRate;
    
    // Additional fields and methods specific to PartTimeEmployee...
}
```

In this example, there is a common table for shared attributes in the `Employee` class, and each subclass has its own table.


### Table Per Concrete Class

Table Per Concrete Class is a strategy where each concrete class in an inheritance hierarchy is mapped to its own database table, including the fields inherited from the superclass.

### Example :

```java
@Entity
public class FullTimeEmployee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    
    private BigDecimal salary;
    
    // Additional fields and methods specific to FullTimeEmployee...
}

@Entity
public class PartTimeEmployee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    
    private BigDecimal hourlyRate;
    
    // Additional fields and methods specific to PartTimeEmployee...
}
```

In this example, there is no common superclass table, and each concrete class has its own table.


## CRUD Operations

In this section, we'll cover the basic CRUD operations using Hibernate. For the purpose of this tutorial, we'll use a simple `Employee` entity as an example.

### Create (Insert) Operation

To save a new employee into the database:

```java
// Create a new employee
Employee newEmployee = new Employee();
newEmployee.setName("John Doe");

// Open a Hibernate session and start a transaction
Session session = HibernateUtil.getSessionFactory().openSession();
session.beginTransaction();

// Save the employee to the database
session.save(newEmployee);

// Commit the transaction and close the session
session.getTransaction().commit();
session.close();
```

### Read (Select) Operation

To retrieve an employee from the database by ID:

```java
// Open a Hibernate session and start a transaction
Session session = HibernateUtil.getSessionFactory().openSession();
session.beginTransaction();

// Retrieve an employee by ID
Employee retrievedEmployee = session.get(Employee.class, 1);

// Commit the transaction and close the session
session.getTransaction().commit();
session.close();

// Access the retrieved employee
System.out.println("Employee Name: " + retrievedEmployee.getName());
```

### Update Operation

To update an existing employee in the database:

```java
// Open a Hibernate session and start a transaction
Session session = HibernateUtil.getSessionFactory().openSession();
session.beginTransaction();

// Retrieve the employee to be updated
Employee employeeToUpdate = session.get(Employee.class, 1);

// Update employee information
employeeToUpdate.setName("Updated Name");

// Commit the transaction and close the session
session.getTransaction().commit();
session.close();
```

### Delete Operation

To delete an employee from the database:

```java
// Open a Hibernate session and start a transaction
Session session = HibernateUtil.getSessionFactory().openSession();
session.beginTransaction();

// Retrieve the employee to be deleted
Employee employeeToDelete = session.get(Employee.class, 1);

// Delete the employee from the database
session.delete(employeeToDelete);

// Commit the transaction and close the session
session.getTransaction().commit();
session.close();
```


## Transient, Persistent, and Detached Objects

In Hibernate, objects are classified into three states: Transient, Persistent, and Detached. Understanding these states is crucial for managing the lifecycle of your entities.

### Transient Objects

Objects that are not associated with any Hibernate session are considered transient. These objects are not in the scope of Hibernate's management. When you create a new object using the `new` keyword, it is in a transient state.

Example of creating a transient object:

```java
// Creating a transient object
Employee transientEmployee = new Employee();
transientEmployee.setName("John Doe");
// 'transientEmployee' is now in a transient state
```

### Persistent Objects

Objects that are associated with a Hibernate session and have been saved or loaded from the database are considered persistent. Hibernate manages persistent objects, and any changes made to these objects are automatically synchronized with the database during the transaction.

Example of making an object persistent:

```java
// Open a Hibernate session and start a transaction
Session session = HibernateUtil.getSessionFactory().openSession();
session.beginTransaction();

// Creating a persistent object
Employee persistentEmployee = new Employee();
persistentEmployee.setName("Jane Doe");

// Save the persistent object to the database
session.save(persistentEmployee);

// Commit the transaction and close the session
session.getTransaction().commit();
session.close();
// 'persistentEmployee' is now in a persistent state
```

### Detached Objects

Objects that were once persistent but are no longer associated with a Hibernate session are considered detached. This can happen when a session is closed, or an object is explicitly evicted from the session.

Example of making an object detached:

```java
// Open a Hibernate session and start a transaction
Session session = HibernateUtil.getSessionFactory().openSession();
session.beginTransaction();

// Creating a persistent object
Employee persistentEmployee = new Employee();
persistentEmployee.setName("Jane Doe");

// Save the persistent object to the database
session.save(persistentEmployee);

// Commit the transaction and close the session
session.getTransaction().commit();
session.close();

// 'persistentEmployee' is now in a detached state
```


## Hibernate Tutorial - Introduction to HQL and the Query Object

Hibernate Query Language (HQL) is a powerful query language provided by Hibernate for performing database operations using object-oriented syntax. It allows developers to write queries in terms of entity classes and their properties rather than native SQL.

### HQL Basics

HQL is similar to SQL but uses the names of Java classes and properties instead of database tables and columns. It operates on persistent objects and their properties, making it more abstract and object-oriented.

### Example HQL Query

```java
// Open a Hibernate session and start a transaction
Session session = HibernateUtil.getSessionFactory().openSession();
session.beginTransaction();

// HQL query to retrieve all employees with a salary greater than 50000
String hqlQuery = "FROM Employee WHERE salary > 50000";
Query<Employee> query = session.createQuery(hqlQuery, Employee.class);

// Execute the query and get the results
List<Employee> employees = query.getResultList();

// Commit the transaction and close the session
session.getTransaction().commit();
session.close();

```

In this example, the HQL query retrieves all Employee objects with a salary greater than 50000. The result is a list of Employee objects.


### The Query Object

The Query object in Hibernate is used to execute HQL queries. It provides methods for setting parameters, specifying result types, and executing the query.

### Example Using the Query Object

```java
// Open a Hibernate session and start a transaction
Session session = HibernateUtil.getSessionFactory().openSession();
session.beginTransaction();

// Using the Query object to execute a parameterized HQL query
Query<Employee> query = session.createQuery("FROM Employee WHERE department = :dept", Employee.class);
query.setParameter("dept", "IT");

// Execute the query and get the results
List<Employee> employeesInIT = query.getResultList();

// Commit the transaction and close the session
session.getTransaction().commit();
session.close();
```

In this example, the Query object is used to execute a parameterized HQL query, retrieving all Employee objects in the "IT" department.


## Hibernate Tutorial - Named Queries

Named Queries in Hibernate provide a way to define and reuse queries by assigning a name to them. This enhances code readability, promotes maintainability, and allows for better organization of queries in your application.

### Defining a Named Query

Named Queries are typically defined in the entity class using annotations. Here's an example:

```java
@Entity
@NamedQuery(
    name = "findEmployeeByDepartment",
    query = "FROM Employee WHERE department = :dept"
)
public class Employee {
    // Entity properties and methods...
}
```

In this example, a named query named "findEmployeeByDepartment" is defined for the Employee entity. It retrieves all employees based on the specified department.


### Using a Named Query

You can then use the named query in your code as follows:

```java
// Open a Hibernate session and start a transaction
Session session = HibernateUtil.getSessionFactory().openSession();
session.beginTransaction();

// Using the named query to retrieve employees in the "IT" department
List<Employee> employeesInIT = session
    .createNamedQuery("findEmployeeByDepartment", Employee.class)
    .setParameter("dept", "IT")
    .getResultList();

// Commit the transaction and close the session
session.getTransaction().commit();
session.close();
```

By using named queries, you can encapsulate the query logic within your entity classes and refer to them by name when needed.

### Benefits of Named Queries

1. `Code Organization`: Named Queries help organize your query logic within entity classes, making it easier to locate and maintain queries.

2. `Reusability`: Named Queries can be reused across different parts of your application, promoting code reuse.

3. `Readability`: The use of named queries in code enhances readability and reduces the verbosity of query strings directly within the code.


## Hibernate Cache

Hibernate provides caching mechanisms to improve the performance of database operations by reducing the number of trips to the database. Caching can be configured at different levels, including the first-level cache (session-level cache) and the second-level cache (application-level cache).

### First-Level Cache

The first-level cache is associated with the Hibernate session. It is enabled by default and stores objects that have been recently queried or saved within the current session.

### Example of First-Level Cache Usage

```java
// Open a Hibernate session and start a transaction
Session session = HibernateUtil.getSessionFactory().openSession();
session.beginTransaction();

// Retrieve an employee (if not already in the cache, a database query is executed)
Employee employee = session.get(Employee.class, 1);

// Retrieve the same employee again (no additional database query is executed)
Employee sameEmployee = session.get(Employee.class, 1);

// Commit the transaction and close the session
session.getTransaction().commit();
session.close();
```
In this example, the first-level cache prevents redundant database queries for the same entity within the same session.

### Second-Level Cache

The second-level cache is a more persistent cache that spans multiple sessions. It can be configured to cache entities or query results.

#### Configuration of Second-Level Cache

To enable the second-level cache, you need to configure it in your Hibernate configuration file `(hibernate.cfg.xml):`

```xml
<!-- Enable the second-level cache -->
<property name="hibernate.cache.use_second_level_cache">true</property>
<property name="hibernate.cache.region.factory_class">org.hibernate.cache.ehcache.EhCacheRegionFactory</property>
```

In this example, we use the Ehcache implementation for the second-level cache.

#### Example of Second-Level Cache Usage

```java
// Open two Hibernate sessions and start transactions
Session session1 = HibernateUtil.getSessionFactory().openSession();
Session session2 = HibernateUtil.getSessionFactory().openSession();
session1.beginTransaction();
session2.beginTransaction();

// Retrieve an employee in the first session (object is now in the second-level cache)
Employee employee = session1.get(Employee.class, 1);

// Retrieve the same employee in the second session (no additional database query is executed)
Employee sameEmployee = session2.get(Employee.class, 1);

// Commit transactions and close sessions
session1.getTransaction().commit();
session1.close();
session2.getTransaction().commit();
session2.close();
```

In this example, the second-level cache prevents redundant database queries for the same entity across different sessions.

### Benefits of Caching

1. `Performance Improvement`: Caching reduces the need for repetitive database queries, improving overall performance.

2. `Reduced Database Load`: Caching helps reduce the load on the database server by serving repeated requests from the cache.

3. `Scalability`: Caching contributes to the scalability of your application by minimizing database interactions.


Hibernate caching is a powerful feature that can significantly enhance the performance of your database operations. However, it should be used judiciously, considering factors such as data volatility and cache configuration.

