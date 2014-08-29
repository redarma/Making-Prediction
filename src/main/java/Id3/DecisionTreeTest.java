package Id3;

import org.junit.*;
import org.rest.news.NewsDAO;
import org.rest.news.NewsDAOTest;
import org.rest.news.Preferencias;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.*;

import dt.*;

public class DecisionTreeTest {
  private DecisionTree makeOne() {
    return new DecisionTree();
  }

  // definiremos las categorias de preferencia

  
  
  private DecisionTree makeOutlookTree() {
    try {

    	// adicionaremos dinamicamente las preferencias
    	
    	  List<String> usuarios= new ArrayList<String>();
    	  NewsDAO dao= new NewsDAO();
    	  usuarios= dao.listarUsuarios();
    	
        makeOne().setAttributes(new String[]{"categoria1",  "categoria2", "categoria3", "categoria4"});
    		  	for (String a : usuarios) 
    		  	{
    		  		Preferencias p = new Preferencias();
    		  		dao.preferencias(a);
    		  		makeOne().addExample(   new String[]{p.preferencia1,    p.preferencia2,         p.preferencia3,     p.preferencia4}, true);
				}
    		  return makeOne();
    } catch ( UnknownDecisionException e ) 
    {
      fail();
      return makeOne(); // this is here to shut up compiler.
    }
  }

  @Test (expected=UnknownDecisionException.class) public void testUnknownDecisionThrowsException() throws UnknownDecisionException {
    DecisionTree tree = makeOne().setAttributes(new String[]{"Outlook"})
                                 .setDecisions("Outlook", new String[]{"Sunny", "Overcast"});

    // this causes exception
    tree.addExample(new String[]{"Rain"}, false);
  }

  @Test public void testOutlookOvercastApplyReturnsTrue() throws BadDecisionException {
    Map<String, String> case1 = new HashMap<String, String>();
    case1.put("preferencia1", "true");
    case1.put("preferencia2", "true");
    case1.put("preferencia3", "true");
    case1.put("preferencia4", "true");
    assertTrue(makeOutlookTree().apply(case1));
  }

  @Test (expected=BadDecisionException.class) public void testOutlookRainInsufficientDataThrowsException() throws BadDecisionException {
    Map<String, String> case1 = new HashMap<String, String>();
    case1.put("Outlook", "Rain");
    case1.put("Temperature", "Mild");
    makeOutlookTree().apply(case1);
  }

  public void attributeIsUsedOnlyOnceInTree(Attribute node, List<String> attributes) {
    for ( Attribute child : node.getDecisions().values() ) {
      if ( !child.isLeaf() ) {
        assertFalse( attributes.contains(child.getName()) );
        attributes.add(child.getName());
        attributeIsUsedOnlyOnceInTree(child, attributes);
      }
    }
  }

  @Test public void testAttributeIsUsedOnlyOnceInTree() {
    DecisionTree tree = makeOutlookTree();
    tree.compile();

    List<String> attributeList = new LinkedList<String>();
    attributeList.add(tree.getRoot().getName());
    attributeIsUsedOnlyOnceInTree(tree.getRoot(), attributeList);
  }


  public static void main(String args[]) {
    org.junit.runner.JUnitCore.main("DecisionTreeTest");
  }
}
