/**
 * This class is used to perform a test on the Module Server by adding
 * example modules and performing operations on the modules.
 * @author Ayden Ballard - 905438
 */
package Modules;

/**
 *
 * @author ayden
 */
public class Main {
    
    public static void main(String[] args){
        
        ModuleCatalogClient client = new ModuleCatalogClient();
        
        //making example modules
        Module advancedProgramming = new Module("compsci",
                "advanced programming",3);
        Module lowLevelProgramming = new Module("compsci",
                "low level prgoramming",2);
        Module databases = new Module("compsci","databases",2);
        Module concurrency = new Module("compsci","concurrency",2);
        Module programming = new Module("compsci","programming",1);
        Module coldWar = new Module("history","Cold war",2);
        Module frenchRev = new Module("history","french revolution",1);
        Module atoms = new Module("chemistry","atoms",1);
        
        //adding the modules to the server
        System.out.println(client.addModule(concurrency));
        System.out.println(client.addModule(lowLevelProgramming));
        System.out.println(client.addModule(databases));
        System.out.println(client.addModule(advancedProgramming));
        System.out.println(client.addModule(programming));
        System.out.println(client.addModule(coldWar));
        System.out.println(client.addModule(frenchRev));
        System.out.println(client.addModule(atoms));
        
        //attempting to add a module that already exists
        System.out.println("\ntrying to add a module that already exists");
        System.out.println(client.addModule(concurrency));
        
        //printing all modules
        System.out.println("\nPrinting all Modules");
        System.out.println(client.getAllModules(""));
        
        //printing all history modules
        System.out.println("Printing All history modules");
        System.out.println(client.getModuleBySubject("history", ""));
        
        //printing all level 2 comp sci modules
        System.out.println("Printing All level 2 compsci modules");
        System.out.println(client.getModuleByLevel("compsci", "2", ""));
        
        //deleting database module in compsci 
        System.out.println("Deleting databases module from compsci");
        if(client.deleteModule(boolean.class, "databases")){
            System.out.println("databases successfully deleted\n");
        } else{
            System.out.println("databases was not deleted succesfully\n");
        }
        
        //setting concurrency and programming in compsci to discontinued
        System.out.println("Setting concurrency to discontinued");
        System.out.println("Setting programming to discontinued");
        System.out.println(client.toggleDiscontinued("concurrency"));
        System.out.println(client.toggleDiscontinued("programming"));
        
        //priting all discontinued modules 
        System.out.println("\nPrinting all discontinued modules");
        System.out.println(client.getAllModules("yes"));
        
        //setting concurrency and programming in compsci to  not discontinued
        System.out.println("Setting concurrency to not discontinued");
        System.out.println("Setting programming to not discontinued");
        System.out.println(client.toggleDiscontinued("concurrency"));
        System.out.println(client.toggleDiscontinued("programming"));
        
        //attempting to toggle the discontinued value of a non-existing module
        System.out.println("\nAttempting to change the discontinued " + 
                " status of a non-existing module");
        System.out.println(client.toggleDiscontinued("Marine Invertebrates"));
    }
    
}
