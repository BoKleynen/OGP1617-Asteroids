package asteroids.model.programs;

import java.util.List;
import asteroids.model.Program;
import asteroids.model.programs.expressions.FunctionCall;
import asteroids.model.programs.expressions.binaryExpressions.arithmeticExpressions.*;
import asteroids.model.programs.expressions.entityExpressions.entityCharacteristicExpressions.*;
import asteroids.model.programs.expressions.unaryExpressions.arithmeticExpressions.*;
import asteroids.model.programs.expressions.binaryExpressions.compareExpressions.*;
import asteroids.model.programs.expressions.entityExpressions.*;
import asteroids.model.programs.expressions.unaryExpressions.logicalExpressions.*;
import asteroids.model.programs.expressions.valueExpressions.*;
import asteroids.model.programs.function.Function;
import asteroids.model.programs.statements.*;
import asteroids.model.programs.statements.actionStatements.*;
import asteroids.part3.programs.IProgramFactory;
import asteroids.part3.programs.SourceLocation;
import asteroids.model.programs.expressions.Expression;

/**
 * @author  Bo Kleynen & Yrjo Koyen
 */
public class ProgramFactory implements IProgramFactory<Expression, Statement, Function, Program> {

	/**
	 * Create a program from the given arguments.
	 *
	 * @param functions The function definitions for the program.
	 * @param main      The main statement of the program. Most likely this is a
	 *                  sequence statement.
	 * @return A new program.
	 */
	@Override
	public Program createProgram(List<Function> functions, Statement main) {
		return new Program(functions, main);
	}

	/**
	 * Create a function definition involving the given function name,
	 * parameters and body.
	 *
	 * @param functionName   The name of the function
	 * @param body
	 * @param sourceLocation
	 */
	@Override
	public Function createFunctionDefinition(String functionName, Statement body, SourceLocation sourceLocation) {
		return new Function(functionName, body);
	}

	/**
	 * Create a statement that represents the assignment of a variable.
	 *
	 * @param variableName   The name of the assigned variable
	 * @param value
	 * @param sourceLocation
	 */
	@Override
	public Statement createAssignmentStatement(String variableName, Expression value, SourceLocation sourceLocation) {
		return new Assignment(variableName, value);
	}

	/**
	 * Create a statement that represents a while loop.
	 *
	 * @param condition      The condition of the while loop
	 * @param body           The body of the loop (most likely this is a sequence
	 * @param sourceLocation
	 */
	@Override
	public Statement createWhileStatement(Expression condition, Statement body, SourceLocation sourceLocation) {
		return new While(condition, body);
	}

	/**
	 * Create a statement that represents a break statement.
	 *
	 * @param sourceLocation
	 */
	@Override
	public Statement createBreakStatement(SourceLocation sourceLocation) {
		return new Break();
	}

	/**
	 * Create a statement that represents a return statement.
	 *
	 * @param value          An expression that evaluates to the value to be returned
	 * @param sourceLocation
	 */
	@Override
	public Statement createReturnStatement(Expression value, SourceLocation sourceLocation) {
		return new Return(value);
	}

	/**
	 * Create an if-then-else statement.
	 *
	 * @param condition      The condition of the if statement
	 * @param ifBody         The body of the if-part, which must be executed when the
	 *                       condition evaluates to true
	 * @param elseBody       The body of the else-part, which must be executed when the
	 *                       condition evaluates to false. Can be null if no else clause is
	 * @param sourceLocation
	 */
	@Override
	public Statement createIfStatement(Expression condition, Statement ifBody, Statement elseBody, SourceLocation sourceLocation) {
		return new If(condition, ifBody, elseBody);
	}

	/**
	 * Create a print statement that prints the value obtained by evaluating the
	 * given expression.
	 *
	 * @param value          The expression to evaluate and print
	 * @param sourceLocation
	 */
	@Override
	public Statement createPrintStatement(Expression value, SourceLocation sourceLocation) {
		return new Print(value);
	}

	/**
	 * Create a sequence of statements.
	 *
	 * @param statements     The statements that must be executed in the given order.
	 * @param sourceLocation
	 */
	@Override
	public Statement createSequenceStatement(List<Statement> statements, SourceLocation sourceLocation) {
		return new Sequence(statements);
	}

