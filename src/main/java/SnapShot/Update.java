package SnapShot;

public class Update {
	private String queueID;
	private String MachineID;
	private String queueNum;
	private String MachineColour;
	

	public String getQueueID() {
		return queueID;
	}

	public void setQueueID(String queueID) {
		this.queueID = queueID;
	}

	public String getMachineID() {
		return MachineID;
	}

	public void setMachineID(String machineID) {
		MachineID = machineID;
	}

	public String getQueueNum() {
		return queueNum;
	}

	public void setQueueNum(String queueNum) {
		this.queueNum = queueNum;
	}

	public String getMachineColour() {
		return MachineColour;
	}

	public void setMachineColour(String machineColour) {
		MachineColour = machineColour;
	}

	@Override
	public String toString() {
		return "Update [queueID=" + queueID + ", MachineID=" + MachineID + ", queueNum=" + queueNum + ", MachineColour="
				+ MachineColour + "]";
	}
	
	public Object clone() {
		Update update = new Update();
		update.setMachineColour(this.MachineColour);
		update.setMachineID(MachineID);
		update.setQueueID(this.queueID);
		update.setQueueNum(queueNum);
		return update;
	}
	
	
}
