package nl.spelberg.util.wicket.popup;

import java.io.Serializable;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.basic.MultiLineLabel;
import org.apache.wicket.markup.html.panel.Panel;

public class YesNoPanel extends Panel {

    public YesNoPanel(String id, String message, final ModalWindow modalWindow, final ConfirmationCallback callback) {
        super(id);

        /*
                Form yesNoForm = new Form("yesNoForm");

                MultiLineLabel messageLabel = new MultiLineLabel("message", message);
                yesNoForm.add(messageLabel);
                modalWindow.setTitle("Please confirm");
                modalWindow.setInitialHeight(200);
                modalWindow.setInitialWidth(350);

                AjaxButton yesButton = new AjaxButton("yesButton", yesNoForm) {

                    @Override
                    protected void onSubmit(AjaxRequestTarget target, Form form) {
                        if (target != null) {
                            callback.confirm(modalWindow, target);
                        }
                    }
                };

                AjaxButton noButton = new AjaxButton("noButton", yesNoForm) {

                    @Override
                    protected void onSubmit(AjaxRequestTarget target, Form form) {
                        if (target != null) {
                            callback.cancel(modalWindow, target);
                        }
                    }
                };

                yesNoForm.add(yesButton);
                yesNoForm.add(noButton);

                add(yesNoForm);
        */

        add(new MultiLineLabel("message", message));
        add(new AjaxFallbackLink<Void>("cancelLink") {
            @Override
            public void onClick(AjaxRequestTarget target) {
                callback.cancel(modalWindow, target);
            }
        });
        add(new AjaxFallbackLink<Void>("confirmLink") {
            @Override
            public void onClick(AjaxRequestTarget target) {
                callback.confirm(modalWindow, target);
            }
        });
    }

    public static ModalWindow createConfirmModal(String id, String title, String modalMessageText,
            final ConfirmationCallback callback) {

        ModalWindow modalWindow = new ModalWindow(id);
        modalWindow.setCookieName(id);
        modalWindow.setTitle(title);
        modalWindow.setInitialHeight(200);
        modalWindow.setInitialWidth(350);
        modalWindow.setContent(new YesNoPanel(modalWindow.getContentId(), modalMessageText, modalWindow, callback));
        modalWindow.setWindowClosedCallback(new ModalWindow.WindowClosedCallback() {

            @Override
            public void onClose(AjaxRequestTarget target) {
                callback.onModalWindowClose(target);
            }
        });

        return modalWindow;
    }

    public static abstract class ConfirmationCallback implements Serializable {

        private boolean confirmed;

        public final void onModalWindowClose(AjaxRequestTarget target) {
            if (Boolean.FALSE.equals(confirmed)) {
                onCancel(target);
            } else if (Boolean.TRUE.equals(confirmed)) {
                onConfirm(target);
            }
        }

        public final void cancel(ModalWindow modalWindow, AjaxRequestTarget target) {
            confirmed = false;
            modalWindow.close(target);
        }

        public final void confirm(ModalWindow modalWindow, AjaxRequestTarget target) {
            confirmed = true;
            modalWindow.close(target);
        }

        public abstract void onConfirm(AjaxRequestTarget target);

        public abstract void onCancel(AjaxRequestTarget target);

    }
}