	/**
	 * Create an expression that evaluates to the current value of the given
	 * variable.
	 *
	 * @param variableName   The name of the variable to read.
	 * @param sourceLocation
	 */
	@Override
	public Expression createReadVariableExpression(String variableName, SourceLocation sourceLocation) {
		return null;
	}

	/**
	 * Create an expression that evaluates to the current value of the given
	 * parameter.
	 *
	 * @param parameterName  The name of the parameter to read.
	 * @param sourceLocation
	 */
	@Override
	public Expression createReadParameterExpression(String parameterName, SourceLocation sourceLocation) {
		return null;
	}

	/**
	 * Create an expression that evaluates to result of calling the given
	 * function with the given list of actual arguments.
	 *
	 * @param functionName   The name of the function to call.
	 * @param actualArgs
	 * @param sourceLocation
	 */
	@Override
	public Expression createFunctionCallExpression(String functionName, List<Expression> actualArgs, SourceLocation sourceLocation) {
		return new FunctionCall(functionName, actualArgs);
	}

	/**
	 * Create an expression that evaluates to the given expression with changed
	 * sign (i.e., negated).
	 *
	 * @param expression
	 * @param sourceLocation
	 */
	@Override
	public Expression createChangeSignExpression(Expression expression, SourceLocation sourceLocation) {
		return new Negate(expression);
	}

	/**
	 * Create an expression that evaluates to true when the given expression
	 * evaluates to false, and vice versa.
	 *
	 * @param expression
	 * @param sourceLocation
	 */
	@Override
	public Expression createNotExpression(Expression expression, SourceLocation sourceLocation) {
		return new Not(expression);
	}

	/**
	 * Creates an expression that represents a literal double value.
	 *
	 * @param value
	 * @param location
	 */
	@Override
	public Expression createDoubleLiteralExpression(double value, SourceLocation location) {
		return new ValueExpression<>(value);
	}

	/**
	 * Creates an expression that represents the null value.
	 *
	 * @param location
	 */
	@Override
	public Expression createNullExpression(SourceLocation location) {
		return new ValueExpression<>(null);
	}

	/**
	 * Creates an expression that represents the self value, evaluating to the
	 * ship that executes the program.
	 *
	 * @param location
	 */
	@Override
	public Expression createSelfExpression(SourceLocation location) {
		return new SelfExpression();
	}

	/**
	 * Creates an expression that evaluates to the ship that is closest to the
	 * ship that is executing the program.
	 *
	 * @param location
	 */
	@Override
	public Expression createShipExpression(SourceLocation location) {
		return new ShipExpression();
	}

	/**
	 * Creates an expression that evaluates to the asteroid that is closest to
	 * the ship that is executing the program.
	 *
	 * @param location
	 */
	@Override
	public Expression createAsteroidExpression(SourceLocation location) {
		return new AsteroidExpression();
	}

	/**
	 * Creates an expression that evaluates to the planetoid that is closest to
	 * the ship that is executing the program.
	 *
	 * @param location
	 */
	@Override
	public Expression createPlanetoidExpression(SourceLocation location) {
		return new PlanetoidExpression();
	}

	/**
	 * Creates an expression that evaluates to one of the bullets fired by the
	 * ship that executes the program.
	 *
	 * @param location
	 */
	@Override
	public Expression createBulletExpression(SourceLocation location) {
		return new BulletExpression();
	}

	/**
	 * Creates an expression that evaluates to the minor planet that is closest
	 * to the ship that is executing the program.
	 *
	 * @param location
	 */
	@Override
	public Expression createPlanetExpression(SourceLocation location) {
		return new MinorPlanetExpression();
	}

	/**
	 * Creates an expression that evaluates to an arbitrary entity in the world
	 * of the ship that is executing the program.
	 *
	 * @param location
	 */
	@Override
	public Expression createAnyExpression(SourceLocation location) {
		return new AnyExpression();
	}

	/**
	 * Returns an expression that evaluates to the position along the x-axis of
	 * the entity to which the given expression evaluates.
	 *
	 * @param expression
	 * @param location
	 */
	@Override
	public Expression createGetXExpression(Expression expression, SourceLocation location) {
		return new GetX(expression);
	}

