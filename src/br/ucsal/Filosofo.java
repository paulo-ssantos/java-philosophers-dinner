package br.ucsal;

import java.util.Random;

public class Filosofo extends Thread {

  final public int id;
  final public Mesa mesa;
  private boolean rightFork;
  private boolean leftFork;
  public int totalTimeEating = 0;
  public int totalTimeThinking = 0;
  public long startTime;
  private long currentTime;
  public long endTime;

  public Random random = new Random();
  final private int MAX_TIME = random.nextInt(5000, 10000);

  // [0] - Pensar
  // [1] - Comer
  public int[] status = new int[2];

  public int flag = 0;

  public Filosofo(int id, Mesa mesa) {
    this.id = id;
    this.mesa = mesa;
    this.startTime = System.currentTimeMillis();
  }

  public void run() {
    while (true) {
      // pensar
      this.think();

      // pegar garfos
      this.getForks();

      // comer
      this.eat();

      // devolver garfos
      this.returnForks();
      
      // verificar se o tempo máximo foi atingido
      this.currentTime = System.currentTimeMillis();
      if (this.currentTime - this.startTime > this.MAX_TIME) {
        this.endTime = System.currentTimeMillis();
        
        this.stop();
        break;
      }
    }
  }

  public void think() {
    System.out.println("Filosofo " + this.id + " pensando");
    this.status[0]++;
    
    try {
      int timeThink = random.nextInt(1000);
      totalTimeThinking += timeThink;
      sleep(timeThink);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public void getForks() {
    System.out.println("Filosofo " + this.id + " pegando garfos");
    // Sicronizar o acesso aos garfos **************
      if (this.id == 4) {
    	this.leftFork = this.mesa.garfos[this.id].tryAcquire();
    	this.rightFork = this.mesa.garfos[0].tryAcquire();
      } else {
    	this.leftFork = this.mesa.garfos[this.id].tryAcquire();
    	this.rightFork = this.mesa.garfos[this.id + 1].tryAcquire();
      }
  }

  public void eat() {
    if (this.leftFork && this.rightFork) {
      System.out.println("Filosofo " + this.id + " comendo");
      this.status[1]++;
      try {
        int timeEat = random.nextInt(1000);
        totalTimeEating += timeEat;
        sleep(timeEat);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    } else {
      System.out.println("----------------------------------------------");
      System.out.println("Filosofo " + this.id + " não conseguiu comer");
      System.out.println("----------------------------------------------");
    }
  }

  public void returnForks() {
	 
	if (this.leftFork && this.rightFork) {
		System.out.println("Filosofo " + this.id + " devolvendo garfos");
    if (this.id == 4) {
      this.mesa.garfos[this.id].release();
      this.mesa.garfos[0].release();
      this.leftFork = false;
      this.rightFork = false;
    } else {
      this.mesa.garfos[this.id].release();
      this.mesa.garfos[this.id + 1].release();
      this.leftFork = false;
      this.rightFork = false;
    }
	}
    
  }

}
