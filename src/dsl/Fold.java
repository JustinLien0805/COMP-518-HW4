package dsl;

import utils.functions.Func2;

// Aggregation (one output item when the stream ends).

public class Fold<A, B> implements Query<A, B> {

	// TODO
	private final B init;
	private final Func2<B,A,B> op;
	private B acc;

	public Fold(B init, Func2<B,A,B> op) {
		// TODO
		this.init = init;
		this.op = op;
		this.acc = init;
	}

	@Override
	public void start(Sink<B> sink) {
		// TODO
		acc = init;
	}

	@Override
	public void next(A item, Sink<B> sink) {
		// TODO
		acc = op.apply(acc, item);
	}

	@Override
	public void end(Sink<B> sink) {
		// TODO
		sink.next(acc);
		sink.end();
	}
	
}
