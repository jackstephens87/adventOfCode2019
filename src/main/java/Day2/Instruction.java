package Day2;

import java.util.Objects;

public class Instruction {

    private OpCode opCode;
    private int firstValue;
    private int secondValue;
    private int changeIndex;

    public Instruction(OpCode opCode, int firstValue, int secondValue, int changeIndex) {
        this.opCode = opCode;
        this.firstValue = firstValue;
        this.secondValue = secondValue;
        this.changeIndex = changeIndex;
    }

    public OpCode getOpCode() {
        return opCode;
    }

    public int getFirstValue() {
        return firstValue;
    }

    public int getSecondValue() {
        return secondValue;
    }

    public int getChangeIndex() {
        return changeIndex;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Instruction)) return false;
        Instruction that = (Instruction) o;
        return getFirstValue() == that.getFirstValue() &&
                getSecondValue() == that.getSecondValue() &&
                getChangeIndex() == that.getChangeIndex() &&
                getOpCode() == that.getOpCode();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getOpCode(), getFirstValue(), getSecondValue(), getChangeIndex());
    }
}
