	
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import asteroids.model.programs.expressions.valueExpressions.ValueExpression;
import asteroids.model.programs.statements.Assignment;
import asteroids.model.programs.statements.Break;
import asteroids.model.programs.statements.Print;
	
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
		
		assertTrue(A.next().equals(A));
		assertTrue(A.next() == null);
		assertTrue(A.next() == null);

		A.resetNext();
		assertTrue(A.next().equals(A));
		assertTrue(A.next() == null);
		assertTrue(A.next() == null);
	}
		

}
