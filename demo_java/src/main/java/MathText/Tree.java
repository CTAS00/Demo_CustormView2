package MathText;

/**
 * Created by koudai_nick on 2018/4/20.
 *
 *     A
 *   B   C
 *  D E
 */

public class Tree {
    private Node FirstNode;

    public  Tree(){
        Node A = new Node("A");
        Node B = new Node("B");
        Node C = new Node("C");
        Node D = new Node("D");
        Node E = new Node("E");

        A.setLeftNode(B);
        A.setRightNode(C);
        B.setLeftNode(D);
        B.setRightNode(E);

        FirstNode = A;

    }
    // 先序遍历 NLR
    public void firstTraverse(){

        TreeFirstTraverse(FirstNode);
    }

    private void TreeFirstTraverse(Node node) {
        if(node ==null){
            return ;
        }
        System.out.print(node.getNodeName());
        TreeFirstTraverse(node.getLeftNode());
        TreeFirstTraverse(node.getRightNode());
    }

    private class Node{
        private Node leftNode;
        private Node rightNode;
        private String NodeName;



        public Node(String NodeName){
            this.NodeName = NodeName;
        }

        public String getNodeName() {
            return NodeName;
        }

        public void setNodeName(String nodeName) {
            NodeName = nodeName;
        }

        public Node getLeftNode() {
            return leftNode;
        }

        public void setLeftNode(Node leftNode) {
            this.leftNode = leftNode;
        }

        public Node getRightNode() {
            return rightNode;
        }

        public void setRightNode(Node rightNode) {
            this.rightNode = rightNode;
        }
    }
}
