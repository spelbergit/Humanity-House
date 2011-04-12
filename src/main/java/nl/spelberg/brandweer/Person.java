package nl.spelberg.brandweer;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import org.springframework.util.Assert;

@Entity
@Table(name = "PERSON")
public class Person {

    @Id
    private Long id;

    @Column
    private String name;

    @Column
    private String email;

    /**
     * For JPA.
     */
    protected Person() {
    }

    public Person(String name, String email) {
        Assert.notNull(name, "name is null");
        Assert.notNull(email, "email is null");
        this.name = name;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
