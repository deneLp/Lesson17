//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package LearnUp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Map.Entry;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main2 {
    public static Object obj = new Object();
    public static volatile int sum = 0;


    public static void main(String[] args) throws ExecutionException, InterruptedException {
        HashMap<Integer, Integer> result = new HashMap(5);
        int arrayLength = 1000;
        ArrayList<Integer> array = new ArrayList(arrayLength);

        for(int i = 0; i < arrayLength; ++i) {
            array.add(100 + (new Random()).nextInt(100));
        }

        List<Integer> numbers = new ArrayList();
        numbers.add(3);
        numbers.add(5);
        numbers.add(7);
        numbers.add(9);
        numbers.add(11);
        ExecutorService service = Executors.newFixedThreadPool(5);
        List<Future<?>> futures = new ArrayList();
        Iterator var7 = numbers.iterator();

        int IndexValue;
        while(var7.hasNext()) {
            IndexValue = (Integer)var7.next();
            int finalIndexValue = IndexValue;
            Future<?> future = service.submit(() -> {
                sum = 0;

                for(int i = 0; i < array.size(); ++i) {
                    if ((Integer)array.get(i) % finalIndexValue == 0) {
                        synchronized(obj) {
                            sum += (Integer)array.get(i);
                        }
                    }
                }

                result.put(finalIndexValue, sum);
            });
            futures.add(future);
        }

        var7 = futures.iterator();

        while(var7.hasNext()) {
            Future<?> future = (Future)var7.next();
            future.get();
        }

        int maxValue = (Integer)Collections.max(result.values());
        IndexValue = 0;
        Iterator var15 = result.entrySet().iterator();

        while(var15.hasNext()) {
            Entry<Integer, Integer> keyValue = (Entry)var15.next();
            if ((Integer)keyValue.getValue() == maxValue) {
                IndexValue = (Integer)keyValue.getKey();
            }
        }

        System.out.println("The  sum of numbers divided on  " + IndexValue + " is max and equal  " + maxValue);
        var15 = numbers.iterator();

        while(var15.hasNext()) {
            int x = (Integer)var15.next();
            sum = 0;

            for(int i = 0; i < array.size(); ++i) {
                if ((Integer)array.get(i) % x == 0) {
                    sum += (Integer)array.get(i);
                }
            }

            System.out.println("" + x + ":" + sum);
        }

    }
}
