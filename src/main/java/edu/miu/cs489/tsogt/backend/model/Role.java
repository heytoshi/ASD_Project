package edu.miu.cs489.tsogt.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer roleId;
    @Column(nullable = false, unique = true)
    @NotBlank
    private String name;
    @ManyToMany(mappedBy = "roles")
    private List<User> users;

    public Role(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (this.getClass() != obj.getClass()) return false;
        Role otherRole = (Role) obj;
        if (this.roleId == null) {
            if (otherRole.roleId != null) return false;
        }
        return (this.roleId.equals(otherRole.roleId)
                && this.name.equals(otherRole.name));
    }

}
