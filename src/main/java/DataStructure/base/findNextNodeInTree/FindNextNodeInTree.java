package DataStructure.base.findNextNodeInTree;

import DataStructure.base.linklist.ListNode;

public class FindNextNodeInTree {

    public TreeLinkNode GetNext(TreeLinkNode pNode) {
        if (pNode.right != null) {
            TreeLinkNode node = pNode.right;
            while (node.left != null)
                node = node.left;
            return node;
        } else {
            while (pNode.next != null) {
                TreeLinkNode parent = pNode.next;
                if (parent.left == pNode)
                    return parent;
                pNode = pNode.next;
            }
        }
        return null;
    }

    public static void main(String[] args) {

        TreeLinkNode root = new TreeLinkNode(1);// 定义节点，同时包装数据

        TreeLinkNode n1 = new TreeLinkNode(2);         // 定义节点，同时包装数据
        TreeLinkNode n2 = new TreeLinkNode(3);
        TreeLinkNode n3= new TreeLinkNode(4);      // 定义节点，同时包装数据
        TreeLinkNode n4 = new TreeLinkNode(5);         // 定义节点，同时包装数据
        TreeLinkNode n5 = new TreeLinkNode(6);
        TreeLinkNode n6 = new TreeLinkNode(7);

        root.setLeft(n1);
        root.setRight(n2);
        n1.setLeft(n3);
        n1.setRight(n4);
        n2.setLeft(n5);
        n2.setRight(n6);



        FindNextNodeInTree findNextNodeInTree = new FindNextNodeInTree();
        findNextNodeInTree.GetNext(n2);

    }
}