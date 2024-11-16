package com.swlo.resources;

public class MutexResource extends SharedResource {
    private final Object monitor = new Object();

    @Override
    public void read(int id) {
        synchronized (monitor) {
            try {
                System.out.println("Reader " + id + " is reading (Mutex).\n");
                Thread.sleep(500);
                System.out.println("Reader " + id + " has finished reading (Mutex).\n");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    @Override
    public void write(int id) {
        synchronized (monitor) {
            try {
                System.out.println("Writer " + id + " is writing (Mutex).\n");
                Thread.sleep(1000);
                System.out.println("Writer " + id + " has finished writing (Mutex).\n");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}