package segmenttree;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SegmentTreeTest {

    @Test
    void gfgExample() {
        int[] a = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        SegmentTree st = new SegmentTree(a);

        assertEquals(5, st.query(1, 3)); // 2 + 3
        st.update(2, 1);
        assertEquals(3, st.query(1, 3)); // 2 + 1
    }

    @Test
    void fullRangeSum() {
        int[] a = {1, 2, 3, 4, 5};
        SegmentTree st = new SegmentTree(a);
        assertEquals(15, st.query(0, 5));
    }

    @Test
    void emptyRangeIsZero() {
        SegmentTree st = new SegmentTree(new int[]{4, 2, 7});
        assertEquals(0, st.query(1, 1));
    }

    @Test
    void singleElementRange() {
        SegmentTree st = new SegmentTree(new int[]{4, 2, 7});
        assertEquals(2, st.query(1, 2));
    }

    @Test
    void updateThenQuery() {
        SegmentTree st = new SegmentTree(new int[]{1, 1, 1, 1, 1});
        st.update(0, 10);
        st.update(4, 10);
        assertEquals(23, st.query(0, 5));
        assertEquals(3, st.query(1, 4));
    }

    @Test
    void rangeOutOfBoundsThrows() {
        SegmentTree st = new SegmentTree(new int[]{1, 2, 3});
        assertThrows(IndexOutOfBoundsException.class, () -> st.query(-1, 2));
        assertThrows(IndexOutOfBoundsException.class, () -> st.query(0, 4));
        assertThrows(IndexOutOfBoundsException.class, () -> st.query(2, 1));
    }

    @Test
    void updateOutOfBoundsThrows() {
        SegmentTree st = new SegmentTree(new int[]{1, 2, 3});
        assertThrows(IndexOutOfBoundsException.class, () -> st.update(-1, 0));
        assertThrows(IndexOutOfBoundsException.class, () -> st.update(3, 0));
    }

    @Test
    void emptyArrayRejected() {
        assertThrows(IllegalArgumentException.class, () -> new SegmentTree(new int[0]));
    }

    @Test
    void randomizedAgainstNaive() {
        Random rng = new Random(42);
        int n = 200;
        int[] a = new int[n];
        for (int i = 0; i < n; i++) a[i] = rng.nextInt(1000);

        SegmentTree st = new SegmentTree(a);

        for (int op = 0; op < 2000; op++) {
            if (rng.nextBoolean()) {
                int idx = rng.nextInt(n);
                int val = rng.nextInt(1000);
                a[idx] = val;
                st.update(idx, val);
            } else {
                int l = rng.nextInt(n);
                int r = l + rng.nextInt(n - l + 1);
                int expected = 0;
                for (int i = l; i < r; i++) expected += a[i];
                assertEquals(expected, st.query(l, r), "mismatch on [" + l + ", " + r + ")");
            }
        }
    }
}
