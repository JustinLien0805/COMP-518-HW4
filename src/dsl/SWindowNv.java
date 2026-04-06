package dsl;

import java.util.ArrayDeque;
import java.util.Queue;

import utils.functions.Func2;

// Naive algorithm for aggregation over a sliding window.

public class SWindowNv<A,B> implements Query<A,B> {

	// TODO
	private final int wndSize;
	private final B init;
	private final Func2<B,A,B> op;
	private Queue<A> window;

	public SWindowNv(int wndSize, B init, Func2<B,A,B> op) {
		if (wndSize < 1) {
			throw new IllegalArgumentException("window size should be >= 1");
		}

		// TODO
		this.wndSize = wndSize;
		this.init = init;
		this.op = op;
		this.window = new ArrayDeque<>();
	}

	@Override
	public void start(Sink<B> sink) {
		// TODO
		window = new ArrayDeque<>();
	}

	@Override
	public void next(A item, Sink<B> sink) {
		// TODO
		window.add(item);
		if (window.size() > wndSize) {
			window.remove();
		}
		if (window.size() == wndSize) {
			B acc = init;
			for (A x : window) {
				acc = op.apply(acc, x);
			}
			sink.next(acc);
		}
	}

	@Override
	public void end(Sink<B> sink) {
		// TODO
		sink.end();
	}
	
}
