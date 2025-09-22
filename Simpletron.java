/*
Taguiam, Jasper P.
BSCS - 3A
Simpletron Memory
*/
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;

public class Simpletron {
    private int pc;
    private String ir;
    private String opcode;
    private String operand;
    private Memory memory;
    private String accumulator;
    private String filename;

    public Simpletron(String filename) {
        this.filename = filename;
        this.memory = new Memory();
        this.pc = 0;
        this.ir = "+0000";
        this.opcode = "00";
        this.operand = "00";
        this.accumulator = "+0000";
        loadProgram();
    }

    private void loadProgram() {
        File file = new File(filename);
        if (!file.exists()) {
            System.out.println("Error: File '" + filename + "' not found.");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.split(";")[0].trim();
                if (line.isEmpty()) continue;
                String[] parts = line.split("\\s+");
                int address = Integer.parseInt(parts[0]);
                String code = parts[1];
                memory.setitem(address, code);
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    public void run() {
        pc = 0;

        while (true) {
            fetch(pc);
            decode(opcode);
            if (opcode.equals("43")) break;
            if (!opcode.equals("40") && !opcode.equals("41") && !opcode.equals("42")) {
                pc++;
            }
        }
        System.out.println("Simpletron execution terminated");
        memory.dump(Integer.parseInt(accumulator), pc, Integer.parseInt(ir), 
                   Integer.parseInt(opcode), Integer.parseInt(operand));
    }

    public void step() {
        if (pc == 0) {
            memory.dump(Integer.parseInt(accumulator), pc, Integer.parseInt(ir), 
                       Integer.parseInt(opcode), Integer.parseInt(operand));
            memory.Press();
        }

        fetch(pc);
        decode(opcode);

        if (opcode.equals("43")) {
            System.out.println("Simpletron execution terminated");
            memory.dump(Integer.parseInt(accumulator), pc, Integer.parseInt(ir), 
                       Integer.parseInt(opcode), Integer.parseInt(operand));
            return;
        }

        if (!opcode.equals("40") && !opcode.equals("41") && !opcode.equals("42")) {
            pc++;
        }

        memory.dump(Integer.parseInt(accumulator), pc, Integer.parseInt(ir), 
                   Integer.parseInt(opcode), Integer.parseInt(operand));
        memory.Press();
    }

    public String fetch(int address) {
        int instr = memory.getitem(address);
        ir = String.format("%+05d", instr);
        opcode = String.format("%02d", instr / 100);
        operand = String.format("%02d", instr % 100);
        pc = address;
        return ir;
    }

    public void store(int address) {
        int accValue = Integer.parseInt(accumulator);
        memory.setitem(address, accValue);
    }

    public void decode(String opcode) {
        int operandInt = Integer.parseInt(operand);
        int accInt = Integer.parseInt(accumulator);

        switch (opcode) {
            case "10":
                System.out.print("Enter value: ");
                int inputValue = memory.readInput();
                memory.setitem(operandInt, inputValue);
                try {
                    while (System.in.available() > 0) {
                        System.in.read();
                    }
                } catch (IOException e) {}
                break;
            case "11":
                System.out.println("Output: " + memory.getitem(operandInt));
                break;
            case "20":
                accInt = memory.getitem(operandInt);
                accumulator = String.format("%+05d", accInt);
                break;
            case "21":
                store(operandInt);
                break;
            case "22":
                accInt = operandInt;
                accumulator = String.format("%+05d", accInt);
                break;
            case "30":
                accInt = Integer.parseInt(accumulator) + memory.getitem(operandInt);
                accumulator = String.format("%+05d", accInt);
                break;
            case "31":
                accInt = Integer.parseInt(accumulator) - memory.getitem(operandInt);
                accumulator = String.format("%+05d", accInt);
                break;
            case "32":
                int divisor = memory.getitem(operandInt);
                if (divisor == 0) {
                    System.out.println("Attempt to divide by zero");
                    System.out.println("Simpletron execution abnormally terminated");
                    memory.dump(Integer.parseInt(accumulator), pc, Integer.parseInt(ir), 
                               Integer.parseInt(opcode), Integer.parseInt(operand));
                    System.exit(1);
                }
                accInt = Integer.parseInt(accumulator) / divisor;
                accumulator = String.format("%+05d", accInt);
                break;
            case "33":
                int modOperand = memory.getitem(operandInt);
                if (modOperand == 0) {
                    System.out.println("Attempt to modulo by zero");
                    System.out.println("Simpletron execution abnormally terminated");
                    memory.dump(Integer.parseInt(accumulator), pc, Integer.parseInt(ir), 
                               Integer.parseInt(opcode), Integer.parseInt(operand));
                    System.exit(1);
                }
                accInt = Integer.parseInt(accumulator) % modOperand;
                accumulator = String.format("%+05d", accInt);
                break;
            case "34":
                accInt = Integer.parseInt(accumulator) * memory.getitem(operandInt);
                accumulator = String.format("%+05d", accInt);
                break;
            case "35":
                accInt = Integer.parseInt(accumulator) + operandInt;
                accumulator = String.format("%+05d", accInt);
                break;
            case "36":
                accInt = Integer.parseInt(accumulator) - operandInt;
                accumulator = String.format("%+05d", accInt);
                break;
            case "37":
                if (operandInt == 0) {
                    System.out.println("Attempt to divide by zero");
                    System.out.println("Simpletron execution abnormally terminated");
                    memory.dump(Integer.parseInt(accumulator), pc, Integer.parseInt(ir), 
                               Integer.parseInt(opcode), Integer.parseInt(operand));
                    System.exit(1);
                }
                accInt = Integer.parseInt(accumulator) / operandInt;
                accumulator = String.format("%+05d", accInt);
                break;
            case "38":
                if (operandInt == 0) {
                    System.out.println("Attempt to modulo by zero");
                    System.out.println("Simpletron execution abnormally terminated");
                    memory.dump(Integer.parseInt(accumulator), pc, Integer.parseInt(ir), 
                               Integer.parseInt(opcode), Integer.parseInt(operand));
                    System.exit(1);
                }
                accInt = Integer.parseInt(accumulator) % operandInt;
                accumulator = String.format("%+05d", accInt);
                break;
            case "39":
                accInt = Integer.parseInt(accumulator) * operandInt;
                accumulator = String.format("%+05d", accInt);
                break;
            case "40":
                pc = operandInt;
                break;
            case "41":
                if (Integer.parseInt(accumulator) < 0) {
                    pc = operandInt;
                } else {
                    pc++;
                }
                break;
            case "42":
                if (Integer.parseInt(accumulator) == 0) {
                    pc = operandInt;
                } else {
                    pc++;
                }
                break;
            case "43":
                break;
            default:
                System.out.println("Invalid opcode: " + opcode + " ");
                System.out.println("Simpletron execution abnormally terminated");
                memory.dump(Integer.parseInt(accumulator), pc, Integer.parseInt(ir), 
                           Integer.parseInt(opcode), Integer.parseInt(operand));
                System.exit(1);
        }
    }

    public static void main(String... args) {
        String filename = "test.sml";
        Simpletron simpletron = new Simpletron(filename);

        while (true) {
            simpletron.step();
            if (simpletron.opcode.equals("43")) break;
        }
    }
}
