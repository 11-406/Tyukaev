import java.util.Arrays;

public class SegmentTree {
    private int[] tree;
    private int size;
    private int[] data;

    public SegmentTree(int[] arr) {
        if (arr.length == 0) {
            this.data = new int[0];
            this.size = 0;
            this.tree = new int[0];
            return;
        }
        this.data = Arrays.copyOf(arr, arr.length);
        this.size = arr.length;
        this.tree = new int[4 * size];
        build(0, 0, size - 1);
    }

    private void build(int node, int left, int right) {
        if (left == right) {
            tree[node] = data[left];
            return;
        }
        int mid = (left + right) / 2;
        build(2 * node + 1, left, mid);
        build(2 * node + 2, mid + 1, right);
        tree[node] = tree[2 * node + 1] + tree[2 * node + 2];
    }

    public int query(int l, int r) {
        return query(0, 0, size - 1, l, r);
    }

    private int query(int node, int nodeLeft, int nodeRight, int l, int r) {
        if (r < nodeLeft || l > nodeRight) return 0;
        if (l <= nodeLeft && nodeRight <= r) return tree[node];
        int mid = (nodeLeft + nodeRight) / 2;
        return query(2 * node + 1, nodeLeft, mid, l, r) +
                query(2 * node + 2, mid + 1, nodeRight, l, r);
    }

    public void update(int index, int value) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        data[index] = value;
        update(0, 0, size - 1, index, value);
    }

    private void update(int node, int nodeLeft, int nodeRight, int index, int value) {
        if (nodeLeft == nodeRight) {
            tree[node] = value;
            return;
        }
        int mid = (nodeLeft + nodeRight) / 2;
        if (index <= mid) update(2 * node + 1, nodeLeft, mid, index, value);
        else update(2 * node + 2, mid + 1, nodeRight, index, value);
        tree[node] = tree[2 * node + 1] + tree[2 * node + 2];
    }

    public void append(int value) {
        if (size == data.length) {
            data = Arrays.copyOf(data, Math.max(1, size * 2));
        }
        data[size] = value;
        size++;
        rebuild();
    }

    public void remove(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        for (int i = index; i < size - 1; i++) {
            data[i] = data[i + 1];
        }
        size--;
        rebuild();
    }

    private void rebuild() {
        tree = new int[4 * size];
        if (size > 0) {
            build(0, 0, size - 1);
        }
    }

    public int getSize() {
        return size;
    }
}