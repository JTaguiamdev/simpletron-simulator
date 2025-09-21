/*
Taguiam, Jasper P.
BSCS - 3A
Simpletron Memory
*/
public class Memory {
    private int size = 100;
    private String[] mem;

    public Memory(int size) {
        this.size = size;
        mem = new String[size];
        for (int i = 0; i < size; i++) {
            mem[i] = "0000";
        }
    }

    public Memory(String[] data) {
        int dataSize = data.length;
        mem = new String[size];
        for (int i = 0; i < dataSize && i < size; i++) {
            mem[i] = data[i];
        }
        for (int i = dataSize; i < size; i++) {
            mem[i] = "0000";
        }
    }

    public void additem(int address, String data) {
        if (address >= 0 && address < size) {
            mem[address] = data;
        } else {
            System.out.println("Error: Memory address out of bounds");
        }
    }

    public String getitem(int address) {
        if (address >= 0 && address < size) {
            return mem[address];
        } else {
            System.out.println("Error: Memory address out of bounds");
            return "0000";
        }
    }

    public void setitem(int address, String value) {
        if (address >= 0 && address < size) {
            mem[address] = value;
        } else {
            System.out.println("Error: Memory address out of bounds");
        }
    }

    public int getSize() {
        return size;
    }

    public void dump() {
        System.out.println("\nMemory Dump:");
        System.out.println("Location   data");
        System.out.println("--------  -----------");
        for (int i = 0; i < size; i++) {
            if (!mem[i].equals("0000")) {
                System.out.printf("%8d  %11s\n", i, mem[i]);
            }
        }
    }
}
