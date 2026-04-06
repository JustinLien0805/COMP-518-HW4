package dsl;

import utils.functions.Func2;

// Sliding window of size 2.

public class SWindow2<A,B> implements Query<A,B> {

	// TODO
	private final Func2<A,A,B> op;
	private A first;
	private boolean hasFirst;

	public SWindow2(Func2<A,A,B> op) {
		// TODO
		this.op = op;
		this.first = null;
		this.hasFirst = false;
	}

	@Override
	public void start(Sink<B> sink) {
		// TODO
		first = null;
		hasFirst = false;
	}

	@Override
	public void next(A item, Sink<B> sink) {
		// TODO
		if (hasFirst) {
			sink.next(op.apply(first, item));
		}
		first = item;
		hasFirst = true;
	}

	@Override
	public void end(Sink<B> sink) {
		// TODO
		sink.end();
	}
	
}
