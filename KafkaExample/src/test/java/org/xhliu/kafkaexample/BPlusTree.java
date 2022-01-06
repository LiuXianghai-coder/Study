package org.xhliu.kafkaexample;

@SuppressWarnings("unchecked")
public class BPlusTree<K extends Comparable<K>, V> {

    static class Node {
        int m;
        Entry[] children;
        // prev: 前一个叶子节点，suc: 后继叶子节点
        Node prev, suc;

        public Node(int m, int M) {
            this.m = m;
            this.children = new Entry[M + 1];
            this.children[0] = new Entry(null, null, null);
        }
    }

    static class Entry {
        private Comparable key;
        private Object val;
        private Node next;

        public Entry(Comparable key, Object val, Node next) {
            this.key = key;
            this.val = val;
            this.next = next;
        }
    }

    private final int M;

    private Node root;

    private int height;

    private int size;

    public BPlusTree(int m) {
        if (m % 2 == 0 || m < 2) {
            throw new IllegalArgumentException("阶数 M 只能是大于 1 的奇数");
        }

        M = m;
        this.root = new Node(0, M);
    }

    int height() {
        return this.height;
    }

    int size() {
        return this.size;
    }

    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException("key 不能为 null");
        }

        return search(root, key, 0);
    }

    private V search(Node x, K key, int h) {
        if (x == null) return null;

        Entry[] entries = x.children;
        if (h == height) {
            for (int i = 1; i <= x.m; ++i) {
                if (eq(entries[i].key, key))
                    return (V) entries[i].val;
            }
            return null;
        }

        for (int i = 1; i <= x.m; ++i) {
            if (eq(entries[i].key, key))
                return search(entries[i].next, key, h + 1);

            if (less(entries[i - 1].key, key) && less(key, entries[i].key))
                return search(entries[i - 1].next, key, h + 1);
        }

        return search(entries[x.m].next, key, h + 1);
    }

    public void put(K key, V val) {
        if (key == null) {
            throw new IllegalArgumentException("key 不能为 null");
        }

        Node result = insert(root, key, val, 0);
        size++;

        if (result == null) return;
        /*
            插入之后形成了分裂节点，此时树的高度增加，同时需要修改 root 节点指向的对象
         */
        root = result;
        height++;
    }

    private Node insert(Node x, K key, V val, int h) {
        int idx;
        Entry t = new Entry(key, val, null);
        Entry[] entries = x.children;

        if (h == height) {
            for (idx = 1; idx <= x.m; ++idx) {
                // 键值对已经存在，使用当前的键值对覆盖原有的键值对
                if (eq(key, entries[idx].key)) {
                    entries[idx].val = val;
                    size--;
                    return null;
                }

                if (less(key, entries[idx].key)) break;
            }
        } else {
            for (idx = 1; idx <= x.m; ++idx) {
                if (less(entries[idx].key, key) || eq(entries[idx].key, key)) continue;
                break;
            }

            // 插入到前一个区间元素中，因为此时的元素已经大于现有的 key 了
            Node u = insert(entries[idx - 1].next, key, val, h + 1);
            // 插入结果为 null 说明没有发生节点分裂，正常返回即可
            if (u == null) return null;

            /*
                由于此时发生了节点分裂，需要将分裂后的节点的根节点插入到当前的节点中，
                首先需要找到根节点的插入位置
            */
            for (idx = 1; idx <= x.m; ++idx)
                if (less(u.children[1].key, entries[idx].key)) break;

            entries[idx - 1].next = u.children[0].next;
            t = u.children[1]; // t 表示待插入的节点
        }

        if (M - idx >= 0)
            System.arraycopy(x.children, idx, x.children, idx + 1, M - idx);

        x.children[idx] = t;
        x.m++;

        if (x.m < M) return null;
        return split(x);
    }

    private Node split(Node x) {
        Node t = new Node(M / 2, M);
        x.m = M / 2 + 1;

        // 将 x 中的后半部分的节点放入 t 中
        if (M / 2 >= 0)
            System.arraycopy(x.children, 1, t.children, 1, M / 2);

        for (int i = 1; i <= M / 2; ++i) {
            x.children[i] = x.children[M / 2 + i + 1];
            x.children[M / 2 + i + 1] = null;
        }

        Node p = new Node(1, M); // 分裂后形成的根节点
        Entry mid = x.children[M / 2 + 1]; // x 的中间节点，它的属性将会被作为根节点的属性

        // 调整相关的链接
        p.children[0].next = t;
        p.children[1] = new Entry(mid.key, null, x);

        x.prev = t;
        t.suc = x;

        return p;
    }

    private boolean eq(Comparable<K> key1, Comparable<K> key2) {
        if (key1 == null || key2 == null) return false;
        return key1.compareTo((K) key2) == 0;
    }

    private boolean less(Comparable<K> key1, Comparable<K> key2) {
        if (key1 == null) return true;
        if (key2 == null) return false;
        return key1.compareTo((K) key2) < 0;
    }

    public String toString() {
        return toString(root, height, "") + "\n";
    }

    private String toString(Node h, int ht, String indent) {
        if (h == null) return "";
        StringBuilder s = new StringBuilder();
        Entry[] children = h.children;

        if (ht == 0) {
            for (int j = 0; j <= h.m; j++) {
                if (children[j] == null) continue;
                s.append(indent).append(children[j].key).append(" ").append(children[j].val).append("\n");
            }
        } else {
            for (int j = 0; j <= h.m; j++) {
                if (children[j] == null) continue;
                if (j > 0) s.append(indent).append("(").append(children[j].key).append(")\n");
                assert children[j] != null;
                s.append(toString(children[j].next, ht - 1, indent + "     "));
            }
        }
        return s.toString();
    }

    public static void main(String[] args) {
        BPlusTree<String, String> st = new BPlusTree<>(5);

        st.put("www.cs.princeton.edu", "128.112.136.12");
        st.put("www.cs.princeton.edu", "128.112.136.11");
        st.put("www.princeton.edu", "128.112.128.15");
        st.put("www.yale.edu", "130.132.143.21");
        st.put("www.simpsons.com", "209.052.165.60");
        st.put("www.apple.com", "17.112.152.32");
        st.put("www.amazon.com", "207.171.182.16");
        st.put("www.ebay.com", "66.135.192.87");
        st.put("www.cnn.com", "64.236.16.20");
        st.put("www.google.com", "216.239.41.99");
        st.put("www.nytimes.com", "199.239.136.200");
        st.put("www.microsoft.com", "207.126.99.140");
        st.put("www.dell.com", "143.166.224.230");
        st.put("www.slashdot.org", "66.35.250.151");
        st.put("www.espn.com", "199.181.135.201");
        st.put("www.weather.com", "63.111.66.11");
        st.put("www.yahoo.com", "216.109.118.65");


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
