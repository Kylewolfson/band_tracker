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
  public void find_findsBandInDatabase_true() {
    Band myBand = new Band("Atom and his Package");
    myBand.save();
    Band savedBand = Band.find(myBand.getId());
    assertTrue(myBand.equals(savedBand));
  }
}
