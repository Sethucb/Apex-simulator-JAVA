package Apex_Simulator;

import Utility.Constants;

public class Register {
	private long []registers = new long[Constants.REG_COUNT];
	//private long reg_X;
	
	/**
	 * Constructor for Register initializes the registers.
	 */
	public Register() {
		//reset all registers to 0
		for(int i=0; i < Constants.REG_COUNT; ++i){
			registers[i] = 0;
		}
	}
	
	/**
	 * getReg_X method returns the last register R16 reserved for X register
	 * @return register R16 reserved for register X 
	 */
	public long getReg_X() {
		return registers[Constants.REG_COUNT-1];
	}

	/**
	 * setReg_X method sets the last register R16 reserved for X register with given value
	 * @param reg_X of type long, value of register R16 reserved for register X 
	 */
	public void setReg_X(long reg_X) {
		registers[Constants.REG_COUNT-1] = reg_X;
	}

	/**
	 * writeReg method writes the register value to the relevant register
	 * @param index of type int, specifies the register (from R0 to R15) for which the value should be written
	 * @param data of type long, contains data or value needed to be written to the given register
	 */
	public long writeReg(int index, long data) throws Exception{
		if(index >= 0 && index < Constants.REG_COUNT)
			return this.registers[index] = data;
		else
			throw new Exception("Illegal register address : R"+index);
	}
	
	/**
	 * readReg method reads the register value from the given register
	 * @param index of type int, specifies the register (from R0 to R15) from which the value should be read
	 */
	public long readReg(int index) throws Exception{
		if(index >= 0 && index < Constants.REG_COUNT)
			return this.registers[index];
		else
			throw new Exception("Illegal register address : R"+index);
	}
}
