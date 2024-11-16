package com.swlo;

import com.swlo.resources.*;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Select synchronization method:");
        System.out.println("1 - Barrier");
        System.out.println("2 - Mutex");
        System.out.println("3 - Semaphore");
        System.out.println("4 - Message Passing");
        System.out.println("5 - Monitor");
        int choice = scanner.nextInt();

        SharedResource resource = switch (choice) {
            case 1 -> new BarrierResource(3, 2);
            case 2 -> new MutexResource();
            case 3 -> new SemaphoreResource();
            case 4 -> new MessageResource();
            case 5 -> new MonitorResource();
            default -> {
                System.out.println("Invalid choice, defaulting to Barrier.");
                yield new BarrierResource(3, 2);
            }
        };

        // Reader threads
        for (int i = 1; i <= 3; i++) {
            final int id = i;
            new Thread(() -> {
                for (int j = 0; j < 10; j++) {
                    resource.read(id);
                }
            }, "Reader " + id).start();
        }

        // Writer threads
        for (int i = 1; i <= 2; i++) {
            final int id = i;
            new Thread(() -> {
                for (int j = 0; j < 5; j++) {
                    resource.write(id);
                }
            }, "Writer " + id).start();
        }
    }
}