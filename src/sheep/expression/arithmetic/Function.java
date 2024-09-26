package sheep.expression.arithmetic;

import sheep.expression.Expression;

import java.util.StringJoiner;

/**
 * A function operation
 * Performs functional operations on a sequence of sub-expressions.
 * @provided
 */
public abstract class Function extends Operation {

    /**
     * Construct a new function expression.
     *
     * @param operator  The name of the function operation, e.g. MEAN
     * @param arguments A sequence of sub-expressions to perform the operation upon.
     * @requires arguments.length &gt; 0
     */
    protected Function(String operator, Expression[] arguments) {
        super(operator, arguments);
    }

    /**
     * The string representation of the expression. For Functions this is the function's identifier followed by
     * sequence of sub-expressions joined by commas "," enclosed in Parentheses "()"
     * <pre>
     * {@code
     * Operation mean = Operation.mean(new Expression[]{new Reference("A1"), new Reference("A2"), new Constant(4)});
     * mean.toString(); // "MEAN(A1, A2, 4)"
     * }</pre>
     *
     * @return the string representation of the expression.
     */
    @Override
    public String toString() {
        StringJoiner builder = new StringJoiner(", ");
        for (Expression argument : arguments) {
            builder.add(argument.render());
        }
        return operator + "(" + builder + ")";
    }
}
