package sheep.parsing;

import sheep.expression.Expression;
import sheep.expression.ExpressionFactory;
import sheep.expression.InvalidExpression;
import sheep.expression.TypeError;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

class InfoExpr extends Expression {

    private String info;

    public InfoExpr(String info) {
        this.info = info;
    }

    public String getInfo() {
        return "(" + info + ")";
    }

    @Override
    public String toString() {
        return getInfo();
    }

    @Override
    public Set<String> dependencies() {
        return null;
    }

    @Override
    public long value() throws TypeError {
        return 0;
    }

    @Override
    public Expression value(Map<String, Expression> state) throws TypeError {
        return null;
    }

    @Override
    public String render() {
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof InfoExpr infoExpr)){
            return false;
        }

        return Objects.equals(info, infoExpr.info);
    }

    @Override
    public int hashCode() {
        return info != null ? info.hashCode() : 0;
    }
}

public class EchoFactory implements ExpressionFactory {

    @Override
    public Expression createReference(String identifier) {
        return new InfoExpr("Reference: " + identifier);
    }

    @Override
    public Expression createConstant(long value) {
        return new InfoExpr("Constant: " + value);
    }

    @Override
    public Expression createEmpty() {
        return new InfoExpr("Empty");
    }

    @Override
    public Expression createOperator(String name, Object[] args) throws InvalidExpression {
        return new InfoExpr("Operator: " + name + " " + Arrays.toString(args));
    }
}
