package gitlet;

// TODO: any imports you need here

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/** Represents a gitlet commit object.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 *  @author TODO
 */
public class Commit implements Serializable {
    /**
     * TODO: add instance variables here.
     *
     * List all instance variables of the Commit class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided one example for `message`.
     */

    /** The message of this Commit. */
    private String message;

    private Date date;

    // Key is filename, value is sha1
    private Map<String, String> files;

    public Commit(String message, Date date, Map<String, String> files) {
        this.message = message;
        this.date = date;
        this.files = files;
    }

    // sha1 for file in the current commit
    public String sha1(String file) {
        return files.get(file);
    }
}
