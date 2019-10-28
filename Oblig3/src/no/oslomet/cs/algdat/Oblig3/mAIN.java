package no.oslomet.cs.algdat.Oblig3;

import java.util.Comparator;

public class mAIN {

    public static class BTNode {

        BTNode(char value) {
            this.value = value;
            this.parent = null;
            this.right = null;
            this.left = null;
        }

        BTNode parent; //< Note: We don't use this in this
        BTNode right; //< Left child
        BTNode left; //< Right child
        char value; //< Value of node
    }


    public static void printBTreePostOrder(BTNode node) {
        if (node == null) {
            return;
        }
        else {
            printBTreePostOrder(node.left);
            printBTreePostOrder(node.right);
            System.out.print(node.value + ", ");
        }
    }

    public static void main (String [] args){
       Integer []s= {4,7,2,9,4,10,8,7,4,6};
        char [] a = {'A','B','C','D','E','F','G'};

        ObligSBinTre<Integer> tres= new ObligSBinTre<>(Comparator.naturalOrder());
        for (int verdi : s) tres.leggInn(verdi);

        ObligSBinTre <Character> tre = new ObligSBinTre<> (Comparator.naturalOrder ());
        for (Character i : a) tre.leggInn (i);
        //System.out.println (tre.antall ());
        System.out.println (tre.postString ());
        //Create level 0
        BTNode root = new BTNode('A');

        //Create level 1
        root.left = new BTNode('B');
        root.right = new BTNode('C');

        //Create level 2
        root.left.left = new BTNode('D');

        root.right.left = new BTNode('E');
        root.right.right = new BTNode('F');

        //Create level 3
        root.right.left.left = new BTNode('G');


        System.out.println("Post order: ");
        printBTreePostOrder(root);
        System.out.println("----------- ");
        System.out.println(tres.postString());
        ObligSBinTre<Character> tr = new ObligSBinTre<>(Comparator.naturalOrder());
        char[] verdier = "IATBHJCRSOFELKGDMPQN".toCharArray();
        for (char c : verdier) tre.leggInn(c);

        System.out.println("Bladnodeordre: ");
        System.out.println (tr.bladnodeverdier ());


    }
}
