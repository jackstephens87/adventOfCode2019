package Day2;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IntcodeComputerTest {

    IntcodeComputer computer = new IntcodeComputer();

    @Test
    void setValueAtPosition() {
        ArrayList<Integer> program = new ArrayList<>(Arrays.asList(1,9,10,3,2,3,11,0,99,30,40,50));
        List<Integer> actual = computer.setValueAtAddress(10, program, 18);
        ArrayList<Integer> expected = new ArrayList<>(Arrays.asList(1,9,10,3,2,3,11,0,99,30,18,50));
        assertEquals(expected, actual);
    }

    @Test
    void addIntegers() {
        ArrayList<Integer> program = new ArrayList<>(Arrays.asList(1,9,10,3,99,3,11,0,99,30,40,50));
        ArrayList<Integer> expected = new ArrayList<>(Arrays.asList(1,9,10,70,99,3,11,0,99,30,40,50));
        assertEquals(expected, computer.runProgram(program));
    }

    @Test
    void multiplyIntegers() {
        ArrayList<Integer> program = new ArrayList<>(Arrays.asList(2,9,10,3,99,3,11,0,99,30,40,50));
        ArrayList<Integer> expected = new ArrayList<>(Arrays.asList(2,9,10,1200,99,3,11,0,99,30,40,50));
        assertEquals(expected, computer.runProgram(program));
    }

    @Test
    void getOpCodeForIterationOne() {
        ArrayList<Integer> program = new ArrayList<>(Arrays.asList(1,9,10,70,2,3,11,0,99,30,40,50));
        OpCode opCode = computer.getOpCodeForIteration(1, program);
        assertEquals(OpCode.ADD, opCode);
    }

    @Test
    void getOpCodeForIterationTwo() {
        ArrayList<Integer> program = new ArrayList<>(Arrays.asList(1,9,10,70,2,3,11,0,99,30,40,50));
        OpCode opCode = computer.getOpCodeForIteration(2, program);
        assertEquals(OpCode.MULTIPLY, opCode);
    }

    @Test
    void getOpCodeForIterationThree() {
        ArrayList<Integer> program = new ArrayList<>(Arrays.asList(1,9,10,70,2,3,11,0,99,30,40,50));
        OpCode opCode = computer.getOpCodeForIteration(3, program);
        assertEquals(OpCode.END, opCode);
    }

    @Test
    void getSequence() {
        ArrayList<Integer> program = new ArrayList<>(Arrays.asList(1,9,10,3,2,3,11,0,99,30,40,50));
        Instruction actual = computer.getInstruction(1, program);
        Instruction expected = new Instruction(OpCode.ADD, 9, 10, 3);
        assertEquals(expected, actual);
    }

    @Test
    void processWholeSequence() {
        ArrayList<Integer> program = new ArrayList<>(Arrays.asList(1,9,10,3,2,3,11,0,99,30,40,50));
        ArrayList<Integer> expected = new ArrayList<>(Arrays.asList(3500,9,10,70,2,3,11,0,99,30,40,50));
        assertEquals(expected, computer.runProgram(program));
    }

    @Test
    void processWholeSequenceTwo() {
        ArrayList<Integer> program = new ArrayList<>(Arrays.asList(1,0,0,0,99));
        ArrayList<Integer> expected = new ArrayList<>(Arrays.asList(2,0,0,0,99));
        assertEquals(expected, computer.runProgram(program));
    }

    @Test
    void processWholeSequenceThree() {
        ArrayList<Integer> program = new ArrayList<>(Arrays.asList(2,3,0,3,99));
        ArrayList<Integer> expected = new ArrayList<>(Arrays.asList(2,3,0,6,99));
        assertEquals(expected, computer.runProgram(program));
    }

    @Test
    void processWholeSequenceFour() {
        ArrayList<Integer> program = new ArrayList<>(Arrays.asList(2,4,4,5,99,0));
        ArrayList<Integer> expected = new ArrayList<>(Arrays.asList(2,4,4,5,99,9801));
        assertEquals(expected, computer.runProgram(program));
    }

    @Test
    void processWholeSequenceFive() {
        ArrayList<Integer> program = new ArrayList<>(Arrays.asList(1,1,1,4,99,5,6,0,99));
        ArrayList<Integer> expected = new ArrayList<>(Arrays.asList(30,1,1,4,2,5,6,0,99));
        assertEquals(expected, computer.runProgram(program));
    }

    @Test
    void processDataFromCSV() throws IOException {
        ArrayList<Integer> gravityAssistProgram = readFromCSV();
        List<Integer> actual = computer.runProgramFromAlarmState(gravityAssistProgram, 12, 2);
        assertEquals(Integer.valueOf(2894520), actual.get(0));
    }

    private ArrayList<Integer> readFromCSV() throws IOException {
        ArrayList<Integer> records = new ArrayList<>();
        List<List<Integer>> allRecords = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("C:\\Personal_Development\\adventOfCode2019\\src\\test\\resources\\Day2\\Day2_input1.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                allRecords.add(convertStringArrayToIntegerArray(line.split(",")));
            }
        }
        for (List<Integer> value : allRecords) {
            records.add(value.get(0));
        }
        return records;
    }

    private List<Integer> convertStringArrayToIntegerArray(String[] stringArray) {
        int size = stringArray.length;
        List<Integer> arr = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            arr.add(i, Integer.parseInt(stringArray[i]));
        }
        return arr;
    }

    @Test
    @DisplayName("Given valid memory, when running program from various different alarm states, then it should find a value that has been set")
    void findNounAndVerbForValue() throws IOException {
        ArrayList<Integer> memory = readFromCSV();
        int expectedOutput = 19690720;
        int noun;
        int verb = 0;
        nounLoop:
        for (noun = 0; noun < 100; noun++) {
            for (verb = 0; verb < 100; verb++) {
                ArrayList<Integer> gravityAssistProgram = (ArrayList<Integer>) memory.clone();
                List<Integer> response = computer.runProgramFromAlarmState(gravityAssistProgram, noun, verb);
                if (response.get(0) == expectedOutput) {
                    break nounLoop;
                }
            }
        }

        assertEquals(93, noun);
        assertEquals(42, verb);
        assertEquals(9342, 100* noun + verb);
    }
}