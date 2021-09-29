# `AVL` 平衡二叉树

## 介绍

> `AVL` 树：Adelson-Velsky and Landis Tree。是计算机科学中最早被发明的自平衡二叉树，在 `AVL` 树中，任意两个节点之间的高度差不会超过 1，因此 `AVL` 树也被称之为高度平衡树。`AVL` 树的插入、删除、查找操作在平均和最坏的时间复杂度都为 $O(log_2N)$ 级别的时间复杂度

在这里，我们给定 `AVL` 树的高度差阈值为 1，当有节点的高度差大于这个阈值时，执行相应的操作以实现树的平衡。



## 平衡的实现

`AVL` 树的平衡也是通过旋转节点来实现平衡的，分别对以下四种情况进行讨论

- 左左情况

  这种情况是指当前处理的节点的左子节点的左子节点的节点高度差大于给定阈值的情况，如下图所示：

  <img src="https://i.loli.net/2021/09/29/iR9LFu8pCOn35x1.png" alt="1.png" style="zoom:80%;" />

  如图所示，当前的 10 节点和底部的 3节点之间的高度差已经大于我们给定的阈值 1了，因此这种情况下需要对 10 节点进行一次右旋转，使得这棵树依旧是满足给定的高度差阈值的。

  进行右旋转之后：

  <img src="https://i.loli.net/2021/09/29/xIbwZmzRYthgVyp.png" alt="1.png" style="zoom:80%;" />

  

- 右右情况

  这种情况就是当前节点的右子节点的右子节点之间节点的高度差大于给定的阈值的情况，如下图所示：

  <img src="https://i.loli.net/2021/09/29/jDIlVfdg32OUtJz.png" alt="1.png" style="zoom:80%;" />

  在这种情况下，对当前节点进行一次左旋转即可实现树的重新平衡，使得当前节点的高度差在给定的阈值范围内

  进行左旋转之后：

  <img src="https://i.loli.net/2021/09/29/ymOP5vR1CgAt3SZ.png" alt="1.png" style="zoom:80%;" />

  

- 左右情况

  这种情况下，是由于当前节点与当前节点的左子节点的右子节点之间的高度差达到指定阈值的情况，如下图所示：

  <img src="https://i.loli.net/2021/09/29/Wut9dOVYMrkZ2JF.png" alt="lr.png" style="zoom:80%;" />

  这种情况下，首先需要对当前节点的左子节点进行一次左旋转，然后再进行一次右旋转即可再次达到树的平衡

  首先对当前节点的左子节点进行左旋转：

  <img src="https://i.loli.net/2021/09/29/B3aYAks6eUPoZCX.png" alt="1.png" style="zoom:80%;" />

  然后再对当前节点进行一次右旋转：

  <img src="https://i.loli.net/2021/09/29/chO9D1luz3bYjkW.png" alt="1.png" style="zoom:80%;" />

- 右左情况

  这种情况是当前节点和当前节点的右子节点的左子节点之间的节点高度差达到了给定的阈值，这种情况如下图所示：

  <img src="https://i.loli.net/2021/09/29/Gk73AdUxnBCfPEz.png" alt="1.png" style="zoom:80%;" />

  这种情况下，需要首先对当前节点的右子节点进行一次右旋转，再对当前节点进行一次左旋转来完成树的重新平衡

  首先对当前节点的右子节点进行一次右旋转：

  <img src="https://i.loli.net/2021/09/29/CH163gB7o9ypnSD.png" alt="1.png" style="zoom:80%;" />

  再对当前节点进行一次左旋转即可：

  <img src="https://i.loli.net/2021/09/29/Bl4sfOZ93SvmpEV.png" alt="1.png" style="zoom:80%;" />

以上就是在处理 `AVL`树的过程中可能会遇到的几种情况，通过分析这几种情况并提供对应的解决策略使得整个树最终保持平衡，这是理解 `AVL`树的基础。



## 实现

对于一棵树来讲，最基本的操作便是增、删、改、查四个操作。查找操作与一般的二叉树的查找没有任何不同，因此在这里不介绍具体的实现思路；对于修改操作来讲，无非就是找到要修改的节点，然后再更新对应的节点数据即可，这与查找操作没有太大的差异。

`AVL` 树中最关键的两个操作便是插入和删除，同时也是实现起来比较困难的地方。主要是要结合上文提到的几种情况进行分析，然后进行旋转操作，这几个过程比较复杂，因此在这里讲述一下实现的思路。

首先，定义 `AVL` 树的节点类：

