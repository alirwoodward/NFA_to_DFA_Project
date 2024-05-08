import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class DFA{

  public ArrayList<ArrayList<String>> acceptStates = new ArrayList<ArrayList<String>>();
  public ArrayList<String> allStates = new ArrayList<String>();
  public DFA(ArrayList<String> nfaStates, ArrayList<ArrayList<String>> dfaStates, ArrayList<String> alphabet, ArrayList<String> startState, ArrayList<transitions> transitions, String nfaStart){
    try {
      FileWriter write = new FileWriter("output.DFA");

      //printing all states line 1
      for(int i = 0; i < dfaStates.size(); i++){
        write.write("{");

        for(int x = 0; x < dfaStates.get(i).size(); x++) {
          write.write((dfaStates.get(i).get(x)));
          //System.out.print(dfaStates.get(x));
          if(x < (dfaStates.get(i).size()-1)){
            write.write(", ");
          }
        }
        write.write("}  ");
      }

      //printing alphabet line 2
      String alpha = convertToFormattedString(alphabet, 'a');
      write.write("\n" + alpha + "\n");
      //printing start string line 3
      String startStateString = convertToFormattedString(startState, 't');
      write.write(startStateString + "\n");

      //printing accept states line 4
      for(int g = 0; g < dfaStates.size(); g++){
        if((dfaStates.get(g)).contains(nfaStart)){
          acceptStates.add(dfaStates.get(g));
        }
      }

      for(int h = 0; h < acceptStates.size(); h++){
        write.write("{");
        for(int d = 0; d < acceptStates.get(h).size(); d++) {
          write.write((acceptStates.get(h)).get(d));
          if(d < (acceptStates.get(h).size()-1))
          write.write(", ");
        }
        write.write("}  ");
      }

      //print BEGIN
      write.write("\nBEGIN\n");

      //print transition function
      for(int t = 0; t < transitions.size(); t++){
        write.write((transitions.get(t)).printTransition() + "\n");
      }

      write.write("END");
      write.close();
    } catch (IOException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
  }

  public String convertToFormattedString(ArrayList<String> arr, char identifier){
    String string = "";

    if(identifier == 's'){
      for(int q = 0; q < arr.size(); q++){
        string = "{";
        if(q != (arr.size()-2)){
          string = string + arr.get(q) + ",  ";
        }else{
          string = string + arr.get(q);
        }
      }
      string = string + "}";
    }else if(identifier == 'a'){
      for(int b = 0; b < arr.size(); b++){
        string = string + arr.get(b) + "  ";
      }
    }else if(identifier == 't'){
      string = "{";
      for(int t = 0; t < arr.size(); t++){
        if(t>0){
          string = string + ", ";
        }
        string = string + arr.get(t);
      }
      string = string + "}";
    }
    return string;
  }

}
