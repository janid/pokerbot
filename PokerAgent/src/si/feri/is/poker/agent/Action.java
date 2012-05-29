package si.feri.is.poker.agent;

public class Action {
	
	public Boolean isCall;
	public Boolean isFold;
	public Integer raiseValue;
	
	public Action(Integer raise) {
		this.isCall = false;
		this.isFold = false;
		this.raiseValue = raise;
	}

	public Action(Boolean isCall) {
		this.isCall = isCall;
		this.isFold = !isCall;
		this.raiseValue = null;
	}

	public Boolean isCall() {
		return isCall;
	}

	public Boolean isFold() {
		return isFold;
	}

	public Boolean isRaise() {
		return raiseValue != null;
	}
	
	public Integer getRaiseValue() {
		return raiseValue;
	}
	
	public String toString() {
		String s = "";
		if(isCall) {
			s = "c";
		} else if(isFold) {
			s = "f";
		} else {
			s = "r" + raiseValue;
		}
		return s;
	}
}
