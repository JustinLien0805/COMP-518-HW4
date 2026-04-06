package dsl;

import java.util.ArrayDeque;
import java.util.Queue;

import utils.Or;

// Feedback composition.

public class Loop<A,B> implements Query<A,B> {

	// TODO
	private final Query<Or<A,B>,B> q;
	private Queue<B> pending;
	private boolean initializing;
	private Sink<B> loopSink;

	public Loop(Query<Or<A,B>,B> q) {
		// TODO
		this.q = q;
		this.pending = new ArrayDeque<>();
		this.initializing = false;
		this.loopSink = null;
	}

	@Override
	public void start(Sink<B> sink) {
		// TODO
		pending = new ArrayDeque<>();
		initializing = true;
		loopSink = new Sink<B>() {
			@Override
			public void next(B item) {
				sink.next(item);
				if (initializing) {
					pending.add(item);
				} else {
					q.next(Or.inr(item), this);
				}
			}

			@Override
			public void end() {
				// handled by the outer query
			}
		};
		q.start(loopSink);
		initializing = false;
		while (!pending.isEmpty()) {
			q.next(Or.inr(pending.remove()), loopSink);
		}
	}

	@Override
	public void next(A item, Sink<B> sink) {
		// TODO
		q.next(Or.inl(item), loopSink);
	}

	@Override
	public void end(Sink<B> sink) {
		// TODO
		q.end(loopSink);
		sink.end();
	}
	
}
