package segmenttree;

import java.util.Objects;

/// An iterative sum [segment tree](https://en.wikipedia.org/wiki/Segment_tree)
/// over a fixed-size array of `int`s.
///
/// The tree is stored in a flat array of length `2n` using the classic
/// "twice-the-input-size" layout. For an input of length `n = 8`:
///
/// ```text
///                        1                      <- root: sum of [0, n)
///                  /            \
///                 2              3
///              /     \         /     \
///             4       5       6       7         <- internal nodes [1, n)
///            / \     / \     / \     / \
///           8   9  10  11  12  13  14  15       <- leaves [n, 2n) = input
/// ```
///
/// Index arithmetic for any node `i` in `[1, 2n)`:
///   - parent     = `i >> 1`
///   - left  kid  = `i << 1`            (a.k.a. `2i`)
///   - right kid  = `i << 1 | 1`        (a.k.a. `2i + 1`)
///   - sibling    = `i ^ 1`
///
/// Every operation runs in `O(log n)` time and is non-recursive.
public final class SegmentTree {

    /// Number of leaves — the size of the original input array.
    private final int n;

    /// Flat backing array. Slot `0` is intentionally unused so that
    /// the parent/child index arithmetic stays branch-free.
    private final int[] tree;

    public SegmentTree(int[] arr) {
        Objects.requireNonNull(arr, "arr");
        if (arr.length == 0) {
            throw new IllegalArgumentException("arr must be non-empty");
        }
        this.n = arr.length;
        this.tree = new int[2 * n];

        // Step 1: copy input values into the leaf range [n, 2n).
        System.arraycopy(arr, 0, tree, n, n);

        // Step 2: fill internal nodes bottom-up. Walking indices from
        // n-1 down to 1 guarantees both children of `i` are already set
        // by the time we compute `tree[i]`.
        for (int i = n - 1; i > 0; i--) {
            tree[i] = tree[2 * i] + tree[2 * i + 1];
        }
    }

    /// Number of elements in the underlying array.
    public int size() {
        return n;
    }

    /// Replaces the value at index `p` with `value` and refreshes all
    /// ancestors so subsequent queries stay consistent.
    public void update(int p, int value) {
        Objects.checkIndex(p, n);

        // Write the new leaf, then walk up to the root recomputing each
        // ancestor as the sum of its two children.
        int i = p + n;
        tree[i] = value;
        for (i >>= 1; i > 0; i >>= 1) {
            tree[i] = tree[2 * i] + tree[2 * i + 1];
        }
    }

    /// Sum on the half-open interval `[l, r)`.
    ///
    /// Two pointers climb the tree from the leaf level, picking up the
    /// largest subtrees that fit fully inside `[l, r)`:
    ///   - if `l` is a *right* child, its parent covers values left of
    ///     the range, so we take `tree[l]` and move `l` past it before
    ///     going up;
    ///   - symmetrically, if `r` is a *right* child, its left sibling
    ///     `tree[r-1]` is fully inside the range, so we take it and
    ///     decrement `r`.
    /// When `l` and `r` meet, every covered subtree has been counted
    /// exactly once.
    public int query(int l, int r) {
        Objects.checkFromToIndex(l, r, n);

        int sum = 0;
        for (l += n, r += n; l < r; l >>= 1, r >>= 1) {
            if ((l & 1) == 1) sum += tree[l++];
            if ((r & 1) == 1) sum += tree[--r];
        }
        return sum;
    }
}
