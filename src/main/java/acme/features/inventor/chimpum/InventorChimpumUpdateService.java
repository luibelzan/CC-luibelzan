package acme.features.inventor.chimpum;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.chimpum.Chimpum;
import acme.entities.configuration.Configuration;
import acme.entities.item.Status;
import acme.features.administrator.configurations.AdministratorConfigurationRepository;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractUpdateService;
import acme.roles.Inventor;

@Service
public class InventorChimpumUpdateService implements AbstractUpdateService<Inventor, Chimpum>{

	@Autowired
	protected InventorChimpumRepository repository;
	
	@Autowired
	protected AdministratorConfigurationRepository adminRepository;
	
	@Override
	public boolean authorise(final Request<Chimpum> request) {
		assert request != null;
		
		final int id = request.getModel().getInteger("id");
		final Chimpum c = this.repository.findChimpumById(id);
		final Inventor inv = this.repository.findInventorByUserAccountId(request.getPrincipal().getAccountId());
		return c.getArtefact().getInventor().getId()==inv.getId() && c.getArtefact().getStatus().equals(Status.NON_PUBLISHED); //&& c.getArtefact().getStatus().equals(ItemType.TOOL); 
	}

	@Override
	public void bind(final Request<Chimpum> request, final Chimpum entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		
		request.bind(entity, errors, "code", "title", "description", "startDate", "finishDate", "budget", "link");
		
	}

	@Override
	public void unbind(final Request<Chimpum> request, final Chimpum entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		
		request.unbind(entity, model, "code", "title", "description", "startDate", "finishDate", "budget", "link", "creationMoment");
		
	}

	@Override
	public Chimpum findOne(final Request<Chimpum> request) {
		assert request != null;
		
		final int id = request.getModel().getInteger("id");
		final Chimpum res = this.repository.findChimpumById(id);
		
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
		
	}

	@Override
	public void update(final Request<Chimpum> request, final Chimpum entity) {
		assert request != null;
		assert entity != null;
		
		this.repository.save(entity);
		
	}

}
