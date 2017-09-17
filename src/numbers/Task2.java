package numbers;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.*;

/**
 * Created by 1 on 15.09.2017.
 */
public class Task2 {
    private int countCore = Runtime.getRuntime().availableProcessors();
    private ExecutorService threadPool = Executors.newFixedThreadPool(countCore);
    private int size = 8000000;


    public void start() throws ExecutionException, InterruptedException {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(i + 1);
        }
        System.out.println("Введите N");
        int N = addConsole();
        for(int i = 0; i < N; i++) {
            System.out.println("Подсчет режима Thread №" + (i + 1));
            thread(list);
            System.out.println("Подсчет режима ThreadPool №" + (i + 1));
            threadPool(list);
        }
        threadPool.shutdown();

    }

    private double getSum(List<Integer> list, int left, int right) {
        double result = 0;
        for (int i = left; i < right; i++) {
            result += Math.sin(list.get(i)) + Math.cos(list.get(i));
        }
        return result;
    }

    private void thread(List<Integer> list) throws ExecutionException, InterruptedException {
        long timeStart = System.currentTimeMillis();
        List<FutureTask> threadArray = new ArrayList<>();
        int length = list.size() / countCore;
        for (int i = 0; i < countCore; i++) {
            final int left = length * i;
            final int right = left + length;
            FutureTask<Double> futureTask = new FutureTask<Double>(() -> getSum(list, left, right));
            new Thread(futureTask).start();
            threadArray.add(futureTask);
        }
        double result = 0;
        for (FutureTask future : threadArray) {
            result += (double) future.get();
        }
        System.out.println("Режим Thread отработал за: " + (timeStart - System.currentTimeMillis()) + " Сумма = " + result);

    }

    private void threadPool(List<Integer> list) throws ExecutionException, InterruptedException {
        long timeStart = System.currentTimeMillis();
        List<FutureTask> threadArray = new ArrayList<>();
        int length = list.size() / countCore;
        for (int i = 0; i < countCore; i++) {
            final int left = length * i;
            final int right = left + length;
            FutureTask<Double> futureTask = new FutureTask<Double>(() -> getSum(list, left, right));
            threadPool.submit(futureTask);
            threadArray.add(futureTask);
        }
        double result = 0;
        for (FutureTask future : threadArray) {
            result += (double) future.get();
        }
        System.out.println("Режим ThreadPool отработал за: " + (timeStart - System.currentTimeMillis()) + " Сумма = " + result);
    }
    public int addConsole(){
        Scanner sc = new Scanner(System.in);
        int x;
        while (true) {
            String str = sc.nextLine();
            try {
                x = Integer.parseInt(str);
                if(x >= 0)
                break;
            } catch(Exception e) {
                System.out.println("Вы ввели не число, или введенное число является отрицательным. Попробуйте снова");
            }
        }return x;
    }
}
