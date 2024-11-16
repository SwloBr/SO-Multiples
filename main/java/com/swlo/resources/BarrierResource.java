package com.swlo.resources;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.ReentrantReadWriteLock;


public class BarrierResource extends SharedResource {
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private final CyclicBarrier readersBarrier;
    private final CyclicBarrier writersBarrier;

    public BarrierResource(int numReaders, int numWriters) {
        readersBarrier = new CyclicBarrier(numReaders, () -> {
            System.out.println("All readers have finished reading. Writers may proceed.\n");
        });
        writersBarrier = new CyclicBarrier(numWriters, () -> {
            System.out.println("All writers have finished writing. Readers may proceed.\n");
        });
    }

    @Override
    public void read(int id) {
        lock.readLock().lock();
        try {
            System.out.println("Reader " + id + " is reading (Barrier).\n");
            Thread.sleep(500);
            System.out.println("Reader " + id + " has finished reading (Barrier).\n");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            lock.readLock().unlock();
            awaitBarrier(readersBarrier);
        }
    }

    @Override
    public void write(int id) {
        lock.writeLock().lock();
        try {
            System.out.println("Writer " + id + " is writing (Barrier).\n");
            Thread.sleep(1000);
            System.out.println("Writer " + id + " has finished writing (Barrier).\n");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            lock.writeLock().unlock();
            awaitBarrier(writersBarrier);
        }
    }

    private void awaitBarrier(CyclicBarrier barrier) {
        try {
            barrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            Thread.currentThread().interrupt();
            System.err.println("Error while waiting at barrier: " + e.getMessage());
        }
    }
}