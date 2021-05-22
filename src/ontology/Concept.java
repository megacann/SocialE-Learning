/**
 * 
 */
package ontology;

//import iweb2.ch5.ontology.intf.Instance;

/**
 * @author babies
 *
 */
public interface Concept {

	public String getName();
	
	public Concept getParent();
	
	public Instance[] getInstances();
}
