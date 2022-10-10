package br.ucsal;

public class App {
    public static void main(String[] args) {
        Philosophers[] philosopherList = startDinner(5);

        finishDinner(philosopherList);

        showStatistics(philosopherList);

        System.out.println("FINALIZADO");
    }

    private static Philosophers[] startDinner(int numberOfPhilosophers) {
        Table table = new Table(numberOfPhilosophers);

        Philosophers[] philosopherList = new Philosophers[numberOfPhilosophers];
        for (int i = 0; i < numberOfPhilosophers; i++) {
            philosopherList[i] = new Philosophers(i, table);
            philosopherList[i].start();
        }
        return philosopherList;
    };

    private static void finishDinner(Philosophers[] philosopherList) {
        for (int i = 0; i < philosopherList.length; i++) {
            try {
                philosopherList[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        philosopherList[0].table.finishTable();
    }

    private static void showStatistics(Philosophers[] philosopherList) {
        System.out.print(
                "\n========================\nTodos Terminaram de executar\nExibindo Dados\n========================");

        for (int i = 0; i < philosopherList.length; i++) {
            System.out.print("\n");
            System.out.println("______________________________________________________________");
            System.out.println("Exibindo estatisticas do Filósofo " + philosopherList[i].id);
            System.out.println("Filósofo " + philosopherList[i].id + " pensou " + philosopherList[i].thinkCount + " vezes");
            System.out.println("Filósofo " + philosopherList[i].id + " comeu " + philosopherList[i].eatCount + " vezes");
            System.out.println("Filósofo " + philosopherList[i].id + " pensou por " + philosopherList[i].totalTimeThinking + " ms");
            System.out.println("Filósofo " + philosopherList[i].id + " comeu por " + philosopherList[i].totalTimeEating + " ms");
            System.out.println("Tempo de execução " + philosopherList[i].totalExecutionTime + " ms");
        }

        System.out.println("\nTempo total de execução da mesa: " + philosopherList[0].table.totalTime + " ms\n");
    }

}
