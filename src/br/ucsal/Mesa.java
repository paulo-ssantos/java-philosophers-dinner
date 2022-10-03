package br.ucsal;

import java.util.concurrent.Semaphore;

public class Mesa {
    public Semaphore[] garfos = new Semaphore[5];

    public Mesa() {
        for (int i = 0; i < 5; i++) {
            this.garfos[i] = new Semaphore(1);
        }
      }
  
}