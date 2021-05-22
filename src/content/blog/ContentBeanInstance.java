package content.blog;

import java.util.Map;

//import iweb2.ch5.ontology.core.BaseConcept;
//import iweb2.ch5.ontology.core.StringAttribute;
//import iweb2.ch5.usecase.email.data.Email;
import bean.ContentBean;
import nbc.Content;
import ontology.Concept;
import ontology.core.BaseConcept;
import ontology.core.BaseInstance;
import ontology.core.StringAttribute;


public class ContentBeanInstance extends BaseInstance{
	private static int DEFAULT_TOP_N_TERMS = 10;
    
    private int id;
    
    public ContentBeanInstance(String contentCategory, ContentBean contentBean) {
    	this(contentCategory, contentBean, DEFAULT_TOP_N_TERMS);
    }

	public ContentBeanInstance(String contentCategory, ContentBean contentBean, int topNTerms) {
		// TODO Auto-generated constructor stub
		super();
		this.id = contentBean.getId();
		//content category is our concept class
		this.setConcept(new BaseConcept(contentCategory));
		
		//extract top N terms from content category and title (split)
		String text = contentBean.getTitle()+" "+contentBean.getBody();
		Content content = new Content(contentBean.getId(), text, topNTerms);
		Map<String, Integer> tfMap = content.getTFMap();
		
		attributes = new StringAttribute[1];
		
		String attrName ="Content_Text_Attribut";
		String attrValue = "";
		for (Map.Entry<String, Integer> tfEntry : tfMap.entrySet()) {
			attrValue = attrValue + " " + tfEntry.getKey();
		}
		attributes[0] = new StringAttribute(attrName, attrValue);
	}
 
}
