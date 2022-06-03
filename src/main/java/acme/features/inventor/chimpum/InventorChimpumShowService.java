package acme.features.inventor.chimpum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.chimpum.Chimpum;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractShowService;
import acme.roles.Inventor;

@Service
public class InventorChimpumShowService implements AbstractShowService<Inventor, Chimpum>{

	@Autowired
	protected InventorChimpumRepository repository;
	
	@Override
	public boolean authorise(final Request<Chimpum> request) {
		assert request != null;
		
		final int id = request.getModel().getInteger("id");
		final Chimpum c = this.repository.findChimpumById(id);
		final Inventor i = this.repository.findInventorByUserAccountId(request.getPrincipal().getAccountId());
		return c.getArtefact().getInventor().getId()==i.getId();
	}

	@Override
	public Chimpum findOne(final Request<Chimpum> request) {
		assert request != null;
		
		final int id = request.getModel().getInteger("id");
		final Chimpum res = this.repository.findChimpumById(id);
		
		return res;
	}

	@Override
	public void unbind(final Request<Chimpum> request, final Chimpum entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		
		request.unbind(entity, model, "code", "creationMoment", "title", "description", "startDate", "finishDate", "budget", "link");
		
	}

}
