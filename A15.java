import java.util.Scanner;
public class A15 {
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        int[] a = new int[9877];
        int i, pq, p, q, times = 0;

        
        for (int k = 0; k < 1023; k++) {
            a[k] = 1;
        }
        for (int k = 1023; k < a.length; k++) {
            if (check_4(k) == 1) {
                a[k] = 1;
            }
        }
        System.out.println("想一個四個數字都不重複的四位數");
        while (true) {
            i = pick(a);
            if (i == -1) {
                System.out.println("沒有符合數字，可前面某一次A、B回答有錯");
                break;
            }
            times++;
            System.out.printf("第 %d 次猜測：%d\n", times, i);
            pq = input_answer();
            p = pq / 10;
            q = pq % 10;
            if (p == 4 && q == 0) {
                System.out.printf("答案是%d，共猜了%d 次\n", i, times);
                break;
            }
            filter(a, i, pq);
        }
    }
    public static int pick(int[] a) {
        for (int i = 0; i < a.length; i++) {
            if (a[i] != 1) {
                return i;
            }
        }
        return -1;
    }
    public static void filter(int[] a, int guess, int pq) {
        int[] x = new int[4];
        int[] y = new int[4];
        cut(guess, y);
        for (int k = 0; k < a.length; k++) {
            if (a[k] == 0) {
                cut(k, x);
                if (compare(x, y) != pq) {
                    a[k] = 1;
                }
            }
        }
    }
    public static int check_4(int x) {
        int[] a = new int[4];
        int[] b = new int[4];
        cut(x, a);
        cut(x, b);
        return repate(a, b);
    }
    public static void cut(int x, int[] a) {
        a[3] = x / 1000;
        a[2] = (x / 100) % 10;
        a[1] = (x / 10) % 10;
        a[0] = x % 10;
    }
    public static int repate(int[] a, int[] b) {
        int flag = 0;
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < b.length; j++) {
                if (i != j) {
                    if (a[i] == b[j]) {
                        flag = 1;
                    }
                }
            }
        }
        return flag;
    }
    public static int compare(int[] a, int[] b) {
        int p = 0, q = 0;
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < b.length; j++) {
                if (a[i] == b[j]) {
                    if (i == j) {
                        p++;
                    } else {
                        q++;
                    }
                }
            }
        }
        return p * 10 + q;
    }
    public static int input_answer() {
        int p, q;
        System.out.print("A= ");
        p = sc.nextInt();
        System.out.print("B= ");
        q = sc.nextInt();
        return p * 10 + q;
    }
}