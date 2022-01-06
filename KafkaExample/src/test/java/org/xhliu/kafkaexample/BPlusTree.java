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

    public Entry delete(K key) {
        if (key == null) {
            throw new IllegalArgumentException("待删除的 key 不能为 null");
        }

        Entry entry = delete(null, root, key, 0);
        if (entry != null) size--;

        return entry;
    }

    private Node insert(Node x, K key, V val, int h) {
        int idx;
        Entry t = new Entry(key, val, null);
        Entry[] entries = x.children;

        if (h == height) {
            for (idx = 1; idx <= x.m; ++idx) {
                if (eq(entries[idx].key, key)) {
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
        if (h == height) return splitLeaf(x);
        return splitIndex(x);
    }

    private Entry delete(Node parent, Node cur, K key, int h) {
        Entry entry;
        int idx;

        if (h == height) {
            for (idx = 1; idx <= cur.m; ++idx)
                if (eq(cur.children[idx].key, key)) break;

            // 如果当前叶子节点不存在这样的键值对元素，则跳过
            if (idx > cur.m) return null;
            entry = cur.children[idx];

            // 移动节点元素列表，删除元素
            if (cur.m + 1 - idx >= 0)
                System.arraycopy(cur.children, idx + 1, cur.children, idx, cur.m + 1 - idx);
            cur.m--;
        } else {
            for (idx = 1; idx <= cur.m; ++idx) {
                if (eq(cur.children[idx].key, key)) {
                    entry = delete(cur, cur.children[idx].next, key, h + 1);
                    if (cur.m < M / 2) reBalance(parent, cur, h);
                    return entry;
                }

                // 当前节点在该节点的后继节点中，递归进行删除
                if (less(key, cur.children[idx].key)) {
                    entry = delete(cur, cur.children[idx - 1].next, key, h + 1);
                    if (cur.m < M / 2) reBalance(parent, cur, h);
                    return entry;
                }
            }

            entry = delete(cur, cur.children[cur.m].next, key, h + 1);
        }

        if (cur.m < M / 2) reBalance(parent, cur, h);
        return entry;
    }

    private Node splitIndex(Node x) {
        Node t = new Node(M / 2, M);
        x.m = M / 2;

        Entry mid = x.children[M / 2 + 1];

        // 将 x 中的后半部分的节点放入 t 中
        for (int i = 1; i <= M / 2; ++i) {
            t.children[i] = x.children[M / 2 + i + 1];
            x.children[M / 2 + i + 1] = null;
        }

        Node p = new Node(1, M); // 分裂后形成的根节点

        // 调整相关的链接
        t.children[0].next = mid.next;
        x.children[M / 2 + 1] = null; // clear mid
        p.children[0].next = x;
        p.children[1] = new Entry(mid.key, mid.val, t);

        return p;
    }

    private Node splitLeaf(Node x) {
        Node t = new Node(M / 2 + 1, M);
        x.m = M / 2;

        Entry mid = x.children[M / 2 + 1]; // x 的中间节点，它的属性将会被作为根节点的属性

        // 将 x 中的后半部分的节点放入 t 中
        for (int i = 1; i <= M / 2 + 1; ++i) {
            t.children[i] = x.children[M / 2 + i];
            x.children[M / 2 + i] = null;
        }

        Node p = new Node(1, M); // 分裂后形成的根节点

        // 调整相关的链接
        p.children[0].next = x;
        p.children[1] = new Entry(mid.key, null, t);

        t.prev = x;
        x.suc = t;

        return p;
    }

    private void reBalance(Node parent, Node cur, int h) {
        if (parent == null) return;

        int idx;
        Entry[] children = parent.children;

        for (idx = 1; idx <= parent.m; ++idx)
            if (less(cur.children[cur.m].key, children[idx].key))
                break;
        idx -= 1;

        Node left = null, right = null;
        if (idx > 0) left = children[idx - 1].next;
        if (idx < parent.m) right = children[idx + 1].next;

        if (left == null && right == null) return;

        if (left != null && left.m > M / 2) {
            // 移动当前节点的元素，为新加入的元素腾出位置
            for (int i = cur.m + 1; i > idx; --i) {
                if (cur.children[i] == null)
                    cur.children[i] = new Entry(null, null, null);
                cur.children[i].key = cur.children[i - 1].key;
                cur.children[i].val = cur.children[i - 1].val;
            }

            // 复制属性到当前节点的第一个元素（从 1 开始计数）
            cur.children[1].key = children[idx].key;
            cur.children[1].val = children[idx].val;
            cur.children[1].next = cur.children[0].next;
            cur.children[0].next = left.children[left.m].next;
            cur.m++;

            // 将从左子节点借用到的元素的属性复制到父节点的分隔元素，使得树最终是有序的
            children[idx].key = left.children[left.m].key;
            children[idx].val = left.children[left.m].val;

            // 删除左子节点的最大元素
            left.children[left.m] = null;
            left.m--;
            return;
        }

        if (right != null && right.m > M / 2) {
            ++cur.m;
            // 如果此时这个位置的对象未实例化，那么首先实例化该位置的对象
            if (cur.children[cur.m] == null)
                cur.children[cur.m] = new Entry(null, null, null);

            // 单纯地复制属性到当前的节点，如果使用引用复制的话会导致出现冗余的链接，甚至出现环
            cur.children[cur.m].key = children[idx + 1].key;
            cur.children[cur.m].val = children[idx + 1].val;
            cur.children[cur.m].next = right.children[0].next;
            right.children[0].next = right.children[1].next;

            // 更新父节点的分隔元素
            children[idx + 1].key = right.children[1].key;
            children[idx + 1].val = right.children[1].val;

            // 由于右子节点被借用了一个元素，因此需要移动右子节点的元素列表使得其依旧是有序的
            if (right.m >= 0)
                System.arraycopy(right.children, 2, right.children, 1, right.m);
            right.m--;
            return;
        }

        if (h == height) {
            if (left != null) {
                ++left.m;
                if (left.children[left.m] == null)
                    left.children[left.m] = new Entry(null, null, null);

                left.children[left.m].key = cur.children[1].key;
                left.children[left.m].val = cur.children[1].val;

                left.suc = cur;
                cur.prev = left;

                children[idx].next = null;
                children[idx] = null;

                if (parent.m + 1 - idx >= 0)
                    System.arraycopy(children, idx + 1, children, idx, parent.m + 1 - idx);
            } else {
                // 复制右兄弟节点的所有元素到当前的处理节点
                for (int i = 1; i <= right.m; ++i)
                    cur.children[++cur.m] = right.children[i];

                cur.suc = right;
                right.prev = cur;

                if (parent.m + 1 - (idx + 1) >= 0)
                    System.arraycopy(children, idx + 1 + 1, children, idx + 1, parent.m + 1 - (idx + 1));
            }

        } else {
            if (left != null) {
                // 首先将父节点的分隔节点复制到当前节点的末尾，由于这个位置可能未实例化，因此首先实例化
                ++left.m;
                if (left.children[left.m] == null)
                    left.children[left.m] = new Entry(null, null, null);

                left.children[left.m].key = children[idx].key;
                left.children[left.m].val = children[idx].val;
                left.children[left.m].next = cur.children[0].next;
                // 复制父节点的分隔节点结束。。。。

                // 再将当前节点的所有元素复制到左兄弟节点，由于位置 0 是一个哨兵元素，因此从元素 1 开始进行复制
                for (int i = 1; i <= cur.m; ++i)
                    left.children[++left.m] = cur.children[i];

                // 合并之后，会出现一条多余的链接，这个链接是多余的
                children[idx].next = null;
                // 删除父节点的分隔元素之后，移动父节点的分隔元素列表，使得原有的父节点的元素依旧是有序的
                if (parent.m + 1 - idx >= 0)
                    System.arraycopy(children, idx + 1, children, idx, parent.m + 1 - idx);
            } else {
                ++cur.m;
                if (cur.children[cur.m] == null)
                    cur.children[cur.m] = new Entry(null, null, null);
                cur.children[cur.m].key = children[idx + 1].key;
                cur.children[cur.m].val = children[idx + 1].val;
                cur.children[cur.m].next = right.children[0].next;
                children[idx + 1].next = null;

                // 复制右兄弟节点的所有元素到当前的处理节点
                for (int i = 1; i <= right.m; ++i)
                    cur.children[++cur.m] = right.children[i];

                // 调整父节点的元素列表
                if (parent.m + 1 - (idx + 1) >= 0)
                    System.arraycopy(children, idx + 1 + 1, children, idx + 1, parent.m + 1 - (idx + 1));
            }
        }
        parent.m--;

        if (parent.m == 0 && h == 1) {
            root = cur;
            height--;
        }
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

        BPlusTree<Integer, Integer> tree = new BPlusTree<>(5);
        for (int i = 1; i <= 22; ++i)
            tree.put(i, i);

        for (int i = 1; i <= 15; ++i)
            tree.delete(i);

        System.out.println("size:    " + tree.size());
        System.out.println("height:  " + tree.height());
        System.out.println(tree);
        System.out.println();
    }
}
