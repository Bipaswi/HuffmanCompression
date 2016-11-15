import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class HuffmanTest {

    //junit tests to see if tree is being updated correctly

    @Test
    public void updateTree(){
        Tree newTree = new Tree();
        EncodingDecoding ed = new EncodingDecoding();
        System.out.println();
        System.out.println("aardv tree");
        System.out.println();
        newTree.treeUpdate("a");
        newTree.print();
        newTree.treeUpdate("a");
        newTree.print();
        newTree.treeUpdate("!");
        newTree.print();
        newTree.treeUpdate("d");
        newTree.print();
        newTree.treeUpdate("v");
        newTree.print();

        System.out.println("tree place 1 " + newTree.root.symbol);
        System.out.println("tree place 2 " + newTree.root.left.symbol);
        System.out.println("tree place 3 " + newTree.root.right.symbol);
        System.out.println("tree place 4 " + newTree.root.right.left.symbol);
        System.out.println("tree place 5 " + newTree.root.right.right.symbol);
        System.out.println("tree place 6 " + newTree.root.right.right.right.symbol);
        System.out.println("tree place 7 " + newTree.root.right.right.left.symbol);
        System.out.println("tree place 7.1 " + newTree.root.right.right.left.left.symbol);
        System.out.println("tree place 7.2 " + newTree.root.right.right.left.right.symbol);

        System.out.println("******** breadth");
        //newTree.breadth(newTree.root.right.weight);
        newTree.printTree();
        System.out.println();
        String code = ed.getCode(newTree.root, newTree.leafTable.get("!"));
        System.out.println("code " + code);

        assertEquals(newTree.root.right.right.left.left, newTree.NYT);

        String code2 = ed.getCode(newTree.root, newTree.leafTable.get("d"));
        System.out.println("code 2 " + code2);

        String code4 = ed.getCode(newTree.root, newTree.leafTable.get("v"));
        System.out.println(" code 4 " + code4);

        String code5 = ed.getCode(newTree.root, newTree.NYT);
        System.out.println(" code5 " + code5);

        String ascii = ed.assciiConvertToBit("33");
        System.out.println(" ascii " + ascii);

        String ascii2 = ed.AsciiToBinary("33");
        System.out.println(" ascii method 2 " + ascii2);

        String test2 = ed.convertBitToAsscii("0101010");
        System.out.println(" test2 " + test2);

    }

    @Test
    public void LargestIndex(){
        Tree newTree = new Tree();

        newTree.insert("a");
        newTree.insert("a");
        newTree.insert("r");
        newTree.insert("d");
        newTree.insert("v");

        newTree.sameWeightLargestIndex(newTree.leafTable.get("r"));
        assertEquals(newTree.biggestIndex.index, 511);
        assertEquals(newTree.biggestIndex.symbol, "a");

    }

    @Test
    public void swapTest(){
        Tree tree = new Tree();
        tree.root = new Node(null, null, null, 0, 5, "root");
        tree.root.left = new Node(null, null, tree.root, 0, 4, "a" );
        tree.root.right = new Node(null, null, tree.root, 0 , 3, "-1");
        tree.root.right.left = new Node(null, null, tree.root.right, 0, 2, "-1");
        tree.root.right.right = new Node(null, null, tree.root.right, 0 , 1, "-1");

        tree.swap(tree.root.right, tree.root.left);
        assertEquals(tree.root.right.symbol, "a");

    }
}

