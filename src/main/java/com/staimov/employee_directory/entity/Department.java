package com.staimov.employee_directory.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "department", schema = "new_employee_directory")
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Department extends BaseEntity {
    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "department",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH},
            orphanRemoval = false,
            fetch = FetchType.LAZY)
    @ToString.Exclude
    @JsonIgnore
    private Set<Employee> employees;
}
