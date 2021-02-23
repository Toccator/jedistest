package DataStructure.base.findNextNodeInTree;

import DataStructure.base.linklist.ListNode;

public class TreeLinkNode {

    int val;
    TreeLinkNode left = null;
    TreeLinkNode right = null;
    TreeLinkNode next = null;

    TreeLinkNode(int val) {
        this.val = val;
    }

    public void setNext(TreeLinkNode next) {
        this.next = next;
    }

    public void setLeft(TreeLinkNode next) {
        this.next = next;
    }

    public void setRight(TreeLinkNode next) {
        this.next = next;
    }


    public int getData() {
        return this.val;
    }
}