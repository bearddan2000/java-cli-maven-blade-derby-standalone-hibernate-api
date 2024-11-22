package example;

import com.blade.Blade;
import com.blade.event.EventType;
import com.blade.mvc.WebContext;
import com.blade.mvc.http.Session;

import example.model.Dog;

public class Main {

	public static void main(String[] args) {
		org.hibernate.Session session = HibernateUtils.getSessionFactory().openSession();

		Dog tbl = new Dog(session);

		try {
			tbl.insert("Am Bulldog", "White");
			tbl.insert("Blue Tick", "Grey");
			tbl.insert("Labrador", "Black");
			tbl.insert("Gr Shepard", "Brown");
		} catch (Exception e) {}

		String result = tbl.selectAll();

        Blade.of()
            .get("/basic-route-example", ctx -> ctx.text(result))
            .post("/basic-route-example", ctx -> ctx.text("POST called"))
            .put("/basic-route-example", ctx -> ctx.text("PUT called"))
            .delete("/basic-route-example", ctx -> ctx.text("DELETE called"))
            .enableCors(true)
            .start(Main.class, args);
			
		HibernateUtils.shutdown();
	}
}
