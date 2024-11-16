package com.swlo.resources;

public class MonitorResource extends SharedResource {
    private int readers = 0;
    private boolean writing = false;

    @Override
    public synchronized void read(int id) {
        try {
            while (writing) {
                wait();
            }
            readers++;
            System.out.println("Reader " + id + " is reading (Monitor).\n");
            Thread.sleep(500);
            System.out.println("Reader " + id + " has finished reading (Monitor).\n");
            readers--;
            if (readers == 0) {
                notifyAll();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public synchronized void write(int id) {
        try {
            while (readers > 0 || writing) {
                wait();
            }
            writing = true;
            System.out.println("Writer " + id + " is writing (Monitor).\n");
            Thread.sleep(1000);
            System.out.println("Writer " + id + " has finished writing (Monitor).\n");
            writing = false;
            notifyAll();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
