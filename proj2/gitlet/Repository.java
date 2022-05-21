package gitlet;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
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
    // File that stores metadata
    public static final File METADATA_FILE = join(CWD, ".gitlet", "metadata");
    // All the branches
    // Key is branch name, value is index of commit in the commits
    private Map<String, Integer> branches;
    private String currentBranch;

    // Staging area, key is filename, value is sha1
    private Map<String, String> stagingAdd;
    private Set<String> stagingRemove;

    // Assume commits are organized as a list, not a tree for now
    private List<String> commits;

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
        Commit initialCommit = new Commit("initial commit", new Date(0), new HashMap<>());
        String sha1 = Utils.sha1(Utils.serialize(initialCommit));
        Utils.writeObject(new File(GITLET_DIR, sha1), initialCommit);
        List<String> commits = new ArrayList<>();
        commits.add(sha1);

        // Save metadata (branches, current branch)
        Map<String, Integer> branches = new HashMap<>();
        branches.put("master", 0);
        new Repository(branches, "master", commits).save();
    }

    public static Repository load() throws GitletException {
        if (!METADATA_FILE.isFile()) {
            throw new GitletException("Not in an initialized Gitlet directory.");
        }
        return Utils.readObject(METADATA_FILE, Repository.class);
    }

    // This is private because only init() called this function
    private Repository(Map<String, Integer> branches, String currentBranch, List<String> commits) {
        this.branches = branches;
        this.currentBranch = currentBranch;
        this.stagingAdd = new HashMap<>();
        this.stagingRemove = new HashSet<>();
        this.commits = commits;
    }

    // Save metadata (branches, current branch)
    public void save() {
        Utils.writeObject(METADATA_FILE, this);
    }

    public void stage(String filename) throws GitletException {
        File file = new File(CWD, filename);
        if (!file.exists()) {
            throw new GitletException("File does not exist.");
        }
        // If this file is already in the staging area, remove it
        stagingRemove.remove(filename);
        stagingAdd.remove(filename);

        byte[] fileContent = Utils.readContents(file);
        String fileSha1 = Utils.sha1(fileContent);
        // If the current working version of the file is identical to the version in the current commit
        // do not stage
        if (!fileSha1.equals(latestCommit().sha1(filename))) {
            stagingAdd.put(filename, fileSha1);
            Utils.writeContents(new File(GITLET_DIR, fileSha1), fileContent);
        }
    }

    private Commit latestCommit() {
        Integer commitIndex = branches.get(currentBranch);
        return Utils.readObject(new File(GITLET_DIR, commits.get(commitIndex)), Commit.class);
    }

    public void commit(String message) throws GitletException {
        if (stagingAdd.isEmpty() && stagingRemove.isEmpty()) {
            throw new GitletException("No changes added to the commit.");
        }
        // Generate and save new commit
        Commit newCommit = latestCommit().commit(message, stagingAdd, stagingRemove);
        String newCommitSha = Utils.sha1(Utils.serialize(newCommit));
        Utils.writeObject(new File(GITLET_DIR, newCommitSha), newCommit);
        // Add new commit to the commits list
        commits.add(newCommitSha);
        // Update head
        branches.put(currentBranch, commits.size() - 1);
        // Clear staging area
        stagingRemove.clear();
        stagingAdd.clear();
    }

    public void checkoutFile(String filename) {
        String sha1 = latestCommit().sha1(filename);
        byte[] content = Utils.readContents(new File(GITLET_DIR, sha1));
        Utils.writeContents(new File(CWD, filename), content);
    }
}
