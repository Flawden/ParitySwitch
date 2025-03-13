public class ParitySwitch {
    private int number = 0;
    private final int max = 1000;
    private boolean isEvenTurn = true;

    public static void main(String[] args) {
        ParitySwitch printer = new ParitySwitch();
        Thread evenThread = new Thread(printer::printEven, "EvenThread");
        Thread oddThread = new Thread(printer::printOdd, "OddThread");
        evenThread.start();
        oddThread.start();
    }

    public synchronized void printEven() {
        while (number < max) {
            while (!isEvenTurn) {
                try { wait(); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            }
            System.out.println(Thread.currentThread().getName() + ": " + number);
            number++;
            isEvenTurn = false;
            notify();
        }
    }

    public synchronized void printOdd() {
        while (number < max) {
            while (isEvenTurn) {
                try { wait(); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            }
            System.out.println(Thread.currentThread().getName() + ": " + number);
            number++;
            isEvenTurn = true;
            notify();
        }
    }
}