import java.util.concurrent.atomic.AtomicInteger;

public class TreeLocator<T> implements Locator<T> {

  private Node<List<T>> root = null;

  @Override
  public int add(T e, Location loc) {
    int keyComp = 0;

    LinkedList<T> list = new LinkedList<>();
    list.insert(e);
    Node<List<T>> child = new Node<List<T>>(loc, list);

    if (root == null) {
      root = child;
    } else {
      for (Node<List<T>> node = root; node != null; ) {
        keyComp++;
        Location location = node.location;

        if (loc.x < location.x && loc.y <= location.y) {
          if (node.l1 == null) {
            node.l1 = child;
            break;
          } else node = node.l1;
        } else if (loc.x <= location.x && loc.y > location.y) {
          if (node.l2 == null) {
            node.l2 = child;
            break;
          } else node = node.l2;
        } else if (loc.x > location.x && loc.y >= location.y) {
          if (node.l3 == null) {
            node.l3 = child;
            break;
          } else node = node.l3;
        } else if (loc.x >= location.x && loc.y < location.y) {
          if (node.l4 == null) {
            node.l4 = child;
            break;
          } else node = node.l4;
        } else {
          node.data.insert(e);
          break;
        }
      }
    }
    return keyComp;
  }

  @Override
  public Pair<List<T>, Integer> get(Location loc) {
    int keyComp = 0;

    for (Node<List<T>> node = root; node != null; ) {
      Location location = node.location;
      keyComp++;
      if (loc.x < location.x && loc.y <= location.y) {
        node = node.l1;
      } else if (loc.x <= location.x && loc.y > location.y) {
        node = node.l2;
      } else if (loc.x > location.x && loc.y >= location.y) {
        node = node.l3;
      } else if (loc.x >= location.x && loc.y < location.y) {
        node = node.l4;
      } else {
        return new Pair<>(node.data, keyComp);
      }
    }
    return new Pair<List<T>, Integer>(new LinkedList<T>(), keyComp);
  }

  @Override
  public Pair<Boolean, Integer> remove(T e, Location loc) {
    Pair<List<T>, Integer> found = get(loc);

    boolean removed = false;

    if (!found.first.empty()) {
      found.first.findFirst();
      while (!found.first.last()) {
        if (found.first.retrieve() == e) {
          found.first.remove();
          removed = true;
        }
        found.first.findNext();
      }
      if (found.first.retrieve() == e) {
        found.first.remove();
        removed = true;
      }
    }
    return new Pair<>(removed, found.second);
  }

  @Override
  public List<Pair<Location, List<T>>> getAll() {
    List<Pair<Location, List<T>>> locations = new LinkedList<>();

    if (root != null) root.getAll(locations);

    return locations;
  }

  @Override
  public Pair<List<Pair<Location, List<T>>>, Integer> inRange(
      Location lowerLeft, Location upperRight) {
    List<Pair<Location, List<T>>> locations = new LinkedList<>();
    AtomicInteger keyComp = new AtomicInteger(0);

    if (root != null) {
      find(root, lowerLeft, upperRight, keyComp, locations);
    }

    return new Pair<>(locations, keyComp.get());
  }


  private void find(
      Node<List<T>> node,
      Location lowerLeft,
      Location upperRight,
      AtomicInteger keyComp,
      List<Pair<Location, List<T>>> list) {
    keyComp.incrementAndGet();


    if (lowerLeft.x <= node.location.x
        && lowerLeft.y <= node.location.y
        && upperRight.x >= node.location.x
        && upperRight.y >= node.location.y) {
      list.insert(new Pair<>(node.location, node.data));
    }

    if (node.l1 != null) {
      if (lowerLeft.x < node.location.x && lowerLeft.y <= node.location.y) {
        find(node.l1, lowerLeft, upperRight, keyComp, list);
      }
    }

    if (node.l2 != null) {
      if ((lowerLeft.x <= node.location.x && lowerLeft.y > node.location.y)
          || (upperRight.x <= node.location.x && upperRight.y > node.location.y)
          || (lowerLeft.x <= node.location.x && upperRight.y > node.location.y)) {
        find(node.l2, lowerLeft, upperRight, keyComp, list);
      }
    }

    if (node.l3 != null) {
      if (upperRight.x > node.location.x && upperRight.y >= node.location.y) {
        find(node.l3, lowerLeft, upperRight, keyComp, list);
      }
    }

    if (node.l4 != null) {
      if ((lowerLeft.x >= node.location.x && lowerLeft.y < node.location.y)
          || (upperRight.x >= node.location.x && upperRight.y < node.location.y)
          || (upperRight.x >= node.location.x && lowerLeft.y < node.location.y)) {
        find(node.l4, lowerLeft, upperRight, keyComp, list);
      }
    }
  }

  private static class Node<T> {
    private Node<T> l1 = null;
    private Node<T> l2 = null;
    private Node<T> l3 = null;
    private Node<T> l4 = null;

    private final T data;
    private final Location location;

    public Node(Location location, T data) {
      this.location = location;
      this.data = data;
    }

    private void getAll(List<Pair<Location, T>> list) {
      list.insert(new Pair<>(location, data));
      if (l1 != null) l1.getAll(list);
      if (l2 != null) l2.getAll(list);
      if (l3 != null) l3.getAll(list);
      if (l4 != null) l4.getAll(list);
    }

    @Override
    public String toString() {
      return "Node{"
          + "location="
          + location
          + ", c1="
          + l1
          + ", c2="
          + l2
          + ", c3="
          + l3
          + ", c4="
          + l4
          + '}';
    }
  }
}
