import java.io.*;
import java.util.*;

public class Relation {
  // Name of the relation.
  private String name;

  // Attribute names for the relation
  private ArrayList<String> attributes;

  // Domain classes or types of attributes; possible values: INTEGER, DECIMAL, VARCHAR
  private ArrayList<String> domains;

  // Actual data storage (list of tuples) for the relation.
  private ArrayList<Tuple> table;

  // METHODS

  // Constructor
  public Relation (String name, ArrayList<String> attributes, ArrayList<String> dNames) {
      this.name = name;
      this.attributes = attributes;
      this.domains = dNames;
      this.table = new ArrayList<Tuple>();
  };

  // returns true if attribute with name aname exists in relation schema
  // false otherwise
  public boolean attributeExists(String aname) {
    for(int i = 0 ; i < attributes.size(); ++i)
    {
        if(attributes.get(i).equals(aname))
        {
          return true; 
        }
    }
    return false; 
  }

  // returns domain type of attribute aname; returns null if not present
  public String attributeType(String aname) {
    int index = -1000;
    for(int i = 0 ; i < this.attributes.size(); ++i)
    {
        if(this.attributes.get(i).equals(aname))
        {
            index = i;
        }
    }

    try{
        return this.domains.get(index);
    }catch(Exception e)
    {
        return null; 
    }
  }

  // Print relational schema to screen.
  public void displaySchema() {
    System.out.print(this.name + "(");
    for(int i = 0 ; i < this.attributes.size(); ++i)
    {
        if(i < this.attributes.size() - 1)
        {
            System.out.print(this.attributes.get(i) + ":" + this.domains.get(i) + ",");
        }else if( i == this.attributes.size() - 1)
        {
            System.out.print(this.attributes.get(i) + ":" + this.domains.get(i) + ")");
        }
    } 
    

  }

  // Set name of relation to rname
  public void setName(String rname) {
    this.name = rname;
  }

  // Add tuple tup to relation.
  public void addTuple(Tuple tup) {
      this.table.add(tup);

  }

  // Print relation to screen (see README for formatting)
  public void displayRelation() {
    this.displaySchema();
    System.out.println();
    System.out.println("Number of tuples: " + this.table.size());
    for(int i = 0 ; i < table.size(); ++i)
    {
      System.out.println(table.get(i).toString());
    }
    System.out.println();

  }
  // Return String version of relation; See README for formatting
  public String toString() {
    String returnValue = "";
    returnValue = returnValue + this.name + "(";
    for(int i = 0 ; i < this.attributes.size(); ++i)
    {
        if(i < this.attributes.size() - 1)
        {
            returnValue = returnValue + this.attributes.get(i) + ":" + this.domains.get(i) + ",";
        }else if( i == this.attributes.size() - 1)
        {
            returnValue = returnValue + this.attributes.get(i) + ":" + this.domains.get(i);
        }
    }
    returnValue += ")\n";
    returnValue = returnValue + "Number of tuples: " + table.size() + "\n\n";
    for(int i = 0 ; i < table.size(); ++i)
    {
      returnValue = returnValue + table.get(i).toString();
      returnValue = returnValue + "\n";
    }
    return returnValue; 


  }

  // Removes duplicate tuples from Relation
  public void removeDuplicates()
  {
    for(int i = 0 ; i < this.table.size() - 1 ; ++i)
    {
      for(int j = i + 1 ; j < this.table.size(); ++j)
      {
        
        if(this.table.get(i).equals(this.table.get(j)))
        {
          this.table.remove(j);
          --j;
        }
      }
    }
  }

  public ArrayList<String> getAttributes()
  {
    return attributes;  
  }

  public ArrayList<String> getDomains()
  { 
    return domains;
  }

  public ArrayList<Tuple> getTable()
  {
    return this.table;
  }
  
  // Checks if Tuple t is in relation. 
  public boolean member(Tuple t)
  {
    
    for(int i = 0 ; i < table.size(); ++i)
    {
      if(table.get(i).equals(t))
      {
        return true; 
      }
    }
    
    return false; 
  }


  //Implementes UNION set operation on Relation r2
  public Relation union(Relation r2)
  {
    ArrayList<String> newDomains = new ArrayList<String>();
    for(int i = 0; i < this.domains.size() ; ++i)
    {
      newDomains.add(this.domains.get(i));
    }

    ArrayList<String> newAttributes = new ArrayList<String>();
    for(int i = 0; i < this.attributes.size() ; ++i)
    {
      newAttributes.add(this.attributes.get(i));
    }
    Relation r = new Relation(this.name, newAttributes, newDomains);

    for(int i = 0 ; i < this.table.size(); ++i)
    {
      r.addTuple(this.table.get(i).clone(newAttributes));
    }

    for(int i = 0 ; i < r2.table.size(); ++i)
    {
      r.addTuple(r2.table.get(i).clone(newAttributes));
    }

    r.removeDuplicates();
    return r;
    
  }

  // Implements INTERSECT set operation on Relation r2.
  public Relation intersect(Relation r2)
  {
    ArrayList<String> newDomains = new ArrayList<String>();
    for(int i = 0; i < this.domains.size() ; ++i)
    {
      newDomains.add(this.domains.get(i));
    }

    ArrayList<String> newAttributes = new ArrayList<String>();
    for(int i = 0; i < this.attributes.size() ; ++i)
    {
      newAttributes.add(this.attributes.get(i));
    }
    Relation r = new Relation(this.name, newAttributes, newDomains);

    

    for(int i = 0 ; i < this.table.size() ; ++i)
    {
      if(r2.member(this.table.get(i)))
      {
        r.addTuple(this.table.get(i).clone(newAttributes));
      }
    } 
    r.removeDuplicates();
    return r;
  }

  // Implements MINUS set operation on Relation r2. 
  public Relation minus(Relation r2)
  {
    ArrayList<String> newDomains = new ArrayList<String>();
    for(int i = 0; i < this.domains.size() ; ++i)
    {
      newDomains.add(this.domains.get(i));
    }

    ArrayList<String> newAttributes = new ArrayList<String>();
    for(int i = 0; i < this.attributes.size() ; ++i)
    {
      newAttributes.add(this.attributes.get(i));
    }
    Relation r = new Relation(this.name, newAttributes, newDomains);

    for(int i = 0 ; i < this.table.size() ; ++i)
    {
      if(!(r2.member(this.table.get(i))))
      {
        r.addTuple(this.table.get(i).clone(newAttributes));
      }
    }
    return r;

  }



  

  

}   