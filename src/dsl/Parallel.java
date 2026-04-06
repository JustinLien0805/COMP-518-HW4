package dsl;

import java.util.ArrayDeque;
import java.util.Queue;

import utils.functions.Func2;

// A variant of parallel composition, which is similar to 'zip'.

public class Parallel<A, B, C, D> implements Query<A, D> {

	// TODO
	private final Query<A,B> q1;
	private final Query<A,C> q2;
	private final Func2<B,C,D> op;
	private Queue<B> left;
	private Queue<C> right;

	public Parallel(Query<A,B> q1, Query<A,C> q2, Func2<B,C,D> op) {
		// TODO
		this.q1 = q1;
		this.q2 = q2;
		this.op = op;
		this.left = new ArrayDeque<>();
		this.right = new ArrayDeque<>();
	}

	@Override
	public void start(Sink<D> sink) {
		// TODO
		left = new ArrayDeque<>();
		right = new ArrayDeque<>();
		q1.start(new Sink<B>() {
			@Override
			public void next(B item) {
				left.add(item);
				drain(sink);
			}

			@Override
			public void end() {
				// handled by the outer query
			}
		});
		q2.start(new Sink<C>() {
			@Override
			public void next(C item) {
				right.add(item);
				drain(sink);
			}

			@Override
			public void end() {
				// handled by the outer query
			}
		});
	}

	@Override
	public void next(A item, Sink<D> sink) {
		// TODO
		q1.next(item, new Sink<B>() {
			@Override
			public void next(B out) {
				left.add(out);
				drain(sink);
			}

			@Override
			public void end() {
				// handled by the outer query
			}
		});
		q2.next(item, new Sink<C>() {
			@Override
			public void next(C out) {
				right.add(out);
				drain(sink);
			}

			@Override
			public void end() {
				// handled by the outer query
			}
		});
	}

	@Override
	public void end(Sink<D> sink) {
		// TODO
		q1.end(new Sink<B>() {
			@Override
			public void next(B item) {
				left.add(item);
				drain(sink);
			}

			@Override
			public void end() {
				// handled by the outer query
			}
		});
		q2.end(new Sink<C>() {
			@Override
			public void next(C item) {
				right.add(item);
				drain(sink);
			}

			@Override
			public void end() {
				// handled by the outer query
			}
		});
		sink.end();
	}

	private void drain(Sink<D> sink) {
		while (!left.isEmpty() && !right.isEmpty()) {
			sink.next(op.apply(left.remove(), right.remove()));
		}
	}
	
}
