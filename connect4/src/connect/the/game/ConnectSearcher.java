package connect.the.game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import klesk.math.search.AlphaBetaSearcher;
import klesk.math.search.State;

public class ConnectSearcher extends AlphaBetaSearcher {

    public ConnectSearcher(State aStartState, boolean aIsMaximizingPlayerFirst,
	    double aMaximumDepth) {
	super(aStartState, aIsMaximizingPlayerFirst, aMaximumDepth);
    }

    @Override
    public void buildChildren(State aParent) {
	List<State> dzieci = new ArrayList<State>();
	ConnectState p = (ConnectState) aParent;
	ConnectState dziecko;

	for (int i = 1; i < p.szerokosc + 1; i++) {
	    dziecko = new ConnectState(p);
	    dziecko.wykonajRuch(i);
	    dziecko.setRootMove(String.valueOf(i));
	    dzieci.add(dziecko);
	}
	p.setChildren(dzieci);
    }

    public static void main(String[] args) throws IOException {

	ConnectState gra;
	
	BufferedReader keyboard = new BufferedReader(new InputStreamReader(
		System.in), 1);

	String decyzja_w = "";
	String decyzja_k = "";
	
	String tmp="";

	double glebokosc = 3.5;

	Scanner wczytaj = new Scanner(System.in);

	int ruchGracza = 0;
	int kto_pierwszy = 1;

	System.out.println("Rozmiar planszy -> wiersze ");
	try {
	    decyzja_w = keyboard.readLine();
	} catch (Exception e) {
	    System.out.println("B³¹d czytania z klawiatury");
	}

	System.out.println("Rozmiar planszy -> kolumny ");
	try {
	    decyzja_k = keyboard.readLine();
	} catch (Exception e) {
	    System.out.println("B³¹d czytania z klawiatury");
	}

	int kolumny = Integer.parseInt("" + decyzja_k.charAt(0));
	int wiersze = Integer.parseInt("" + decyzja_w.charAt(0));
	

	if (wiersze < 4 || kolumny < 4) {
	    System.out
		    .println("Wysokoœæ i szerokoœæ planszy nie mog¹ byæ mniejsze ni¿ 4 !");
	    System.exit(1);
	}

	/*System.out.println("Kto zaczyna: komputer(0) czy gracz(1):");

	kto_pierwszy = wczytaj.nextInt();*/

	if (kto_pierwszy == 0) {
	    gra = new ConnectState(wiersze, kolumny, false);
	    do{
	    System.out.println("Podaj kolumnê: \n");
	    tmp = keyboard.readLine();
	    System.out.println(gra.toString());
	    ruchGracza = Integer.valueOf(tmp);
	    }while(ruchGracza >= gra.szerokosc || ruchGracza < -1);
	    
	    gra.wykonajRuch(ruchGracza);
	}else{
	    gra = new ConnectState(wiersze, kolumny, true);
	    System.out.println(gra.toString());
	}

	while (true) {

	    if (sprawdz_zwyciezce(gra))
		break;

	    System.out.println("Podaj kolumnê: \n");
	    ruchGracza = wczytaj.nextInt();
	    gra.wykonajRuch(ruchGracza);

	    gra.setParent(null);

	    ConnectSearcher szukaj = new ConnectSearcher(gra, true, glebokosc);
	    szukaj.doSearch();
	    String ruchy = szukaj.getMovesMiniMaxes().toString();
	    System.out.println("oceny: " + ruchy);
	    
	    kto_pierwszy = 0;
	    
	    if (szukaj.getMovesMiniMaxes().isEmpty()) {
		int move = (int) (Math.random() * gra.szerokosc);
		gra.wykonajRuch(move);
	    } else {
		gra.ruchKomputera(ruchy);
	    }
	    
	    if (sprawdz_zwyciezce(gra))
		break;

	    System.out.println("\n" + gra.toString() + "\n");
	}

	wczytaj.close();
    }

    static boolean sprawdz_zwyciezce(ConnectState cs) {
	if (cs.computeHeuristicGrade() == Double.POSITIVE_INFINITY) {
	    System.out.println("Zwyciêstwo komputera.\n");
	    System.out.println(cs.toString());
	    return true;
	} else if (cs.computeHeuristicGrade() == Double.NEGATIVE_INFINITY) {
	    System.out.println("Zwyciêstwo gracza.\n");
	    System.out.println(cs.toString());
	    return true;
	} else
	    return false;

    }

}
