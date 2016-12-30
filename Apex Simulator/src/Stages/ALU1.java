package Stages;

import Apex_Simulator.Processor;
import Apex_Simulator.CycleListener;
import Apex_Simulator.ProcessListener;
import Utility.Constants;
import Utility.Instruction;

public class ALU1 implements ProcessListener{
	
	public Processor processor;
	public Instruction instruction;
	public CycleListener pc;	
	public CycleListener result;	

	/**
	 * Constructor for ALU1 stage initializes PC(instruction Address), result(like a latch which has results of the stage).
	 * @param processor a Processor object.
	 */
	public ALU1(Processor processor) {
		pc = new CycleListener(processor);
		result = new CycleListener(processor);
		this.processor = processor;
		processor.processListeners.add(this);
	}
	
	/**
	 * process method performs the ALU1 processes such as stall implementation if stall based on isStall flag from processor, 
	 * reads the source value from source register from decode stage
	 * seeking the required data for processing and implementing forwarding of register to register operations, Load and store operations.
	 * Register-to-register instructions: ADD, SUB, MOVC, MUL, AND, OR, EX-OR (all done on the Integer ALU in two cycles(1st cycle here)). 
	 * You can assume that the result of multiplying two registers will fit into a single register.
	 */
	public void process() {
		try{
			
			instruction = processor.decode.instruction;
		
			if(instruction != null && instruction.opCode.ordinal() >= 10)
			{
				instruction = null;
				return;			
			}		
			processor.decode.readSources();			
			if(processor.isStalled && instruction != null){
				if(instruction.src1 != null || instruction.src2 != null){
									
				  if( instruction.src1Add!=null && processor.writeBack.instruction != null  && processor.writeBack.instruction.dest != null
						   && processor.writeBack.instruction.dest.intValue() == instruction.src1Add){					  
					   instruction.src1 = processor.register.readReg(instruction.src1Add.intValue());
					   instruction.src1Stall = false;
				   }
				  else if(instruction.src1Add!=null && processor.memoryStage.instruction != null  && processor.memoryStage.instruction.dest != null
						   && processor.memoryStage.instruction.dest.intValue() == instruction.src1Add
						   && processor.memoryStage.instruction.opCode != Constants.OpCode.LOAD){
					   instruction.src1 = processor.memoryStage.result.temRread();	
					   instruction.src1Stall = false;
				   }			  
				  
				  
				  if(instruction.src2Add != null && processor.writeBack.instruction != null && processor.writeBack.instruction.dest != null
						   && processor.writeBack.instruction.dest.intValue()  == instruction.src2Add){
					   instruction.src2 = processor.register.readReg(instruction.src2Add.intValue());
					   instruction.src2Stall = false;
				   }				 
				  else if(instruction.src2Add!=null && processor.memoryStage.instruction != null  && processor.memoryStage.instruction.dest != null
						   && processor.memoryStage.instruction.dest.intValue()  == instruction.src2Add
						   && processor.memoryStage.instruction.opCode != Constants.OpCode.LOAD){
					   instruction.src2 = processor.memoryStage.result.temRread();	
					   instruction.src2Stall = false;
				   } 
				  
				  if(!instruction.src1Stall && !instruction.src2Stall){
					  processor.isStalled = false;
					  instruction.src1Stall = false;
					  instruction.src2Stall = false;
				  }
			   }			
			}
			
			if(processor.isStalled){
				instruction = null;
				return;	
			}
			
			pc.write(processor.decode.pc.read());
		
		}
		catch(Exception e)
		{
			e.printStackTrace();			
		}
 }
	
	/**
	 * clearStage method clears the ALU1 stage.
	 */
	public void clearStage() {
		pc.write(0);
		result.write(0);
		instruction = null;
	}
	
	/**
	 * pcValue method returns the pc Value(instruction address) of the ALU1 stage.
	 * @return long value of the pc Value(instruction address)
	 */
	public Long pcValue(){
		return pc.read();
	}
	
	/**
	 * toString method returns the instruction currently in ALU1 as string if instruction is not null or returns the IDLE constants.
	 * @return String of the instruction or IDLE constants
	 */
	@Override
	public String toString() {
		if(instruction == null){
			return Constants.OpCode.IDLE.name();
		}
		else{
			return instruction.toString();
		}
	}

}