```java
/**
* AVL 树的节点对象
*/
private static class Node implements Comparable<Node> {
    private int val;
    private Node left, right;
    private int height = 0; // 当前节点的树高度

    private Node(int val, Node left, Node right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }

    @Override
    public int compareTo(Node o) {
        if (null == o) return 1;

        return this.val - o.val;
    }

    @Override
    public boolean equals(Object obj) {
        if (null == obj) return false;
        if (obj.getClass() != this.getClass())
            return false;

        Node o2 = (Node) obj;

        return this.val == o2.val;
    }

    @Override
    public String toString() {
        return "val: " + val;
    }
}
```



### 插入操作的实现

实现思路：递归地查找当前插入的节点的插入位置，将其插入后再检测当前的处理节点与其相关的子节点之间是否存在高度差达到指定阈值的节点，如果存在这样的节点，那么就需要调整这个节点。

重新调整当前的处理节点：

```java
/**
* 重新调节当前的节点树
* @param parent ： 当前处理的要重新调节平衡的节点
* @param node ： 插入的节点
* @return ： 重新调节之后的得到的根节点
*/
private Node reBalance(Node parent, Node node) {
    parent.height = Math.max(getHeight(parent.left), getHeight(parent.right)) + 1;
    /*
            当左右子树的高度差达到指定阈值时，需要进行相应的调整
         */
    if (getHeight(parent.left) - getHeight(parent.right) == factor) {
        if (less(node, parent.left))
            /*
                   此时的情况如下所示（带 ? 表示该节点可能存在）：node 节点在 left 节点的左边，此时的情况如 左左情况一致
                   因此，只需将根节点进行一次右旋转即可。
                                parent
                               /      \
                             left     right?
                            /    \
                          left?  right?
                          /   \
                       node?  node? （node 的位置不确定可能在左边，可能在右边，甚至可能在父节点 left）
                */
            parent = rrRotate(parent, parent.right);
        else
            /*
                   此时的情况如下所示（带 ? 表示该节点可能存在）：node 节点在 left 节点的右边，此时的情况如 左右情况一致
                   此时，需要先对左子节点进行一次左旋转，然后对根节点进行一次右旋转
                                parent
                               /      \
                             left     right?
                            /    \
                          left?  right?
                                 /    \
                               node?  node? （node 的位置不确定可能在左边，可能在右边，甚至可能在父节点 right）
                 */
            parent = lrRotate(parent, parent.left);
    } else if (getHeight(parent.right) - getHeight(parent.left) == factor) {
        if (less(node, parent.right))
            /*
                   此时的情况如下所示（带 ? 表示该节点可能存在）：node 节点在 right 节点的左边，此时的情况如 右左情况一致
                   此时，需要先对右子节点进行一次右旋转，然后对根节点进行一次左旋转
                                parent
                               /      \
                             left?    right
                                     /    \
                                   left?  right?
                                   /    \
                                 node?  node? （node 的位置不确定可能在左边，可能在右边，甚至可能在父节点 left）
                */
            parent = rlRotate(parent, parent.right);
        else
            /*
                   此时的情况如下所示（带 ? 表示该节点可能存在）：node 节点在 right 节点的右边，此时的情况如 右右情况一致
                   因此，对根节点执行一次左旋转即可
                                parent
                               /      \
                             left?    right
                                     /    \
                                   left?  right?
                                          /    \
                                        node?  node? （node 的位置不确定可能在左边，可能在右边，甚至可能在父节点 right）
                */
            parent = llRotate(parent, parent.right);
    }

    return parent;
}
```

每次只要递归地调整处理节点即可实现树整体的调节。

具体的插入操作的实现如下：

```java
 /**
 * 插入一个节点，返回插入后的根节点，如果插入重复元素，则会覆盖原有的节点
 * @param node ： 待插入的节点元素
 * @return ： 插入后的根节点
 */
public Node insert(Node node) {
    // 根节点为 null， 表示当前树还没有节点，因此将该节点置为根节点
    if (null == root) {
        root = node;
        root.height = 1;
        return root;
    }

    // 递归插入该节点，同时更新根节点
    root = insert(node, root);
    return root;
}

private Node insert(Node node, Node parent) {
    if (parent == null) {
        parent = node;
        return parent;
    }

    if (less(node, parent)) {
        parent.left = insert(node, parent.left);
    } else {
        parent.right = insert(node, parent.right);
    }

    return reBalance(parent, node);
}
```



### 删除操作的实现

