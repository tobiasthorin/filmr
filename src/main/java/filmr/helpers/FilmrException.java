package filmr.helpers;

public class FilmrException extends Exception {

    private final String msg;

    public FilmrException(String msg) {
        super(msg);
        this.msg = msg;
    }
}
