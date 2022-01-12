package SnapShot;

public class State {
	
	private Update update;
	private long time;
	
	public State(Update update,long start) {
		this.update=update;
		time = System.currentTimeMillis()-start;
	}
	
	public State() {}
	
	public Update getUpdate() {
		return update;
	}

	public void setUpdate(Update update) {
		this.update = update;
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
