# NFA_to_DFA_Project
Ali Woodward

main.java
makeNFA.java
NFAtoDFA.java
transitions.java
DFA.java

key notes for running:

To execute, run:
javac *.java
java main

The project takes in an input file extension "input.nfa" located in the same folder: example provided and can be switched out with other files
Please make sure that everything is seperated by TABS as that is how the file reader is meant to process the input

main.java:
Main function to run the project

makeNFA.java:
Does all of the file reading and processing to create the NFA given by the input file

transitions.java:
Transition class made to hold transition objects

NFAtoDFA.java:
Carries out the conversion from NFA to DFA

DFA.java:
Formats and outputs the created DFA to a file "output.DFA"
