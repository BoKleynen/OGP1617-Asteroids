	
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
import asteroids.model.programs.statements.If;
import asteroids.model.programs.statements.Print;
import asteroids.model.programs.statements.Sequence;
import asteroids.model.programs.statements.Statement;
import asteroids.model.programs.statements.While;
import asteroids.model.programs.statements.actionStatements.ActionStatement;
import asteroids.model.programs.statements.actionStatements.Turn;

	
public class testNextStatement {
		
	@Test
	public void testSimpleStatementNext() {
		Assignment A = new Assignment("Hello", new ValueExpression<Double>(5.0));
		assertTrue(A.next().equals(A));
		assertTrue(A.next() == null);
		assertTrue(A.next() == null);

		A.resetNext();
		assertTrue(A.next().equals(A));
		assertTrue(A.next() == null);
		assertTrue(A.next() == null);
		
		Break B = new Break();
		assertTrue(B.next().equals(B));
		assertTrue(B.next() == null);
		assertTrue(B.next() == null);

		B.resetNext();
		assertTrue(B.next().equals(B));
		assertTrue(B.next() == null);
		assertTrue(B.next() == null);
	}
	
	@Test
	public void testActionStatementNext() {
		ValueExpression<Double> d = new ValueExpression<Double>(0.5);
		ActionStatement turn = new Turn(d);
		
		assertTrue(turn.next().equals(turn));
		assertTrue(turn.next() == null);
		assertTrue(turn.next() == null);
		
		turn.resetNext();
		
		assertTrue(turn.next().equals(turn));
		assertTrue(turn.next() == null);
		assertTrue(turn.next() == null);	
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
		
		Sequence seq = new Sequence(lst);
		
		assertTrue(seq.next().equals(A));
		assertTrue(seq.next().equals(P));
		assertTrue(seq.next().equals(B));
		assertTrue(seq.next() == null);
		assertTrue(seq.next() == null);
		
		seq.resetNext();
		
		assertTrue(seq.next().equals(A));
		assertTrue(seq.next().equals(P));
		assertTrue(seq.next().equals(B));
		assertTrue(seq.next() == null);
		assertTrue(seq.next() == null);
	}
	
	@Test
	public void testIfNext() {
		Assignment A = new Assignment("Hello", new ValueExpression<Double>(5.0));
		Print P = new Print(new ValueExpression<Double>(5.0));
		Break B = new Break();
		List<Statement> lst = new ArrayList<>();
		lst.add(A);
		lst.add(P);
		lst.add(B);
		
		Sequence seq = new Sequence(lst);
		
		ValueExpression<Boolean> condition = new ValueExpression<Boolean>(true);
		If ifStatement = new If(condition, seq, P);
		
		assertTrue(ifStatement.next().equals(A));
		assertTrue(ifStatement.next().equals(P));
		assertTrue(ifStatement.next().equals(B));
		assertTrue(ifStatement.next() == null);
		assertTrue(ifStatement.next() == null);
		assertTrue(ifStatement.next() == null);
		
		condition = new ValueExpression<Boolean>(false);
		ifStatement = new If(condition, seq, P);
		
		assertTrue(ifStatement.next().equals(P));
		assertTrue(ifStatement.next() == null);
		assertTrue(ifStatement.next() == null);
		assertTrue(ifStatement.next() == null);
	}
	
	@Test
	public void testWhileNext() {
		Assignment A = new Assignment("Hello", new ValueExpression<Double>(5.0));
		Print P = new Print(new ValueExpression<Double>(5.0));
		List<Statement> lst = new ArrayList<>();
		Break B = new Break();
		lst.add(A);
		lst.add(P);
		lst.add(P);
		lst.add(B);
		
		Sequence seqWithoutBreak = new Sequence(lst);
		
		// When condition remains true, the sequence will be returned in order forever
		ValueExpression<Boolean> condition = new ValueExpression<Boolean>(true);
		While whileStatement = new While(condition, seqWithoutBreak);
		
		assertTrue(whileStatement.next().equals(A));
		assertTrue(whileStatement.next().equals(P));
		assertTrue(whileStatement.next().equals(P));
		assertTrue(whileStatement.next().equals(B));
		assertTrue(whileStatement.next().equals(A));
		assertTrue(whileStatement.next().equals(P));
		
		// When condition is false, null we be returned
		condition = new ValueExpression<Boolean>(false);
		whileStatement = new While(condition, seqWithoutBreak);
		
		assertTrue(whileStatement.next() == null);
		assertTrue(whileStatement.next() == null);
		assertTrue(whileStatement.next() == null);
		assertTrue(whileStatement.next() == null);
		
	}
}
