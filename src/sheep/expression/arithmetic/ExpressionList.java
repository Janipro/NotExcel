package sheep.expression.arithmetic;

import sheep.expression.Expression;

/**
 * A List expression.
 * List operations must have the operator name ",".
 * @provided
 */
public class ExpressionList extends Arithmetic {
    /**
     * Construct a new list expression.
     *
     * @param arguments A sequence of sub-expressions to perform the operation upon.
     * @requires arguments.length &gt; 0
     */
    protected ExpressionList(Expression[] arguments) {
        super(",", arguments);
    }

    /**
     * Perform a list operation over the list of arguments.
     * (Simply return the last value.)
     *
     * @param arguments A list of numbers to perform the operation upon.
     * @return the last argument
     */
    @Override
    protected long perform(long[] arguments) {
        return arguments[arguments.length - 1];
    }
}
