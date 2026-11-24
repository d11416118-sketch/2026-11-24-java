import java.util.Scanner;

/*
 猜數字遊戲（電腦猜、人回答幾 A 幾 B）
 依照課堂的演算法步驟 1～5，把 11/03、11/10、11/17 寫過的函式組合起來。
 */
public class A1 {
    static Scanner sc = new Scanner(System.in);

    /*
     主程式：
     步驟 1：建立 a[9877]，把不是四位數、或數字有重複的位置設為 1（11/10 A2）。
     步驟 2：挑出第一個 a[i] != 1 的 i，當作這次要猜的數字並印出。
     步驟 3：由人輸入 i 和心中答案比對後是幾 A 幾 B（11/03 A1）。
             如果是 4A0B 就結束程式，否則繼續步驟 4。
     步驟 4：把所有 a[k] == 0 的 k 拿來跟 i 比對，結果不是剛好 pAqB 的，
             就把 a[k] 設為 1 排除掉（11/17 A1）。
     步驟 5：回到步驟 2 重複，直到猜中為止。
     */
    public static void main(String[] args) {
        int[] a = new int[9877];
        int i, pq, p, q, times = 0;

        // 步驟 1
        for (int k = 0; k < 1023; k++) {
            a[k] = 1;
        }
        for (int k = 1023; k < a.length; k++) {
            if (check_4(k) == 1) {
                a[k] = 1;
            }
        }

        System.out.println("請在心裡想一個四個數字都不重複的四位數（例如 5319），我來猜！");

        while (true) {
            // 步驟 2
            i = pick(a);
            if (i == -1) {
                System.out.println("沒有符合的數字了，可能是前面某一次 A、B 回答錯了。");
                break;
            }
            times++;
            System.out.printf("第 %d 次猜測：%d\n", times, i);

            // 步驟 3
            pq = input_answer();
            p = pq / 10;
            q = pq % 10;
            if (p == 4 && q == 0) {
                System.out.printf("猜中了！答案是 %d，總共猜了 %d 次。\n", i, times);
                break;
            }

            // 步驟 4
            filter(a, i, pq);
            // 步驟 5：回到 while 開頭，也就是步驟 2
        }
    }

    /*
     步驟 2：由陣列 a 中挑出第一個值不是 1 的索引，當作要猜的數字回傳；
     如果全部都被排除了就回傳 -1。
     */
    public static int pick(int[] a) {
        for (int i = 0; i < a.length; i++) {
            if (a[i] != 1) {
                return i;
            }
        }
        return -1;
    }

    /*
     步驟 4：把所有還沒被排除（a[k] == 0）的 k 跟這次猜的 i 比對，
     比對結果和人回答的 pq（A×10+B）不同的，就把 a[k] 設為 1 排除掉。
     （對應 11/17 A1 的比對邏輯）
     */
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

    /*
     檢查四位數 x 有沒有重複的數字，有重複回傳 1，沒有回傳 0。
     做法：把 x 拆進 a、b 兩個陣列，交給 repate() 比對不同位置的數字。（11/10 A2）
     */
    public static int check_4(int x) {
        int[] a = new int[4];
        int[] b = new int[4];
        cut(x, a);
        cut(x, b);
        return repate(a, b);
    }

    /*
     把四位數 x 拆成各位數字存入陣列：
     a[0] 個位、a[1] 十位、a[2] 百位、a[3] 千位。（11/10 A2）
     */
    public static void cut(int x, int[] a) {
        a[3] = x / 1000;
        a[2] = (x / 100) % 10;
        a[1] = (x / 10) % 10;
        a[0] = x % 10;
    }

    /*
     比較兩個陣列「不同位置」的數字，只要有任兩位相同就回傳 1（有重複），否則回傳 0。
     （11/10 A2，已把 i==j 修正成 i!=j）
     */
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

    /*
     猜數字遊戲的 ?A?B 判定：比較兩組四位數字，
     數字相同且位置相同算 A，數字相同但位置不同算 B。
     回傳 A×10+B（十位數是 A、個位數是 B）。（11/03 A2）
     */
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

    /*
     分別讀入 A 與 B 的數量，合併成 A×10+B 一個整數回傳。（11/03 A1、11/17 A1）
     */
    public static int input_answer() {
        int p, q;
        System.out.print("A= ");
        p = sc.nextInt();
        System.out.print("B= ");
        q = sc.nextInt();
        return p * 10 + q;
    }
}
