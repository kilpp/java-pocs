package segmenttree;

/// Quick demo of [SegmentTree] using a Java 25 instance `main` method
/// (JEP 512) and `java.lang.IO` for terminal output.
public class Main {

    void main() {
        var data = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        var tree = new SegmentTree(data);

        // sum on range [1, 3)  ->  2 + 3 = 5
        IO.println(tree.query(1, 3));

        // overwrite element at index 2
        tree.update(2, 1);

        // sum on range [1, 3)  ->  2 + 1 = 3
        IO.println(tree.query(1, 3));
    }
}
