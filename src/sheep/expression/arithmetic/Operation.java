package sheep.expression.arithmetic;

import sheep.expression.Expression;
import sheep.expression.TypeError;
import sheep.expression.basic.Constant;
import sheep.expression.basic.Nothing;

import java.util.*;

/**
 * An operation expression.
 * Performs some operation on a sequence of sub-expressions.
 * @provided
 */
public abstract class Operation extends Expression {
    protected final String operator;
    protected final Expression[] arguments;

    protected Operation(String operator, Expression[] arguments) {
        this.operator = operator;
        this.arguments = arguments;
    }

    /**
     * Construct a new addition (plus) operation.
     *
     * @param arguments A sequence of sub-expressions to perform the operation upon.
     * @return A plus expression.
     * @requires arguments.length &gt; 0
     */
    public static Operation plus(Expression[] arguments) {
        return new Plus(arguments);
    }

    /**
     * Construct a new list (comma) operation.
     *
     * @param arguments A sequence of sub-expressions to perform the operation upon.
     * @return A list expression.
     * @requires arguments.length &gt; 0
     */
    public static Operation list(Expression[] arguments) {
        return new ExpressionList(arguments);
    }

    /**
     * Construct a new subtraction (minus) operation.
     *
     * @param arguments A sequence of sub-expressions to perform the operation upon.
     * @return A minus expression.
     * @requires arguments.length &gt; 0
     */
    public static Operation minus(Expression[] arguments) {
        return new Minus(arguments);
    }

    /**
     * Construct a new multiplication (times) operation.
     *
     * @param arguments A sequence of sub-expressions to perform the operation upon.
     * @return A times expression.
     * @requires arguments.length &gt; 0
     */
    public static Operation times(Expression[] arguments) {
        return new Times(arguments);
    }

    /**
     * Construct a new division (divide) operation.
     *
     * @param arguments A sequence of sub-expressions to perform the operation upon.
     * @return A divide expression.
     * @requires arguments.length &gt; 0
     */
    public static Operation divide(Expression[] arguments) {
        return new Divide(arguments);
    }

    /**
     * Construct a new less than (less) operation.
     *
     * @param arguments A sequence of sub-expressions to perform the operation upon.
     * @return A less expression.
     * @requires arguments.length &gt; 0
     */
    public static Operation less(Expression[] arguments) {
        return new Less(arguments);
    }

    /**
     * Construct a new equal to (equal) operation.
     *
     * @param arguments A sequence of sub-expressions to perform the operation upon.
     * @return A equal expression.
     * @requires arguments.length &gt; 0
     */
    public static Operation equal(Expression[] arguments) {
        return new Equal(arguments);
    }

    /**
     * Construct a new mean operation.
     *
     * @param arguments A sequence of sub-expressions to perform the operation upon.
     * @return A mean expression.
     * @requires arguments.length &gt; 0
     */
    public static Operation mean(Expression[] arguments) {
        return new Mean(arguments);
    }

    /**
     * Construct a new median operation.
     *
     * @param arguments A sequence of sub-expressions to perform the operation upon.
     * @return A median expression.
     * @requires arguments.length &gt; 0
     */
    public static Operation median(Expression[] arguments) {
        return new Median(arguments);
    }

    /**
     * Construct a new identity operation.
     *
     * @param arguments A sequence of sub-expressions to perform the identity function upon.
     * @return An identity function expression.
     * @requires arguments.length &gt; 0
     */
    public static Operation identity(Expression[] arguments) {
        return new Identity(arguments);
    }

    /**
     * Build a new Operator
     *
     * @param operator  The identifier for the operator type
     * @param arguments The arguments to the operator.
     * @return An operator expression
     */
    public static Operation build(String operator, Expression[] arguments) {
        return switch (operator) {
            case "+" -> plus(arguments);
            case "-" -> minus(arguments);
            case "*" -> times(arguments);
            case "/" -> divide(arguments);
            case "<" -> less(arguments);
            case "=" -> equal(arguments);
            case "MEAN" -> mean(arguments);
            case "MEDIAN" -> median(arguments);
            case "," -> list(arguments);
            case "" -> identity(arguments);
            default -> throw new RuntimeException();
        };
    }

    /**
     * Get a string representation of this Operation's operator.
     *
     * @return this function's operator as a string
     */
    public String getOperator() {
        return operator;
    }

    /**
     * Get a list of this operator's arguments.
     *
     * @return a list of this operator's arguments
     */
    public List<Expression> getExpressions() {
        return new ArrayList<>(List.of(this.arguments));
    }

    /**
     * Create an operation of the same type as this operation with a new list of expressions.
     *
     * @param expressions the new arguments to the new function.
     * @return a Function expression
     */
    public Operation withExpressions(List<Expression> expressions) {
        expressions = expressions.stream().filter((e) -> !(e instanceof Nothing)).toList();
        return build(this.operator, expressions.toArray(expressions.toArray(new Expression[0])));
    }

    /**
     * Dependencies of the operator expression. The dependencies of an operator expression are the union of all
     * sub-expressions.
     * <pre>
     * {@code
     * Operator plus = Operator.plus(new Expression[]{Operator.minus(new Expression[]{new Reference("A1"), new Reference("A2")}), new Reference("B1")});
     * plus.dependencies() // {"A1", "A2", "B1"}
     * }</pre>
     *
     * @return A set containing the union of all sub-expression dependencies.
     */
    @Override
    public Set<String> dependencies() {
        Set<String> dependencies = new HashSet<>();
        for (Expression expression : arguments) {
            dependencies.addAll(expression.dependencies());
        }
        return dependencies;
    }

    @Override
    public String render() {
        return this.toString();
    }

    /**
     * The result of evaluating this expression.
     * <p>
     * An operation expression will evaluate to a {@link Constant} expression that stores the result of performing the
     * specific operation.
     * <p>
     * During evaluation the operation should evaluate each sub-expression and convert the resulting values to numeric
     * values to perform the operation.
     *
     * @param state A mapping of references to the expression they hold.
     * @return A constant expression of the result.
     * @throws TypeError If any of the sub-expressions cannot be converted to a numeric value.
     */
    @Override
    public Expression value(Map<String, Expression> state) throws TypeError {
        long[] values = new long[arguments.length];
        for (int i = 0; i < arguments.length; i++) {
            values[i] = arguments[i].value(state).value();
        }
        return new Constant(perform(values));
    }

    /**
     * Evaluate the expression to a numeric value. For operation expressions, a type error will always be thrown.
     *
     * @return Nothing will be returned as a {@link TypeError} is always thrown.
     * @throws TypeError Will always be thrown by {@link Operation}.
     */
    @Override
    public long value() throws TypeError {
        throw new TypeError();
    }

    /**
     * Perform the operation over a list of numbers.
     * <p>
     * This is an abstract method that should be implemented by each subclass.
     *
     * @param arguments A list of numbers to perform the operation upon.
     * @return The result of performing the arithmetic operation.
     */
    protected abstract long perform(long[] arguments);
}
