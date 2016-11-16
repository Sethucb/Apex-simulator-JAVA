package Processor;

public class CycleListener {
	private long tempResult;
	private long finalResult;
	public Integer cycle;
	
	public CycleListener(int cycle) {
		this.cycle = cycle;
	}
	
	public CycleListener(Processor processor) {
		this(0);
		processor.cycleListener.add(this);		
	}
	
	public Long read() {
		return finalResult;
	}
	
	public Long temRread() {
		return tempResult;
	}

	public void write(long result) {
		this.tempResult = result;		
	}

	public void ChangeCycle(CycleListener cL) {
		finalResult = tempResult;
	}

}