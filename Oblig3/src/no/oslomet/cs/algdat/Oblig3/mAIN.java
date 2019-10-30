package no.oslomet.cs.algdat.Oblig3;


import java.util.Comparator;

import static java.util.Comparator.naturalOrder;


public class mAIN {

    public static void main(String[] args) {
        Integer[] s = {4, 7, 2, 9, 4, 10, 8, 7, 4, 6};
        char[] a = {'A', 'B', 'C', 'D', 'E', 'F', 'G'};

        ObligSBinTre<Integer> tres = new ObligSBinTre<> (naturalOrder ());
        for (int verdi : s) tres.leggInn (verdi);
        System.out.println ("Bladnodeordre: ");
        System.out.println (tres.bladnodeverdier ());
        System.out.println ("----------- ");
        System.out.println ("Post order: ");
        System.out.println (tres.postString ());

        ObligSBinTre<Character> tre = new ObligSBinTre<> (naturalOrder ());
        for (Character i : a) tre.leggInn (i);
        System.out.println ("Blanodeordre");
        System.out.println (tre.bladnodeverdier ());
        System.out.println ("----------- ");
        System.out.println (tre.postString ());

        ObligSBinTre<Character> tr = new ObligSBinTre<> (naturalOrder ());
        char[] verdier = "IATBHJCRSOFELKGDMPQN".toCharArray ();
        for (char c : verdier) tre.leggInn (c);
        System.out.println ("Bladnodverdier: ");
        System.out.println (tr.bladnodeverdier ());
        System.out.println ("-------------");
        System.out.println ("postordre");
        System.out.println (tr.postString ());

        System.out.println ("-------------");

        //Oppgave3
        int[] i = {4,7,2,9,4,10,8,7,6,4,1};
        ObligSBinTre<Integer> tree = new ObligSBinTre<>(Comparator.naturalOrder());
        for (int verdi : i) tree.leggInn(verdi);
        System.out.println(tree);


    }
}
