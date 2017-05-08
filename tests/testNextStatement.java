	
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import asteroids.model.Program;
import asteroids.model.programs.expressions.valueExpressions.ValueExpression;
import asteroids.model.programs.function.Function;
import asteroids.model.programs.statements.Assignment;
import asteroids.model.programs.statements.Break;
import asteroids.model.programs.statements.Print;
import asteroids.model.programs.statements.Sequence;
import asteroids.model.programs.statements.Statement;
import asteroids.model.programs.statements.While;

	
public class testNextStatement {
		
	@Test
	public void testAssignmentNext() {
		Assignment A = new Assignment("Hello", new ValueExpression<Double>(5.0));
		assertTrue(A.next().equals(A));
		assertTrue(A.next() == null);
		assertTrue(A.next() == null);

		A.resetNext();
		assertTrue(A.next().equals(A));
		assertTrue(A.next() == null);
		assertTrue(A.next() == null);
	}
	
	@Test
	public void testBreakNext() {
		Break A = new Break();
		assertTrue(A.next().equals(A));
		assertTrue(A.next() == null);
		assertTrue(A.next() == null);

		A.resetNext();
		assertTrue(A.next().equals(A));
		assertTrue(A.next() == null);
		assertTrue(A.next() == null);
	}
	
	
	@Test
	public void testSequenceNext() {
		Assignment A = new Assignment("Hello", new ValueExpression<Double>(5.0));
		Print P = new Print(new ValueExpression<Double>(5.0));
		Break B = new Break();
		List<Statement> lst = new ArrayList<>();
		lst.add(A);
		lst.add(P);
		lst.add(B);
	}
}
