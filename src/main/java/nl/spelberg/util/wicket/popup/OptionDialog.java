package nl.spelberg.util.wicket.popup;

import java.util.List;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.model.Model;

public class OptionDialog extends ModalWindow {

    private Model<String> titleModel;
    private Model<String> messageModel;

    public OptionDialog(String id, List<Button> buttons) {
        super(id);

        add(new Label("title", titleModel));
        add(new Label("message", messageModel));

    }

}
