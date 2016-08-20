package one.util.huntbugs.ui.views;

import java.util.function.Consumer;

public abstract class SimpleStore<T> {

	protected T data;
	protected Consumer<T> subscriber;
	
	public SimpleStore(T defaultValue) {
		this.data = defaultValue;
	}
	
	public void set(T data) {
		this.data = data;
		if (this.subscriber != null) {
			this.subscriber.accept(data);
		}
	}
	
	public void subscribe(Consumer<T> subscriber) {
		if (this.subscriber != null) {
			throw new IllegalStateException("Only one subscriber is supported");
		}
		this.subscriber = subscriber;
		this.subscriber.accept(data);
	}
	
	public void unsubscribe() {
		if (this.subscriber == null) {
			throw new IllegalStateException("A subscriber already released");
		}
		this.subscriber = null;
	}
	
}
