package nl.spelberg.brandweer;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.wicketrad.annotation.FieldOrder;
import org.wicketrad.jpa.propertytable.annotation.DeleteLink;
import org.wicketrad.propertyeditor.annotation.TextArea;
import org.wicketrad.propertyeditor.annotation.TextField;
import org.wicketrad.propertyeditor.annotation.validation.Length;
import org.wicketrad.propertytable.annotation.BeanLink;
import org.wicketrad.propertytable.annotation.LabelProperty;
import org.wicketrad.service.Identifiable;

@Table(name = "mybean")
@Entity
@DeleteLink(page = MyListPage.class, resourceKey = "deleteLink")
@BeanLink(page = MyUpdatePage.class, resourceKey = "editLink")
public class MyBean implements Identifiable<Long> {

    @Column(name = "bean_id")
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "someOtherValue")
    private String someOtherValue;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @FieldOrder(1)
    @TextField
    @Length(min = 5, max = 10)
    @LabelProperty
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @FieldOrder(2)
    @TextArea
    @LabelProperty
    public String getSomeOtherValue() {
        return someOtherValue;
    }

    public void setSomeOtherValue(String value) {
        this.someOtherValue = value;
    }
}
