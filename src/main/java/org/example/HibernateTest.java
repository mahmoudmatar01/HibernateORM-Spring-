package org.example;

import org.hibernate.Session;


public class HibernateTest {
    public static void main(String[] args) {

//      Take an instance from Employee Entity and set initial values
        Employee employee=new Employee();
        employee.setId(1L);
        employee.setName("Mahmoud Matar");

//      Take an instance from Cv Entity and set initial values
        Address employeeAddress=new Address();
        employeeAddress.setCountry("Employee about");
        employeeAddress.setCityName("More information about employee");

//      Take an instance from Department Entity and set initial values
        Department department=new Department();
        department.setName("Human Resource");
//      Take an instance from Department Entity and set initial values
        Manger manger=new Manger();
        department.setName("Mr. Ahmed");

//      Set the employee cv to employee in One To One Relationship
//      Set the employee department to employee in One To Many Relationship
//      add the employee to employee deportment in Many To one Relationship
//      Set the employee manger to employee in Many To Many Relationship
        employee.setEmployeeAddress(employeeAddress);
        employee.setDepartment(department);
        department.getEmployees().add(employee);
        employee.getMangerList().add(manger);
        manger.getEmployeeList().add(employee);


//        Using the Hibernate Api

        // create a session factory and create a session from the session factory
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        // save model objects
        session.save(manger);
        session.save(employeeAddress);
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
        System.out.println(employee.getName()+ " have a Cv id : "+employee.getEmployeeAddress().getId());
        System.out.println(employee.getName()+ " in department: "+employee.getDepartment().getName());
        System.out.println(employee.getName()+ "'s Manger name "+employee.getMangerList().get(0).getName());
        System.out.println(department.getName()+" has First employee : "+department.getEmployees().get(0).getName());
        System.out.println(manger.getName()+" mange First employee "+manger.getEmployeeList().get(0).getName());

    }
}