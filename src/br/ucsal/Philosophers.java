package br.ucsal;

import java.util.Random;

public class Philosophers extends Thread {
  final public int id;
  final public Table table;
  private boolean rightFork;
  private boolean leftFork;
  private int forksQuantity;

  private int timeThinking;
  private int timeEating;

  public int totalTimeEating = 0;
  public int totalTimeThinking = 0;
  public int eatCount = 0;
  public int thinkCount = 0;
  public long totalExecutionTime = 0;

  private long startTime;
  private long endTime;

  public Random random = new Random();
  final private int MAX_TIME = random.nextInt(5000, 10000);

  public Philosophers(int id, Table table) {
    this.id = id;
    this.table = table;
    this.forksQuantity = this.table.forks.length;
    this.startTime = System.currentTimeMillis();
  }

  public void run() {
    while (true) {
      this.think();

      this.getForks();

      this.eat();

      this.returnForks();

      this.verifyTotalTime();
    }
  }

  private void think() {
    System.out.println("Filosofo " + this.id + " pensando");
    this.thinkCount++;

    try {
      this.timeThinking = random.nextInt(1000);
      this.totalTimeThinking += this.timeThinking;
      sleep(this.timeThinking);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  private void getForks() {
    System.out.println("Filosofo " + this.id + " pegando garfos");

    if (this.id == forksQuantity - 1) {
      this.leftFork = this.table.forks[this.id].tryAcquire();
      this.rightFork = this.table.forks[0].tryAcquire();
    } else {
      this.leftFork = this.table.forks[this.id].tryAcquire();
      this.rightFork = this.table.forks[this.id + 1].tryAcquire();
    }
  }

  private void eat() {
    if (this.leftFork && this.rightFork) {
      System.out.println("Filosofo " + this.id + " comendo");
      this.eatCount++;

      try {
        timeEating = random.nextInt(1000);
        this.totalTimeEating += timeEating;
        sleep(timeEating);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    } else {
      System.out.println("Filosofo " + this.id + " não conseguiu comer");
    }
  }

  private void returnForks() {
    if (this.leftFork && this.rightFork) {
      System.out.println("Filosofo " + this.id + " devolvendo garfos");
      if (this.id == forksQuantity - 1) {
        this.table.forks[this.id].release();
        this.table.forks[0].release();
        this.leftFork = false;
        this.rightFork = false;
      } else {
        this.table.forks[this.id].release();
        this.table.forks[this.id + 1].release();
        this.leftFork = false;
        this.rightFork = false;
      }
    } else if (this.leftFork) {
      System.out.println("Filosofo " + this.id + " devolvendo garfo esquerdo");
      this.table.forks[this.id].release();
      this.leftFork = false;
    } else if (this.rightFork) {
      System.out.println("Filosofo " + this.id + " devolvendo garfo direito");
      if (this.id == forksQuantity - 1) {
        this.table.forks[0].release();
        this.rightFork = false;
      } else {
        this.table.forks[this.id + 1].release();
        this.rightFork = false;
      }
    }
  }

  private void verifyTotalTime() {
    this.endTime = System.currentTimeMillis();
    if (this.endTime - this.startTime > this.MAX_TIME) {
      this.totalExecutionTime = this.endTime - this.startTime;
      System.out.println("Filosofo " + this.id + " encerrou sua execução");
      this.stop();
    }
  }
}
