package acme.features.inventor.chimpum;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.chimpum.Chimpum;
import acme.entities.configuration.Configuration;
import acme.entities.item.Item;
import acme.entities.item.Status;
import acme.features.administrator.configurations.AdministratorConfigurationRepository;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.datatypes.Money;
import acme.framework.services.AbstractCreateService;
import acme.roles.Inventor;

@Service
public class InventorChimpumCreateService implements AbstractCreateService<Inventor, Chimpum>{

	
	@Autowired
	protected InventorChimpumRepository repository;
	
	@Autowired
	protected AdministratorConfigurationRepository adminRepository;
	
	@Override
	public boolean authorise(final Request<Chimpum> request) {
		assert request != null;
		
		final int id = request.getModel().getInteger("id");
		final Item i = this.repository.findItemById(id);
		final Inventor inv = this.repository.findInventorByUserAccountId(request.getPrincipal().getAccountId());
		return i.getInventor().getId()==inv.getId() && i.getStatus().equals(Status.NON_PUBLISHED); //&& c.getArtefact().getStatus().equals(ItemType.TOOL); 
	}

	@Override
	public void bind(final Request<Chimpum> request, final Chimpum entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		
		final Calendar calendar = Calendar.getInstance();
		final Item i = this.repository.findItemById(request.getModel().getInteger("id"));
		
		request.bind(entity, errors, "code", "title", "description", "startDate", "finishDate", "budget", "link");
		entity.setCreationMoment(calendar.getTime());
		entity.setArtefact(i);
		
	}

	@Override
	public void unbind(final Request<Chimpum> request, final Chimpum entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		
		request.unbind(entity, model, "code", "title", "description", "startDate", "finishDate", "budget", "link");
		
		model.setAttribute("id", request.getModel().getInteger("id"));
		
	}

	@Override
	public Chimpum instantiate(final Request<Chimpum> request) {
		assert request != null;
		
		final Chimpum res = new Chimpum();
		final Money budget = new Money();
		final Item i = this.repository.findItemById(request.getModel().getInteger("id"));
		//final Date fecha = new Date();
		budget.setCurrency("EUR");
		budget.setAmount(0.0);
		
		res.setCode("");
		res.setTitle("");
		res.setDescription("");
		res.setBudget(budget);
		res.setLink("");
		res.setArtefact(i);
		return res;
	}
	

	@Override
	public void validate(final Request<Chimpum> request, final Chimpum entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		
		if (entity.getFinishDate() != null && entity.getStartDate() != null && entity.getStartDate().after(entity.getFinishDate())) {
			errors.state(request, false, "startDate", "inventor.message.form.error.date");
		}

		if (entity.getFinishDate() != null && entity.getStartDate() != null && entity.getFinishDate().before(entity.getStartDate())) {
			errors.state(request, false, "finishDate", "inventor.message.form.error.date2");
		}
		
		Calendar calendar;

		calendar = Calendar.getInstance();
		calendar.setTime(entity.getStartDate());
		calendar.add(Calendar.MONTH, -1);
		
		if(calendar.getTime().before(entity.getCreationMoment())) {
			errors.state(request, false, "startDate", "inventor.message.form.error.startDate");
		}
		if(entity.getStartDate()!=null & entity.getFinishDate()!=null) {
			final long diff = (entity.getFinishDate().getTime()-entity.getStartDate().getTime())/1000/60/60/24;

			if(entity.getStartDate().before(entity.getFinishDate()) && diff<7) {
				errors.state(request, false, "finishDate", "inventor.message.form.error.finishDate");
			}
			
		}
		
		final Configuration c = this.adminRepository.findConfiguration();
		
		if(entity.getBudget().getAmount()<=0) {
			errors.state(request, false, "budget", "inventor.messages.form.error.budget.ammount");
		}
		
		if(!c.getAcceptedCurr().contains(entity.getBudget().getCurrency())) {
			errors.state(request, false, "budget", "inventor.messages.form.error.budget.currency");
		}
		
		final List<Item> items = this.repository.findItemsWithChimpum();
		for(final Item i:items) {
			if(i.getId()==entity.getArtefact().getId()) {
				errors.state(request, false, "title", "inventor.messages.form.error.duplicate");
			}
		}
		
		

		
	}

	@Override
	public void create(final Request<Chimpum> request, final Chimpum entity) {
		assert request != null;
		assert entity != null;
		
		final Item i = this.repository.findItemById(request.getModel().getInteger("id"));
		entity.setArtefact(i);
		this.repository.save(entity);
		
	}

}
