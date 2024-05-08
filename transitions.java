import java.util.ArrayList;

public class transitions{

  String startState = new String();
  String transition = new String();
  String endState = new String();

  public void transitions(){
    startState = "";
    transition = "";
    endState = "";
  }

//set start state for transition
  public void setStart(String start){
    startState = start;
  }

//set transition
  public void setTransition(String tran){
    transition = tran;
  }

//set where transition ends
  public void setEnd(String end){
    endState = end;
  }

  public String getStart(){
    return startState;
  }

  public String getTransition(){
    return transition;
  }

  public String getEnd(){
    return endState;
  }

//helps print transition object
  public String printTransition(){
    return startState + ", " + transition + " = " + endState;
  }

//compares two transitions to see if they are the same
  public boolean sameTransition(String startS, String trans, String endS){
    if((endS == endState) && (startS == startState) && (trans == transition)){
      return true;
    }else{
      return false;
    }
  }

}
