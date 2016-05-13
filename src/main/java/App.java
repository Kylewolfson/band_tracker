
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
