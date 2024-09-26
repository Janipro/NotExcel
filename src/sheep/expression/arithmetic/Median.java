package sheep.expression.arithmetic;

import sheep.expression.Expression;

import java.util.Arrays;

/**
 * A median operation. Median operations must have the operator name "MEDIAN".
 */
public class Median extends Function {

    /**
     * Construct a new median expression.
     * @param arguments A sequence of sub-expressions to perform the operation upon.
     * @requires arguments.length > 0.
     */
    protected Median(Expression[] arguments) {
        super("MEDIAN", arguments);
    }

    /**
     * Perform a median operation over the list of arguments.
     * @param arguments A list of numbers to perform the operation upon.
     * @return the median of the arguments.
     */
    @Override
    protected long perform(long[] arguments) {
        var argsCopy = Arrays.stream(arguments).sorted().toArray();

        if (argsCopy.length % 2 == 0) {
            return (argsCopy[argsCopy.length / 2] + argsCopy[(argsCopy.length / 2) - 1]) / 2;
        }
        return argsCopy[argsCopy.length / 2];
    }
}
