package std.demo.local.spel;

import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

public class SPELDemo {
	public static void main(String[] args) {
		ExpressionParser parser = new SpelExpressionParser();
		Expression exp = parser.parseExpression("'put spel expression here'");
		String msg = exp.getValue(String.class); 
		
		System.out.println(msg);
	}
}
