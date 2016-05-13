
import java.util.Map;
import java.util.HashMap;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;
import java.util.ArrayList;

public class App {
  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    get("/", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("bands", Band.all());
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/bands/:id", (request,response) ->{
      Map<String, Object> model = new HashMap<String, Object>();
      Band band = Band.find(Integer.parseInt(request.params("id")));
      model.put("band", band);
      model.put("venues", band.getVenues());
      model.put("template", "templates/band_detail.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/bands/:id/edit_band", (request,response) ->{
      Map<String, Object> model = new HashMap<String, Object>();
      Band band = Band.find(Integer.parseInt(request.params("id")));
      model.put("band", band);
      model.put("venues", Venue.all());
      model.put("template", "templates/edit_band.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/edit_band_venues/:id", (request, response) -> {
      Integer id = Integer.parseInt(request.params("id"));
      String[] venues = request.queryParamsValues("venue");
      Band band = Band.find(id);
      band.clearVenues();
      if (venues != null) {
        for (String venueId : venues) {
          Venue addVenue = Venue.find(Integer.parseInt(venueId));
          band.addVenue(addVenue);
        }
      }
      response.redirect("/bands/"+id.toString()+"/edit_band");
      return null;
    });

    get("/new_band", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("bands", Band.all());
      model.put("template", "templates/new_band.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/new_band", (request, response) -> {
      String name = request.queryParams("name");
      Band newBand = new Band(name);
      newBand.save();
      response.redirect("/new_band");
      return null;
    });

    get("/venue_list", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("venues", Venue.all());
      model.put("template", "templates/venue_list.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/new_venue", (request, response) -> {
      String name = request.queryParams("name");
      Venue newVenue = new Venue(name);
      System.out.println(newVenue.getName());
      newVenue.save();
      response.redirect("/venue_list");
      return null;
    });

  }
}
