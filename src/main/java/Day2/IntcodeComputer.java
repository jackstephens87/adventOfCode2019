package Day2;

import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;

import java.util.ArrayList;
import java.util.List;

public class IntcodeComputer {

    final int INSTRUCTION_LENGTH = 4;

    public List<Integer> runProgramFromAlarmState(ArrayList<Integer> memory, int noun, int verb) {
        ArrayList<Integer> alarmStateMemory = returnProgramToAlarmState(memory, noun, verb);
        return runProgram(alarmStateMemory);
    }

    private ArrayList<Integer> returnProgramToAlarmState(ArrayList<Integer> memory, int noun, int verb) {
        memory.set(1, noun);
        memory.set(2, verb);
        return memory;
    }

    public ArrayList<Integer> runProgram(ArrayList<Integer> memory) {
        ArrayList<Integer> response = (ArrayList<Integer>) memory.clone();
        int instructionPointer = 0;
        int iteration = 1;
        Instruction instruction = new Instruction(OpCode.ADD, 0, 0, 0);
        while (instructionPointer < response.size() && instruction.getOpCode() != OpCode.END) {
            instruction = getInstruction(iteration, response);
            if (instruction.getOpCode() == OpCode.ADD) {
                response = addition(response, instruction);
            } else if (instruction.getOpCode() == OpCode.MULTIPLY) {
                response = multiply(response, instruction);
            } else if (instruction.getOpCode() == OpCode.END) {
                break;
            } else {
                throw new IllegalArgumentException();
            }
            instructionPointer = instructionPointer + INSTRUCTION_LENGTH;
            iteration++;
        }
        return response;
    }

    Instruction getInstruction(int iteration, List<Integer> memory) {
        OpCode opCode = getOpCodeForIteration(iteration, memory);
        int firstValue, secondValue, changeIndex;
        firstValue = secondValue = changeIndex = 0;
        if (opCode != OpCode.END) {
            firstValue = getFirstValue(iteration, memory);
            secondValue = getSecondValue(iteration, memory);
            changeIndex = getChangeIndex(iteration, memory);
        }

        return new Instruction(opCode, firstValue, secondValue, changeIndex);
    }

    OpCode getOpCodeForIteration(int iteration, List<Integer> memory) {
        int index = (iteration - 1) * INSTRUCTION_LENGTH;
        int value = memory.get(index);
        return OpCode.valueOf(value);
    }

    private int getFirstValue(int iteration, List<Integer> memory) {
        int index = (iteration - 1) * INSTRUCTION_LENGTH + 1;
        return memory.get(index);
    }

    private int getSecondValue(int iteration, List<Integer> memory) {
        int index = (iteration - 1) * INSTRUCTION_LENGTH + 2;
        return memory.get(index);
    }

    private int getChangeIndex(int iteration, List<Integer> memory) {
        int index = (iteration - 1) * INSTRUCTION_LENGTH + 3;
        return memory.get(index);
    }

    private ArrayList<Integer> addition(ArrayList<Integer> memory, Instruction instruction) {
        ArrayList<Integer> response = (ArrayList<Integer>) memory.clone();
        int addition = addition(response.get(instruction.getFirstValue()), response.get(instruction.getSecondValue()));
        return setValueAtAddress(instruction.getChangeIndex(), response, addition);
    }

    ArrayList<Integer> setValueAtAddress(int index, ArrayList<Integer> memory, int value) {
        ArrayList<Integer> response = (ArrayList<Integer>) memory.clone();
        response.set(index, value);
        return response;
    }

    private int addition(int inputOne, int inputTwo) {
        return inputOne + inputTwo;
    }

    private ArrayList<Integer> multiply(ArrayList<Integer> memory, Instruction instruction) {
        ArrayList<Integer> response = (ArrayList<Integer>) memory.clone();
        int multiply = multiply(response.get(instruction.getFirstValue()), response.get(instruction.getSecondValue()));
        return setValueAtAddress(instruction.getChangeIndex(), response, multiply);
    }

    private int multiply(Integer inputOne, Integer inputTwo) {
        return inputOne * inputTwo;
    }
}
