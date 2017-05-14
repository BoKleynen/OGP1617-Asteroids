import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import asteroids.model.Program;
import asteroids.model.programs.expressions.Expression;
import asteroids.model.programs.expressions.valueExpressions.ValueExpression;
import asteroids.model.programs.function.Function;
import asteroids.model.programs.statements.simpleStatements.Assignment;
import asteroids.model.programs.statements.simpleStatements.Break;
import asteroids.model.programs.statements.composedStatements.Sequence;
import asteroids.model.programs.statements.Statement;
import asteroids.model.programs.statements.composedStatements.While;

public class testWhileLoop {
	
	Program P;
	List<Function> F;
	While loop;
	Statement statement;
	Sequence S;
	Break B;
	Expression condition;
	List<Statement> statementList;
	
	@Before
	public void setup() {
		statementList = new ArrayList<>();
		F = new ArrayList<Function>();
		statement = new Assignment("var", new ValueExpression<Integer>(5));
		condition = new ValueExpression<Boolean>(true);
		B = new Break();
	}
	
	@Test
	public void testBreakOutOfWhileLoopWithSequence() {
		statementList.add(statement);
		statementList.add(B);
		S = new Sequence(statementList);
		loop = new While(condition, S);
		P = new Program(F, loop);

		loop.execute();
	}
	
	@Test
	public void testBreakOutOfWhileLoopWithSingleStatement() {
		loop = new While(condition, B);
		P = new Program(F, loop);

		loop.execute();
	}
	
	@Test
	public void testWhileLoopWithFalseCondition() {
		statementList.add(statement);
		statementList.add(B);
		S = new Sequence(statementList);
		condition = new ValueExpression<Boolean>(false);
		loop = new While(condition, S);
		
		loop.execute();
	}
	

}
