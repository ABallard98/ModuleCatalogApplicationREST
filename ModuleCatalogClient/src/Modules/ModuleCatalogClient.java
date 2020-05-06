package Modules;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;

/**
 * Jersey REST client generated for REST resource:ModulesServer [Modules]<br>
 * USAGE:
 * <pre>
 *        ModuleCatalogClient client = new ModuleCatalogClient();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * @author ayden
 */
public class ModuleCatalogClient {

    private WebTarget webTarget;
    private Client client;
    private static final String BASE_URI = "http://localhost:8080/ModuleCatalogServer/webresources";

    public ModuleCatalogClient() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("Modules");
    }

    public String getModuleBySubject(String subject, String discontinued) throws ClientErrorException {
        WebTarget resource = webTarget;
        if (discontinued != null) {
            resource = resource.queryParam("discontinued", discontinued);
        }
        resource = resource.path(java.text.MessageFormat.format("{0}", new Object[]{subject}));
        return resource.request(javax.ws.rs.core.MediaType.TEXT_PLAIN).get(String.class);
    }

    public String getAllModules(String discontinued) throws ClientErrorException {
        WebTarget resource = webTarget;
        if (discontinued != null) {
            resource = resource.queryParam("discontinued", discontinued);
        }
        return resource.request(javax.ws.rs.core.MediaType.TEXT_PLAIN).get(String.class);
    }

    public String toggleDiscontinued(Object requestEntity) throws ClientErrorException {
        return webTarget.request(javax.ws.rs.core.MediaType.TEXT_PLAIN).put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.TEXT_PLAIN), String.class);
    }

    public String getModuleByLevel(String subject, String level, String discontinued) throws ClientErrorException {
        WebTarget resource = webTarget;
        if (discontinued != null) {
            resource = resource.queryParam("discontinued", discontinued);
        }
        resource = resource.path(java.text.MessageFormat.format("{0}/{1}", new Object[]{subject, level}));
        return resource.request(javax.ws.rs.core.MediaType.TEXT_PLAIN).get(String.class);
    }

    public String addModule(Object requestEntity) throws ClientErrorException {
        return webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON), String.class);
    }

    public <T> T deleteModule(Class<T> responseType, String module) throws ClientErrorException {
        return webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{module})).request().delete(responseType);
    }

    public void close() {
        client.close();
    }
    
}
