package test;

import manage.TestBase;
import org.testng.annotations.Test;

public class DraftTest extends TestBase {
    public DraftTest() {
        super();
    }

    @Test
    public void draftVerification()
    {
        signInAccount();
        goToGmail();
        goToDraftFolder();
        createDraft();
        updateDraft();
        singOut();
    }

}
