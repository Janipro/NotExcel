package sheep.expression.arithmetic;

import sheep.expression.Expression;

/**
 * The identity function expression.
 * Identity functions must have the operator name "".
 * @provided
 */
public class Identity extends Function {
    /**
     * Construct a new identity expression. "()"
     *
     * @param arguments A sequence of sub-expressions to perform the operation upon.
     * @requires arguments.length &gt; 0
     */
    protected Identity(Expression[] arguments) {
        super("", arguments);
    }

    /**
     * Perform an identity operation on the first element of the list of arguments.
     *
     * @param arguments A list of numbers to perform the operation upon.
     * @return The first argument.
     */
    @Override
    protected long perform(long[] arguments) {
        return arguments[0];
    }
}
