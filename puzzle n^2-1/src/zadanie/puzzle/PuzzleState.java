package zadanie.puzzle;

import klesk.math.search.StateImpl;

public class PuzzleState extends StateImpl {

    public int n;
    public int n2;
    public byte[][] plansza;

    public PuzzleState(int n, int razy) {
	super(null);

	if (n > 9 || n < 2) {
	    System.out.println("Podano zbyt ma³e lub zbyt du¿e N!");
	    System.exit(1);
	}

	this.n = n;
	this.n2 = n * n;

	plansza = new byte[n][n];

	for (int i = 0; i < n; i++) {
	    for (int j = 0; j < n; j++) {
		plansza[i][j] = (byte) (i * n + j + 1);
	    }
	}

	mieszaj(razy);

	computeHeuristicGrade();
	setG(0);
    }

    public PuzzleState(PuzzleState aParent) {
	super(aParent);

	n = aParent.n;
	n2 = aParent.n2;

	plansza = new byte[n][n];

	for (int i = 0; i < n; i++) {
	    for (int j = 0; j < n; j++) {
		plansza[i][j] = aParent.plansza[i][j];
	    }
	}
    }

    public PuzzleState(int n, String txt) {
	super(null);

	if (n > 9 || n < 2) {
	    System.out.println("Podano zbyt ma³e lub zbyt du¿e N!");
	    System.exit(1);
	}

	this.n = n;
	this.n2 = n * n;

	plansza = new byte[n][n];

	if (n <= 3) {
	    for (int i = 0; i < n; i++) {
		for (int j = 0; j < n; j++) {
		    int k = i * n + j;

		    plansza[i][j] = Byte.valueOf(txt.substring(k, k + 1))
			    .byteValue();
		}
	    }
	} else if (n <= 9) {
	    for (int i = 0; i < n; i++) {
		for (int j = 0; j < n; j++) {
		    int k = (i * n + j) * 2;

		    plansza[i][j] = Byte.valueOf(txt.substring(k, k + 2))
			    .byteValue();
		}
	    }
	} else {
	    for (int i = 0; i < n; i++) {
		for (int j = 0; j < n; j++) {
		    int k = (i * n + j) * 3;

		    plansza[i][j] = Byte.valueOf(txt.substring(k, k + 3))
			    .byteValue();
		}
	    }
	}

	computeHeuristicGrade();
	setG(0);
    }

    @Override
    public double computeHeuristicGrade() {
	int counter = 0;

	for (int i = 0; i < n; i++) {
	    for (int j = 0; j < n; j++) {
		if (plansza[i][j] != (i * n + j + 1))
		    counter++;
	    }
	}

	setH(counter);

	return counter;
    }

    @Override
    public String getHashCode() {
	return toString();
    }

    @Override
    public String toString() {
	String wyniki = "";

	for (int i = 0; i < n; i++) {
	    for (int j = 0; j < n; j++) {
		if (plansza[i][j] < 10) {
		    wyniki += " ";
		}
		wyniki += plansza[i][j];
		if (j < n - 1)
		    wyniki += ",";
	    }
	    if (i < n - 1)
		wyniki += "\n";
	}

	return wyniki;
    }

    private void mieszaj(int razy) {
	int i = n - 1;
	int j = i;
	byte zbior[] = { 0, 0, 0, 0 };

	for (int k = 0; k < razy; k++) {
	    int pozycja = 0;

	    if ((i - 1) > -1) {
		zbior[pozycja] = 1;
		pozycja++;
	    }
	    if ((i + 1) < n) {
		zbior[pozycja] = 2;
		pozycja++;
	    }
	    if ((j - 1) > -1) {
		zbior[pozycja] = 3;
		pozycja++;
	    }
	    if ((j + 1) < n) {
		zbior[pozycja] = 4;
		pozycja++;
	    }

	    byte losowanie = (byte) (Math.random() * pozycja);

	    if (zbior[losowanie] == 1) {
		plansza[i][j] = plansza[i - 1][j];
		plansza[i - 1][j] = (byte) n2;
		i = i - 1;
	    } else if (zbior[losowanie] == 2) {
		plansza[i][j] = plansza[i + 1][j];
		plansza[i + 1][j] = (byte) n2;
		i = i + 1;
	    } else if (zbior[losowanie] == 3) {
		plansza[i][j] = plansza[i][j - 1];
		plansza[i][j - 1] = (byte) n2;
		j = j - 1;
	    } else {
		plansza[i][j] = plansza[i][j + 1];
		plansza[i][j + 1] = (byte) n2;
		j = j + 1;
	    }
	}
    }

}
