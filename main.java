import java.io.File;
public class main{

  public static void main(String[] args) {

    //import file
    File file = new File("input.nfa");
    //create NFA using input file in the makeNFA class
    //the creation of this NFA automatically generates the NFA to DFA conversion
    makeNFA nfaFromFile = new makeNFA(file);

  }
}
