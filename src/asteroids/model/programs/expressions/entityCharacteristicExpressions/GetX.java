package asteroids.model.programs.expressions.entityCharacteristicExpressions;

import asteroids.model.programs.expressions.Expression;

public class GetX extends Expression<Object> {

	public GetX(Expression E) {
		this.expression = E;
	}
	
	private Expression getExpression() {
		return this.expression;
	}
	
	Expression expression;
	
	@Override
	public Object getValue() {
		return this.getExpression().getStatement().getProgram().getShip().getPosition().getX();
	}
	

}
