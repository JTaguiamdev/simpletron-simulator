/*
Taguiam, Jasper P.
BSCS - 3A
Simpletron Memory
*/
import java.util.Scanner;

public class Memory {
    private String[] mem = new String[100];
    private Scanner scanner = new Scanner(System.in);

    public Memory() {
        for (int i = 0; i < mem.length; i++) {
            mem[i] = "+0000";
        }
    }

    private static String formatMemory(int data) {
        return String.format("%+05d", data);
    }

    public int getitem(int address) {
        return Integer.parseInt(mem[address]);
    }

    public void setitem(int address, int data) {
        mem[address] = formatMemory(data);
    }

    public void setitem(int address, String data) {
        mem[address] = data;
    }

    public int readInput() {
        return scanner.nextInt();
    }

    public void writeOutput(int data) {
        System.out.println(data);
    }

    public void clear() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (Exception e) {
            for (int i = 0; i < 50; i++) {
                System.out.println();
            }
        }
    }

    public void Press() {
        System.out.print("Press Enter to continue...");
        scanner.nextLine();
        clear();
    }

    public void dump(int accumulator, int programCounter, int instructionRegister, int operationCode, int operand) {
        System.out.println("REGISTERS:");
        System.out.println("accumulator:\t\t" + formatMemory(accumulator));
        System.out.println("programCounter:\t\t" + String.format("%02d", programCounter));
        System.out.println("instructionRegister:\t" + formatMemory(instructionRegister));
        System.out.println("operationCode:\t\t" + String.format("%02d", operationCode));
        System.out.println("operand:\t\t" + String.format("%02d", operand));

        System.out.println("\nMEMORY:");
        System.out.print("     ");
        for (int col = 0; col < 10; col++) {
            System.out.printf("%8d", col);
        }
        System.out.println();

        for (int row = 0; row < 10; row++) {
            System.out.printf(" %d0  ", row);
            for (int col = 0; col < 10; col++) {
                int index = row * 10 + col;
                System.out.printf("%8s", mem[index]);
            }
            System.out.println();
        }
    }
}
