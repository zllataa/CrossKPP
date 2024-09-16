import java.util.Random;

class CPUQueue {
    private int destroyedProcessesFirstProcessor;
    private int destroyedProcessesSecondProcessor;
    private int totalProcesses;

    public CPUQueue(int totalProcesses) {
        this.destroyedProcessesFirstProcessor = 0;
        this.destroyedProcessesSecondProcessor = 0;
        this.totalProcesses = totalProcesses;
    }

    public synchronized void process(CPUProcess process, int processorNumber) {
        // Перевірка доступності процесора та початок обробки
        if (processorNumber == 1) {
            if (destroyedProcessesFirstProcessor < totalProcesses / 2) {
                System.out.println("Processor 1: Process started");
                // Моделювання обробки процесу
                try {
                    Thread.sleep(process.getProcessingTime());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Processor 1: Process completed");
            } else {
                System.out.println("Processor 1: Process destroyed");
                destroyedProcessesFirstProcessor++;
            }
        } else if (processorNumber == 2) {
            if (destroyedProcessesSecondProcessor < totalProcesses / 2) {
                System.out.println("Processor 2: Process started");
                // Моделювання обробки процесу
                try {
                    Thread.sleep(process.getProcessingTime());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Processor 2: Process completed");
            } else {
                System.out.println("Processor 2: Process destroyed");
                destroyedProcessesSecondProcessor++;
            }
        }
    }

    public void printStatistics() {
        double destroyedPercentageFirstProcessor = ((double) destroyedProcessesFirstProcessor / totalProcesses) * 100;
        double destroyedPercentageSecondProcessor = ((double) destroyedProcessesSecondProcessor / totalProcesses) * 100;

        System.out.println("Destroyed processes on Processor 1: " + destroyedProcessesFirstProcessor);
        System.out.println("Destroyed processes on Processor 2: " + destroyedProcessesSecondProcessor);
        System.out.println("Destroyed percentage on Processor 1: " + destroyedPercentageFirstProcessor + "%");
        System.out.println("Destroyed percentage on Processor 2: " + destroyedPercentageSecondProcessor + "%");
    }
}

class CPUProcess extends Thread {
    private int processingTime;

    public CPUProcess(int processingTime) {
        this.processingTime = processingTime;
    }

    public int getProcessingTime() {
        return processingTime;
    }

    @Override
    public void run() {
        // Моделювання намагання почати обробку на процесорах
        CPUQueue cpuQueue = new CPUQueue(20); // Загальна кількість процесів
        cpuQueue.process(this, 1);
        cpuQueue.process(this, 2);
    }
}

class CPU extends Thread {
    private CPUQueue cpuQueue;

    public CPU(CPUQueue cpuQueue) {
        this.cpuQueue = cpuQueue;
    }

    @Override
    public void run() {
        // Моделювання обслуговування одного потоку процесів трьома центральними процесорами
        for (int i = 0; i < 20; i++) { // Загальна кількість процесів
            CPUProcess process = new CPUProcess(generateRandomTime(100, 500));
            process.start();
            // Обслуговування процесу на першому процесорі
            cpuQueue.process(process, 1);
            // Обслуговування процесу на другому процесорі
            cpuQueue.process(process, 2);
        }

        cpuQueue.printStatistics(); // Вивід статистики
    }

    private int generateRandomTime(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min + 1) + min;
    }
}

public class Main {
    public static void main(String[] args) {
        // Моделювання обслуговування
        CPUQueue cpuQueue = new CPUQueue(20); // Загальна кількість процесів
        CPU cpu = new CPU(cpuQueue);
        cpu.start();
    }
}
