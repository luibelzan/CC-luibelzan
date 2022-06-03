package acme.features.administrator.dashboard;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.item.ItemType;
import acme.enums.Status;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AdministratorDashboardRepository extends AbstractRepository {

	@Query("SELECT COUNT(item) FROM Item item WHERE item.type = :type")
	Integer getItemTotalsByType(ItemType type);
	
	@Query("SELECT COUNT(patronage) FROM Patronage patronage WHERE patronage.status = :status")
	Integer getPatronageTotalsByStatus(Status status);
	
	@Query("SELECT MIN(patronage.budget.amount), MAX(patronage.budget.amount), AVG(patronage.budget.amount), STDDEV(patronage.budget.amount) from Patronage patronage WHERE patronage.status = :status")
	String getPatronageBudgetByStatus(Status status);
	
	@Query("SELECT item.retailPrice.currency, MIN(item.retailPrice.amount), MAX(item.retailPrice.amount), AVG(item.retailPrice.amount), STDDEV(item.retailPrice.amount) from Item item WHERE item.type = :type GROUP BY item.retailPrice.currency")
	List<String> getItemsByType(ItemType type);
	
	@Query("SELECT item.retailPrice.currency, item.technology, MIN(item.retailPrice.amount), MAX(item.retailPrice.amount), AVG(item.retailPrice.amount), STDDEV(item.retailPrice.amount) from Item item WHERE item.type = acme.entities.item.ItemType.COMPONENT GROUP BY item.retailPrice.currency, item.technology")
	List<String> getComponentsInCurrencies();
	
	@Query("SELECT COUNT(c), COUNT(i) from Item i LEFT JOIN Chimpum c ON c.artefact.id = i.id")
	String getArtefactsWithChimpumRatio();
	
	@Query("SELECT c.budget.currency, MIN(c.budget.amount), MAX(c.budget.amount), AVG(c.budget.amount), STDDEV(c.budget.amount) from Chimpum c GROUP BY c.budget.currency")
	List<String> getChimpumByCurrency();
}
