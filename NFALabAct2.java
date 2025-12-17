import java.util.*;

public class NFALabAct2 {

    // NFA transition table
    private static Map<String, Map<Character, Set<String>>> nfa = new HashMap<>();

    // Accepting states
    private static final Set<String> acceptStates = new HashSet<>(Set.of("q2"));

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        initializeNFA();

        System.out.print("Enter a string: ");
        String input = scanner.nextLine();

        boolean accepted = simulateNFA(input);

        if (accepted) {
            System.out.println("Accepted");
        } else {
            System.out.println("Rejected");
        }

        scanner.close();
    }

    // Initialize the NFA transitions
    private static void initializeNFA() {
        nfa.put("q0", new HashMap<>());
        nfa.put("q1", new HashMap<>());
        nfa.put("q2", new HashMap<>());

        // q0 transitions
        nfa.get("q0").put('a', Set.of("q0", "q1"));
        nfa.get("q0").put('b', Set.of("q0"));

        // q1 transitions
        nfa.get("q1").put('b', Set.of("q2"));

        // q2 transitions (stay accepting)
        nfa.get("q2").put('a', Set.of("q2"));
        nfa.get("q2").put('b', Set.of("q2"));
    }

    // Simulate the NFA
    private static boolean simulateNFA(String input) {
        Set<String> currentStates = new HashSet<>();
        currentStates.add("q0");

        for (char symbol : input.toCharArray()) {
            Set<String> nextStates = new HashSet<>();

            for (String state : currentStates) {
                Map<Character, Set<String>> transitions = nfa.get(state);
                if (transitions != null && transitions.containsKey(symbol)) {
                    nextStates.addAll(transitions.get(symbol));
                }
            }

            currentStates = nextStates;
        }

        // Check if any current state is accepting
        for (String state : currentStates) {
            if (acceptStates.contains(state)) {
                return true;
            }
        }

        return false;
    }
}
