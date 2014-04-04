package glinski.si.sudoku;

public class SudokuState extends StateImpl {

	private int n;
	private int n2;

	private byte[][] board;

	@Override
	public double computeHeuristicGrade() {

		double counter = 0;

		for (int a = 0; a < n2; a++) {
			for (int b = 0; b < n2; b++) {
				if (board[a][b] == 0) {
					counter++;
				}
			}
			setH(counter);
		}
		return counter;
	}

	public SudokuState(int n) {
		super(null);
		this.n = n;
		this.n2 = n * n;

		board = new byte[n2][n2];

		for (int i = 0; i < n2; i++) {
			for (int j = 0; j < n2; j++) {
				board[i][j] = 0;
			}
		}
		computeHeuristicGrade();
	}

	@Override
	public boolean isAdmissible() {
		// TODO Auto-generated method stub
		byte[] group = new byte[n2];

		// sprawdzanie wierszy
		for (int i = 0; i < n2; i++) {
			for (int j = 0; j < n2; j++)
				group[j] = board[i][j];

			if (!check(group))
				return false;

		}

		// sprawdzanie kolumn
		for (int i = 0; i < n2; i++) {
			for (int j = 0; j < n2; j++)
				group[j] = board[j][i];

			if (!check(group))
				return false;

		}

		// sprawdzanie podkwadratow
		for (int i = 0; i < n2; i++) {
			int j = i / n;
			int k = i % n;

			for (int l = 0; l < n2; l++) {
				group[l] = board[j * n + l / n][k * n + l % n];
			}
			if (!check(group))
				return false;

		}

		return true;
	}

	private boolean check(byte[] group) {
		for (int i = 0; i < n2; i++) {
			if (group[i] == 0)
				continue;
			for (int j = i + 1; j < n2; j++) {
				if (group[i] == group[j])
					return false;
			}
		}
		return true;

	}

	public SudokuState(String txt) {// do sudoku 9x9
		super(null);
		n = 3;
		n2 = n * n;

		board = new byte[n2][n2];

		for (int i = 0; i < n2; i++) {
			for (int j = 0; j < n2; j++) {
				int k = i * n2 + j;
				board[i][j] = Byte.valueOf(txt.substring(k, k + 1));
			}
		}
		computeHeuristicGrade();
	}

	@Override
	public String toString() {
		/*
		 * String result = "";
		 * 
		 * for (int i = 0; i < n2; i++) { for (int j = 0; j < n2; j++) { if (j <
		 * n2) { result += board[i][j];
		 * 
		 * if (j % n == n - 1) { result += "|"; } else { result += ","; } } } if
		 * (i < n2 - 1) { result += "\n";
		 * 
		 * if (i % n == n - 1) { for (int k = 0; k < (n2 + n - 1); k++) { if (k
		 * == (n2 + n - 2)) { result += "-------"; } else { result += "-"; }
		 * 
		 * } result += "\n"; } }
		 * 
		 * }
		 * 
		 * return result;
		 */
		StringBuilder sb = new StringBuilder("");
		for (int i = 0; i < n2; i++) {
			for (int j = 0; j < n2; j++) {
				if (j < n2) {
					sb.append(board[i][j]);
					if (j % n == n - 1) {
						sb.append("|");
					} else {
						sb.append(",");
					}
				}
			}
			if (i < n2 - 1) {
				sb.append("\n");

				if (i % n == n - 1) {
					for (int k = 0; k < (n2 + n - 1); k++) {
						if (k == (n2 + n - 2)) {
							sb.append("-------");
						} else {
							sb.append("-");
						}

					}
					sb.append("\n");
				}
			}
		}
		return sb.toString();

	}

	public SudokuState(State aParent) {
		super(aParent);
		SudokuState sudokuParent = (SudokuState) aParent;
		this.n = sudokuParent.n;
		this.n2 = n * n;

		board = new byte[n2][n2];

		for (int i = 0; i < n2; i++) {
			for (int j = 0; j < n2; j++) {
				board[i][j] = sudokuParent.board[i][j];

			}
		}
	}

	public int getN2() {
		return n2;
	}

	public void setN2(int n2) {
		this.n2 = n2;
	}

	public int getN() {
		return n;
	}

	public void setN(int n) {
		this.n = n;
	}

	public byte[][] getBoard() {
		return board;
	}

	public void setBoard(byte[][] board) {
		this.board = board;
	}

	@Override
	public String getHashCode() {
		// TODO Auto-generated method stub

		//return toString();
		return String.valueOf(this.hashCode());
	}

	/*
	 * public static String getTxt() { return txt; }
	 * 
	 * public static void setTxt(String txt) { SudokuState.txt = txt; }
	 */

	/*
	 * public static void main(String args[]) { SudokuState s = new
	 * SudokuState(3);
	 * 
	 * SudokuState s2 = new SudokuState(txt); System.out.println("Sudoku\n");
	 * //System.out.println(s2.isAdmissible());
	 * System.out.println(s2.computeHeuristicGrade()); }
	 */

}
