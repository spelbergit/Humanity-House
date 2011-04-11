package nl.spelberg.brandweer;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.PageLink;
import org.wicketrad.jpa.propertytable.DefaultBeanPropertyTableProvider;
import org.wicketrad.propertytable.BeanPropertyTable;
import org.wicketrad.propertytable.IPropertyTableProvider;

public class MyListPage extends WebPage {
    public MyListPage() {
        BeanPropertyTable table = new BeanPropertyTable("table");
        IPropertyTableProvider provider = new DefaultBeanPropertyTableProvider(MyBean.class);
        table.init(provider, MyBean.class, 10);
        table.setStyles(
                "listTableHeader", "listTableFirstRowCell", "listTableSecondRowCell", "listTablePager");
        this.add(table);
        PageLink link = new PageLink("createLink", MyCreatePage.class);
        this.add(link);
    }
}
