package numbers;

import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Created by 1 on 13.09.2017.
 */
public class Task1 {
    private Scanner sc;

    public double enterNumbers(){
        sc = new Scanner(System.in);
        double x;
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

    public String enterSymbol(){
        sc = new Scanner(System.in);
        String[]symbol = new String[]{"+","-","*","/","%", "==",">","<"};
        String input = "";
        boolean x = true;
        System.out.println("Введите символ: ");
        exit:
        while (x){
            input = sc.nextLine();
            for (int i = 0; i < symbol.length; i++){
                if(symbol[i].equals(input)){
                    break exit;
                }
            }System.out.println("Вы ввели неверный символ попробуйте снова");
        }return input;
    }

    public void start() throws ExecutionException, InterruptedException {
        System.out.println("Введите число А");
        double a = enterNumbers();
        System.out.println("Введите число B");
        double b = enterNumbers();
        System.out.println("Какое математическое действие необходимо применить к этим числам: " +
                            "\n1. +\n2. -\n3.*\n4./\n5.%\n6.==\n7.>\n8.<");
        String str = enterSymbol();
        start(a,b,str);
    }


    public void start(double a, double b, String str) throws ExecutionException, InterruptedException {
        if(str.equals("+")||str.equals("-")||str.equals("*")||str.equals("/")||str.equals("%")){
         FutureTask<Double>futureTask = new FutureTask<Double>(() ->  {
             if(str.equals("+")){return a + b;}
             else if(str.equals("-")){return a - b;}
             else if(str.equals("*")){return a * b;}
             else if(str.equals("/")){return a / b;}
             else if(str.equals("%")){return a % b;}
             else return null;
         });
         new Thread(futureTask).start();
         double result = futureTask.get();
         System.out.println("Результат операции: " + "[" + str + "]" +  " равен " + result);}
         else {FutureTask<Boolean> futureTask = new FutureTask<Boolean>(() -> {
            if(str.equals("==")){return a == b;}
            else if (str.equals(">")){return a > b;}
            else return a < b;
        });
        new Thread(futureTask).start();
        boolean result = futureTask.get();
        System.out.println("Результат операции: " + "[" + str + "]" + " равен " + result);}
    }

}
