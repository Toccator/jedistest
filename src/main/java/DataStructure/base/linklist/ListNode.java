package DataStructure.base.linklist;


public class ListNode {
    int val;
    ListNode next;
//    ListNode(int x) { val = x; }

    public ListNode(int val) {                // 必须有数据才有Node
        this.val = val;
    }

    /**
     * 设置下一个节点关系
     *
     * @param next 保存下一个Node类引用
     */
    public void setNext(ListNode next) {
        this.next = next;
    }

    /**
     * 取得当前节点的下一个节点
     *
     * @return 当前节点的下一个节点引用
     */
    public ListNode getNext() {
        return this.next;
    }

    /**
     * 设置或修改当前节点包装的数据
     *
     */
    public void setData(int val) {
        this.val = val;
    }

    /**
     * 取得包装的数据
     *
     * @return
     */
    public int getData() {
        return this.val;
    }
}
