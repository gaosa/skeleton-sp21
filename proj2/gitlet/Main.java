package gitlet;

/** Driver class for Gitlet, a subset of the Git version-control system.
 *  @author TODO
 */
public class Main {

    /** Usage: java gitlet.Main ARGS, where ARGS contains
     *  <COMMAND> <OPERAND1> <OPERAND2> ... 
     */
    public static void main(String[] args) {
        try {
            if (args.length < 1) {
                throw new GitletException("Please enter a command.");
            }
            String firstArg = args[0];
            switch (firstArg) {
                case "init":
                    if (args.length != 1) {
                        throw new GitletException("Incorrect operands");
                    }
                    Repository.init();
                    break;
                case "add":
                    // TODO: handle the `add [filename]` command
                    break;
                // TODO: FILL THE REST IN
                default:
                    throw new GitletException("No command with that name exists.");
            }
        } catch (GitletException e) {
            System.out.println(e.getMessage());
        }
    }
}
