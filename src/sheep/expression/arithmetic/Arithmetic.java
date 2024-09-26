package sheep.expression.arithmetic;

import sheep.expression.Expression;

import java.util.StringJoiner;


/**
 * An arithmetic expression.
 * Performs arithmetic operations on a sequence of sub-expressions.
 * @provided
 */
public abstract class Arithmetic extends Operation {

    /**
     * Construct a new arithmetic expression.
     *
     * @param operator  The name of the arithmetic operation, e.g. plus.
     * @param arguments A sequence of sub-expressions to perform the operation upon.
     * @requires arguments.length &gt; 0
     */
    protected Arithmetic(String operator, Expression[] arguments) {
        super(operator, arguments);
    }

    /**
     * The string representation of the expression.
     * For arithmetic, this is the sequence of sub-expressions joined
     * by the operator node.
     * <pre>
     * {@code
     * Arithmetic plus = Arithmetic.plus(new Expression[]{new Reference("A1"), new Reference("A2"), new Constant(4)});
     * plus.toString(); // "A1 + A2 + 4"
     * }</pre>
     *
     * @return the string representation of the expression.
     */
    @Override
    public String toString() {
        StringJoiner builder = new StringJoiner(" " + operator + " ");
        for (Expression argument : arguments) {
            builder.add(argument.render());
        }
        return builder.toString();
    }
}
