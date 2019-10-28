package no.oslomet.cs.algdat.Oblig3;

////////////////// ObligSBinTre /////////////////////////////////

import com.sun.source.tree.BinaryTree;

import java.util.*;

public class ObligSBinTre<T> implements Beholder<T> {
    private static final class Node<T>   // en indre nodeklasse
    {
        private T verdi;                   // nodens verdi
        private Node<T> venstre, høyre;    // venstre og høyre barn
        private Node<T> forelder;          // forelder

        // konstruktør
        private Node(T verdi, Node<T> v, Node<T> h, Node<T> forelder) {
            this.verdi = verdi;
            venstre = v;
            høyre = h;
            this.forelder = forelder;
        }

        private Node(T verdi, Node<T> forelder)  // konstruktør
        {
            this (verdi, null, null, forelder);
        }

        @Override
        public String toString() {
            return "" + verdi;
        }

    } // class Node

    private Node<T> rot;                            // peker til rotnoden
    private int antall;                             // antall noder
    private int endringer;                          // antall endringer

    private final Comparator<? super T> comp;       // komparator

    public ObligSBinTre(Comparator<? super T> c)    // konstruktør
    {
        rot = null;
        antall = 0;
        comp = c;
    }

    @Override
    public boolean leggInn(T verdi) {
        Objects.requireNonNull (verdi, "Ulovlig med nullverdier!");

        Node<T> p = rot, q = null;               // p starter i roten
        int cmp = 0;                             // hjelpevariabel

        while (p != null)       // fortsetter til p er ute av treet
        {
            q = p;                                 // q er forelder til p
            cmp = comp.compare (verdi, p.verdi);     // bruker komparatoren
            p = cmp < 0 ? p.venstre : p.høyre;     // flytter p
        }

        // p er nå null, dvs. ute av treet, q er den siste vi passerte

        p = new Node<T> (verdi, p);                   // oppretter en ny node

        if (q == null) rot = p;                  // p blir rotnode
        else if (cmp < 0) q.venstre = p;         // venstre barn til q
        else q.høyre = p;                        // høyre barn til q
        endringer++;
        antall++;                                // én verdi mer i treet
        return true;
    }

    @Override
    public boolean inneholder(T verdi) {
        if (verdi == null) return false;

        Node<T> p = rot;

        while (p != null) {
            int cmp = comp.compare (verdi, p.verdi);
            if (cmp < 0) p = p.venstre;
            else if (cmp > 0) p = p.høyre;
            else return true;
        }

        return false;
    }

    @Override
    public boolean fjern(T verdi) {
        throw new UnsupportedOperationException ("Ikke kodet ennå!");
    }

    public int fjernAlle(T verdi) {
        throw new UnsupportedOperationException ("Ikke kodet ennå!");
    }

    @Override
    public int antall() {
        return antall;
    }

    public int antall(T verdi) {
        throw new UnsupportedOperationException ("Ikke kodet ennå!");

    }

    @Override
    public boolean tom() {
        return antall == 0;
    }

    @Override
    public void nullstill() {
        throw new UnsupportedOperationException ("Ikke kodet ennå!");
    }

    private static <T> Node<T> nesteInorden(Node<T> p) {
        throw new UnsupportedOperationException ("Ikke kodet ennå!");
    }

    @Override
    public String toString() {
        throw new UnsupportedOperationException ("Ikke kodet ennå!");
    }

    public String omvendtString() {
        throw new UnsupportedOperationException ("Ikke kodet ennå!");
    }

    public String høyreGren() {
        throw new UnsupportedOperationException ("Ikke kodet ennå!");
    }

    public String lengstGren() {
        throw new UnsupportedOperationException ("Ikke kodet ennå!");
    }

    public String[] grener() {
        throw new UnsupportedOperationException ("Ikke kodet ennå!");
    }

