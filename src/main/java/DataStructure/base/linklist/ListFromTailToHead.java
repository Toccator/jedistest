package DataStructure.base.linklist;




import java.util.ArrayList;
import java.util.Stack;

public class ListFromTailToHead {

    //打印一个链表
    /**
     * 递归方法
     *
     */

//    public ArrayList<Integer> printListFromTailToHead(ListNode listNode) {
//        ArrayList<Integer> ret = new ArrayList<>();
//        if (listNode != null) {
//            ret.addAll(printListFromTailToHead(listNode.next));
//            ret.add(listNode.val);
//        }
//        return ret;
//    }

    /**
     * 头部插入法
     *
     */
//    public ArrayList<Integer> printListFromTailToHead(ListNode listNode) {
//        // 头插法构建逆序链表
//        ListNode head = new ListNode(-1);
//        while (listNode != null) {
//            ListNode memo = listNode.next;
//            listNode.next = head.next;
//            head.next = listNode;
//            listNode = memo;
//        }
//        // 构建 ArrayList
//        ArrayList<Integer> ret = new ArrayList<>();
//        head = head.next;
//        while (head != null) {
//            ret.add(head.val);
//            head = head.next;
//        }
//        return ret;
//    }


    /**
     * 尾部插入法
//     * @param listNode
     * @return
     */
    public ArrayList<Integer> printListFromTailToHead(ListNode listNode) {
        Stack<Integer> stack = new Stack<>();
        while (listNode != null) {
            stack.add(listNode.val);
            listNode = listNode.next;
        }
        ArrayList<Integer> ret = new ArrayList<>();
        while (!stack.isEmpty())
            ret.add(stack.pop());
        return ret;
    }


    public static void main(String[] args) {

        ListNode root = new ListNode(1);      // 定义节点，同时包装数据
        ListNode n1 = new ListNode(2);         // 定义节点，同时包装数据
        ListNode n2 = new ListNode(3);         // 定义节点，同时包装数据
        root.setNext(n1);                         // 设置节点关系
        n1.setNext(n2);                           // 设置节点关系
//        // 第二步：根据节点关系取出所有数据
        ListNode currentNode = root;          // 当前从根节点开始读取
//        while (currentNode != null) {                        // 当前节点存在数据
//            System.out.println(currentNode.getData());
//            currentNode = currentNode.getNext();            // 将下一个节点设置为当前节点
//        }

        ListFromTailToHead listFromTailToHead = new ListFromTailToHead();
        ArrayList<Integer> invertNode = listFromTailToHead.printListFromTailToHead(currentNode);

        for (Integer node:invertNode) {
            System.out.println(node.toString());
        }

    }
}
