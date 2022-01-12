package SnapShot;

public class Update {
	private String queueID;
	private String machineID;
	private String queueNum;
	private String machineColour;


	public String getQueueID() {
		return queueID;
	}

	public void setQueueID(String queueID) {
		this.queueID = queueID;
	}

	public String getMachineID() {
		return machineID;
	}

	public void setMachineID(String machineID) {
		this.machineID = machineID;
	}

	public String getQueueNum() {
		return queueNum;
	}

	public void setQueueNum(String queueNum) {
		this.queueNum = queueNum;
	}

	public String getMachineColour() {
		return machineColour;
	}

	public void setMachineColour(String machineColour) {
		this.machineColour = machineColour;
	}

	
	public Object clone() {
		Update update = new Update();
		update.setMachineColour(this.machineColour);
		update.setMachineID(machineID);
		update.setQueueID(this.queueID);
		update.setQueueNum(queueNum);
		return update;
	}
	
	
}
