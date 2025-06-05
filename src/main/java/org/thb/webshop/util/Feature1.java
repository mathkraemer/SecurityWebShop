public class Feature1 {

  // Converter function
  public String convertToString(Object o){
    if (o != null)
      return o.toString();
    else
      return "no value";
  }

  // This is an alternative converter function
    public String convertToString2(Object o){
    if (o == null)
      return o.toString();
    else
      return "no value";
  }

    // Lets fix this
    public String convertToString3(Object o){
    if (o != null)
      return o.toString();
    else
      return "no value";
  }
  
}
