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

}
