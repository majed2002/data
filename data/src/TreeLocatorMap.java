
public class TreeLocatorMap<K extends Comparable<K>> implements LocatorMap<K> {

  private final Map<K, Location> map = new BST<>();
  private final Locator<K> locator = new TreeLocator<>();

  @Override
  public Map<K, Location> getMap() {
    return map;
  }

  @Override
  public Locator<K> getLocator() {
    return locator;
  }

  @Override
  public Pair<Boolean, Integer> add(K k, Location loc) {
    Pair<Boolean, Integer> rsultt = map.insert(k, loc);
    if (rsultt.first) locator.add(k, loc);

    return rsultt;
  }

  @Override
  public Pair<Boolean, Integer> move(K k, Location loc) {
    Pair<Boolean, Integer> result = map.find(k);

    if (result.first) {
      locator.remove(k, map.retrieve());
      locator.add(k, loc);
      map.update(loc);
    }
    return result;
  }

  @Override
  public Pair<Location, Integer> getLoc(K k) {
    Pair<Boolean, Integer> rsult = map.find(k);

    return new Pair<>((rsult.first) ? map.retrieve() : null, rsult.second);
  }

  @Override
  public Pair<Boolean, Integer> remove(K k) {
    Pair<Boolean, Integer> result = map.find(k);
    if (result.first) {
      locator.remove(k, map.retrieve());
    }
    return map.remove(k);
  }

  @Override
  public List<K> getAll() {
    return map.getAll();
  }

  @Override
  public Pair<List<K>, Integer> getInRange(Location lowerLeft, Location upperRight) {
    Pair<List<Pair<Location, List<K>>>, Integer> inRange = locator.inRange(lowerLeft, upperRight);

    List<K> keys = new LinkedList<>();

    List<Pair<Location, List<K>>> list = inRange.first;

    if (list != null && !list.empty()) {
      list.findFirst();
      while (!list.last()) {
        List<K> kList = list.retrieve().second;
        if (kList != null && !kList.empty()) {
          kList.findFirst();
          while (!kList.last()) {
            keys.insert(kList.retrieve());
            kList.findNext();
          }
          keys.insert(kList.retrieve());
        }
        list.findNext();
      }
      List<K> kList = list.retrieve().second;
      if (kList != null && !kList.empty()) {
        kList.findFirst();
        while (!kList.last()) {
          keys.insert(kList.retrieve());
          kList.findNext();
        }
        keys.insert(kList.retrieve());
      }
    }
    return new Pair<>(keys, inRange.second);
  }
}
