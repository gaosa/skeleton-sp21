package gitlet;

import java.io.File;
import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
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
    // All the branches
    // Key is branch name, value is sha1 of commit
    private Map<String, String> branches;
    private String currentBranch;

    /* TODO: fill in the rest of this class. */
    public static void init() throws GitletException {
        if (GITLET_DIR.exists()) {
            throw new GitletException("A Gitlet version-control system already exists in the current directory.");
        }
        if (!GITLET_DIR.mkdir()) {
            throw new GitletException("Cannot init Gitlet.");
        }

        // Create and save the initial commit
        // new Date(0) is the time 0
        Commit initialCommit = new Commit("initial commit", new Date(0));
        byte[] content = Utils.serialize(initialCommit);
        String sha1 = Utils.sha1(content);
        Utils.writeObject(new File(GITLET_DIR, sha1), content);

        // Save metadata (branches, current branch)
        new Repository(Collections.singletonMap("master", sha1), "master").save();
    }

    // This is private because only init() called this function
    private Repository(Map<String, String> branches, String currentBranch) {
        this.branches = branches;
        this.currentBranch = currentBranch;
    }

    // Save metadata (branches, current branch)
    public void save() {
        Utils.writeObject(new File(GITLET_DIR, "metadata"), Utils.serialize(this));
    }
}
