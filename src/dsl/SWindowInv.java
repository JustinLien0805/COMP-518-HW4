package dsl;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.function.BinaryOperator;

// Efficient algorithm for aggregation over a sliding window.
// It assumes that there is a 'remove' operation for updating
// the aggregate when an element is evicted from the window.

public class SWindowInv<A> implements Query<A,A> {

	// TODO
	private final int wndSize;
	private final A init;
	private final BinaryOperator<A> insert;
	private final BinaryOperator<A> remove;
	private Queue<A> window;
	private A acc;

	public SWindowInv
	(int wndSize, A init, BinaryOperator<A> insert, BinaryOperator<A> remove)
	{
		if (wndSize < 1) {
			throw new IllegalArgumentException("window size should be >= 1");
		}
		
		// TODO
		this.wndSize = wndSize;
		this.init = init;
		this.insert = insert;
		this.remove = remove;
		this.window = new ArrayDeque<>();
		this.acc = init;
	}

	@Override
	public void start(Sink<A> sink) {
		// TODO
		window = new ArrayDeque<>();
		acc = init;
	}

	@Override
	public void next(A item, Sink<A> sink) {
		// TODO
		window.add(item);
		acc = insert.apply(acc, item);
		if (window.size() > wndSize) {
			acc = remove.apply(acc, window.remove());
		}
		if (window.size() == wndSize) {
			sink.next(acc);
		}
	}

	@Override
	public void end(Sink<A> sink) {
		// TODO
		sink.end();
	}
	
}
