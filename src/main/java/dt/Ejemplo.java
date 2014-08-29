/**
 *implementando para nuestro ejemplo de noticias el clasificador
 *donde tomaremos de todos nuestros usuarios las preferencias de 
 *categorias, las categorias que no les gusta como falso y las que eleigieron como true
 */

package dt;
import java.util.*;
class Ejemplos 
{
  class Ejemplo {
    private Map<String, String> values;
    private boolean classifier;
    public Ejemplo(String[] attributeNames, String[] attributeValues,boolean classifier) 
    {
      assert(attributeNames.length == attributeValues.length);
      values = new HashMap<String, String>();
      for ( int i = 0 ; i < attributeNames.length ; i++ ) {
        values.put(attributeNames[i], attributeValues[i]);
      }
      this.classifier = classifier;
    }

    public Ejemplo(Map<String, String> attributes, boolean classifier)
    {
      this.classifier = classifier;
      this.values = attributes;
    }

    public Set<String> getAttributes() {
      return values.keySet();
    }

    public String getAttributeValue(String attribute) {
      return values.get(attribute);
    }

    public boolean matchesClass(boolean classifier) {
      return classifier == this.classifier;
    }
  }

  private List<Ejemplo> examples;

  public Ejemplos() {
    examples = new LinkedList<Ejemplo>();
  }
  
  public void add(String[] attributeNames, String[] attributeValues,
                  boolean classifier) {
    examples.add(new Ejemplo(attributeNames, attributeValues, classifier));
  }

  public void add(Map<String, String> attributes, boolean classifier) {
    examples.add(new Ejemplo(attributes, classifier));
  }

  int countDecisions(String attribute, String decision) {
    int count = 0;

    for ( Ejemplo e : examples ) {
      if ( e.getAttributeValue(attribute).equals(decision) )
        count++;
    }

    return count;
  }
// extraemos las desiciones
  public Map<String, Set<String> > extractDecisions() 
  {
    Map<String, Set<String> > decisions = new HashMap<String, Set<String> >();

    for ( String attribute : extractAttributes() ) {
      decisions.put(attribute, extractDecisions(attribute));
    }

    return decisions;
  }

  public int countNegative(String attribute, String decision,
                           Map<String, String> attributes) {
    return countClassifier(false, attribute, decision, attributes);
  }
  
  public int countPositive(String attribute, String decision,
                           Map<String, String> attributes) {
    return countClassifier(true, attribute, decision, attributes);
  }
  
  public int countNegative(Map<String, String> attributes) {
    return countClassifier(false, attributes);
  }
  
  public int countPositive(Map<String, String> attributes) {
    return countClassifier(true, attributes);
  }
  
  public int count(String attribute, String decision, Map<String, String> attributes) {
    attributes = new HashMap(attributes);
    attributes.put(attribute, decision);

    return count(attributes);
  }
  
  public int count(Map<String, String> attributes) {
    int count = 0;
    nextExample:
    	for ( Ejemplo e : examples ) {
    		for ( Map.Entry<String, String> attribute : attributes.entrySet() )
    			if ( !(e.getAttributeValue(attribute.getKey()).equals(attribute.getValue())) )
    				continue nextExample;
      // hacemos que todos los atributos coincida  con nuestros ejemplos
      count++;
    }

    return count;
  }
  
  public int countClassifier(boolean classifier, Map<String, String> attributes) {
    int count = 0;
    nextExample:
    	for ( Ejemplo e : examples ) {
    		for ( Map.Entry<String, String> attribute : attributes.entrySet() )
    			if ( !(e.getAttributeValue(attribute.getKey()).equals(attribute.getValue())) )
    				continue nextExample;
      if ( e.matchesClass(classifier) )
        count++;
    }

    return count;
  }

  public int countClassifier(boolean classifier, String attribute,String decision, Map<String, String> attributes) 
  {
    attributes = new HashMap(attributes);
    attributes.put(attribute, decision);
    return countClassifier(classifier, attributes);
  }
  
  /**
   * retornamos el numero de ejemplos.
   */
  public int count() {
    return examples.size();
  }

  /**
   * retornamos los atributos
   */
  public Set<String> extractAttributes()
  {
    Set<String> attributes = new HashSet<String>();

    for ( Ejemplo e : examples ) {
      attributes.addAll(e.getAttributes());
    }

    return attributes;
  }
// recuperamos las desiciones
  private Set<String> extractDecisions(String attribute) {
    Set<String> decisions = new HashSet<String>();
    for ( Ejemplo e : examples ) 
    {
      decisions.add(e.getAttributeValue(attribute));
    }

    return decisions;
  }
}
