/**
 * 
 */
package ontology;

//import iweb2.ch5.ontology.intf.Attribute;
//import iweb2.ch5.ontology.intf.Concept;

/**
 * @author babis
 *
 */
public interface Instance {

	public Attribute[] getAtrributes();
	
	public Concept getConcept();
	
	public void print();
	
	public Attribute getAttributeByName(String attrName); 
}
