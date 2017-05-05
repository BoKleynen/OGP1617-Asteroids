import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import asteroids.model.Program;
import asteroids.model.Ship;
import asteroids.model.programs.expressions.entityCharacteristicExpressions.GetX;
import asteroids.model.programs.expressions.entityCharacteristicExpressions.GetY;
import asteroids.model.programs.expressions.valueExpressions.ValueExpression;
import asteroids.model.programs.function.Function;
import asteroids.model.programs.statements.Assignment;
import asteroids.model.programs.statements.Statement;

public class testGetXY {
	
	ValueExpression<Integer> E;
	Statement S;
	ArrayList<Function> F;
	Program P;
	Ship s;
	
	@Before
	public void setup() {
		E = new ValueExpression<Integer>(10);
		S = new Assignment("Ten", E);
		F = new ArrayList<Function>();
		P = new Program(F, S);
		s = new Ship();

	}
	@Test
	public void testGetX() {
		s.loadProgram(P);
		GetX gx = new GetX(E);
		GetY gy = new GetY(E);
		assertTrue(gx.getValue().equals(s.getPosition().getX()));
		assertTrue(gy.getValue().equals(s.getPosition().getY()));
	}
	
	@Test
	public void testLoadProgram() {
		s.loadProgram(P);
		assertTrue(s.getProgram().equals(P));
		assertTrue(P.getShip().equals(s));
	}

}
