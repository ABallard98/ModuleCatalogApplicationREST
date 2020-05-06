/**
 * This class is used to represent a module, each module has a parent subject,
 * the module name, the module level and the discontinued status of the module.
 * @author Ayden Ballard - 905438
 */
package Modules;

/**
 *
 * @author ayden
 */
public class Module {
    
    private String subject; //name of the subjec the module belongs to
    private String module; //name of the module
    private int level; //level of the module
    private boolean discontinued; //discontinued status of the module
    
    /**
     * Constructor for a module object
     * @param subject - subject name
     * @param module - module name
     * @param level - module level
     */
    public Module(String subject, String module, int level){
        setSubject(subject);
        setModule(module);
        setLevel(level);
        setDiscontinued(false); //set not discontinued by default
    }
    
    /**
     * Empty constructor for module object
     */
    public Module(){
    }
    
    /**
     * Method to set subject name of the module 
     * @param subject - subject name
     */
    public void setSubject(String subject){
        this.subject = subject;
    }

    /**
     * Method to set the module name
     * @param module - module name
     */
    public void setModule(String module){
        this.module = module;
    }
    
    /**
     * Method to set the level of the module
     * @param level - level of the module
     */
    public void setLevel(int level){
        this.level = level;
    }
    
    /**
     * Method to set the discontinued status of the module
     * @param discontinued - status of the module
     */
    public void setDiscontinued(boolean discontinued){
        this.discontinued = discontinued;
    }
    
    /**
     * Method to get the subject name of the module
     * @return String - subject name
     */
    public String getSubject(){
        return this.subject;
    }
    
    /**
     * Method to get the module name
     * @return String - module name
     */
    public String getModule(){
        return this.module;
    }
    
    /**
     * Method to get the level of the module
     * @return Integer - level of the module
     */
    public int getLevel(){
        return this.level;
    }
    
    /**
     * Method to get the discontinued status of the module
     * @return Boolean - true if discontinued, else false
     */
    public boolean getDiscontinued(){
        return this.discontinued;
    }
    
    /**
     * Method to get all the information about a module in a string
     * @return String - information about the module
     */
    public String toString(){
        return this.subject + ", " + this.module + ", " + this.level +
                ", " + this.discontinued;
    }
}
