import java.util.List;
import java.util.ArrayList;
import org.sql2o.*;

public class Venue {
  private int id;
  private String name;
  private int bands_id;

  public Venue(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public int getId() {
    return id;
  }

  public static List<Venue> all() {
    String sql = "SELECT * FROM venues";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Venue.class);
    }
  }

  @Override
  public boolean equals(Object otherVenue){
    if (!(otherVenue instanceof Venue)) {
      return false;
    } else {
      Venue newVenue = (Venue) otherVenue;
      return this.getId() == newVenue.getId();
    }
  }

  public void save() {
    if (!this.getName().equals("")) {
      try(Connection con = DB.sql2o.open()) {
        String sql = "INSERT INTO venues (name) VALUES (:name)";
        this.id = (int) con.createQuery(sql, true)
        .addParameter("name", this.name)
        .executeUpdate()
        .getKey();
      }
    }
  }

  public static Venue find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM venues WHERE id=:id";
      return con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Venue.class);
    }
  }

  public String checkboxMemory(Band band) {
    try(Connection con = DB.sql2o.open()) {
      String joinQuery = "SELECT venues.* FROM bands" +
      " JOIN bands_venues ON (bands.id = bands_venues.band_id)" +
      " JOIN venues ON (bands_venues.venue_id = venues.id)" +
      " WHERE band_id = :band_id AND venue_id = :venue_id;";

      Venue venue = con.createQuery(joinQuery)
        .addParameter("band_id", band.getId())
        .addParameter("venue_id", this.getId())
        .executeAndFetchFirst(Venue.class);
      if (venue == null){
        return null;
      }
      else {
        return "checked";
      }
    }
  }

}
