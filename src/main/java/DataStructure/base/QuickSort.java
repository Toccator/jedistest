package DataStructure.base;

public class QuickSort {


    public static void main(String[] args) {

        int[] num = {50, 45, 78, 64, 52, 11, 64, 55, 99, 12, 18};
        System.out.println(arrayToString(num, "未排序"));
        System.out.println("排序次数："+QuickSort(num, 0, num.length - 1));
        System.out.println(arrayToString(num, "排序"));
        System.out.println("数组个数：" + num.length);
//        System.out.println("循环次数：" + count);
    }


    private static int QuickSort(int[] num, int left, int right) {
        int count = 0;
        //如果left等于right，即数组只有一个元素，直接返回
        if (left >= right) {
            return left;
        }
        //设置最左边的元素为基准值
        int key = num[left];
        //数组中比key小的放在左边，比key大的放在右边，key值下标为i
        int i = left;
        int j = right;
        while (i < j) {
            //j向左移，直到遇到比key小的值
            while (num[j] >= key && i < j) {
                j--;
            }
            //i向右移，直到遇到比key大的值
            while (num[i] <= key && i < j) {
                i++;
            }
            //i和j指向的元素交换
            if (i < j) {
                int temp = num[i];
                num[i] = num[j];
                num[j] = temp;
            }
        }

        System.out.println(arrayToString(num, "跑一圈"));
        //
        num[left] = num[i];
//        System.out.println(arrayToString(num, "换一下"));

        num[i] = key;
        System.out.println(arrayToString(num, "换一下"));

        count++;

        QuickSort(num, left, i - 1);
        QuickSort(num, i + 1, right);
        return count;
    }

    /**
     * 将一个int类型数组转化为字符串
     *
     * @param arr
     * @param flag
     * @return
     */
    private static String arrayToString(int[] arr, String flag) {
        String str = "数组为(" + flag + ")：";
        for (int a : arr) {
            str += a + "\t";
        }
        return str;

    }
}