package br.ucsal;

public class App {
    public static void main(String[] args) {
        Mesa mesa = new Mesa();

        Filosofo[] filosofos = new Filosofo[5];
        for (int i = 0; i < 5; i++) {
            filosofos[i] = new Filosofo(i, mesa);
            filosofos[i].start();
        }

        // verificar quando todas as trheads terminarem

        for (int i = 0; i < 5; i++) {
            try {
                filosofos[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < 5; i++) {
            System.out.print("\n");
            System.out.println("______________________________________________________________");
            System.out.println("Filosofo " + filosofos[i].id + " Encerrado");
            System.out.println("Filosofo " + filosofos[i].id + " pensou " + filosofos[i].status[0] + " vezes");
            System.out.println("Filosofo " + filosofos[i].id + " comeu " + filosofos[i].status[1] + " vezes");
            System.out.println("Filosofo " + filosofos[i].id + " pensou por " + filosofos[i].totalTimeThinking + " ms");
            System.out.println("Filosofo " + filosofos[i].id + " comeu por " + filosofos[i].totalTimeEating + " ms");
            System.out.println("Tempo de execução " + (filosofos[i].endTime - filosofos[i].startTime) + " ms");
            System.out.print("\n");
            
        }

        System.out.println("FINALIZADO");
    };

}
