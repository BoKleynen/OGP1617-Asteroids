package asteroids.model.programs.expressions.entityCharacteristicExpressions;

import asteroids.model.programs.expressions.Expression;

public class GetY extends Expression<Double> {

	public GetY(Expression E) {
		this.expression = E;
	}
	
	private Expression getExpression() {
		return this.expression;
	}
	
	Expression expression;
	
	@Override
	public Double getValue() {
		return this.getExpression().getStatement().getProgram().getShip().getPosition().getY();
	}
	

}