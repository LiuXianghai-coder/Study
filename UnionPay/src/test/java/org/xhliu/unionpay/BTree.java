package org.xhliu.unionpay;

public class BTree<Key extends Comparable<Key>, Value> {
    private final int M;

    static final class Node {
        private int m;
        private final Entry[] children;

        private Node(int m, int M) {
            this.m = m;
            this.children = new Entry[M + 1];
            this.children[0] = new Entry(null, null, null);
        }
    }

    static class Entry {
        private Comparable key;
        private Object value;
        private Node next;

        Entry(Comparable key, Object value, Node next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

    private Node root;

    private int height;

    private int size;

    public BTree(int m) {
        if (m % 2 == 0) {
            throw new IllegalArgumentException("阶数 M 只能是奇数");
        }

        M = m;
        root = new Node(1, M);
    }

    public int size() {
        return this.size;
    }

    public int height() {
        return this.height;
    }

    public Value get(Key key) {
        if (key == null) {
            throw new IllegalArgumentException("key 不能为 null");
        }

        return search(root, key, height);
    }

    @SuppressWarnings("unchecked")
    public Value search(Node x, Key key, int h) {
        if (x == null) return null;

        Entry[] entries = x.children;
        for (int i = 0; i < x.m; ++i) {
            if (eq(entries[i].key, key))
                return (Value) entries[i].value;
        }

        for (int i = 1; i < x.m; ++i) {
            if (less(entries[i - 1].key, key) && less(key, entries[i].key))
                return search(entries[i - 1].next, key, h + 1);
        }

        return null;
    }

    public void put(Key key, Value value) {
        if (key == null) {
            throw new IllegalArgumentException("argument key fot put() is null");
        }

        Node res = insert(root, key, value, 0);
        size++;
        if (res == null) return;
        root = res;
        height++;
    }

    @SuppressWarnings("unchecked")
    private Node insert(Node x, Key key, Value value, int h) {
        int idx;
        Entry t = new Entry(key, value, null);
        Entry[] entries = x.children;
        if (h == height) {
            for (idx = 1; idx < x.m; ++idx) {
                if (eq(key, entries[idx].key)) {
                    entries[idx].value = value;
                    size--;
                    return null;
                }

                if (less(key, entries[idx].key)) break;
            }
        } else {
            for (idx = 1; idx < x.m; ++idx) {
                if (eq(key, entries[idx].key)) {
                    entries[idx].value = value;
                    size--;
                    return null;
                }

                if (less(entries[idx].key, key)) continue;

                Node u = insert(entries[idx - 1].next, key, value, h + 1);
                if (u == null) return null;

                t.key = u.children[1].key;
                t.value = u.children[1].value;
                t.next = u;
                break;
            }

            if (idx == x.m) {
                Node u = insert(entries[x.m - 1].next, key, value, h + 1);
                if (u == null) return null;

                t.key = u.children[1].key;
                t.value = u.children[1].value;
                t.next = u;
            }
        }

        for (int i = M; i > idx; --i)
            x.children[i] = x.children[i - 1];

        x.children[idx] = t;
        x.m++;

        if (x.m < M) return null;
        return split(x);
    }

    private Node split(Node x) {
        Node t = new Node(M / 2, M);
        x.m = M / 2;

        for (int i = 1; i <= M / 2; ++i) {
            t.children[i] = x.children[M / 2 + i + 1];
            x.children[M / 2 + i + 1] = null;
        }

        Node p = new Node(1, M);
        Entry mid = x.children[M / 2 + 1];
        x.children[M / 2 + 1] = null; // clear mid
        p.children[0].next = x;
        p.children[1] = new Entry(mid.key, mid.value, t);

        return p;
    }

    @SuppressWarnings("unchecked")
    private boolean less(Comparable<Key> key1, Comparable<Key> key2) {
        if (key1 == null) return true;
        if (key2 == null) return false;
        return key1.compareTo((Key) key2) < 0;
    }

    @SuppressWarnings("unchecked")
    private boolean eq(Comparable<Key> key1, Comparable<Key> key2) {
        if (key1 == null || key2 == null) return false;
        return key1.compareTo((Key) key2) == 0;
    }

    public String toString() {
        return toString(root, height, "") + "\n";
    }

    private String toString(Node h, int ht, String indent) {
        StringBuilder s = new StringBuilder();
        Entry[] children = h.children;

        if (ht == 0) {
            for (int j = 0; j < h.m; j++) {
                s.append(indent).append(children[j].key).append(" ").append(children[j].value).append("\n");
            }
        }
        else {
            for (int j = 0; j < h.m; j++) {
                if (j > 0) s.append(indent).append("(").append(children[j].key).append(")\n");
                s.append(toString(children[j].next, ht-1, indent + "     "));
            }
        }
        return s.toString();
    }

    public static void main(String[] args) {
        BTree<String, String> st = new BTree<>(5);

        st.put("www.cs.princeton.edu", "128.112.136.12");
        st.put("www.cs.princeton.edu", "128.112.136.11");
        st.put("www.princeton.edu",    "128.112.128.15");
        st.put("www.yale.edu",         "130.132.143.21");
        st.put("www.simpsons.com",     "209.052.165.60");
        st.put("www.apple.com",        "17.112.152.32");
        st.put("www.amazon.com",       "207.171.182.16");
        st.put("www.ebay.com",         "66.135.192.87");
        st.put("www.cnn.com",          "64.236.16.20");
        st.put("www.google.com",       "216.239.41.99");
        st.put("www.nytimes.com",      "199.239.136.200");
        st.put("www.microsoft.com",    "207.126.99.140");
        st.put("www.dell.com",         "143.166.224.230");
        st.put("www.slashdot.org",     "66.35.250.151");
        st.put("www.espn.com",         "199.181.135.201");
        st.put("www.weather.com",      "63.111.66.11");
        st.put("www.yahoo.com",        "216.109.118.65");


        System.out.println("cs.princeton.edu:  " + st.get("www.cs.princeton.edu"));
        System.out.println("hardvardsucks.com: " + st.get("www.harvardsucks.com"));
        System.out.println("simpsons.com:      " + st.get("www.simpsons.com"));
        System.out.println("apple.com:         " + st.get("www.apple.com"));
        System.out.println("ebay.com:          " + st.get("www.ebay.com"));
        System.out.println("dell.com:          " + st.get("www.dell.com"));
        System.out.println();

        System.out.println("size:    " + st.size());
        System.out.println("height:  " + st.height());
        System.out.println(st);
        System.out.println();
    }
}
