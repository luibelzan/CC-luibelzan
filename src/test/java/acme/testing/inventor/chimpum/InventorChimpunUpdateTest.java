package acme.testing.inventor.chimpum;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class InventorChimpunUpdateTest extends TestHarness{

	@ParameterizedTest
	@CsvFileSource(resources = "/inventor/chimpum/update-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void positiveTest(final int recordIndex, final String code, final String title, final String description, final String startDate, final String finishDate, final String budget, final String link) {
		super.signIn("inventor1", "inventor1");
		
		super.clickOnMenu("Inventor", "List Own Tools");
		super.checkListingExists();
		
		super.clickOnListingRecord(1);
		super.clickOnButton("List associated chimpum");
		super.checkListingExists();
		super.clickOnListingRecord(0);
		
		super.fillInputBoxIn("code", code);
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("description", description);
		super.fillInputBoxIn("startDate", startDate);
		super.fillInputBoxIn("finishDate", finishDate);
		super.fillInputBoxIn("budget", budget);
		super.fillInputBoxIn("link", link);
		
		super.clickOnSubmit("Update");
		
		super.clickOnMenu("Inventor", "List Own Tools");
		super.checkListingExists();
		
		super.clickOnListingRecord(1);
		super.checkFormExists();
		
		super.clickOnButton("List associated chimpum");
		super.checkListingExists();
		
		super.checkColumnHasValue(recordIndex, 0, code);
		super.checkColumnHasValue(recordIndex, 1, title);
		super.checkColumnHasValue(recordIndex, 2, budget);
		
		super.signOut();
	}
	
	
	@ParameterizedTest
	@CsvFileSource(resources = "/inventor/chimpum/update-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void negativeTest(final int recordIndex, final String code, final String title, final String description, final String startDate, final String finishDate, final String budget, final String link) {
		super.signIn("inventor1", "inventor1");
		
		super.clickOnMenu("Inventor", "List Own Tools");
		super.checkListingExists();
		
		super.clickOnListingRecord(1);
		super.clickOnButton("List associated chimpum");
		super.checkListingExists();
		super.clickOnListingRecord(0);
		
		super.fillInputBoxIn("code", code);
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("description", description);
		super.fillInputBoxIn("startDate", startDate);
		super.fillInputBoxIn("finishDate", finishDate);
		super.fillInputBoxIn("budget", budget);
		super.fillInputBoxIn("link", link);
		
		super.clickOnSubmit("Update");
		super.checkErrorsExist();
	}
}
