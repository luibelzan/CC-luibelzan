package acme.testing.inventor.chimpum;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class InventorChimpumListTest extends TestHarness{
	
	@ParameterizedTest
	@CsvFileSource(resources = "/inventor/chimpum/list-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void InventorListChimpumTest(final int recordIndex, final String code, final String creationMoment, final String title, final String description, final String startDate, final String finishDate, final String budget, final String link) {
		
		super.signIn("administrator", "administrator");
		
		super.clickOnMenu("Inventor", "List Own Tools");
		
		super.checkListingExists();
		
		super.clickOnListingRecord(0);
		super.checkFormExists();
		
		super.clickOnButton("List associated chimpum");
		super.checkListingExists();
		
		super.checkColumnHasValue(recordIndex, 0, code);
		super.checkColumnHasValue(recordIndex, 1, title);
		super.checkColumnHasValue(recordIndex, 2, budget);
		super.checkColumnHasValue(recordIndex, 3, creationMoment);
		
		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		
		super.checkInputBoxHasValue("code", code);
		super.checkInputBoxHasValue("creationMoment", creationMoment);
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("description", description);
		super.checkInputBoxHasValue("startDate", startDate);
		super.checkInputBoxHasValue("finishDate", finishDate);
		super.checkInputBoxHasValue("budget", budget);
		super.checkInputBoxHasValue("link", link);
		
		super.signOut();
	}

}
