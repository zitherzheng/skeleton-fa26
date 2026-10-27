import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private final int N;
    private final boolean[] opened;
    private final WeightedQuickUnionUF unionFind, unionFindWithBottom;
    private int numberOfOpenSites = 0;
    private final int TOP, BOTTOM;
    private static final int[][] NEIGHBORS = {
            {-1, 0}, {1, 0}, {0, -1}, {0, 1}
    };

    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException();
        }

        this.N = N;
        TOP = N * N;
        BOTTOM = N * N + 1;

        opened = new boolean[N * N + 2];
        opened[TOP] = true;
        opened[BOTTOM] = true;

        unionFind = new WeightedQuickUnionUF(N * N + 1);
        unionFindWithBottom = new WeightedQuickUnionUF(N * N + 2);
    }

    public void open(int row, int col) {
        if (!checkRange(row, col)) {
            throw new IndexOutOfBoundsException();
        }

        int index = getIndex(row, col);
        if (opened[index]) {
            return;
        }
        opened[index] = true;
        numberOfOpenSites++;

        for (int[] neighbor : NEIGHBORS) {
            int neighborRow = row + neighbor[0];
            int neighborCol = col + neighbor[1];
            if (neighborCol < 0 || neighborCol >= N) {
                continue;
            }

            int neighborIndex = getIndex(neighborRow, neighborCol);
            if (!opened[neighborIndex]) {
                continue;
            }
            unionFindWithBottom.union(neighborIndex, index);
            if (neighborIndex != BOTTOM) {
                unionFind.union(neighborIndex, index);
            }
        }
    }

    public boolean isOpen(int row, int col) {
        if (!checkRange(row, col)) {
            throw new IndexOutOfBoundsException();
        }
        return opened[getIndex(row, col)];
    }

    public boolean isFull(int row, int col) {
        if (!checkRange(row, col)) {
            throw new IndexOutOfBoundsException();
        }
        return opened[getIndex(row, col)]
                && (unionFind.connected(TOP, getIndex(row, col)));
    }

    public int numberOfOpenSites() {
        return numberOfOpenSites;
    }

    public boolean percolates() {
        return unionFindWithBottom.connected(TOP, BOTTOM);
    }

    private int getIndex(int row, int col) {
        if (row == -1) {
            return TOP;
        }
        if (row == N) {
            return BOTTOM;
        }
        if (!checkRange(row, col)) {
            throw new IndexOutOfBoundsException();
        }
        return row * N + col;
    }

    private boolean checkRange(int row, int col) {
        return (row >= 0 && row < N) && (col >= 0 && col < N);
    }

}
