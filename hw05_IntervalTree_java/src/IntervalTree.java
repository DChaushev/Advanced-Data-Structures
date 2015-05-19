
/**
 *
 * Interval Tree implementation in Java
 *
 * @author:
 * @keywords: Data Structures, Dynamic RMQ, Interval update and query
 * @modified:
 *
 * Implement a dynamic RMQ with interval query and update. The update should add
 * a certain value to all cells in a given interval, while the query should
 * return the maximum value inside an interval.
 *
 * @author Dimitar
 */
public class IntervalTree {

    private final int[] tree;
    private final int[] tree2;

    /**
     * Creates a new interval tree with initial values given in values. Each
     * update and query should allow indices [0, values.size() - 1].
     *
     * @param values
     */
    public IntervalTree(int[] values) {
        int size = 2 * getNextNumberPowerOfTwo(values.length);
        tree = new int[size];
        tree2 = new int[size];

        for (int i = 0; i < values.length; i++) {
            updateIndex(i + tree.length / 2, values[i]);
        }

        for (int i = size / 2, j = 0; j < values.length; i++, j++) {
            tree2[i] = values[j];
        }

    }

    /**
     * Adds the value add to each element in the interval [idx1, idx2].
     */
    void update(int idx1, int idx2, int add) {

        int left = idx1 + tree.length / 2;
        int right = idx2 + tree.length / 2;

        if (left == right) {
            tree2[left] += add;
            updateIndex(left, add);
            return;
        }

        tree2[left] += add;
        tree2[right] += add;

        updateIndex(left, add);
        updateIndex(right, add);

        int leftParent = left / 2;
        int rightParent = right / 2;

        while (leftParent != rightParent) {

            if (left % 2 == 0) {
                tree2[left + 1] += add;
                updateIndex(left + 1, add);
            }

            if (right % 2 == 1) {
                tree2[right - 1] += add;
                updateIndex(right - 1, add);
            }

            left = leftParent;
            right = rightParent;

            leftParent = left / 2;
            rightParent = right / 2;
        }
    }

    /**
     * Returns the maximum value in the interval [idx1, idx2].
     */
    int query(int idx1, int idx2) {

        int left = idx1 + tree.length / 2;
        int right = idx2 + tree.length / 2;

        if (left == right) {
            int max = tree[left];
            int leftParent = left / 2;
            while (leftParent != 0) {
                max += tree2[leftParent];
                leftParent /= 2;
            }
            return max;
        }

        int rightMax = tree[right];
        int leftMax = tree[left];

        while (true) {

            int leftParent = left / 2;
            int rightParent = right / 2;

            if (leftParent == rightParent) {

                int max = Math.max(leftMax, rightMax);

                while (leftParent != 0) {
                    max += tree2[leftParent];
                    leftParent /= 2;
                }
                return max;
            }

            if (left % 2 == 0) {
                leftMax = Math.max(leftMax + tree2[leftParent], tree[left + 1] + tree2[leftParent]);
            } else {
                leftMax += tree2[leftParent];
            }

            if (right % 2 == 1) {
                rightMax = Math.max(rightMax + tree2[rightParent], tree[right - 1] + tree2[rightParent]);
            } else {
                rightMax += tree2[rightParent];
            }

            left = leftParent;
            right = rightParent;
        }
    }

    private int getNextNumberPowerOfTwo(Integer x) {
        if (x == 1) {
            return 2;
        }
        int highestBit = Integer.highestOneBit(x);
        return highestBit == x ? highestBit : highestBit * 2;
    }

    private void updateIndex(int idx, int value) {
        tree[idx] += value;
        idx /= 2;

        while (idx != 0) {
            int val = tree2[idx];
            tree[idx] = Math.max(tree[idx * 2] + val, tree[idx * 2 + 1] + val);
            idx /= 2;
        }
    }
}
