import java.io.*;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class Singer {
    int num;
    int order;

    Singer(int num, int order) {
        this.num = num;
        this.order = order;
    }
}

public class Main {

    private static BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
    private static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    private static int N, M;
    private static HashSet<Integer>[] firstOrders;
    private static HashSet<Integer> alreadyOrders = new HashSet<>();
    private static PriorityQueue<Integer> pq = new PriorityQueue<>((o1, o2) -> {
        if (firstOrders[o1].size() == firstOrders[o2].size()) {
            return o1 - o2;
        }
        return firstOrders[o1].size() - firstOrders[o2].size();
    });
    private static int[] optimizationOrder;

    public static void main(String[] args) throws IOException {
        new Main().solution();
    }

    private void solution() throws IOException {
        init();
        printResult(findOptimizationOrder());
    }

    private void init() throws IOException {
        initNAndM();
        initHashSet();
        initSingerOrders();
        initQueue();
    }

    private void initNAndM() throws IOException {
        List<Integer> numbers = readNumbers();
        N = numbers.get(0);
        M = numbers.get(1);
    }

    private void initHashSet() {
        firstOrders = new HashSet[N + 1];
        for (int i = 1; i <= N; i++) {
            firstOrders[i] = new HashSet<>();
        }
    }

    private List<Integer> readNumbers() throws IOException {
        return Arrays.stream(bf.readLine().split(" "))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

    private void initSingerOrders() throws IOException {
        for (int i = 0; i < M; i++) {
            List<Integer> numbers = readNumbers();
            initFirstOrders(numbers);
        }
    }

    private void initFirstOrders(List<Integer> numbers) {
        for (int i = 2; i <= numbers.get(0); i++) {
            int count = 0;
            while (++count < i) {
                firstOrders[numbers.get(i)].add(numbers.get(count));
            }
        }
    }

    private void initQueue() {
        pq.clear();
        for (int i = 1; i <= N; i++) {
            if (alreadyOrders.contains(i)) {
                continue;
            }
            pq.offer(i);
        }
    }

    private boolean findOptimizationOrder() {
        optimizationOrder = new int[N];
        int count = -1;
        while (++count < N) {
            int num = pq.poll();
            alreadyOrders.add(num);
            if (firstOrders[num].size() != 0) {
                return false;
            }
            optimizationOrder[count] = num;
            removeHashSetNum(num);
            initQueue();
        }
        return true;
    }

    private void removeHashSetNum(int num) {
        IntStream.rangeClosed(1, N)
                .filter(it -> firstOrders[it].contains(num))
                .forEach(it -> firstOrders[it].remove(num));
    }

    private void printResult(boolean canFind) throws IOException {
        if (canFind) {
            for (int num : optimizationOrder) {
                bw.write(num + "\n");
            }
        } else {
            bw.write("0");
        }
        bw.flush();
        bw.close();
    }

    private void printHash() {
        for(int i = 1; i <= N; i++) {
            firstOrders[i].stream()
                    .forEach(it -> System.out.print(it + " "));
            System.out.println();
        }
    }


}