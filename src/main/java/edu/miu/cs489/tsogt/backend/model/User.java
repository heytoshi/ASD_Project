package edu.miu.cs489.tsogt.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    @NotBlank(message = "* first name is required")
    private String firstName;
    @Column(nullable = false)
    @NotBlank(message = "* last name is required")
    private String lastName;
    @Column(nullable = false, unique = true)
    @NotBlank(message = "* email is required")
    @Email(message = "{errors.invalid_email}")
    private String email;
    @Column(nullable = false)
    @NotBlank(message = "* password is required")
    @Size(min = 8)
    private String password;
    @Column(nullable = false, unique = true)
    @NotBlank(message = "* phone number is is required")
    private String phoneNumber;
    @Column(nullable = false, unique = true)
    @NotBlank(message = "* username is required")
    private String username;

    @OneToOne(cascade = CascadeType.PERSIST)
    private Address address;

    @OneToMany(mappedBy = "owner")
    private List<Book> bookList;

    @OneToMany(mappedBy = "owner")
    private List<Transaction> transactionList;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> roles;

    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;

    public User(String firstName, String lastName, String email, String password, String phoneNumber, String username, boolean accountNonExpired, boolean accountNonLocked, boolean credentialsNonExpired, boolean enabled) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.username = username;
        this.accountNonExpired = accountNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.credentialsNonExpired = credentialsNonExpired;
        this.enabled = enabled;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String[] userRoles = getRoles().stream()
                .map((role) -> role.getName())
                .toArray(String[]::new);
        Collection<GrantedAuthority> authorities = AuthorityUtils.
                createAuthorityList(userRoles);
        return authorities;
    }
}
