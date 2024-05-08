import java.io.File;  // Import the File class
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

public class makeNFA{

  public NFAtoDFA nfa = new NFAtoDFA();
  public transitions transition;
  public ArrayList<String> Qstates = new ArrayList<String>();
  public ArrayList<String> InputAlphabet = new ArrayList<String>();
  public String startState = new String();
  public ArrayList<String> AcceptStates = new ArrayList<String>();
  public ArrayList<transitions> NFAtransitions = new ArrayList<transitions>();

  public makeNFA(File file){

    try {
      Scanner scnr = new Scanner(file);
      int lineNum = 1;

      while (scnr.hasNextLine()) {
        String line = scnr.nextLine();

        //list of states
        if(lineNum == 1){
          String[] statesArr = line.split(" ");
          for(int a = 0; a < statesArr.length; a++){
            String stateString = statesArr[a];
            //remove curly brackets to seperate states
            stateString = stateString.replace("{", "");
            stateString = stateString.replace("}", "");
            //adding each state to an ArrayList (only the state, no curly brackets)
            Qstates.add(stateString);
          }

        //input alphabet
        }else if(lineNum == 2){
          String[] inputAlphabet = line.split(" ");
          for(int b = 0; b < inputAlphabet.length; b++){
            InputAlphabet.add(inputAlphabet[b]);
          }

        //start state
        }else if(lineNum == 3){
          line = line.replace("{", "");
          line = line.replace("}", "");
          //set startState without curly brackets
          startState = line;

        //accept states
        }else if(lineNum == 4){
          String[] acceptString = line.split(" ");
          for(int d = 0; d < acceptString.length; d++){
            String accept = acceptString[d];
            //remove curly brackets from states
            accept = accept.replace("{", "");
            accept = accept.replace("}", "");
            //add accept states to an ArrayList
            AcceptStates.add(accept);
          }

        //transition function lines
        }else if(lineNum >= 6){
          //while loop breaks if end line is reached
          if(line.equals("END")){
            break;
          }

          transition = new transitions();

          String[] tranString = line.split(" ");
          //index 0 = start state
          //index 1 = transition
          //index 2 = "=" --ignore
          //index 3 = end state

          transition.setStart(tranString[0].substring(1, 2));
          transition.setTransition(tranString[1]);
          transition.setEnd(tranString[3].substring(1, 2));

          NFAtransitions.add(transition);
        }
        lineNum++;

      }
      scnr.close();
    } catch (FileNotFoundException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }


    //create NFA read from file (the actual NFA class occurs in the NFAtoDFA file)
    nfa.NFA(Qstates, InputAlphabet, startState, AcceptStates, NFAtransitions);
  }
}
