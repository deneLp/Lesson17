package LearnUp;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {

    public static Object obj = new Object();
    public static volatile int sum = 0;

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        HashMap<Integer, Integer> result = new HashMap<>(5);
        int arrayLength = 1000;
        ArrayList<Integer> array = new ArrayList<>(arrayLength);

        for (int i = 0; i < arrayLength; i++) {
            array.add(100 + new Random().nextInt(100));
        }
        List<Integer> numbers = new ArrayList<>();
        numbers.add(3);
        numbers.add(5);
        numbers.add(7);
        numbers.add(9);
        numbers.add(11);

        ExecutorService service = Executors.newFixedThreadPool(5);
        List<Future<?>> futures = new ArrayList<>();

        for (int x: numbers)  {

            Future<?> future = service.submit(() -> {
                sum = 0;
                for (int i = 0; i < array.size(); i++) {
                    if (array.get(i) % x == 0) {

                        synchronized (obj) {
                            sum = sum + array.get(i);

                        }
                    }
                }
                result.put(x,sum);
            });

            futures.add(future);

        }

        for (Future<?> future: futures) {
            future.get();
        }

        int maxValue = Collections.max(result.values());
        int IndexValue = 0;
        for (Map.Entry<Integer, Integer> keyValue:result.entrySet()) {
            if (keyValue.getValue() == maxValue){
                IndexValue = keyValue.getKey();
            }
        }
        System.out.println("The  sum of numbers divided on  " + IndexValue + " is max and equal  "  + maxValue);

        //проверка: выведем на экран суммы полученные суммы чисел
        for (int x : numbers) {
            sum = 0;
            for (int i = 0; i < array.size(); i++) {
                if (array.get(i) % x == 0) {
                    sum = sum + array.get(i);
                }
            }
            System.out.println("" + x + ":" + sum);
        }
    }
}
