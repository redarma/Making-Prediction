/*
 * implementacion del algorimo id3
 * */

package dt;
import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ID3Algorithm implements Algorithm {
  final Logger logger = LoggerFactory.getLogger(ID3Algorithm.class);
  private Ejemplos examples;

  public ID3Algorithm(Ejemplos examples) {
    this.examples = examples;
  }
  public Attribute nextAttribute(Map<String, String> chosenAttributes, Set<String> usedAttributes) {
    double currentGain = 0.0, bestGain = 0.0;
    String bestAttribute = "";

    /*
     * Si no hay valores positivos para los atributos ya elegidos, 
      * regresamos tupla false en el clasificador. Si no hay ejemplos negativos, 
      * A continuación, devolver una tupla verdadera al clasificador.
     */
    if ( examples.countPositive(chosenAttributes) == 0 )
      return new Attribute(false);
    else if ( examples.countNegative(chosenAttributes) == 0 )
      return new Attribute(true);
    logger.debug("Eleccion de atributo {} Atributos faltantes.",
                remainingAttributes(usedAttributes).size());
    logger.debug("Existe atributo elegido/las desiciones son {}.", chosenAttributes);

    for ( String attribute : remainingAttributes(usedAttributes) ) {
      // para los atributos restantes, determinamos la ganancia 
      // elegmos los atributos 
      // 
      currentGain = informationGain(attribute, chosenAttributes);
      logger.debug("Evaluando informacion {}, la ganancia es {}",
                  attribute, currentGain);
      if ( currentGain > bestGain ) {
        bestAttribute = attribute;
        bestGain = currentGain;
      }
    }


    if ( bestGain == 0.0 ) {
      boolean classifier = examples.countPositive(chosenAttributes) > 0;
      logger.debug("creamos nueva hoja con los resultados del clasificador{}.", classifier);
      return new Attribute(classifier);
    } else {
      logger.debug("creamos un nuevo atributo no hoja  {}.", bestAttribute);
      return new Attribute(bestAttribute);
    }
  }

  private Set<String> remainingAttributes(Set<String> usedAttributes) {
    Set<String> result = examples.extractAttributes();
    result.removeAll(usedAttributes);
    return result;
  }

  // calculo de entropias
  private double entropy(Map<String, String> specifiedAttributes) {
    double totalExamples = examples.count();
    double positiveExamples = examples.countPositive(specifiedAttributes);
    double negativeExamples = examples.countNegative(specifiedAttributes);

    return -nlog2(positiveExamples / totalExamples) - 
            nlog2(negativeExamples / totalExamples);
  }
  // calculo de entropias

  private double entropy(String attribute, String decision, Map<String, String> specifiedAttributes) {
    double totalExamples = examples.count(attribute, decision, specifiedAttributes);
    double positiveExamples = examples.countPositive(attribute, decision, specifiedAttributes);
    double negativeExamples = examples.countNegative(attribute, decision, specifiedAttributes);

    return -nlog2(positiveExamples / totalExamples) - 
            nlog2(negativeExamples / totalExamples);
  }
//calculo de la ganancia
  private double informationGain(String attribute, Map<String, String> specifiedAttributes) 
  {
    double sum = entropy(specifiedAttributes);
    double examplesCount = examples.count(specifiedAttributes);

    if ( examplesCount == 0 )
      return sum;

    Map<String, Set<String> > decisions = examples.extractDecisions();

    for ( String decision : decisions.get(attribute) ) {
      double entropyPart = entropy(attribute, decision, specifiedAttributes);
      double decisionCount = examples.countDecisions(attribute, decision);

      sum += -(decisionCount / examplesCount) * entropyPart;
    }

    return sum;
  }

  // caluclo de logaritmo en base 2
  private double nlog2(double value) {
    if ( value == 0 )
      return 0;

    return value * Math.log(value) / Math.log(2);
  }
}
