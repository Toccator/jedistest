package DataStructure.base;


class ListNode {
    int val;
    ListNode next;
    ListNode(int newdata) {
    }

    public ListNode() {
        
    }

    public void add(int newdata) {
        ListNode newNode = new ListNode(newdata);    //创建一个结点
        if (this.next == null) {
            this.next = newNode;
        } else {
            this.next.add(newdata);
        }
    }

}


public class mergeTwoLists {
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode dummyHead = new ListNode(0);
        ListNode currNode = dummyHead;


        while (l1 != null && l2 != null) {
            if (l1.val < l2.val) {
                currNode.next = l1;
                l1 = l1.next;
            } else {
                currNode.next = l2;
                l2 = l2.next;
            }
            currNode = currNode.next;
        }

        if (l1 != null) currNode.next = l1;
        if (l2 != null) currNode.next = l2;

        return dummyHead.next;
    }

    public static void main(String[] args) {
        ListNode listNode = new ListNode();



    }

}
