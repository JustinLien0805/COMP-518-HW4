package dsl;

import java.util.function.Function;

// Apply a function elementwise.

public class Map<A,B> implements Query<A,B> {

	// TODO
	private final Function<A,B> op;

	public Map(Function<A,B> op) {
		// TODO
		this.op = op;
	}

	@Override
	public void start(Sink<B> sink) {
		// TODO
	}

	@Override
	public void next(A item, Sink<B> sink) {
		// TODO
		sink.next(op.apply(item));
	}

	@Override
	public void end(Sink<B> sink) {
		// TODO
		sink.end();
	}
	
}
