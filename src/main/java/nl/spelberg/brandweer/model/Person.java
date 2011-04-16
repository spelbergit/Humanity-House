package nl.spelberg.brandweer.model;

import java.util.Arrays;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import nl.spelberg.util.AbstractVersioned;
import org.springframework.util.Assert;

@Entity
@Table(name = "PERSON")
public class Person extends AbstractVersioned {

    @Id
    private Long id;

    @Column
    private String name;

    @Column
    private String email;

    @Column
    private String fotoName;

    @Column
    private String fotoType;

    @Column
    private byte[] fotoData;


    /**
     * For JPA.
     */
    @Deprecated
    protected Person() {
    }

    public Person(Long id) {
        this.id = id;
    }

    public Person(String name, String email, String fotoName, String fotoType, byte[] fotoData) {
        Assert.notNull(name, "name is null");
        Assert.notNull(email, "email is null");
        Assert.notNull(fotoName, "foto is null");
        Assert.notNull(fotoType, "fotoType is null");
        Assert.notNull(fotoData, "fotoData is null");
        this.name = name;
        this.email = email;
        this.fotoName = fotoName;
        this.fotoType = fotoType.toLowerCase();
        this.fotoData = fotoData;
        validateFields();
    }

    public Person(String fotoName, String fotoType, byte[] fotoData) {
        Assert.notNull(fotoName, "foto is null");
        Assert.notNull(fotoType, "fotoType is null");
        Assert.notNull(fotoData, "fotoData is null");
        this.fotoName = fotoName;
        this.fotoType = fotoType;
        this.fotoData = fotoData;
        validateFields();
    }

    private void validateFields() {
        Assert.isTrue(Arrays.asList("jpg", "png", "gif").contains(this.fotoType));
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

    public String getFotoName() {
        return fotoName;
    }

    public String getFotoType() {
        return fotoType;
    }

    public byte[] getFotoData() {
        return fotoData;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Person");
        sb.append("{id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", fotoName='").append(fotoName).append('\'');
        sb.append(", fotoType='").append(fotoType).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
