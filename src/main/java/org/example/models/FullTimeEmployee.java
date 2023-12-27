package org.example.models;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
//@DiscriminatorValue("FT_EMPLOYEE")
public class FullTimeEmployee extends Employee{
    private int salary;

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }
}
