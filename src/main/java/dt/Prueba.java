package dt;
import org.rest.news.NewsDAO;
import org.rest.news.NewsDAOTest;
import org.rest.news.Preferencias;

import java.beans.DesignMode;
import java.util.*;

import dt.*;

public class Prueba {
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
    		  		makeOne().addExample(   new String[]{p.preferencia1,    p.preferencia2,p.preferencia3,p.preferencia4}, true);
				}
    		  return makeOne();
    } catch ( UnknownDecisionException e ) 
    {
      return makeOne();
    }
  }

  public static void main(String args[]) {
	  Prueba pr= new Prueba();
	  pr.makeOutlookTree();
  }
}
