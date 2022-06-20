package gitlet;

import java.io.File;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import static gitlet.Utils.*;

// TODO: any imports you need here

/** Represents a gitlet repository.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 *  @author TODO
 */
public class Repository implements Serializable {
    /**
     * TODO: add instance variables here.
     *
     * List all instance variables of the Repository class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided two examples for you.
     */

    /** The current working directory. */
    public static final File CWD = new File(System.getProperty("user.dir"));
    /** The .gitlet directory. */
    public static final File GITLET_DIR = join(CWD, ".gitlet");
    public static final File GITLET_META = join(GITLET_DIR, "meta");
    private Map<String, String> branches;
    private String curBranch;

    private Repository(Map<String, String> branches, String curBranch) {
        this.branches = branches;
        this.curBranch = curBranch;
    }

    public static void init() {
        if (GITLET_DIR.exists()) {
            throw new GitletException("A Gitlet version-control system already exists in the current directory.");
        }
        if (!GITLET_DIR.mkdir()) {
            throw new GitletException("Cannot create .gitlet dir");
        }
        Commit initialCommit = new Commit("initial commit", new Date(0), null, new HashSet<>());
        String sha1 = writeObjectIfNotExists(initialCommit);
        String curBranch = "master";
        Map<String, String> branches = new HashMap<>();
        branches.put(curBranch, sha1);
        writeObject(GITLET_META, new Repository(branches, curBranch));
    }
}
