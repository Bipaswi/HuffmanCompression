
public class Node {

    public Node right = null;
    public Node left = null;
    public Node parent = null;
    public int weight = 0;
    public int index = 0;
    public String symbol = null;

    public Node(Node right, Node left, Node parent, int weight, int index, String symbol) {
        this.right = right;
        this.left = left;
        this.parent = parent;
        this.weight = weight;
        this.index = index;
        this.symbol = symbol;

    }

    public boolean isRight(Node node){
      return parent.right == node;
    }

    public boolean isLeft(Node node){
       return parent.left == node;
    }

    public boolean isLeaf(Node node){
        return node.left == null && node.right == null;
    }
}
