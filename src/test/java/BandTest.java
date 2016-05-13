import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;


public class BandTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void Band_instantiatesCorrectly_true() {
    Band myBand = new Band("Atom and his Package");
    assertEquals(true, myBand instanceof Band);
  }

  @Test
  public void getName_recipeInstantiatesWithName_String() {
    Band myBand = new Band("Mountain Goats");
    assertEquals("Mountain Goats", myBand.getName());
  }

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Band.all().size(), 0);
  }


  @Test
  public void save_assignsIdToObject() {
    Band myBand = new Band("Atom and his Package");
    myBand.save();
    Band savedBand = Band.all().get(0);
    assertEquals(myBand.getId(), savedBand.getId());
  }

  @Test
  public void delete_deletesBandFromDatabase_true() {
    Band myBand = new Band("Atom and his Package");
    myBand.save();
    myBand.delete();
    assertEquals(Band.all().size(), 0);
  }

  @Test
  public void addVenue_addVenueToBand() {
    Venue myVenue = new Venue("Bagdad");
    myVenue.save();
    Band myBand = new Band("Atom and his Package");
    myBand.save();
    myBand.addVenue(myVenue);
    Venue savedVenue = myBand.getVenues().get(0);
    assertTrue(myVenue.equals(savedVenue));
  }

  @Test
  public void editBand_ChangesBandName() {
    Band myBand = new Band("Adam and his Package");
    myBand.save();
    myBand.editBand("Atom and his Package");
    assertEquals("Atom and his Package", myBand.getName());
  }


  // @Test
  // public void delete_clearsJoinTableAsWellAsBand_true() {
  //   Venue myVenue = new Venue("Bagdad");
  //   myVenue.save();
  //   Band myBand = new Band("Atom and his Package");
  //   myBand.save();
  //   myBand.addVenue(myVenue);
  //   myBand.delete();
  //   int venueId = myVenue.getId();
  //   Integer nullChecker;
  //   String sql = "SELECT id FROM bands_venues";
  //   try(Connection con = DB.sql2o.open()) {
  //     nullChecker = con.createQuery(sql).executeAndFetchFirst(Integer.class);
  //
  //   assertEquals((int)venueId, (int)nullChecker);
  //   }
  // }


}
