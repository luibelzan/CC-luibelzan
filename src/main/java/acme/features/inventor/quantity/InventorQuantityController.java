package acme.features.inventor.quantity;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.quantity.Quantity;
import acme.framework.controllers.AbstractController;
import acme.roles.Inventor;

@Controller
public class InventorQuantityController extends AbstractController<Inventor, Quantity>{

	@Autowired
	protected InventorQuantityCreateService createService;
	
	@Autowired
	protected InventorQuantityListService listService;
	
	@Autowired
	protected InventorQuantityShowService showService;
	
	@Autowired
	protected InventorQuantityUpdateService updateService;
	
	@PostConstruct
	protected void initialise() {
		
		super.addCommand("create", this.createService);
		super.addCommand("list-by-toolkit",  "list", this.listService);
		super.addCommand("show", this.showService);
		super.addCommand("update", this.updateService);
	}
}
