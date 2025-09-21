/*
Taguiam, Jasper P.
BSCS - 3A
Simpletron Simulator
*/
import java.util.Scanner;
import java.io.File;

public class Simpletron {
    Memory memory = new Memory(100);
    int pc = 0;
    int accumulator = 0;
    String ir = "0000";
    int program_size = 0;
    String opcode, operand;
    boolean halt = false;
    Scanner keyboard = new Scanner(System.in);

    public Simpletron(String filename) {
        String[] data = new String[2];
        try {
            Scanner scanner = new Scanner(new File(filename));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (!line.isEmpty()) {
                    data = line.split(" ");
                    if (data.length >= 2) {
                        memory.additem(Integer.parseInt(data[0]), data[1]);
                        program_size++;
                    }
                }
            }
            scanner.close();
            System.out.println("Program loaded successfully. Size: " + program_size + " instructions");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Simpletron() {
        System.out.println("Welcome to the Simpletron Simulator!");
        System.out.println();
        System.out.println("Let's load your program step by step.");
        System.out.println("I'll show you a memory location number followed by a question mark (?).");
        System.out.println("Simply enter your 4-digit instruction for that location and press Enter.");
        System.out.println();
        System.out.println("For example:");
        System.out.println("  00 ? 1007    (This stores instruction 1007 at location 00)");
        System.out.println();
        System.out.println("When you're finished entering all instructions, type -9999 to begin execution.");
        System.out.println("Ready to start? Here we go!");
        System.out.println();
        
        int location = 0;
        while (true) {
            System.out.printf("%02d ? ", location);
            int instruction = keyboard.nextInt();
            if (instruction == -9999) {
                break;
            }
            memory.setitem(location, String.format("%04d", instruction));
            location++;
            program_size++;
        }
    }

    public void execute() {
        System.out.println("\nProgram execution begins");
        pc = 0;
        halt = false;

        while (!halt && pc < 100) {
            ir = memory.getitem(pc);
            
            if (ir.length() >= 4) {
                opcode = ir.substring(0, 2);
                operand = ir.substring(2, 4);
            } else {
                opcode = "00";
                operand = "00";
            }

            executeInstruction(opcode, operand);
            
            if (!halt) {
                pc++;
            }
        }
        
        System.out.println("\nSimpletron execution terminated");
        memory.dump();
        System.out.println("\nRegisters:");
        System.out.printf("%-20s %+05d\n", "accumulator", accumulator);
        System.out.printf("%-20s %02d\n", "instructionCounter", pc);
        System.out.printf("%-20s %s\n", "instructionRegister", ir);
        System.out.printf("%-20s %s\n", "operationCode", opcode);
        System.out.printf("%-20s %s\n", "operand", operand);
    }

    private void executeInstruction(String opcode, String operand) {
        int op = Integer.parseInt(opcode);
        int addr = Integer.parseInt(operand);

        switch (op) {
            case 10:
                System.out.print("Enter an integer: ");
                int input = keyboard.nextInt();
                memory.setitem(addr, String.format("%04d", input));
                break;
                
            case 11:
                String value = memory.getitem(addr);
                System.out.println(Integer.parseInt(value));
                break;
                
            case 20:
                accumulator = Integer.parseInt(memory.getitem(addr));
                break;
                
            case 21:
                memory.setitem(addr, String.format("%04d", accumulator));
                break;
                
            case 30:
                accumulator += Integer.parseInt(memory.getitem(addr));
                break;
                
            case 31:
                accumulator -= Integer.parseInt(memory.getitem(addr));
                break;
                
            case 32:
                int divisor = Integer.parseInt(memory.getitem(addr));
                if (divisor != 0) {
                    accumulator /= divisor;
                } else {
                    System.out.println("FATAL ERROR: Division by zero");
                    halt = true;
                }
                break;
                
            case 33:
                accumulator *= Integer.parseInt(memory.getitem(addr));
                break;
                
            case 40:
                pc = addr - 1;
                break;
                
            case 41:
                if (accumulator < 0) {
                    pc = addr - 1;
                }
                break;
                
            case 42:
                if (accumulator == 0) {
                    pc = addr - 1;
                }
                break;
                
            case 43:
                halt = true;
                break;
                
            default:
                System.out.println("FATAL ERROR: Invalid operation code");
                halt = true;
                break;
        }
        
        if (accumulator > 9999 || accumulator < -9999) {
            System.out.println("FATAL ERROR: Accumulator overflow");
            halt = true;
        }
    }

    public static void main(String[] args) {
        Simpletron simulator;
        
        if (args.length > 0) {
            simulator = new Simpletron(args[0]);
        } else {
            simulator = new Simpletron();
        }
        
        simulator.execute();
    }
}
