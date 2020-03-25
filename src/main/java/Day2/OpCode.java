package Day2;

import java.util.HashMap;
import java.util.Map;

public enum OpCode {

    ADD(1),
    MULTIPLY(2),
    END(99);

    private final int value;
    private static Map map = new HashMap<>();

    OpCode(int opCode) {
        this.value = opCode;
    }

    static  {
        for (OpCode opCode : OpCode.values()) {
            map.put(opCode.value, opCode);
        }
    }

    public static OpCode valueOf(int opCode) {
        return (OpCode) map.get(opCode);
    }
}
