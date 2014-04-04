package glinski.si.sudoku;

import java.util.ArrayList;
import java.util.List;

public class SudokuSolver extends AStarSearcher {

    public SudokuSolver(State aInitialState, boolean aIsStopAfterFirstSolution,
	    boolean aIsStopAfterSecondSolution) {
	super(aInitialState, aIsStopAfterFirstSolution,
		aIsStopAfterSecondSolution);
    }

    @Override
    public boolean isSolution(State aState) {
	return aState.getH() == 0 && aState.isAdmissible() ;
    }

    @Override
    public void buildChildren(State aParent) {
	// TODO Auto-generated method stub
	SudokuState sudokuParent = (SudokuState) aParent;
	int n4 = sudokuParent.getN2() * sudokuParent.getN2();
	int j = 0, k = 0;
	for (int i = 0; i < n4; i++) {
	    j = i / sudokuParent.getN2();
	    k = i % sudokuParent.getN2();

	    if (sudokuParent.getBoard()[j][k] == 0)
		break;
	}

	List<State> children = new ArrayList<State>();

	for (int i = 1; i <= sudokuParent.getN2(); i++) {
	    SudokuState child = new SudokuState(sudokuParent);
	    child.getBoard()[j][k] = (byte) i;
	    if (!child.isAdmissible())
		continue;
	    child.computeHeuristicGrade();
	    children.add(child);
	}
	sudokuParent.setChildren(children);
    }

    public static void main(String args[]) {

	// sudoku string
	String txt = "003020600900305001001006400008102900700000008006008200002609500800203009005010300";

	SudokuState s3 = new SudokuState(txt);
	System.out.println("Sudoku\n");
	System.out.println("Poprawne dane wejsciowe: "+s3.isAdmissible());
	System.out.println("Heurystyka: "+s3.computeHeuristicGrade() + "\n\n");


	SudokuSolver solver = new SudokuSolver(s3, false, false);
	long t1 = System.currentTimeMillis();
	solver.doSearch();
	long t2 = System.currentTimeMillis();
	System.out.println("Time: "+(t2-t1));
	System.out.println("Visited: " +  solver.getClosed().size()+"\n");

	List<State> solution = solver.getSolutions();

	for (State sol : solution) {
	    System.out.println(sol+"\n");
	    System.out.println(solution.size());
	}
    }

}
