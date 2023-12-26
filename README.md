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

