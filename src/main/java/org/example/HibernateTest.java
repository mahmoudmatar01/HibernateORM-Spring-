package org.example;

import org.example.models.*;
import org.example.utils.HibernateUtil;
import org.hibernate.Session;



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

//      Take an instance from Manger Entity and set initial values
        Manger manger=new Manger();
        manger.setName("Mr. Ahmed");

        Manger manger2=new Manger();
        manger2.setName("Mr. Mustafa");

//      Take an instance from FullTime employee
        FullTimeEmployee fullTimeEmployee=new FullTimeEmployee();
        fullTimeEmployee.setName("Mustafa Matar");
        fullTimeEmployee.setSalary(12);

//      Take an instance from FullTime employee
        PartTimeEmployee partTimeEmployee=new PartTimeEmployee();
        partTimeEmployee.setName("Mohamed Matar");
        partTimeEmployee.setSalary(13);


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
        session.save(fullTimeEmployee);
        session.save(partTimeEmployee);
        session.getTransaction().commit();
        session.close();

        // Retrieving Objects using session.get
        employee=null;
        manger=null;
        manger2=null;
        fullTimeEmployee=null;
        partTimeEmployee=null;
        session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        employee = session.get(Employee.class, 1);
        manger = session.get(Manger.class, 1);
        manger2 = session.get(Manger.class, 2);
        partTimeEmployee = session.get(PartTimeEmployee.class, 4);
        fullTimeEmployee = session.get(FullTimeEmployee.class, 3);

//      Initialize lists
        manger2.getEmployeeList().size();
        manger.getEmployeeList().size();

        session.getTransaction().commit();
        session.close();

        System.out.println("Employee Name Retrieved From Database : "+employee.getName());
        System.out.println(employee.getName()+ " have a Cv id : "+employee.getEmployeeAddress().getId());
        System.out.println(employee.getName()+ " in department: "+employee.getDepartment().getName());
        System.out.println(department.getName()+" has employee : ");
        for (Employee employee1:department.getEmployees()){
            System.out.println(employee1.getName());
        }
        System.out.println(manger.getName()+" Mange : ");
        for(Employee employee1:manger.getEmployeeList()){
            System.out.println(employee1.getName());
        }
        System.out.println(manger2.getName()+" Mange : ");
        for(Employee employee1:manger2.getEmployeeList()){
            System.out.println(employee1.getName());
        }
        System.out.println("Part Time Employee name "+partTimeEmployee.getName()+" and it salary : "+partTimeEmployee.getSalary());
        System.out.println("Full Time Employee name "+fullTimeEmployee.getName()+" and it salary : "+fullTimeEmployee.getSalary());

    }
}