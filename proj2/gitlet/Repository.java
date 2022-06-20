package gitlet;

import java.io.File;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
    private Map<String, String> stagingAdd;
    private Set<String> stagingRemove;

    private Repository(Map<String, String> branches, String curBranch) {
        this.branches = branches;
        this.curBranch = curBranch;
        this.stagingAdd = new HashMap<>();
        this.stagingRemove = new HashSet<>();
    }

    public static void init() {
        if (GITLET_DIR.exists()) {
            throw new GitletException("A Gitlet version-control system already exists in the current directory.");
        }
        if (!GITLET_DIR.mkdir()) {
            throw new GitletException("Cannot create .gitlet dir");
        }
        Commit initialCommit = new Commit("initial commit", new Date(0), null, new HashMap<>());
        String sha1 = writeObjectIfNotExists(initialCommit);
        String curBranch = "master";
        Map<String, String> branches = new HashMap<>();
        branches.put(curBranch, sha1);
        new Repository(branches, curBranch).save();
    }

    public static Repository load() {
        try {
            return readObject(GITLET_META, Repository.class);
        } catch (Exception e) {
            throw new GitletException("Not in an initialized Gitlet directory.");
        }
    }

    public void save() {
        writeObject(GITLET_META, this);
    }

    public void add(String filename) {
        File file = join(CWD, filename);
        if (!file.exists()) {
            throw new GitletException("File does not exist.");
        }
        Commit commit = readObject(branches.get(curBranch), Commit.class);
        byte[] content = readContents(file);
        String sha1 = sha1(content);
        // If the current working version of the file is identical
        // to the version in the current commit,
        // do not stage it to be added,
        // and remove it from the staging area if it is already there
        if (sha1.equals(commit.get(filename))) {
            stagingAdd.remove(filename);
            stagingRemove.remove(filename);
        } else if (stagingRemove.contains(filename)) {
            // The file will no longer be staged for removal
            stagingRemove.remove(filename);
        } else {
            // Staging an already-staged file overwrites
            // the previous entry in the staging area with the new contents.
            stagingAdd.put(filename, sha1);
            writeObjectIfNotExists(content);
        }
        save();
    }

    public void rm(String filename) {
        File file = join(CWD, filename);
        if (!file.exists()) {
            throw new GitletException("File does not exist.");
        }
        Commit commit = readObject(branches.get(curBranch), Commit.class);
        if (!stagingAdd.containsKey(filename)
                && !stagingRemove.contains(filename)
                && commit.get(filename) == null) {
            throw new GitletException("No reason to remove the file.");
        }
        // Unstage the file if it is currently staged for addition.
        stagingAdd.remove(filename);
        // If the file is tracked in the current commit,
        // stage it for removal and remove the file from the working directory
        if (!stagingRemove.contains(filename) && commit.get(filename) != null) {
            stagingRemove.add(filename);
            restrictedDelete(file);
        }
        save();
    }

    public void commit(String message) {
        if (stagingAdd.isEmpty() && stagingRemove.isEmpty()) {
            throw new GitletException("No changes added to the commit.");
        }
        if (message.isBlank()) {
            throw new GitletException("Please enter a commit message.");
        }
        String curSha1 = branches.get(curBranch);
        Commit curCommit = readObject(curSha1, Commit.class);
        Commit newCommit = new Commit(message, new Date(), curSha1, curCommit.newFiles(stagingAdd, stagingRemove));
        String newSha1 = writeObjectIfNotExists(newCommit);
        stagingRemove.clear();
        stagingAdd.clear();
        branches.put(curBranch, newSha1);
        save();
    }
}