删除操作是一个比较复杂的实现，因为如果任意删除一个节点元素的话，那么树的平衡性就很难得到保障。相比较与红黑树使用多个节点组成 3- 节点或者 4- 节点来删除键来维护树的平衡性，`AVL` 树的做法就更加纯粹一些。

主要有两种方式来实现节点的删除操作：一是把要删除的节点不断地旋转，将它旋转到叶子节点然后再删除它，然后再重新调整数的平衡；二是从当前节点的左子节点找到最大的元素节点或者从右子节点中找到最小的元素节点来填充当前节点的数据，然后再向下递归地删除这个节点元素，再重新调整树的平衡性。

想比较而言，使用第二种方式是一个更加简单有效的实现方式，因此在这里也采用第二种方式来实现，这里的实现填充的是右子节点的最小元素数据。

具体实现代码如下图所示：

```java
/**
* 删除对应的 node 节点，然后返回删除节点之后的根节点
* @param node：待删除的节点
* @return ： 删除节点之后的根节点
*/
public Node delete(Node node) {
    return delete(node, root);
}

/**
* 删除操作的基本流程：
*  1、找到对应的节点，然后使用右子节点的最小节点替换掉当前的节点
*  2、在右子树中递归地删除使用的替换节点，直到为空
*  3、在删除的过程中需要重新调整树
* @param node ： 待删除的节点
* @param parent ：当前处理的节点
* @return ： 处理完之后的根节点
*/
private Node delete(Node node, Node parent) {
    // 当前处理的节点为空，说明已经到到递归终点了，停止递归
    if (parent == null)
        return null;

    /*
   	 	删除节点之后，需要使用一个别的节点来替换该节点以维持树的平衡
    	有两种方案可以选取：左子树的最右节点和右子树的最左节点，这里选用的是第二种
    */
    if (node.equals(parent)) {
        if (parent.right == null) {
            // 右子节点为空，那么只需要放弃这个节点的引用，使得垃圾收集器删除即可
            parent = parent.left;
            return parent;
        } else {
            // 查找右子树的最左节点，以充当平衡节点
            Node rNode = parent.right;
            while (rNode.left != null)
                rNode = rNode.left;
            // 查找替换节点结束

            parent.val = rNode.val; // 更新当前的待删除节点，此时的树依旧是平衡的
            // 由于已经将替换节点提上来了，因此需要删除这个节点
            parent.right = delete(rNode, parent.right);
        }
    } else if (less(node, parent)) {
        parent.left = delete(node, parent.left);
    } else {
        parent.right = delete(node, parent.right);
    }

    // 重新调整树的平衡
    parent.height = Math.max(getHeight(parent.left), getHeight(parent.right)) + 1;
    if (getHeight(parent.right) - getHeight(parent.left) == factor) {
        if (getHeight(parent.right.right) >= getHeight(parent.right.left))
            /*
                   此时的情况如下所示（带 ? 表示该节点可能存在）：与右右情况一致
                   因此，对根节点执行一次左旋转即可
                                parent
                               /      \
                             left?    right
                                     /    \
                                   left?  right?
                                          /    \
                                        left?   right?
            */
            parent = llRotate(parent, parent.right);
        else
            /*
                   此时的情况如下所示（带 ? 表示该节点可能存在）：与右左情况一致
                   需要对右子节点执行一次右旋转，然后对根节点执行一次左旋转
                                parent
                               /      \
                             left?    right
                                     /    \
                                   left?  right?
                                  /    \
                               left?   right?
                */
            parent = rlRotate(parent, parent.right);

    } else if (getHeight(parent.left) - getHeight(parent.right) == factor) {
        if (getHeight(parent.left.left) >= getHeight(parent.left.right))
            /*
                   此时的情况如下所示（带 ? 表示该节点可能存在）：与左左情况一致
                   对根节点执行一次右旋转即可
                                parent
                               /      \
                             left    right?
                            /    \
                          left?  right?
                         /    \
                      left?   right?
                */
            parent = rrRotate(parent, parent.left);
        else
            /*
                   此时的情况如下所示（带 ? 表示该节点可能存在）：与左右情况一致
                   需要对左子节点执行一次左旋转，然后对根节点执行一次右旋转即可
                                parent
                               /      \
                             left    right?
                            /    \
                          left?  right?
                                 /    \
                              left?   right?
                */
            parent = lrRotate(parent, parent.right);
    }

    return parent;
}
```



总体上相比较于红黑树而言， `AVL` 树中需要的操作更多，因此整体性能上要比红黑树要差一些。



整体的实现代码：https://github.com/LiuXianghai-coder/Test-Repo/blob/master/DataStructure/AVL.java