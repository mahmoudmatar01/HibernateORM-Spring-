package org.example.models;
import jakarta.persistence.*;
import org.example.models.Manger;

import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
//@DiscriminatorColumn(name = "employee_type", discriminatorType = DiscriminatorType.STRING)
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "employee_name", length = 100, nullable = false)
    private String name;
    @OneToOne
    @JoinColumn(name = "cv_id")
    private Address employeeAddress;
    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @ManyToMany(mappedBy = "employeeList", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
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
