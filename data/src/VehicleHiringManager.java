public class VehicleHiringManager {

  private LocatorMap<String> locatorMap;

  public VehicleHiringManager() {
    locatorMap = new TreeLocatorMap<>();
  }

  // Returns the locator map.
  public LocatorMap<String> getLocatorMap() {
    return locatorMap;
  }

  // Sets the locator map.
  public void setLocatorMap(LocatorMap<String> locatorMap) {
    this.locatorMap = locatorMap;
  }

  // Inserts the vehicle id at location loc if it does not exist and returns true.
  // If id already exists, the method returns false.
  public boolean addVehicle(String id, Location loc) {
    return locatorMap.add(id, loc).first;
  }

  // Moves the vehicle id to location loc if id exists and returns true. If id not
  // exist, the method returns false.
  public boolean moveVehicle(String id, Location loc) {
    return locatorMap.move(id, loc).first;
  }

  // Removes the vehicle id if it exists and returns true. If id does not exist,
  // the method returns false.
  public boolean removeVehicle(String id) {
    return locatorMap.remove(id).first;
  }

  // Returns the location of vehicle id if it exists, null otherwise.
  public Location getVehicleLoc(String id) {
    return locatorMap.getLoc(id).first;
  }

  // Returns all vehicles located within a square of side 2*r centered at loc
  // (inclusive of the boundaries).
  public List<String> getVehiclesInRange(Location loc, int r) {
    Location upperRight = new Location(loc.x + r, loc.y + r);
    Location lowerLeft = new Location(loc.x - r, loc.y - r);

    return locatorMap.getInRange(lowerLeft, upperRight).first;
  }
}
