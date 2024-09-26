package sheep.parsing;

import sheep.expression.Expression;
import sheep.expression.ExpressionFactory;
import sheep.expression.InvalidExpression;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A parser of Basic, Arithmetic, and broader functional components.
 */
public class ComplexParser implements Parser {

    /**
     * Factory used to construct parsed expressions.
     */
    private final ExpressionFactory factory;

    /**
     * Construct a new parser.
     * Parsed expressions are constructed using the expression factory.
     * @param factory Factory used to construct parsed expressions.
     */
    public ComplexParser(ExpressionFactory factory) {
        this.factory = factory;
    }

    /*
     * [5] Single version of token parser inspires by SimpleParser.
     * [7] yield statement inspired from top comment.
     */
    private Expression tryParseToken(ComplexScanner.Token token) throws ParseException,
            InvalidExpression {
        return switch (token.type()) {
            case OP -> factory.createOperator(token.name(),
                    new Expression[]{factory.createEmpty()});
            case REFERENCE -> factory.createReference(token.name());
            case CONST -> factory.createConstant(Long.parseLong(token.name()));
            case FUNC -> {
                if (token.contents() != null && !token.contents().isEmpty()) {
                    yield factory.createOperator(token.name(), new Expression[]{splitTokens(
                            ComplexScanner.tokenize(token.contents())
                    )});
                } else {
                    yield factory.createOperator("", new Expression[]{factory.createEmpty()});
                }
            }
        };
    }

    /*
     * Splits a list of tokens on operators (or functions)
     * similar to how SimpleParser splits strings.
     */
    private Expression splitTokens(List<ComplexScanner.Token> tokens) throws ParseException,
            InvalidExpression {
        if (tokens.isEmpty()) {
            return factory.createEmpty();
        }

        if (tokens.size() == 1) {
            return tryParseToken(tokens.getFirst());
        }

        for (int i = 0; i < tokens.size(); i++) {
            var token = tokens.get(i);

            // Split on each operator, similarly to SimpleParser.
            if (token.type() == ComplexScanner.TokenType.OP) {
                var firstHalf  = tokens.subList(0, i);
                var secondHalf = tokens.subList(i + 1, tokens.size());
                var firstHalfExpression = splitTokens(firstHalf);
                var secondHalfExpression = splitTokens(secondHalf);
                return factory.createOperator(token.name(),
                        new Expression[]{firstHalfExpression, secondHalfExpression});
            }

            // Also split functions according to the javadocs.
            if (token.type() == ComplexScanner.TokenType.FUNC) {
                var content = ComplexScanner.tokenize(token.contents());
                var expression = splitTokens(content);
                return factory.createOperator(token.name(), new Expression[]{expression});
            }
        }
        throw new ParseException("Could not parse tokens");
    }

    /**
     Attempt to parse a string expression into an expression.
     If the string contains parentheses "()" than the contents of the parentheses should be parsed.
     The contents between should follow the same rules as the top-level expression.
     e.g. leading and trailing whitespace should be ignored, etc.
     The parenthesised expressions should be constructed using ExpressionFactory::createOperator().
     If the parentheses are preceded by a name (parsing rules identical to reference)
     the operator should have that name, Otherwise it should have the name "".
     If the contents of the parentheses is an ExpressionList the list's expressions
     should be extracted and passed directly to createOperator().

     Any string that contains commas "," should be split on "," and the components between
     should be parsed. The components between should follow the same rules as
     the top-level expression, If any component cannot be parsed, the whole expression
     cannot be parsed. The comma delimited expressions should be constructed
     using ExpressionFactory::createOperator(",", expressions).

     Any remaining String should be parsed identically to SimpleParser
     *
     * @param input A string to attempt to parse.
     * @return The result of parsing the expression.
     * @throws ParseException If the string input is not recognisable as an expression.
     */
    @Override
    public Expression parse(String input) throws ParseException {
        try {
            return splitTokens(ComplexScanner.tokenize(input));
        } catch (InvalidExpression e) {
            throw new ParseException(e);
        }
    }
}
