import java.util.*;

public class Tuple {

  private ArrayList<String> attributes;
  private ArrayList<String> domains;
  private ArrayList<Comparable> tuple;

  // METHODS
  
  // Constructor; 
  public Tuple (ArrayList<String> attr, ArrayList<String> dom) {
    this.attributes = attr;
    this.domains = dom; 
    this.tuple = new ArrayList<Comparable>();
  }

  // Add String s at the end of the tuple
  public void addStringComponent(String s) {
    this.tuple.add(s);
  }

  // Add Double d at the end of the tuple
  public void addDoubleComponent(Double d) {
    this.tuple.add(d);
  }

  // Add Integer i at the end of the tuple
  public void addIntegerComponent(Integer i) {
    this.tuple.add(i);
  }

  // return String representation of tuple; See README for formatting
  public String toString() {
    String returnValue = "";
    for(int i = 0 ; i < this.tuple.size(); ++i)
    {
      returnValue += this.tuple.get(i);
      returnValue += ":";
    }
    return returnValue; 
  }
  
  // Returns true if Tuple compareTuple is equivalent to this tuple
  public boolean equals(Tuple compareTuple)
  {
    //Check domains size
      if(this.domains.size() != compareTuple.domains.size())
      {
        return false; 
      }

    //Check domains type
    for(int i = 0 ; i < this.domains.size(); ++i)
    {
      if(this.domains.get(i) != compareTuple.domains.get(i))
      {
        return false; 
      }
    }

    //Check content
    for(int i = 0 ; i < this.tuple.size() ; ++i)
    {
      if(!(this.tuple.get(i).compareTo(compareTuple.tuple.get(i)) == 0))
      {
        return false; 
      }
    }
    return true;
  }

  // Returns clone of this Tuple with Attributes attr
  public Tuple clone(ArrayList<String> attr)
  {
    ArrayList<String> newDomains = new ArrayList<String>();
    for(int i = 0; i < this.domains.size() ; ++i)
    {
      newDomains.add(this.domains.get(i));
    }

    Tuple newTuple = new Tuple(attr, newDomains);
    for(int i = 0 ; i < this.tuple.size(); ++i)
    {
      if(this.domains.get(i).equals("VARCHAR"))
      {
        newTuple.addStringComponent((String)this.tuple.get(i));
        
      }else if(this.domains.get(i).equals("INTEGER"))
      {
        newTuple.addIntegerComponent((Integer) this.tuple.get(i));

      }else if(this.domains.get(i).equals("DECIMAL"))
      {
        newTuple.addDoubleComponent((Double) this.tuple.get(i));
      }
    }
    return newTuple; 
    
  }
  

};