package edu.sjtu.stap.checkmate.framework.instrument;

/**
 * 
 * @author Yilei
 *
 */
public class InstrumentInfo {
	// Full Qualified Name, Identify the Class
	private String className;

	// Sequence Number
	private int seqNumber;

	// Sequence point location;
	private int lines;

	// Thread Tag, number, color, etc.
	private String threadTag;

	// Extension

	public InstrumentInfo(String className, int seqNumber, int lines,
			String threadTag) {
		this.className = className;
		this.seqNumber = seqNumber;
		this.lines = lines;
		this.threadTag = threadTag;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public int getSeqNumber() {
		return seqNumber;
	}

	public void setSeqNumber(int seqNumber) {
		this.seqNumber = seqNumber;
	}

	public int getLines() {
		return lines;
	}

	public void setLines(int lines) {
		this.lines = lines;
	}

	public String getThreadTag() {
		return threadTag;
	}

	public void setThreadTag(String threadTag) {
		this.threadTag = threadTag;
	}
}
