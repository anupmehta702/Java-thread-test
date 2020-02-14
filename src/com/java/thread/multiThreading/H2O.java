package com.java.thread.multiThreading;

import java.util.ArrayList;
import java.util.List;

public class H2O {
    String[] input = new String[]{"O", "O", "H", "H", "H", "H"};
    //String[] input = new String[]{"H", "H", "H", "H", "O", "O"};
    //String[] input = new String[]{"O", "H", "H", "H", "O", "H"};
    List<String> output = new ArrayList<>();
    Integer hCount = 0;
    Integer oCount = 0;

    public void printH2O() {
        System.out.println(output);
        hCount = 0;
        oCount = 0;
        output = new ArrayList<>();
    }

    public static void main(String[] args) {
        H2O h2o = new H2O();
        Thread hydrogenThread = new Thread(new Hydrogen(h2o));
        Thread oxygenThread = new Thread(new Oxygen(h2o));
        hydrogenThread.start();
        oxygenThread.start();
    }
}

class Hydrogen implements Runnable {
    H2O h2o;

    public Hydrogen(H2O h2o) {
        this.h2o = h2o;
    }

    @Override
    public void run() {
        String[] input = h2o.input;
        for (int index = 0; index < input.length; index++) {
            if (input[index] == "H") {
                synchronized (h2o) {
                    if (h2o.hCount < 2) {
                        h2o.output.add(input[index]);
                        h2o.hCount++;
                    }
                    if (h2o.hCount == 2 && h2o.output.size() != 3) {
                        try {
                            h2o.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    if (h2o.output.size() == 3) {
                        h2o.printH2O();
                        h2o.notify();
                    }
                }
            }
        }
    }
}

class Oxygen implements Runnable {
    H2O h2o;

    public Oxygen(H2O h2o) {
        this.h2o = h2o;
    }

    @Override
    public void run() {
        String[] input = h2o.input;
        for (int index = 0; index < input.length; index++) {
            if (input[index] == "O") {
                synchronized (h2o) {
                    if (h2o.oCount < 1) {
                        h2o.output.add(input[index]);
                        h2o.oCount++;
                    }
                    if (h2o.oCount == 1 && h2o.output.size() != 3) {
                        try {
                            h2o.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    if (h2o.output.size() == 3) {
                        h2o.printH2O();
                        h2o.notify();
                    }
                }
            }
        }
    }
}

/*
Input -{"O", "H", "H", "H", "O", "H"}
 Output -[H, H, O] [O, H, H]
Input -{"H", "H", "H", "H", "O", "O"};
Output -[H, H, O] [O, H, H]
Input -{"O", "O", "H", "H", "H", "H"}
output - [H, H, O] [O, H, H]

 */