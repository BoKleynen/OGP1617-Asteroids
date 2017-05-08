import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import asteroids.model.programs.expressions.valueExpressions.ValueExpression;

public class testValueExpression {
	
	@Test
	public void testGetValue() {
		ValueExpression<Double> V1 = new ValueExpression<Double>(5.0);
		ValueExpression<Double> V2 = new ValueExpression<Double>(2.0);
	}
	
	@Test
	public void testModulo() {
		List<Number> myList = new ArrayList<>();
		myList.add(5);
		myList.add(2.13);
		myList.add(156);
		System.out.println(myList.get(0));
		System.out.println(myList.get(1));
		System.out.println(myList.get(2));
	}
}
