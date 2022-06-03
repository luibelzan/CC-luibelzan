package acme.testing.inventor.chimpum;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class InventorChimpumDeleteTest extends TestHarness{

	@ParameterizedTest
	@CsvFileSource(resources = "/inventor/chimpum/delete-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void positiveTest(final int recordIndex, final String code) {
		super.signIn("administrator", "administrator");
		
		super.clickOnMenu("Inventor", "List Own Tools");
		super.checkListingExists();
		
		super.clickOnListingRecord(0);
		super.clickOnButton("List associated chimpum");
		super.checkListingExists();
		super.clickOnListingRecord(0);
		super.checkFormExists();
		
		super.clickOnSubmit("Delete");
		
		super.clickOnMenu("Inventor", "List Own Tools");
		super.checkListingExists();
		
		super.clickOnListingRecord(0);
		super.clickOnButton("List associated chimpum");
		super.checkListingEmpty();
		
	}
}
