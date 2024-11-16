package com.swlo.resources;

import java.util.LinkedList;
import java.util.Queue;

public class MessageResource extends SharedResource {
    private final Queue<String> messageQueue = new LinkedList<>();

    @Override
    public void read(int id) {
        synchronized (messageQueue) {
            if (!messageQueue.isEmpty()) {
                String message = messageQueue.poll();
                System.out.println("Reader " + id + " read message from queue: " + message);
            } else {
                System.out.println("Reader " + id + " found no messages in the queue.");
            }
        }
    }

    @Override
    public void write(int id) {
        String message = "Message from Writer " + id;
        synchronized (messageQueue) {
            messageQueue.add(message);
            System.out.println("Writer " + id + " added message to queue: " + message);
        }
    }
}
