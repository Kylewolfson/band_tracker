import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;


public class VenueTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void Venue_instantiatesCorrectly_true() {
    Venue myVenue = new Venue("Crystal Ballroom");
    assertEquals(true, myVenue instanceof Venue);
  }

  @Test
  public void getName_recipeInstantiatesWithName_String() {
    Venue myVenue = new Venue("Bagdad");
    assertEquals("Bagdad", myVenue.getName());
  }

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Venue.all().size(), 0);
  }


  @Test
  public void save_assignsIdToObject() {
    Venue myVenue = new Venue("Crystal Ballroom");
    myVenue.save();
    Venue savedVenue = Venue.all().get(0);
    assertEquals(myVenue.getId(), savedVenue.getId());
  }

  @Test
  public void find_findsVenueInDatabase_true() {
    Venue myVenue = new Venue("Crystal Ballroom");
    myVenue.save();
    Venue savedVenue = Venue.find(myVenue.getId());
    assertTrue(myVenue.equals(savedVenue));
  }
}
