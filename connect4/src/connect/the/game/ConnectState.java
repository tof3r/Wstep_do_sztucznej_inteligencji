package connect.the.game;

import java.util.Random;

import klesk.math.search.StateImpl;

public class ConnectState extends StateImpl {

    public byte[][] plansza;
    public int szerokosc;
    public int wysokosc;
    public boolean ruch = false;

    public ConnectState(ConnectState aParent) {
	super(aParent);

	this.szerokosc = aParent.szerokosc;
	this.wysokosc = aParent.wysokosc;
	this.ruch = aParent.ruch;

	plansza = new byte[wysokosc][szerokosc];

	for (int i = 0; i < aParent.wysokosc; i++) {
	    for (int j = 0; j < aParent.szerokosc; j++) {
		plansza[i][j] = aParent.plansza[i][j];
	    }
	}
	computeHeuristicGrade();
    }

    public ConnectState(int wys, int szer, boolean r) {
	super(null);
	this.wysokosc = wys;
	this.szerokosc = szer;
	this.ruch = r;
	plansza = new byte[wys][szer];

	for (int i = 0; i < wys; i++) {
	    for (int j = 0; j < szer; j++) {
		plansza[i][j] = 0;
	    }
	}

	if (ruch == true) {
	    Random generator = new Random();
	    wykonajRuch(generator.nextInt(szerokosc) + 1);
	}

	computeHeuristicGrade();
    }

    public void wykonajRuch(int kolumna) {
	kolumna = kolumna - 1;

	for (int i = wysokosc - 1; i >= 0; i--) {
	    if (plansza[i][kolumna] == 0) {
		if (ruch == false)
		    plansza[i][kolumna] = 1;
		else if (ruch == true)
		    plansza[i][kolumna] = 2;

		ruch = !ruch;
		break;
	    }
	}
    }

    public void ruchKomputera(String ruchy) {
	ruchy = ruchy.substring(1, ruchy.length() - 1);
	String rekord[] = ruchy.split(", ");
	int zmienna[] = new int[rekord.length];
	double wartosc[] = new double[rekord.length];
	double temp = Double.NEGATIVE_INFINITY;
	int wybor = 0;

	for (int i = 0; i < rekord.length; i++) {
	    zmienna[i] = Integer.valueOf(rekord[i].substring(0, 1));
	    wartosc[i] = Double.valueOf(rekord[i].substring(2,
		    rekord[i].length()));
	}

	for (int i = 0; i < wartosc.length; i++) {
	    if (wartosc[i] > temp) {
		temp = wartosc[i];
		wybor = i;
	    }
	}

	wykonajRuch(zmienna[wybor]);

    }

