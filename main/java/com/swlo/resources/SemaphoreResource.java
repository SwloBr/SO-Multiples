package com.swlo.resources;

import java.util.concurrent.Semaphore;

public class SemaphoreResource extends SharedResource {
    private final Semaphore semaphore = new Semaphore(1);

    @Override
    public void read(int id) {
        try {
            semaphore.acquire();
            System.out.println("Reader " + id + " is reading (Semaphore).\n");
            Thread.sleep(500);
            System.out.println("Reader " + id + " has finished reading (Semaphore).\n");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            semaphore.release();
        }
    }

    @Override
    public void write(int id) {
        try {
            semaphore.acquire();
            System.out.println("Writer " + id + " is writing (Semaphore).\n");
            Thread.sleep(1000);
            System.out.println("Writer " + id + " has finished writing (Semaphore).\n");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            semaphore.release();
        }
    }
}
