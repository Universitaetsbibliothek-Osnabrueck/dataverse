package edu.harvard.iq.dataverse.api;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import static javax.ws.rs.core.Response.Status.OK;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

/**
 * In order to execute this test code you must be configured with DataCite
 * credentials.
 */
public class PidsIT {

    @BeforeClass
    public static void setUpClass() {
        RestAssured.baseURI = UtilIT.getRestAssuredBaseUri();
    }

    @Ignore
    @Test
    public void testGetPid() {
        String pid = "";
        pid = "10.70122/FK2/9BXT5O"; // findable
        pid = "10.70122/FK2/DNEUDP"; // draft

        Response createUser = UtilIT.createRandomUser();
        createUser.prettyPrint();
        createUser.then().assertThat()
                .statusCode(OK.getStatusCode());
        String username = UtilIT.getUsernameFromResponse(createUser);
        String apiToken = UtilIT.getApiTokenFromResponse(createUser);

        UtilIT.makeSuperUser(username).then().assertThat().statusCode(OK.getStatusCode());

        Response getPid = UtilIT.getPid(pid, apiToken);
        getPid.prettyPrint();
    }

    @Test
    public void testGetUnreservedPids() {
        Response createUser = UtilIT.createRandomUser();
        createUser.prettyPrint();
        createUser.then().assertThat()
                .statusCode(OK.getStatusCode());
        String username = UtilIT.getUsernameFromResponse(createUser);
        String apiToken = UtilIT.getApiTokenFromResponse(createUser);

        UtilIT.makeSuperUser(username).then().assertThat().statusCode(OK.getStatusCode());

        Response unreserved = UtilIT.getUnreservedPids(apiToken);
        unreserved.prettyPrint();
    }

//    @Ignore
    @Test
    public void testReservePid() {
        String pid = "";
        pid = "doi:10.70122/FK2/OQ7LN4"; // 2086
        pid = "2086";
        pid = "4"; // doi:10.5072/FK2/HSS61L (id 4) changed to doi:10.70122/FK2/HSS61L
        pid = "6"; // doi:10.5072/FK2/ASDBZL (id 6) changed to doi:10.70122/FK2/ASDBZL
        pid = "19"; // doi:10.5072/FK2/0UQQS1 (id 19) changed to doi:10.70122/FK2/0UQQS1
        pid = "doi:10.70122/FK2/BPJ78V"; // 34
        pid = "doi:10.70122/FK2/EXMBWD"; // 39
        Response createUser = UtilIT.createRandomUser();
        createUser.prettyPrint();
        createUser.then().assertThat()
                .statusCode(OK.getStatusCode());
        String username = UtilIT.getUsernameFromResponse(createUser);
        String apiToken = UtilIT.getApiTokenFromResponse(createUser);

        UtilIT.makeSuperUser(username).then().assertThat().statusCode(OK.getStatusCode());

        Response reservePid = UtilIT.reservePid(pid, apiToken);
        reservePid.prettyPrint();
        // These are some errors seen along the way:
        /**
         * "message": "javax.ejb.EJBTransactionRolledbackException: Exception
         * thrown from bean: javax.ejb.EJBTransactionRolledbackException:
         * Exception thrown from bean: java.lang.RuntimeException: Response
         * code: 400, can't write unknown attribute `repository_id`"
         */
        /**
         * "message": "result: Problem calling createIdentifier:
         * javax.ejb.EJBTransactionRolledbackException: Exception thrown from
         * bean: javax.ejb.EJBTransactionRolledbackException: Exception thrown
         * from bean: java.lang.RuntimeException: Response code: 403, Access is
         * denied"
         */
    }

}