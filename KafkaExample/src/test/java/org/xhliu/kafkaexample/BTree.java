package org.xhliu.kafkaexample;

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
        root = new Node(0, M);
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

        return search(root, key);
    }

    @SuppressWarnings("unchecked")
    public Value search(Node x, Key key) {
        if (x == null) return null;

        Entry[] entries = x.children;
        for (int i = 1; i <= x.m; ++i) {
            if (eq(entries[i].key, key))
                return (Value) entries[i].value;
        }

        for (int i = 1; i <= x.m; ++i) {
            if (less(entries[i - 1].key, key) && less(key, entries[i].key))
                return search(entries[i - 1].next, key);
        }

        return search(entries[x.m].next, key);
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
            for (idx = 1; idx <= x.m; ++idx) {
                if (eq(key, entries[idx].key)) {
                    entries[idx].value = value;
                    size--;
                    return null;
                }

                if (less(key, entries[idx].key)) break;
            }
        } else {
            for (idx = 1; idx <= x.m; ++idx) {
                if (eq(key, entries[idx].key)) {
                    entries[idx].value = value;
                    size--;
                    return null;
                }

                if (less(entries[idx].key, key)) continue;

                Node u = insert(entries[idx - 1].next, key, value, h + 1);
                if (u == null) return null;

                for (idx = 1; idx <= x.m; ++idx)
                    if (less(u.children[1].key, x.children[idx].key)) break;

                x.children[idx - 1].next = u.children[0].next;
                t = u.children[1];
                break;
            }

            if (idx > x.m) {
                Node u = insert(entries[x.m].next, key, value, h + 1);
                if (u == null) return null;

                for (idx = 1; idx <= x.m; ++idx)
                    if (less(u.children[1].key, x.children[idx].key)) break;

                x.children[idx - 1].next = u.children[0].next;
                t = u.children[1];
            }
        }

        if (M - idx >= 0) System.arraycopy(x.children, idx, x.children, idx + 1, M - idx);

        x.children[idx] = t;
        x.m++;

        if (x.m < M) return null;
        return split(x);
    }

    public Entry delete(Key key) {
        Entry entry = delete(null, root, key, 0);
        size--;

        return entry;
    }

    @SuppressWarnings("unchecked")
    private Entry delete(Node parent, Node cur, Key key, int h) {
        Entry entry;
        int idx;
        // 待删除的节点是一个叶子节点，直接进行删除
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

            // 如果删除节点之后该节点不满足条件，需要对该节点进行重平衡
            if (cur.m < M / 2) reBalance(parent, cur);
        } else {
            /*
                待删除的节点不是叶子节点，那么需要从元素的所有后继节点中找到最小的元素（或者从前驱节点中找到最大的元素）
                覆盖掉当前 key 所在的元素，然后在后继节点中删除该最小元素
             */
            for (idx = 1; idx <= cur.m; ++idx) {
                if (eq(cur.children[idx].key, key)) break;
                // 当前节点在该节点的后继节点中，递归进行删除
                if (less(key, cur.children[idx].key))
                    return delete(cur, cur.children[idx - 1].next, key, h + 1);
            }

            // idx > cur.m 说明待删除的节点在最后的一个区间内，同样地，通过递归的方式进行删除
            if (idx > cur.m)
                return delete(cur, cur.children[cur.m].next, key, h + 1);

            // 删除内部节点元素
            Entry min = min(cur);
            cur.children[idx].key = min.key;
            cur.children[idx].value = min.value;

            return delete(cur, cur.children[idx].next, (Key) min.key, h + 1);
        }

        return entry;
    }

    @SuppressWarnings("unchecked")
    private void reBalance(Node parent, Node cur) {
        int idx;
        Entry[] children = parent.children;
        for (idx = 1; idx <= parent.m; ++idx)
            if (less(cur.children[cur.m].key, children[idx].key))
                break;
        idx -= 1;

        // 找到该叶子节点的左右兄弟节点
        Node left = null, right = null;
        if (idx > 0) left = children[idx - 1].next;
        if (idx < parent.m) right = children[idx + 1].next;

        if (left == null && right == null) return;

        /*
            左子节点存在多余的元素，从左子节点借用一个元素，使得节点最终满足 B 树的条件
         */
        if (left != null && left.m > M / 2) {
            // 移动当前节点的元素，为新加入的元素腾出位置
            for (int i = cur.m + 1; i > idx; --i) {
                if (cur.children[i] == null)
                    cur.children[i] = new Entry(null, null, null);
                cur.children[i].key = cur.children[i - 1].key;
                cur.children[i].value = cur.children[i - 1].value;
            }

            // 复制属性到当前节点的第一个元素（从 1 开始计数）
            cur.children[1].key = children[idx].key;
            cur.children[1].value = children[idx].value;
            cur.m++;

            // 将从左子节点借用到的元素的属性复制到父节点的分隔元素，使得树最终是有序的
            children[idx].key = left.children[left.m].key;
            children[idx].value = left.children[left.m].value;

            // 删除左子节点的最大元素
            left.children[left.m] = null;
            left.m--;
            return;
        }

        /*
            右子节点存在多余的元素，因此从右子节点借用一个元素到父节点的分隔节点，
            同时将原有的旧分隔元素移动到到当前的节点，使得它维持 B 树的结构
        */
        if (right != null && right.m > M / 2) {
            ++cur.m;
            // 如果此时这个位置的对象未实例化，那么首先实例化该位置的对象
            if (cur.children[cur.m] == null)
                cur.children[cur.m] = new Entry(null, null, null);

            // 单纯地复制属性到当前的节点，如果使用引用复制的话会导致出现冗余的链接，甚至出现环
            cur.children[cur.m].key = children[idx + 1].key;
            cur.children[cur.m].value = children[idx + 1].value;

            // 更新父节点的分隔元素
            children[idx + 1].key = right.children[1].key;
            children[idx + 1].value = right.children[1].value;

            // 由于右子节点被借用了一个元素，因此需要移动右子节点的元素列表使得其依旧是有序的
            if (right.m >= 0)
                System.arraycopy(right.children, 2, right.children, 1, right.m);
            right.m--;
            return;
        }

        /*
            由于左右兄弟节点都不存在多余的元素，因此需要从父节点借用一个元素，合并成为一个节点
            一般会优先选择左兄弟节点作为合并后的节点，因为这样就不需要移动前半部分元素
         */
        if (left != null) {
            // 首先将父节点的分隔节点复制到当前节点的末尾，由于这个位置可能未实例化，因此首先实例化
            ++left.m;
            if (left.children[left.m] == null)
                left.children[left.m] = new Entry(null, null, null);

            left.children[left.m].key = children[idx].key;
            left.children[left.m].value = children[idx].value;
            ++left.m;
            // 复制父节点的分隔节点结束。。。。

            // 再将当前节点的所有元素复制到左兄弟节点，由于位置 0 是一个哨兵元素，因此从元素 1 开始进行复制
            for (int i = 1; i <= cur.m; ++i)
                left.children[++left.m] = cur.children[i];

            // 合并之后，会出现一条多余的链接，这个链接是多余的
            children[idx].next = null;
            // 删除父节点的分隔元素之后，移动父节点的分隔元素列表，使得原有的父节点的元素依旧是有序的
            if (parent.m + 1 - idx >= 0)
                System.arraycopy(children, idx + 1, children, idx, parent.m + 1 - idx);
            return;
        }

        /*
            由于不存在左兄弟节点，因此只能选择右边的兄弟节点，将右兄弟节点的元素复制到当前节点
            Hint：根据 B 树的定义，不可能存在既不含有左兄弟节点，也不含有右兄弟节点的叶子节点
         */
        ++cur.m;
        if (cur.children[cur.m] == null)
            cur.children[cur.m] = new Entry(null, null, null);
        cur.children[cur.m].key = children[idx + 1].key;
        cur.children[cur.m].value= children[idx + 1].value;
        children[idx + 1].next = null;

        for (int i = 1; i <= right.m; ++i)
            cur.children[++cur.m] = right.children[i];
        // 调整父节点的元素列表
        if (parent.m + 1 - (idx + 1) >= 0)
            System.arraycopy(children, idx + 1 + 1, children, idx + 1, parent.m + 1 - (idx + 1));
        parent.m--; // 这里又有一个坑 :(
    }

    private Entry min(Node x) {
        if (x.children[1].next == null)
            return x.children[1];

        return min(x.children[1].next);
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
        if (h == null) return "";
        StringBuilder s = new StringBuilder();
        Entry[] children = h.children;

        if (ht == 0) {
            for (int j = 0; j <= h.m; j++) {
                if (children[j] == null) continue;
                s.append(indent).append(children[j].key).append(" ").append(children[j].value).append("\n");
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
        BTree<Integer, Integer> st = new BTree<>(5);

        for (int i = 1; i <= 22; ++i)
            st.put(i, i);

        System.out.println("size: " + st.size());
        System.out.println("height: " + st.height());
        System.out.println(st);
        System.out.println();

        st.delete(1);
        st.delete(6);
        st.delete(5);
        st.delete(4);

        System.out.println("size: " + st.size());
        System.out.println("height: " + st.height());
        System.out.println(st);
        System.out.println();
    }
}