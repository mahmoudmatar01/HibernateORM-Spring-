package org.example;

import org.hibernate.Session;


public class Main {
    public static void main(String[] args) {

        Employee employee=new Employee();
        employee.setId(1L);
        employee.setName("Mahmoud Matar");

        // Using the Hibernate Api
        /*
        1. create a session factory
        2. create a session from the session factory
        3. use the session to save model objects
        * */

        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(employee);
        session.getTransaction().commit();



    }
}