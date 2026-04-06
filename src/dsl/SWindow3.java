package dsl;

import utils.functions.Func3;

// Sliding window of size 3.

public class SWindow3<A,B> implements Query<A,B> {

	// TODO
	private final Func3<A,A,A,B> op;
	private A first;
	private A second;
	private int size;

	public SWindow3(Func3<A,A,A,B> op) {
		// TODO
		this.op = op;
		this.first = null;
		this.second = null;
		this.size = 0;
	}

	@Override
	public void start(Sink<B> sink) {
		// TODO
		first = null;
		second = null;
		size = 0;
	}

	@Override
	public void next(A item, Sink<B> sink) {
		// TODO
		if (size >= 2) {
			sink.next(op.apply(first, second, item));
			first = second;
			second = item;
			size = 2;
		} else if (size == 1) {
			second = item;
			size = 2;
		} else {
			first = item;
			size = 1;
		}
	}

	@Override
	public void end(Sink<B> sink) {
		// TODO
		sink.end();
	}
	
}
