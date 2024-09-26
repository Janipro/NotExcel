package sheep.expression.arithmetic;

import sheep.expression.Expression;

import java.util.Arrays;

/**
 * A mean operation. Mean operations must have the operator name "MEAN".
 */
public class Mean extends Function {

    /**
     * Construct a new mean expression.
     * @param arguments A sequence of sub-expressions to perform the operation upon.
     * @requires arguments.length > 0.
     */
    protected Mean(Expression[] arguments) {
        super("MEAN", arguments);
    }

    /**
     * Perform a mean operation over the list of arguments.
     * @param arguments A list of numbers to perform the operation upon.
     * @return the mean of the arguments.
     */
    @Override
    protected long perform(long[] arguments) {
        return Arrays.stream(arguments).sum() / arguments.length;
    }
}
