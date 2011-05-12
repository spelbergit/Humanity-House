package nl.spelberg.util.wicket;

public enum ContentType {
    TEXT_CMD("text/cmd"),
    TEXT_CSS("text/css"),
    TEXT_CSV("text/csv"),
    TEXT_HTML("text/html"),
    TEXT_JAVASCRIPT("text/javascript"),
    TEXT_PLAIN("text/plain"),
    TEXT_XML("text/xml");

    private final String value;

    ContentType(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
