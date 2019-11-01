package no.oslomet.cs.algdat.Oblig3;

////////////////// ObligSBinTre /////////////////////////////////



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
    public boolean inneholder(T verdi)
    {
        if (verdi == null) return false;

        Node<T> p = rot;

        while (p != null)
        {
            int cmp = comp.compare(verdi, p.verdi);
            if (cmp < 0) p = p.venstre;
            else if (cmp > 0) p = p.høyre;
            else return true;
        }

        return false;
    }

    @Override
    public boolean fjern(T verdi) {
        if (verdi == null) return false;  // treet har ingen nullverdier

        Node<T> p = rot, q = null;   // q skal være forelder til p

        while (p != null)            // leter etter verdi
        {
            int cmp = comp.compare(verdi,p.verdi);      // sammenligner
            if (cmp < 0) { q = p; p = p.venstre; }      // går til venstre
            else if (cmp > 0) { q = p; p = p.høyre; }   // går til høyre
            else break;    // den søkte verdien ligger i p
        }
        if (p == null) return false;   // finner ikke verdi

        if (p.venstre == null || p.høyre == null)  // Tilfelle 1) og 2)
        {
            Node<T> b = p.venstre != null ? p.venstre : p.høyre;  // b for barn
            if (p == rot) rot = b;
            else if (p == q.venstre) q.venstre = b;
            else q.høyre = b;
        }
        else  // Tilfelle 3)
        {
            Node<T> s = p, r = p.høyre;   // finner neste i inorden
            while (r.venstre != null)
            {
                s = r;    // s er forelder til r
                r = r.venstre;
            }

            p.verdi = r.verdi;   // kopierer verdien i r til p

            if (s != p) s.venstre = r.høyre;
            else s.høyre = r.høyre;
        }

        antall--;   // det er nå én node mindre i treet
        return true;




    }

    public int fjernAlle(T verdi) {
        if (verdi == null) throw new
                IllegalArgumentException("verdi er null!");

        Node<T> p = rot;
        Node<T> q = null;
        Node<T> r = null;
        Node<T> s = null;

        Stakk<Node<T>> stakk = new TabellStakk<>();

        while (p != null)
        {
            int cmp = comp.compare(verdi,p.verdi);

            if (cmp < 0)
            {
                s = r;
                r = q = p;
                p = p.venstre;
            }
            else
            {
                if (cmp == 0)
                {
                    stakk.leggInn(q);
                    stakk.leggInn(p);
                }

                q = p;
                p = p.høyre;
            }
        }


        int verdiAntall = stakk.antall()/2;

        if (verdiAntall == 0) return 0;

        while (stakk.antall() > 2)
        {
            p = stakk.taUt();
            q = stakk.taUt();

            if (p == q.venstre) q.venstre = p.høyre;
            else q.høyre = p.høyre;
        }



        p = stakk.taUt();
        q = stakk.taUt();

        if (p.venstre == null || p.høyre == null)
        {
            Node<T> x = p.høyre == null ? p.venstre : p.høyre;
            if (p == rot) rot = x;
            else if (p == q.venstre) q.venstre = x;
            else q.høyre = x;
        }
        else
        {
            p.verdi = r.verdi;
            if (r == p.høyre) p.høyre = r.høyre;
            else s.venstre = r.høyre;
        }

        antall -= verdiAntall;

        return verdiAntall;
    }

    @Override
    public int antall() {
        return antall;
    }

    public int antall(T verdi) {
            if(verdi.equals(null)) return 0;
            int forekomster = 0;
            Node<T> p = rot;
            while (p != null)
            {
                int cmp = comp.compare(verdi, p.verdi);
                if (cmp < 0) p = p.venstre;
                else{
                    if (cmp == 0) forekomster++;
                    p = p.høyre;
                }
            }
            return forekomster;
        }

    @Override
    public boolean tom() {
        return antall == 0;
    }

    @Override
    public void nullstill() {
        if (!tom()) nullstill(rot);  // nullstiller
        rot = null; antall = 0;      // treet er nå tomt
        endringer++;   // treet er endret
    }

    private void nullstill(Node<T> p)
    {
        if (p.venstre != null)
        {
            nullstill(p.venstre);
            p.venstre = null;
        }
        if (p.høyre != null)
        {
            nullstill(p.høyre);
            p.høyre = null;
        }
        p.verdi = null;
    }


    private static <T> Node<T> nesteInorden(Node<T> p) {
        if(p.høyre != null){
            p = p.høyre;
            while(p.venstre != null){
                p = p.venstre;
            }
            return p;
        }else{
            while(p.forelder != null && p.forelder.høyre != p){
                    p = p.forelder;

                }
                return p.forelder;
            }

    }

    @Override
    public String toString()
    {
        if(tom()) return "[]";


        StringBuilder stringb = new StringBuilder();
        stringb.append("[");

        Node<T> p = rot;
        while(p.venstre != null){
            p = p.venstre;
        }

        for(int i = 0; i < antall; i++){
            stringb.append(p.verdi);
            if(i != (antall-1)) stringb.append(", ");
            p = nesteInorden(p);
        }

        stringb.append("]");


        return stringb.toString();
    }

    public String omvendtString()
    {
        if(tom()) return "[]";

        StringBuilder b  = new StringBuilder();
        b.append("[");

        Stakk<Node<T>> stakk = new TabellStakk<>();
        Node<T> p = rot;

        for ( ; p.høyre!= null; p = p.høyre)
        {
            stakk.leggInn(p);
        }
        b.append(p.verdi);
        while (true)
        {
            if (p.venstre != null)
            {
                for (p = p.venstre; p.høyre != null; p = p.høyre)
                {
                    stakk.leggInn(p);
                }
            }
            else if (!stakk.tom())
            {
                p = stakk.taUt();

            }

            else break;
            b.append(", ");
            b.append(p.verdi);

        }

        b.append("]");

        return  b.toString();
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

        StringJoiner s = new StringJoiner(", ", "[",  "]");

        Node p = rot;
        if (rot == null){
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
                    s.add (q.verdi.toString ());


                }
            }    //s.append (']');
        //String result = s.deleteCharAt(s.length() - 2).toString()+"]" + s.toString ().length ();
        // result += "]";
        return s.toString ();
    }

    public String postString() {


        StringJoiner s = new StringJoiner(", ", "[",  "]");


        Node p = rot;
        if (rot == null){
            return s.toString ();

        }
        TabellStakk<Node> stakk = new TabellStakk<> () ;
        stakk.leggInn (p);

      while (!stakk.tom ()){

          Node q = stakk.kikk ();

          if (q.høyre == null && q.venstre == null){
              Node r = stakk.taUt ();

              if (r.verdi != null) {

                  s.add (r.verdi.toString ());

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
