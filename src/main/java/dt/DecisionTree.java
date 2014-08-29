/**
 *
 */

package dt;

import java.util.*;

public class DecisionTree {

  private LinkedHashSet<String> attributes;
  private Map<String, Set<String> > decisions;
  private boolean decisionsSpecified;
  private Ejemplos examples;
  private boolean compiled;
  private Attribute rootAttribute;
  private Algorithm algorithm;
  public DecisionTree() 
  {
    algorithm = null;
    examples = new Ejemplos();
    attributes = new LinkedHashSet<String>();
    decisions = new HashMap<String, Set<String> >();
    decisionsSpecified = false;
  }

  private void setDefaultAlgorithm() {
    if ( algorithm == null )
      setAlgorithm(new ID3Algorithm(examples));
  }

  public void setAlgorithm(Algorithm algorithm) 
  {
    this.algorithm = algorithm;
  }
// modificamos atributos
  
  public DecisionTree setAttributes(String[] attributeNames) {
    compiled = false;

    decisions.clear();
    decisionsSpecified = false;

    attributes.clear();

    for ( int i = 0 ; i < attributeNames.length ; i++ )
      attributes.add(attributeNames[i]);

    return this;
  }

  
  // modificamos desiciones
  public DecisionTree setDecisions(String attributeName, String[] decisions) 
  {
    if ( !attributes.contains(attributeName) ) 
    {
      // TODO 
      return this;
    }

    compiled = false;
    decisionsSpecified = true;
    Set<String> decisionsSet = new HashSet<String>();
    for ( int i = 0 ; i < decisions.length ; i++ )
      decisionsSet.add(decisions[i]);
    this.decisions.put(attributeName, decisionsSet);
    return this;
  }

  /**
   * adicionando patrones de desicion
   */
  public DecisionTree addExample(String[] attributeValues, boolean classification) throws UnknownDecisionException
  {
    String[] attributes = this.attributes.toArray(new String[0]);

    if ( decisionsSpecified )
      for ( int i = 0 ; i < attributeValues.length ; i++ )
        if ( !decisions.get(attributes[i]).contains(attributeValues[i]) ) {
          throw new UnknownDecisionException(attributes[i], attributeValues[i]);
        }
    compiled = false;
    examples.add(attributes, attributeValues, classification);
    return this;
  }

  public DecisionTree addExample(Map<String, String> attributes, boolean classification) throws UnknownDecisionException {
    compiled = false;
    examples.add(attributes, classification);
    return this;
  }

  public boolean apply(Map<String, String> data) throws BadDecisionException {
    compile();
    return rootAttribute.apply(data);
  }

  private Attribute compileWalk(Attribute current, Map<String, String> chosenAttributes, Set<String> usedAttributes) {
    // si el atributo actual es una hoja, por lo tanto no es una desicion
    // buscamos siguientes atributos
    if ( current.isLeaf() )
      return current;
    // recuperamos las desiciones para las siguientes atributos(from this.decisions)
    String attributeName = current.getName();
    // retiramos el atributo para siguientes desiciones
    usedAttributes.add(attributeName);
    for ( String decisionName : decisions.get(attributeName) ) {
      // sobre escribimos la desicion de los atributos
      chosenAttributes.put(attributeName, decisionName);

      // buscamos el siguiente atributo a buscar
      current.addDecision(decisionName, compileWalk(algorithm.nextAttribute(chosenAttributes, usedAttributes), chosenAttributes, usedAttributes));
    }
    // luego retiramos el atributo
    chosenAttributes.remove(attributeName);
    // retorna el sub arbol de desicion
    return current;
  }

// corremos nuestro arbol
  public void compile() 
  {
    if ( compiled )
      return;
    setDefaultAlgorithm();
    Map<String, String> chosenAttributes = new HashMap<String, String>();
    Set<String> usedAttributes = new HashSet<String>();
    if ( !decisionsSpecified )
      decisions = examples.extractDecisions();
    rootAttribute = compileWalk(algorithm.nextAttribute(chosenAttributes, usedAttributes), chosenAttributes, usedAttributes);
    compiled = true;
  }

  public String toString() {
    compile();

    if ( rootAttribute != null )
      return rootAttribute.toString();
    else
      return "";
  }

  public Attribute getRoot() {
    return rootAttribute;
  }
}
