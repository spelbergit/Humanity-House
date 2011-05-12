package nl.spelberg.brandweer.model;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import nl.spelberg.util.AbstractVersioned;
import org.springframework.util.Assert;

@Entity
@Table(name = "PERSON")
@TableGenerator(name = "PERSON_GEN", table = "SEQUENCE_TABLE", pkColumnName = "SEQ_NAME",
        valueColumnName = "SEQ_COUNT", pkColumnValue = "PERSON_SEQ")
@NamedQueries({
        @NamedQuery(name = "Person.all", query = "from Person order by photo.lastModified desc"),
        @NamedQuery(name = "Person.findMostRecent",
                query = "from Person order by photo.lastModified desc"), @NamedQuery(
                name = "Person.findByPhoto",
                query = "from Person where photo.name = :photoName")})
public class Person extends AbstractVersioned {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "PERSON_GEN")
    private Long id;

    @Column
    private String name;

    @Column
    private String email;

    @Embedded
    private Photo photo;

    /**
     * For JPA.
     */
    @Deprecated
    protected Person() {
    }

    public Person(String name, String email, Photo photo) {
        Assert.notNull(name, "name is null");
        Assert.notNull(email, "email is null");
        Assert.notNull(photo, "foto is null");
        this.name = name;
        this.email = email;
        this.photo = photo;
    }

    public Person(Photo photo) {
        Assert.notNull(photo, "foto is null");
        this.photo = photo;
    }

    public Long id() {
        return id;
    }

    public String name() {
        return name;
    }

    public String email() {
        return email;
    }

    public Photo foto() {
        return photo;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Person");
        sb.append("{id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", foto='").append(photo).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