    @Override
    public double computeHeuristicGrade() {
	double wynikX = 0;
	double wynikO = 0;

	for (int i = 0; i < szerokosc; i++) {
	    if (plansza[0][i] == 1) {
		return Double.NEGATIVE_INFINITY;
	    } else if (plansza[0][i] == 2) {
		return Double.POSITIVE_INFINITY;
	    }
	}

	// Obliczenie heurystyki dla X

	// kolumny
	int wartosc = 0;

	for (int i = 0; i < szerokosc; i++) {
	    for (int j = wysokosc - 1; j > 2; j--) {
		if (plansza[j][i] == 1 && wartosc == 0) {
		    wartosc++;
		    for (int k = 1; k < 4; k++) {
			if (plansza[j - k][i] == 1)
			    wartosc++;
			else if (plansza[j - k][i] == 2) {
			    wartosc = 0;
			    break;
			}
		    }
		    if (wartosc == 4)
			return Double.NEGATIVE_INFINITY;
		    wynikX -= wartosc ^ 3;
		}
	    }
	    wartosc = 0;
	}

	// wiersze
	wartosc = 0;

	for (int i = 0; i < wysokosc; i++) {
	    for (int j = 0; j < szerokosc - 3; j++) {
		for (int k = 0; k < 4; k++) {
		    if (plansza[i][j + k] == 1)
			wartosc++;
		    else if (plansza[i][j + k] == 2) {
			wartosc = 0;
			break;
		    }
		}
		if (wartosc == 4)
		    return Double.NEGATIVE_INFINITY;
		wynikX -= wartosc ^ 3;
		wartosc = 0;
	    }
	    wartosc = 0;
	}

	// skosy lewe
	wartosc = 0;

	for (int i = wysokosc - 1; i > 2; i--) {
	    for (int j = 0; j < szerokosc - 3; j++) {
		for (int k = 0; k < 4; k++) {
		    if (plansza[i - k][j + k] == 1)
			wartosc++;
		    else if (plansza[i - k][j + k] == 2) {
			wartosc = 0;
			break;
		    }
		}
		if (wartosc == 4)
		    return Double.NEGATIVE_INFINITY;
		wynikX -= wartosc ^ 3;
		wartosc = 0;
	    }
	    wartosc = 0;
	}

	// skosy prawe
	wartosc = 0;

	for (int i = wysokosc - 1; i > 2; i--) {
	    for (int j = szerokosc - 1; j > 2; j--) {
		for (int k = 0; k < 4; k++) {
		    if (plansza[i - k][j - k] == 1)
			wartosc++;
		    else if (plansza[i - k][j - k] == 2) {
			wartosc = 0;
			break;
		    }
		}
		if (wartosc == 4)
		    return Double.NEGATIVE_INFINITY;
		wynikX -= wartosc ^ 3;
		wartosc = 0;
	    }
	    wartosc = 0;
	}

	// Obliczenie heurystyki dla O
	// kolumny
	wartosc = 0;

	for (int i = 0; i < szerokosc; i++) {
	    for (int j = wysokosc - 1; j > 2; j--) {
		if (plansza[j][i] == 2 && wartosc == 0) {
		    wartosc++;
		    for (int k = 1; k < 4; k++) {
			if (plansza[j - k][i] == 2)
			    wartosc++;
			else if (plansza[j - k][i] == 1) {
			    wartosc = 0;
			    break;
			}
		    }
		    if (wartosc == 4)
			return Double.POSITIVE_INFINITY;
		    wynikO += wartosc ^ 3;
		}
	    }
	    wartosc = 0;
	}

	// wiersze
	wartosc = 0;

	for (int i = 0; i < wysokosc; i++) {
	    for (int j = 0; j < szerokosc - 3; j++) {
		for (int k = 0; k < 4; k++) {
		    if (plansza[i][j + k] == 2)
			wartosc++;
		    else if (plansza[i][j + k] == 1) {
			wartosc = 0;
			break;
		    }
		}
		if (wartosc == 4)
		    return Double.POSITIVE_INFINITY;
		wynikO += wartosc ^ 3;
		wartosc = 0;
	    }
	    wartosc = 0;
	}

	// skosy lewe
	wartosc = 0;

	for (int i = wysokosc - 1; i > 2; i--) {
	    for (int j = 0; j < szerokosc - 3; j++) {
		for (int k = 0; k < 4; k++) {
		    if (plansza[i - k][j + k] == 2)
			wartosc++;
		    else if (plansza[i - k][j + k] == 1) {
			wartosc = 0;
			break;
		    }
		}
		if (wartosc == 4)
		    return Double.POSITIVE_INFINITY;
		wynikO += wartosc ^ 3;
		wartosc = 0;
	    }
	    wartosc = 0;
	}

	// skosy prawe
	wartosc = 0;

	for (int i = wysokosc - 1; i > 2; i--) {
	    for (int j = szerokosc - 1; j > 2; j--) {
		for (int k = 0; k < 4; k++) {
		    if (plansza[i - k][j - k] == 2)
			wartosc++;
		    else if (plansza[i - k][j - k] == 1) {
			wartosc = 0;
			break;
		    }
		}
		if (wartosc == 4)
		    return Double.POSITIVE_INFINITY;
		wynikO += wartosc ^ 3;
		wartosc = 0;
	    }
	    wartosc = 0;
	}

	return wynikX + wynikO;

    }

    @Override
    public String toString() {
	StringBuilder wypisz = new StringBuilder();

	for (int i = 0; i < wysokosc; i++) {
	    for (int j = 0; j < szerokosc; j++) {
		if (plansza[i][j] == 1)
		    wypisz.append("|X|");
		else if (plansza[i][j] == 2)
		    wypisz.append("|O|");
		else
		    wypisz.append("| |");
	    }
	    if (i < wysokosc - 1)
		wypisz.append("\n");
	}
	wypisz.append("\n");

	for (int i = 1; i < szerokosc + 1; i++) {
	    wypisz.append(" - ");
	}
	wypisz.append("\n");
	
	for (int i = 1; i < szerokosc + 1; i++) {
	    wypisz.append(" " + i + " ");
	}
	wypisz.append("\n");
	return wypisz.toString();
    }

    @Override
    public String getHashCode() {
	return toString();
    }

}
