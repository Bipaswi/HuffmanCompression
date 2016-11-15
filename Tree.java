import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class Tree {

    //https://www.cs.duke.edu/csed/curious/compression/adaptivehuff.html#implementations

    public Node biggestIndex;
    public Node root;
    public Node NYT; // Not Yet Transferred
    public Node list[]; // fast look up for all nodes
    public int listTop; // top of the list
    public HashMap<String, Node> leafTable = new HashMap<>(); //fast look up for leaves

    public Tree() {
        //table = new Node[256];
        list = new Node[513];
        listTop = 513;
        list[--listTop] = root = NYT = new Node(null, null, null, 0, 512, "root");
    }

    //inserts symbol into tree and increments it's weight
    public Node insert(String symbol){
        //giving birth to new nyt and external nodes
            Node temp = NYT;
            Node newSymbol = new Node(null, null, temp, 1, NYT.index -1, symbol);
            list[--listTop] = newSymbol;
            //test
            NYT.symbol = "";
            //
            NYT = new Node(null, null, temp, 0, NYT.index - 2, "-1");
            list[--listTop] = NYT;
            temp.left = NYT;
            temp.right = newSymbol;
            leafTable.put(symbol, newSymbol);
            return temp.right;
    }

    //finds the largest weight and and highest index before swapping
    public void sameWeightLargestIndex(Node searchNode){
        int successCount = 0;
        for (int i = listTop; (i < 512); i++) {
            if ((list[i].index > searchNode.index) && (list[i].weight == searchNode.weight) && (list[i] != searchNode.parent)) {
                successCount++;
                biggestIndex = list[i];
            }
        }
        if (successCount == 0) {
            biggestIndex = searchNode;
        }
    }

    //prints all nodes in tree
    public void printNode(Node node){
        if(node == null){
            return;
        }
        System.out.print("(" + " " + node.index + " " + node.symbol + " " + node.weight);
        printNode(node.left);
        printNode(node.right);
        System.out.print(")");
    }

    public void print(){
        printNode(root);
        System.out.println();
    }

    public Node printTree() {
        Queue<Node> queue = new LinkedList<>() ;
        if (root == null)
            return null;
        //queue.clear();
        queue.add(root);
        while(!queue.isEmpty()){
            Node node = queue.remove();
            //if (node.weight == weight) return node;
            System.out.print("| " + node.index + " " + node.symbol +" "+  node.weight + " ");
            if(node.right != null) queue.add(node.right);
            if(node.left != null) queue.add(node.left);

        }
        return null;
    }


    //swap two nodes around in order to balance the tree
    public Node swap(Node maxIndexNode, Node current){
        Node saveCurrent = current;
        int saveCurrentIndex = current.index;

        if (maxIndexNode.isLeft(maxIndexNode)) {
            maxIndexNode.parent.left = current;
        }
        else {
            maxIndexNode.parent.right = current;
        }
        if (saveCurrent.isLeft(saveCurrent)) {
            saveCurrent.parent.left = maxIndexNode;
        }
        else {
            saveCurrent.parent.right = maxIndexNode;
        }
        //swap parents
        Node tempMax = maxIndexNode.parent;
        maxIndexNode.parent = saveCurrent.parent;
        saveCurrent.parent = tempMax;
        //swap indexes;
        current.index = maxIndexNode.index;
        maxIndexNode.index = saveCurrentIndex;

        return saveCurrent;
    }

    //tree manipulation, adds and balances the tree as new symbopls are entered into it.
    public void treeUpdate(String symbol) {
        Node t = leafTable.get(symbol);
        while(t != root) {
            if (t != null) {
                sameWeightLargestIndex(t);
                if(biggestIndex.index > t.index){
                    swap(biggestIndex,t);
                }
                if ((t.left != null) && (t.right != null) && (t.symbol.equals("-1"))) {
                    t.weight = t.left.weight + t.right.weight;
                }
                else {
                    t.weight++;
                }
                t = t.parent;
            }
            else {
                t = insert(symbol);
                t = t.parent;
            }
        }
        root.weight++;
    }
}

