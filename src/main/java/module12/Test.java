package module12;

public class Test {
    public static void main(String[] args) {
        TimePrinter1 time = new TimePrinter1();
        new SecondsPrinter1(time);
        new FiveSecondsNotification1(time);
    }
}

class TimePrinter1 {
    private int second = 1;
    public synchronized void printSecond() {
        while (second < 5000) {
            try {
                Thread.sleep(1000);
                System.out.println(second);
                second++;
                notify();
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (second % 5 == 0) {
                try {
                    wait(1);
                    System.out.println(second);
                    second++;
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                notify();
            }
        }
    }

    public synchronized void printFiveSeconds() {
        while (second < 5000) {
            if (second % 5 == 0) {
                try {
                    Thread.sleep(999);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("5 seconds passed");
            }
            try {
                notify();
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

class SecondsPrinter1 extends Thread {
    private TimePrinter1 printer;

    public SecondsPrinter1(TimePrinter1 printer) {
        this.printer = printer;
        this.start();
    }

    @Override
    public void run() {
        printer.printSecond();
    }
}

class FiveSecondsNotification1 extends Thread {
    private TimePrinter1 printer;

    public FiveSecondsNotification1(TimePrinter1 printer) {
        this.printer = printer;
        this.start();
    }

    @Override
    public void run() {
        printer.printFiveSeconds();
    }
}
