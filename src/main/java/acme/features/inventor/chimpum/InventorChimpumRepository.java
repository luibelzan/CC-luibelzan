package acme.features.inventor.chimpum;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.chimpum.Chimpum;
import acme.entities.item.Item;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Inventor;

@Repository
public interface InventorChimpumRepository extends AbstractRepository{

	
	@Query("select c from Chimpum c where c.artefact.id = :id")
	List<Chimpum> findChimpumByItemId(int id);
	
	@Query("select c from Chimpum c where c.id = :id")
	Chimpum findChimpumById(int id);
	
	@Query("select i from Inventor i where i.userAccount.id = :id")
	Inventor findInventorByUserAccountId(int id);
	
	@Query("select i from Item i where i.id = :id")
	Item findItemById(int id);
	
	@Query("select c.artefact from Chimpum c")
	List<Item> findItemsWithChimpum();
}