	/**
	 * Returns an expression that evaluates to the position along the y-axis of
	 * the entity to which the given expression evaluates.
	 *
	 * @param expression
	 * @param location
	 */
	@Override
	public Expression createGetYExpression(Expression expression, SourceLocation location) {
		return new GetY(expression);
	}

	/**
	 * Returns an expression that evaluates to the velocity along the x-axis of
	 * the entity to which the given expression evaluates.
	 *
	 * @param expression
	 * @param location
	 */
	@Override
	public Expression createGetVXExpression(Expression expression, SourceLocation location) {
		return new GetVX(expression);
	}

	/**
	 * Returns an expression that evaluates to the velocity along the y-axis of
	 * the entity to which the given expression evaluates.
	 *
	 * @param expression
	 * @param location
	 */
	@Override
	public Expression createGetVYExpression(Expression expression, SourceLocation location) {
		return null;
	}

	/**
	 * Returns an expression that evaluates to the radius of the entity to which
	 * the given expression evaluates.
	 *
	 * @param expression
	 * @param location
	 */
	@Override
	public Expression createGetRadiusExpression(Expression expression, SourceLocation location) {
		return new GetRadius(expression);
	}

	/**
	 * Returns an expression that evaluates to true if the evaluation of the
	 * first expression yields a value that is less than the value obtained by
	 * evaluating the second expression.
	 *
	 * @param e1
	 * @param e2
	 * @param location
	 */
	@Override
	public Expression createLessThanExpression(Expression e1, Expression e2, SourceLocation location) {
		return new LessThan(e1, e1);
	}

	/**
	 * Returns an expression that evaluates to true if the evaluation of the
	 * first expression yields a value that is equal to the value obtained by
	 * evaluating the second expression.
	 *
	 * @param e1
	 * @param e2
	 * @param location
	 */
	@Override
	public Expression createEqualityExpression(Expression e1, Expression e2, SourceLocation location) {
		return new Equal(e1, e2);
	}

	/**
	 * Returns an expression that evaluates to the addition of the values
	 * obtained by evaluating the first and second given expressions.
	 *
	 * @param e1
	 * @param e2
	 * @param location
	 */
	@Override
	public Expression createAdditionExpression(Expression e1, Expression e2, SourceLocation location) {
		return new Addition(e1, e2);
	}

	/**
	 * Returns an expression that evaluates to the multiplication of the values
	 * obtained by evaluating the first and second given expressions.
	 *
	 * @param e1
	 * @param e2
	 * @param location
	 */
	@Override
	public Expression createMultiplicationExpression(Expression e1, Expression e2, SourceLocation location) {
		return new Multiplication(e1, e2);
	}

	/**
	 * Returns an expression that evaluates to the square root of the value
	 * obtained by evaluating the given expression.
	 *
	 * @param expression
	 * @param location
	 */
	@Override
	public Expression createSqrtExpression(Expression expression, SourceLocation location) {
		return new Sqrt(expression);
	}

	/**
	 * Returns an expression that evaluates to the direction (in radians) of the
	 * ship executing the program.
	 *
	 * @param location
	 */
	@Override
	public Expression createGetDirectionExpression(SourceLocation location) {
		return new GetDirection();
	}

	/**
	 * Returns a statement that turns the thruster of the ship executing the
	 * program on.
	 *
	 * @param location
	 */
	@Override
	public Statement createThrustOnStatement(SourceLocation location) {
		return new EnableThruster();
	}

	/**
	 * Returns a statement that turns the thruster of the ship executing the
	 * program off.
	 *
	 * @param location
	 */
	@Override
	public Statement createThrustOffStatement(SourceLocation location) {
		return new DisableThruster();
	}

	/**
	 * Returns a statement that fires a bullet from the ship that is executing
	 * the program.
	 *
	 * @param location
	 */
	@Override
	public Statement createFireStatement(SourceLocation location) {
		return new FireBullet();
	}

	/**
	 * Returns a statement that makes the ship that is executing the program
	 * turn by the given amount.
	 *
	 * @param angle
	 * @param location
	 */
	@Override
	public Statement createTurnStatement(Expression angle, SourceLocation location) {
		return new Turn(angle);
	}

	/**
	 * Returns a statement that does nothing.
	 *
	 * @param location
	 */
	@Override
	public Statement createSkipStatement(SourceLocation location) {
		return new Skip();
	}
}
