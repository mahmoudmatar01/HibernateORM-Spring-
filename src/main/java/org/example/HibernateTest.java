package org.example;

import org.example.models.Address;
import org.example.models.Department;
import org.example.models.Employee;
import org.example.models.Manger;
import org.example.utils.HibernateUtil;
import org.hibernate.Session;

import java.util.List;


public class HibernateTest {
    public static void main(String[] args) {

//      Take an instance from Employee Entity and set initial values
        Employee employee=new Employee();
        employee.setName("Mahmoud Matar");

        Employee employee2=new Employee();
        employee2.setName("Ahmed Matar");

//      Take an instance from Cv Entity and set initial values
        Address employeeAddress=new Address();
        employeeAddress.setCountry("Employee about");
        employeeAddress.setCityName("More information about employee");

//      Take an instance from Department Entity and set initial values
        Department department=new Department();
        department.setName("Human Resource");
//      Take an instance from Department Entity and set initial values
        Manger manger=new Manger();
        manger.setName("Mr. Ahmed");

        Manger manger2=new Manger();
        manger2.setName("Mr. Mustafa");

//      Set the employee cv to employee in One-To-One Relationship
//      Set the employee department to employee in One-To-Many Relationship
//      add the employee to employee deportment in Many-To-one Relationship
//      Set the employee manger to employee in Many-To-Many Relationship
        employee.setEmployeeAddress(employeeAddress);
        employee.setDepartment(department);
        department.getEmployees().add(employee);
        manger.getEmployeeList().add(employee);
        manger2.getEmployeeList().add(employee);
        manger2.getEmployeeList().add(employee2);


//        Using the Hibernate Api

        // create a session factory and create a session from the session factory
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        // save model objects
        session.save(employee);
        session.save(employee2);
        session.save(manger);
        session.save(manger2);
        session.save(employeeAddress);
        session.save(department);
        session.getTransaction().commit();
        session.close();

        // Retrieving Objects using session.get
        employee=null;
        manger=null;
        manger2=null;
        session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        employee = session.get(Employee.class, 1);
        manger = session.get(Manger.class, 1);
        manger2 = session.get(Manger.class, 2);

        session.getTransaction().commit();
        session.close();

        System.out.println("Employee Name Retrieved From Database : "+employee.getName());
        System.out.println(employee.getName()+ " have a Cv id : "+employee.getEmployeeAddress().getId());
        System.out.println(employee.getName()+ " in department: "+employee.getDepartment().getName());
        System.out.println(employee.getName()+ "'s Manger name "+employee.getMangerList().get(0).getName());
        System.out.println(department.getName()+" has First employee : "+department.getEmployees().get(0).getName());

    }
}