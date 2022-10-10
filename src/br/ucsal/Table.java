package br.ucsal;

import java.util.concurrent.Semaphore;

public class Table {
    public Semaphore[] forks;
    private long startTime;
    private long endTime;
    public long totalTime;

    public Table(int numberOfForks) {
        this.forks = new Semaphore[numberOfForks];
        for (int i = 0; i < this.forks.length; i++) {
            this.forks[i] = new Semaphore(1);
            this.startTime = System.currentTimeMillis();
        }
    }

    public void finishTable() {
        this.endTime = System.currentTimeMillis();
        this.totalTime = this.endTime - this.startTime;
    }
}