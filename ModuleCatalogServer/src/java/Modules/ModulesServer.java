/**
 * This class is used to create a REST application server to add, delete and
 * edit Modules and their information
 * @author Ayden Ballard - 905438
 * 
 * Response to feedback: 
 * I have responded to feedback from my last coursework by correcting my 
 * parallel read operations by synchronising the clone operations. 
 */
package Modules;

import java.util.HashMap;
import javax.ejb.Singleton;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

/**
 * REST Web Service
 * @author ayden
 */
@Path("Modules")
@Singleton
public class ModulesServer {

    @Context
    private UriInfo context;
    private HashMap<Integer,JsonObject> modules = new HashMap<>();
    
    
    /**
     * Creates a new instance of ModulesServer
     */
    public ModulesServer() {
    }
    
    /**
     * Method to create a new module
     * @param newModule - JsonObject, module to be added
     */
    @POST
    @Consumes("application/json")
    public synchronized String addModule(JsonObject newModule){
        HashMap<Integer,JsonObject> modulesClone =
            (HashMap<Integer,JsonObject>) modules.clone();
        for(JsonObject m : modulesClone.values()){
            if(m.getString("module").equals(newModule.getString("module"))){
                return "failure - module with the name " + 
                    newModule.getString("module") + 
                    "already exists with that name";
            }
        }
        modules.put(newModule.hashCode(),newModule);
        return "success - " + newModule.getString("module") + 
            " was added successfully";
    }   
    
    /**
     * Method to get all current existing modules
     * @return String - information about each existing module
     */
    @GET
    @Produces("text/plain")
    public synchronized String getAllModules(@DefaultValue("") @QueryParam("discontinued")
        String discontinued){
        String output = "";
        HashMap<Integer,JsonObject> modulesClone =
                (HashMap<Integer,JsonObject>) modules.clone();
        if(discontinued.equals("yes") || discontinued.equals("no")){ 
            Boolean discontinuedBool = true;
            if(discontinued.equals("no")){ //if query is no
                discontinuedBool = false;
            }
            for(JsonObject m : modulesClone.values()){
                if(m.getBoolean("discontinued") == discontinuedBool){
                    output += moduleToString(m) + "\n";
                }        
            }
        }
        else{ //if no query or something else was parsed in
            for(JsonObject m : modulesClone.values()){
                output += moduleToString(m) + "\n";
            }
        }
        return output;
    }
    
    /**
     * Method to get all modules of a particular subject
     * @param subject - subject to find the modules of
     * @return String - information about each existing module of the subject
     */
    @GET
    @Path("/{subject}")
    @Produces("text/plain")
        public synchronized String getModuleBySubject(@PathParam("subject") String subject,
        @DefaultValue("") @QueryParam("discontinued") String discontinued){
        String output = "";
        HashMap<Integer,JsonObject> modulesClone =
                (HashMap<Integer,JsonObject>) modules.clone();
        if(discontinued.equals("yes") || discontinued.equals("no")){
            boolean discontinuedBool = true;
            if(discontinued.equals("no")){
                discontinuedBool = false;
            }
            for(JsonObject m : modulesClone.values()){
                if(m.getJsonString("subject").getString().equals(subject) &&
                        m.getBoolean("discontinued") == discontinuedBool){
                    output += moduleToString(m) + "\n";
                }
            }
        }
        else{ //if no query or something else was parsed in
            for(JsonObject m : modulesClone.values()){
                if(m.getJsonString("subject").getString().equals(subject)){
                    output += moduleToString(m) + "\n";
                }
            }
        }
        return output;
    }
    
    /**
     * Method to get all modules of a particular subject and level, also
     * takes a query parameter to filter out discontinued ("yes") or 
     * not-discontinued ("no") modules. If no query parameter is parsed in then
     * by default it will load both discontinued and not-discontinued modules
     * @param subject - subject to find the modules of
     * @param level - level of modules to search for
     * @param discontinued - the discontinued status of the modules to search for
     * @return String - information about each existing module of the subject
     *                  and level
     */
    @GET
    @Path("/{subject}/{level}")
    @Produces("text/plain")
    public synchronized String getModuleByLevel(@PathParam("subject") String subject,
            @PathParam("level") int level,
            @DefaultValue("")@QueryParam("discontinued") String discontinued){
        String output = "";
        Boolean discontinuedBool;
        HashMap<Integer,JsonObject> modulesClone =
                (HashMap<Integer,JsonObject>) modules.clone();
        //if the discontinued status is yes or no
        if(discontinued.equals("yes") || discontinued.equals("no")){
            discontinuedBool = true;
            if(discontinued.equals("no")){ 
                discontinuedBool = false;
            }
            for(JsonObject m : modulesClone.values()){
                if(m.getJsonString("subject").getString().equals(subject) &&
                        m.getInt("level") == level && 
                        m.getBoolean("discontinued") == discontinuedBool){
                    output += moduleToString(m) + "\n";
                }
            }
        }
        else{ //if no query or something else was parsed in
            for(JsonObject m : modulesClone.values()){
                if(m.getJsonString("subject").getString().equals(subject) &&
                        m.getInt("level") == level){
                    output += moduleToString(m) + "\n";
                }
            }
        }
        return output;
    }
    
    /**
     * Method to delete a module 
     * @param module - String, name of the module to delete
     */
    @DELETE
    @Path("{module}")
    public synchronized boolean deleteModule(@PathParam("module") String module){
        HashMap<Integer,JsonObject> modulesClone =
                (HashMap<Integer,JsonObject>) modules.clone();
        for(JsonObject m : modulesClone.values()){
            if(m.getJsonString("module").getString().equals(module)){
                modules.remove(m);
                return true;
            }
        }
        return false;
    }
    
    /**
     * Method to toggle discontinued status of a module, if it is
     * currently discontinued then it will become discontinued and vice versa
     * @param module - String, name of the module to be toggled
     * @return String - states if the module was successfully toggled
     */
    @PUT
    @Consumes("text/plain")
    @Produces("text/plain")
    public synchronized String toggleDiscontinued(String module){
            for(JsonObject m : modules.values()){
                if(m.getJsonString("module").getString().equals(module)){ 
                    //creating the new module to replace the old one
                    JsonObjectBuilder toReplace = Json.createObjectBuilder()
                        .add("subject",m.getString("subject"))
                        .add("module",m.getString("module"))
                        .add("level",m.getInt("level"))
                        .add("discontinued",!m.getBoolean("discontinued"));
                    //replacing the module
                    modules.remove(m.hashCode());
                    JsonObject newModule = toReplace.build();
                    modules.put(newModule.hashCode(),newModule);
                    return module + " discontinued status is now " 
                            + !m.getBoolean("discontinued");
                }
            }
        return module + "'s discontinued status was not successfully changed";
    }
    
    /**
     * Method to produce a string containing the information about a module
     * @param module - module to collect information about
     * @return String - information about the module
     */
    private String moduleToString(JsonObject module){
        return  "Subject: " + module.getJsonString("subject").getString() + 
                        "\nName: " + module.getJsonString("module").getString() +
                        "\nLevel: " + module.getInt("level") + 
                        "\nDiscontinued: " + module.getBoolean("discontinued") + "\n";
    }
}
