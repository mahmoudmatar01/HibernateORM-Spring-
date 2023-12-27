package org.example;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "employee_table")
public class Employee {
    @Id
    private Long id;
    @Column(name = "employee_name", length = 100, nullable = false)
    private String name;
    @OneToOne
    @JoinColumn(name = "cv_id")
    private Address employeeAddress;
    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @ManyToMany(mappedBy = "employeeList", cascade = CascadeType.ALL)
    private List<Manger> mangerList=new ArrayList<>();

    // setter and getter

    public List<Manger> getMangerList() {
        return mangerList;
    }

    public void setMangerList(List<Manger> mangerList) {
        this.mangerList = mangerList;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Address getEmployeeAddress() {
        return employeeAddress;
    }

    public void setEmployeeAddress(Address employeeCV) {
        this.employeeAddress = employeeCV;
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
