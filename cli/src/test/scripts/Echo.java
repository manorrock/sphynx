/**
 * Echo script.
 *
 * @author Manfred Riem (mriem@manorrock.com)
 */
public class Echo {

    /**
     * Main method.
     * 
     * @param arguments the command-line arguments.
     */
    public static void main(String[] arguments) {
        if (arguments.length > 0) {
            for(String argument : arguments) {
                System.out.print(argument);
            }
        }
        System.out.println();
    }
}