    public String bladnodeverdier() {
        StringBuilder s = new StringBuilder ();
        s.append ('[');
        Node p = rot;
        if (rot == null){
            s.append (']');
            return s.toString ();
        }

        TabellStakk<Node> stakk = new TabellStakk<> () ;
        stakk.leggInn (p);
        while (!stakk.tom ()){
            Node q = stakk.taUt ();
            if (q.høyre != null){
                stakk.leggInn (q.høyre);
            }
            if(q.venstre != null){
                stakk.leggInn (q.venstre);

            } if (q.venstre == null && q.høyre == null){

                s.append (q.verdi);
                if (q.verdi != null) {
                    s.append (',');
                    s.append (' ');
                }
                }
            }
          s.append (']');
        return s.toString ();
    }

    public String postString() {
      /*  TabellStakk<Node> stakk = new TabellStakk<> ();
        StringBuilder s = new StringBuilder ();
        s.append ('[');
        if (stakk == null) {
            s.append (']');
            return s.toString ();
        }
        Node r = rot;
        stakk.leggInn (r); // legger noe øverst på stakken
        Node fo = null;

        while (!stakk.tom ()) {

            Node tmp = stakk.kikk ();
            //
            if (fo == null || fo.venstre == tmp || fo.høyre == tmp) {
                if (tmp.venstre != null)
                    stakk.leggInn (r.venstre);
                else if (tmp.høyre != null)
                    stakk.leggInn (tmp.høyre);
                else {
                    stakk.taUt ();
                    s.append (tmp.verdi);
                    s.append (',');
                }

            } else if (tmp.venstre == fo) {
                if (tmp.høyre != null) {
                    stakk.leggInn (tmp.høyre);
                } else {
                    stakk.taUt ();
                    s.append (tmp.verdi);
                    s.append (',');
                }
            } else if (tmp.høyre == fo) {
                stakk.taUt ();
                s.append (tmp.verdi);
                s.append (',');
            }
            fo = tmp;
        }

        s.append (']');
        return s.toString ();
*/

        StringBuilder s = new StringBuilder ();

   /*     // legge inn først node in stakk
        Node p = rot;
        s1.leggInn (p);
        while (s1.tom () == false) {
            // take out the root and insert into second stack.
            Node temp = s1.taUt ();
            s2.leggInn (temp);
            // now we have the root, push the left and right child of root into
            // the first stack.
            if(temp.venstre!=null){
                s1.leggInn (temp.venstre);
            }
            if(temp.høyre!=null){
                s1.leggInn (temp.høyre);
            }
        }
        //once the all node are traversed, take out the nodes from second stack and print it.
        System.out.println("Preorder Traversal: ");
        while(s2.tom ()==false){
            s.append (s2.taUt ());
          s.append (',');
            s.append (' ') ;
        }
        s.append (']');*/
        Node p = rot;
        TabellStakk<Node> stakk = new TabellStakk<> () ;
        stakk.leggInn (p);
        s.append ('[');
      while (!stakk.tom ()){

          Node q = stakk.kikk ();

          if (q.høyre == null && q.venstre == null){
              Node r = stakk.taUt ();

              if (r.verdi != null) {

                  s.append (r.verdi);
                  s.append (' ');
                  s.append (',');
              }
          }
          else{
              if(q.høyre != null){
                  stakk.leggInn (q.høyre);
                  q.høyre = null;
              }
              if (q.venstre !=null){
                  stakk.leggInn (q.venstre);
                  q.venstre=null;
              }
          }
      }
        s.append (']');
        return s.toString ();
    }

    @Override
    public Iterator<T> iterator() {
        return new BladnodeIterator ();
    }

    private class BladnodeIterator implements Iterator<T> {
        private Node<T> p = rot, q = null;
        private boolean removeOK = false;
        private int iteratorendringer = endringer;

        private BladnodeIterator()  // konstruktør
        {
            throw new UnsupportedOperationException ("Ikke kodet ennå!");
        }

        @Override
        public boolean hasNext() {
            return p != null;  // Denne skal ikke endres!
        }

        @Override
        public T next() {
            throw new UnsupportedOperationException ("Ikke kodet ennå!");
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException ("Ikke kodet ennå!");
        }

    } // BladnodeIterator

} // ObligSBinTre
