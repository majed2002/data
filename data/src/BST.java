 

public class BST<K extends Comparable<K>, T> implements Map<K, T> {
  public BSTNode<K, T> root, current; // Do not change this

  @Override
  public boolean empty() {
    return root == null;
  }

  @Override
  public boolean full() {
    return false;
  }

  @Override
  public T retrieve() {
    if(current == null)
    	return null;
    return current.data;
    

  }

  @Override
  public void update(T e) {
    if (current != null) {
      current.data = e;
    }
  }

  @Override
  public Pair<Boolean, Integer> find(K key) {
    int keyComp = 0;
    boolean findee = false;

    for (BSTNode<K, T> node = root; node != null; ) {
      keyComp++;
      if (node.key.compareTo(key) > 0) {
        node = node.left;
      } else if (node.key.compareTo(key) < 0) {
        node = node.right;
      } else {
        current = node;
        findee = true;
        break;
      }
    }

    return new Pair<>(findee, keyComp);
  }

  @Override
  public Pair<Boolean, Integer> insert(K k, T e) {
    int keyComp = 0;
    boolean inserted = false;
    if (root == null) {
      root = current = new BSTNode<>(k, e);
      inserted = true;
    } else {
      for (BSTNode<K, T> node = root; node != null; ) {
        keyComp++;
        if (node.key.compareTo(k) > 0) {
          if (node.left == null) {
            node.left = new BSTNode<>(k, e);
            current = node.left;
            inserted = true;
            break;
          } else {
            node = node.left;
          }
        } else if (node.key.compareTo(k) < 0) {
          if (node.right == null) {
            node.right = new BSTNode<>(k, e);
            current = node.right;
            inserted = true;
            break;
          } else {
            node = node.right;
          }
        } else {
          break;
        }
      }
    }

    return new Pair<>(inserted, keyComp);
  }

  @Override
  public Pair<Boolean, Integer> remove(K key) {
    int keyComp = 0;
    boolean found = false;
    // search for k
    K k1 = key;
    BSTNode<K, T> p = root;
    BSTNode<K, T> q = null; // parent of p
    while (p != null) {
      keyComp++;
      if (k1.compareTo(p.key) < 0) {
        q = p;
        p = p.left;
      } else if (k1.compareTo(p.key) > 0) {
        q = p;
        p = p.right;
      } else {
        // found the key
        // check the three cases
        if ((p.left != null) && (p.right != null)) {
          // case 3: two children
          // search for the min in the right subtree
          BSTNode<K, T> min = p.right;
          q = p;
          while (min.left != null) {
            q = min;
            min = min.left;
          }
          p.key = min.key;
          p.data = min.data;
          k1 = min.key;
          p = min;
          // Now fall back to either case 1 or 2
        }

        // the subtree rooted at p will change here
        if (p.left != null) {
          p = p.left;
        } else { // One or no children
          p = p.right;
        }

        if (q == null) { // No parent for p, root must change
          root = p;
        } else {
          if (k1.compareTo(q.key) < 0) {
            q.left = p;
          } else {
            q.right = p;
          }
        }
        found = true;
        break;
      }
    }

    return new Pair<>(found, keyComp);
  }

  @Override
  public List<K> getAll() {
    List<K> list = new LinkedList<>();

    getKeys(root, list);

    return list;
  }

  private void getKeys(BSTNode<K, T> node, List<K> list) {
    if (node != null) {
      getKeys(node.left, list);
      list.insert(node.key);
      getKeys(node.right, list);
    }
  }

  public static class BSTNode<K extends Comparable<K>, T> {

    public K key;
    public T data;
    public BSTNode<K, T> left, right;

    public BSTNode(K key, T data) {
      this.key = key;
      this.data = data;
      left = right = null;
    }
  }
}
