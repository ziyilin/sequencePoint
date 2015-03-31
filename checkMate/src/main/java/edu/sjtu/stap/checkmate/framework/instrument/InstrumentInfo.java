package edu.sjtu.stap.checkmate.framework.instrument;

/**
 * 
 * @author Yilei
 * 
 */
public class InstrumentInfo implements Comparable {
	private int column, sp;
	private String type, instance;

	public InstrumentInfo(int column, String type, int sp, String instance) {
		this.column = column;
		this.sp = sp;
		this.type = type;
		this.instance = instance;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public int getSp() {
		return sp;
	}

	public void setSp(int sp) {
		this.sp = sp;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getInstance() {
		return instance;
	}

	public void setInstance(String instance) {
		this.instance = instance;
	}

	@Override
	public int compareTo(Object o) {
		InstrumentInfo ii = (InstrumentInfo) o;
		if (this.column == ii.getColumn())
			return 0;
		else
			return this.column < ii.getColumn() ? -1 : 1;
	}
}