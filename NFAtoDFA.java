import java.util.ArrayList;
import java.util.*;

public class NFAtoDFA{

  //initialize variables for the NFA
  public ArrayList<String> NFAstates = new ArrayList<String>();
  public ArrayList<String> NFAalphabet = new ArrayList<String>();
  public String NFAstartState = new String();
  public ArrayList<String> NFAacceptStates = new ArrayList<String>();
  public ArrayList<transitions> NFAtransitions = new ArrayList<transitions>();


  //initialize variables for the DFA
  public DFA dfa;
  public ArrayList<ArrayList<String>> DFAstates = new ArrayList<ArrayList<String>>();
  public ArrayList<String> DFAalphabet = new ArrayList<String>();
  public ArrayList<String> DFAstartState = new ArrayList<String>();
  public ArrayList<String> DFAacceptStates = new ArrayList<String>();
  public ArrayList<transitions> DFAtransitions = new ArrayList<transitions>();


  //other variables
  public transitions transition;
  public boolean eps = true;
  public ArrayList<ArrayList<String>> newStates = new ArrayList<ArrayList<String>>();
  public ArrayList<String> makeState;


  public void NFA(ArrayList<String> states, ArrayList<String> alphabet, String startState, ArrayList<String> acceptStates, ArrayList<transitions> transitionArr){

    //all elements of the NFA that will be used to convert to the DFA
    NFAstates = states;
    NFAalphabet = alphabet;
    NFAstartState = startState;
    NFAacceptStates = acceptStates;
    NFAtransitions = transitionArr;


    conversion();

  }

  public void conversion(){

    //start by declaring the input alphabets as being the same
    DFAalphabet = NFAalphabet;

    //initialize start state (check for epsilon transitions)
    String startingAt = NFAstartState;

    while(eps == true)
    {
      DFAstartState.add(startingAt);
      startingAt = epsilonTransitions(startingAt);

    }
    eps = true;


    //new states is an ArrayList that holds new reachable states as determined by transitions begining at the accept state.
    //this ArrayList is used in the while loop in a FIFO queue type manner where elements are removed from the front
    //to begin, newStates will only include the start state but more will be added in otherTransitions()
    newStates.add(DFAstartState);

    //this while loop runs until all reachable states and their transitions have been accounted for
    while(newStates.size() != 0){
      ArrayList<String> now = newStates.get(0);
      if(now.get(0) == "EM" || DFAstates.contains(now)){
        newStates.remove(0);
        now = newStates.get(0);
        DFAstates.add(now);
        newStates.remove(0);
        otherTransitions(now);
      }else{
        DFAstates.add(now);
        newStates.remove(0);
        otherTransitions(now);
      }
    }

    //send all of DFA information to DFA class for formatting and printing
    DFA dfa = new DFA(NFAstates, DFAstates, DFAalphabet, DFAstartState, DFAtransitions, NFAstartState);

  }

  public String epsilonTransitions(String state){
    //loop through transitions to check start state and transition that follows
    //maybe make a function for all epsilon transitions
    for(int i = 0; i < NFAtransitions.size(); i++){
      String currentStart = (NFAtransitions.get(i)).getStart();
      String currentTransition = (NFAtransitions.get(i)).getTransition();
      //if NFA start state is linked to an epsilon transition, new start state for DFA
      if((currentStart.equals(state)) && (currentTransition.equals("EPS"))){
        return NFAtransitions.get(i).getEnd();
      }
    }
    eps = false;
    return "";
  }

  public void otherTransitions(ArrayList<String> curState){
    //for each letter in the input alphabet
    for(int b = 0; b < DFAalphabet.size(); b++){

      makeState = new ArrayList<String>();
      transition = new transitions();
      //and each og NFA state in the new DFA state

      for(int a = 0; a < curState.size(); a++){

        //now that we have the state and letter pairing we need to go through the transitions to see where it leads
        for(int c = 0; c < NFAtransitions.size(); c++){

          //if there is a transition found for the same current state and transition we are looking for

          if(  (curState.get(a)).equals((NFAtransitions.get(c)).getStart()) &&  DFAalphabet.get(b).equals((NFAtransitions.get(c)).getTransition()) ){

            //check for epsilon transitions

            String start = (NFAtransitions.get(c)).getEnd();
            while(eps == true)
            {
              if(makeState.contains(start) == false){
                makeState.add(start);
                start = epsilonTransitions(start);
              }else{
                eps = false;
              }

            }
            //make state now contains all of the states that are transitioned to on the specified letter, including via epsilon states
            eps = true;
          }

        }

      }

      //at the end of each loop for the alphabet (so all transitions from all states have been accounted for for that letter)
      //transitions are added to DFAtransitions and the endStates from these transitions are added to an ArrayList newStates

      //sorts current state we are looking at so if it is already included it will not be included again

      Collections.sort(makeState);

      if(makeState.size() >= 1){
        //System.out.println("ON " + DFAalphabet.get(b));
        transition.setStart(convertToFormattedStateString(curState));
        transition.setTransition(DFAalphabet.get(b));
        String end = convertToFormattedStateString(makeState);
        transition.setEnd(end);
      }else if(makeState.size() == 0){
        makeState.add("EM");
        transition.setStart(convertToFormattedStateString(curState));
        transition.setTransition(DFAalphabet.get(b));
        String end = convertToFormattedStateString(makeState);
        transition.setEnd(end);
      }

      //if transition is not already accounted for

      boolean there = false;

      for(int t = 0; t < DFAtransitions.size(); t++){
        if((DFAtransitions.get(t)).sameTransition(transition.getStart(), transition.getTransition(), transition.getEnd())){
          there = true;
        }
      }

      if(there == false){
        DFAtransitions.add(transition);
      }

      //}

      // if a new state is created via these transitions, it is added to the newStates ArrayList
      if(DFAstates.contains(makeState) == false){
        newStates.add(makeState);

        //DFAstates.add(makeState);
        //System.out.println(DFAstates);
      }

    }

  }

  public String convertToFormattedStateString(ArrayList<String> stateArray){
    String string = "{";
    for(int a = 0; a < stateArray.size(); a++){
      if(a>0){
        string = string + ", ";
      }
      string = string + stateArray.get(a);
    }
    string = string + "}";

    return string;
  }



}
