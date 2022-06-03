package acme.features.inventor.chimpum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.chimpum.Chimpum;
import acme.entities.item.Item;
import acme.entities.item.Status;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractDeleteService;
import acme.roles.Inventor;

@Service
public class InventorChimpumDeleteService implements AbstractDeleteService<Inventor, Chimpum>{

	@Autowired
	protected InventorChimpumRepository repository;;
	
	@Override
	public boolean authorise(final Request<Chimpum> request) {
		assert request != null;
		
		final int id = request.getModel().getInteger("id");
		final Chimpum c = this.repository.findChimpumById(id);
		final Inventor inv = this.repository.findInventorByUserAccountId(request.getPrincipal().getAccountId());
		final Item i = c.getArtefact();
		return i.getInventor().getId()==inv.getId() && i.getStatus().equals(Status.NON_PUBLISHED); //&& c.getArtefact().getStatus().equals(ItemType.TOOL); 
	}

	@Override
	public void bind(final Request<Chimpum> request, final Chimpum entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		
		request.bind(entity, errors, "code", "creationMoment", "title", "description", "startDate", "finishDate", "budget", "link");
		
	}

	@Override
	public void unbind(final Request<Chimpum> request, final Chimpum entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		
		request.unbind(entity, model, "code", "creationMoment", "title", "description", "startDate", "finishDate", "budget", "link");
		
	}

	@Override
	public Chimpum findOne(final Request<Chimpum> request) {
		assert request != null;
		
		final int id = request.getModel().getInteger("id");

		return this.repository.findChimpumById(id);
	}

	@Override
	public void validate(final Request<Chimpum> request, final Chimpum entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		
	}

	@Override
	public void delete(final Request<Chimpum> request, final Chimpum entity) {
		assert request != null;
		assert entity != null;
		
		this.repository.delete(entity);
		
	}

}
