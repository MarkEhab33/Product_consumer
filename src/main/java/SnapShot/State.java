package SnapShot;

public class State {
	
	Update update;
	long time;
	
	public Update getUpdate() {
		return update;
	}

	public void setUpdate(Update update) {
		this.update = update;
	}
	
	
	public State(Update update,long start) {
		this.update=update;
		time = System.currentTimeMillis()-start;
	}
	
	public State() {}

	@Override
	public String toString() {
		return "State [update=" + update.toString() + ", time=" + time + "]";
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}
	
	
	public Object clone() {
		State newState=new State();
		newState.setTime(this.time);
		newState.setUpdate((Update) this.update.clone());
		return newState;
	}
	
	
}
