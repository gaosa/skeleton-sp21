package gitlet;

/** Driver class for Gitlet, a subset of the Git version-control system.
 *  @author TODO
 */
public class Main {

    /** Usage: java gitlet.Main ARGS, where ARGS contains
     *  <COMMAND> <OPERAND1> <OPERAND2> ... 
     */
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Please enter a command.");
            return;
        }
        try {
            String firstArg = args[0];
            // Move it out of switch because this is a special case
            if (firstArg.equals("init")) {
                if (args.length > 1) {
                    throw new GitletException("Incorrect operands.");
                }
                Repository.init();
                return;
            }
            Repository repository = Repository.load();
            switch (firstArg) {
                case "add":
                    if (args.length != 2) {
                        throw new GitletException("Incorrect operands.");
                    }
                    String filename = args[1];
                    repository.stage(filename);
                    break;
                case "commit":
                    if (args.length != 2) {
                        throw new GitletException("Incorrect operands.");
                    }
                    String message = args[1];
                    if (message.isBlank()) {
                        throw new GitletException("Please enter a commit message.");
                    }
                    repository.commit(message);
                    break;
                // TODO: FILL THE REST IN
                default:
                    throw new GitletException("No command with that name exists.");
            }
            repository.save();
        } catch (GitletException e) {
            System.out.println(e.getMessage());
        }
    }
}
