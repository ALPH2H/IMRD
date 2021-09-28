import java.io.*;
import java.util.*;
import java.lang.*;

public class Database {

  private Map<String, Relation> relations;

  // METHODS

  // Constructor; creates an empty HashMap object
  public Database() {
    this.relations = new HashMap<String, Relation>();
  }

  // Add relation r with name rname to HashMap
  // if relation does not already exist.
  // Make sure to set the name within r to rname.
  // returns true on successful add; false otherwise
  public boolean addRelation(String rname, Relation r) {
    try {
      this.relations.put(rname, r);
      return true;
    } catch (Exception e) {
      return false;
    }

  }

  // Delete relation with name rname from HashMap
  // if relation exists. returns true on successful delete; false otherwise
  public boolean deleteRelation(String rname) {
    if (this.relations.containsKey(rname)) {
      this.relations.remove(rname);
      return true;
    } else {
      return false;
    }
  }

  // Returns true if relation with name rname exists in HashMap
  // false otherwise
  public boolean relationExists(String rname) {
    if (this.relations.containsKey(rname)) {
      return true;
    } else {
      return false;
    }

  }

  // Retrieve and return relation with name rname from HashMap;
  // returns null if it does not exist.
  public Relation getRelation(String rname) {
    if (this.relations.containsKey(rname)) {
      return this.relations.get(rname);
    } else {
      return null;
    }
  }

  // Prints database schema to screen.
  public void displaySchema() {
    System.out.println("Database Schema");
    System.out.println("-------- ------");

    this.relations.forEach((k, v) -> {
      v.displaySchema();
      System.out.println();
    });
    System.out.println();
  }



  /** 
   * This method initalizes Databases from a 
   * custom file format. Support for CSV files and other 
   * flat-file formats will be added.
   */
  public void initializeDatabase(String dir) {
    FileInputStream fin1 = null;
    BufferedReader infile1 = null;
    try {
      fin1 = new FileInputStream(dir + "/catalog.dat");
      infile1 = new BufferedReader(new InputStreamReader(fin1));
      int numberOfRelations = Integer.parseInt(infile1.readLine()); 
      
      for(int i = 0 ; i < numberOfRelations ; ++i)
      {
        String nameOfRelation = infile1.readLine();
        //System.out.println(nameOfRelation);
        int relationLength = Integer.parseInt(infile1.readLine());
        ArrayList<String> attr = new ArrayList<String>(relationLength);
        ArrayList<String> domains = new ArrayList<String>(relationLength);

        for(int j = 0 ; j < relationLength ; ++j)
        {
          String attribute = infile1.readLine();
          String domain = infile1.readLine();
          attr.add(attribute);
          domains.add(domain);
        }
          Relation r = new Relation(nameOfRelation, attr, domains); 
          try{
            FileInputStream tupleInfo = new FileInputStream(dir + "/" + nameOfRelation + ".dat");
            
            BufferedReader bufferedTupleInfo = new BufferedReader(new InputStreamReader(tupleInfo));
            int numberOfTuples = Integer.parseInt(bufferedTupleInfo.readLine());
            for(int k = 0 ; k < numberOfTuples; ++k)
            { 
              Tuple tuple = new Tuple(attr, domains);
              for(int l = 0 ; l < relationLength ; ++l)
              {
                if(domains.get(l).equals("VARCHAR"))
                {
                  String string = bufferedTupleInfo.readLine();
                  tuple.addStringComponent(string);
                }else if(domains.get(l).equals("INTEGER"))
                {
                  int integer = Integer.parseInt(bufferedTupleInfo.readLine());
                  tuple.addIntegerComponent(integer);
                }else if(domains.get(l).equals("DECIMAL"))
                {
                  double aDouble = Double.parseDouble(bufferedTupleInfo.readLine());
                  tuple.addDoubleComponent(aDouble);
                }
              }
              r.addTuple(tuple);
            }
            bufferedTupleInfo.close();
          }catch(IOException e){
            System.out.println("Error reading file");
          }
          this.addRelation(nameOfRelation, r);
        }
        fin1.close();
      } catch (IOException e) {
        System.out.println("Error reading file");
      }
    }

}