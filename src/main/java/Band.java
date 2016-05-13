import java.util.List;
import java.util.ArrayList;
import org.sql2o.*;

public class Band {
  private int id;
  private String name;
  private int venues_id;

  public Band(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public int getId() {
    return id;
  }

  public static List<Band> all() {
    String sql = "SELECT * FROM bands";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Band.class);
    }
  }

  @Override
  public boolean equals(Object otherBand){
    if (!(otherBand instanceof Band)) {
      return false;
    } else {
      Band newBand = (Band) otherBand;
      return this.getId() == newBand.getId();
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO bands (name) VALUES (:name)";
      this.id = (int) con.createQuery(sql, true)
      .addParameter("name", this.name)
      .executeUpdate()
      .getKey();
    }
  }

  public static Band find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM bands WHERE id=:id";
      return con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Band.class);
    }
  }

  public void delete() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM bands WHERE id = :id";
      con.createQuery(sql)
        .addParameter("id", id)
        .executeUpdate();
      sql = "DELETE FROM bands_venues WHERE band_id = :id";
      con.createQuery(sql)
        .addParameter("id", id)
        .executeUpdate();
    }
  }

  public void addVenue(Venue venue) {
    try (Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO bands_venues (band_id, venue_id) VALUES (:band_id, :venue_id);";
      con.createQuery(sql, true)
        .addParameter("band_id", this.getId())
        .addParameter("venue_id", venue.getId())
        .executeUpdate();
    }
  }

  public List<Venue> getVenues() {
    try(Connection con = DB.sql2o.open()) {
      String joinQuery = "SELECT venues.* FROM bands" +
      " JOIN bands_venues ON (bands.id = bands_venues.band_id)" +
      " JOIN venues ON (bands_venues.venue_id = venues.id)" +
      " WHERE band_id = :band_id;";

      List<Venue> venues = con.createQuery(joinQuery)
        .addParameter("band_id", this.getId())
        .executeAndFetch(Venue.class);
      return venues;
    }
  }

  public void editBand(String newName) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE bands SET name = :name WHERE id = :id;";
      con.createQuery(sql)
        .addParameter("id", this.id)
        .addParameter("name", newName)
        .executeUpdate();
      String synch = "SELECT name FROM bands WHERE id = :id;";
      this.name = con.createQuery(synch)
        .addParameter("id", this.id)
        .executeAndFetchFirst(String.class);
      // probably easier and more efficient to just update the local object directly here, but this is more complicated and thus must be better.
    }
  }

}
