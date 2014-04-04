package zadanie.puzzle;

import java.util.ArrayList;
import java.util.List;

import klesk.math.search.AStarSearcher;
import klesk.math.search.State;

public class puzzle_searcher extends AStarSearcher {

    public puzzle_searcher(State aInitialState,
	    boolean aIsStopAfterFirstSolution,
	    boolean aIsStopAfterSecondSolution) {
	super(aInitialState, aIsStopAfterFirstSolution,
		aIsStopAfterSecondSolution);
    }

    @Override
    public boolean isSolution(State aState) {
	boolean koniec = (aState.getH() == 0);

	if (koniec) {
	    State kolejny = aState;
	    double krok = kolejny.getG();

	    List<String> kroki = new ArrayList<String>();

	    System.out.println("Kroków: " + (int) krok + "\n");

	    if (kolejny.getParent() != null) {
		do {
		    kolejny = kolejny.getParent();
		    kroki.add(kolejny.toString());
		} while (kolejny.getParent() != null);

		for (int i = 0; i < krok; i++) {
		    System.out.println("Krok " + i + ":\n"
			    + kroki.get((int) krok - (i + 1)) + "\n");
		}
	    }
	}

	return koniec;
    }

    @Override
    public void buildChildren(State aParent) {
	aParent.getChildren();
	PuzzleState p = (PuzzleState) aParent;

	List<State> dzieci = new ArrayList<State>();

	int i = 0;
	int j = 0;
	boolean tmp = false;

	for (i = 0; i < p.n; i++) {
	    for (j = 0; j < p.n; j++) {

		if (p.plansza[i][j] == p.n2) {
		    tmp = true;
		    break;
		}
	    }
	    if (tmp)
		break;

	}

	if ((i + 1) < p.n) {
	    PuzzleState child = new PuzzleState(p);
	    child.plansza[i][j] = p.plansza[i + 1][j];
	    child.plansza[i + 1][j] = (byte) p.n2;

	    child.computeHeuristicGrade();
	    child.setG(p.getG() + 1);
	    dzieci.add(child);
	}

	if ((i - 1) > -1) {
	    PuzzleState child = new PuzzleState(p);
	    child.plansza[i][j] = p.plansza[i - 1][j];
	    child.plansza[i - 1][j] = (byte) p.n2;

	    child.computeHeuristicGrade();
	    child.setG(p.getG() + 1);
	    dzieci.add(child);
	}

	if ((j + 1) < p.n) {
	    PuzzleState child = new PuzzleState(p);
	    child.plansza[i][j] = p.plansza[i][j + 1];
	    child.plansza[i][j + 1] = (byte) p.n2;

	    child.computeHeuristicGrade();
	    child.setG(p.getG() + 1);
	    dzieci.add(child);
	}

	if ((j - 1) > -1) {
	    PuzzleState child = new PuzzleState(p);
	    child.plansza[i][j] = p.plansza[i][j - 1];
	    child.plansza[i][j - 1] = (byte) p.n2;

	    child.computeHeuristicGrade();
	    child.setG(p.getG() + 1);
	    dzieci.add(child);
	}

	p.setChildren(dzieci);

    }

    public static void main(String[] args) {

	PuzzleState sudoku = new PuzzleState(3, 20);
	puzzle_searcher szukaj = new puzzle_searcher(sudoku, true, false);

	szukaj.doSearch();

	System.out.println("Wynik:\n" + szukaj.getSolutions().get(0));
    }

}
