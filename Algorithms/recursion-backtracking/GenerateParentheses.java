import java.util.*;

/*
https://leetcode.com/explore/learn/card/recursion-ii/503/recursion-to-iteration/2772/
Given n pairs of parentheses, write a function to generate all combinations of well-formed parentheses.
For example, given n = 3, a solution set is:
[
  "((()))",
  "(()())",
  "(())()",
  "()(())",
  "()()()"
]
 */
public class GenerateParentheses {
    static List<String> output;

    public static void main(String[] args) {
        System.out.println(GenerateParentheses.generateParenthesis(3));
        System.out.println(GenerateParentheses.checkIfValidAndGetParenthesis(new LinkedList<>(Arrays.asList('(', ')', '(', ')', '(', ')'))));
    }

    public static List<String> generateParenthesis(int n) {
        GenerateParentheses.output = new ArrayList<>();
        GenerateParentheses.backTrace(n, new LinkedList<>(), 0);
        return GenerateParentheses.output;
    }

    private static void backTrace(int n, LinkedList<Character> characters, int currentValue) {
        if (characters.size() == n * 2) {
            String value = GenerateParentheses.checkIfValidAndGetParenthesis(characters);
            if (value != null) GenerateParentheses.output.add(value);
            return;
        }
        for (int i = currentValue; i < n * 2; i++) {
//            Try with open brace
            characters.add('(');
            GenerateParentheses.backTrace(n, characters, i + 1);
            characters.removeLast();
//            Try with closed brace
            characters.add(')');
            GenerateParentheses.backTrace(n, characters, i + 1);
            characters.removeLast();
        }
    }

    private static String checkIfValidAndGetParenthesis(LinkedList<Character> characters) {
        if (characters == null || characters.isEmpty()) return null;
        StringBuilder sb = new StringBuilder();
        Stack<Character> stack = new Stack<>();
        for (Character c : characters) {
            sb.append(c);
            if (c == '(') {
                stack.add('(');
            } else {
                if (stack.isEmpty()) return null;
                Character peek = stack.peek();
                if (peek == '(') {
                    stack.pop();
                } else {
                    return null;
                }
            }
        }
        return stack.isEmpty() ? sb.toString() : null;
    }

    public static List<String> generateParenthesis_optimal(int n) {
        List<String> ans = new ArrayList();
        GenerateParentheses.backtrack(ans, "", 0, 0, n);
        return ans;
    }

    public static void backtrack(List<String> ans, String cur, int open, int close, int max) {
        if (cur.length() == max * 2) {
            ans.add(cur);
            return;
        }
        if (open < max)
            GenerateParentheses.backtrack(ans, cur + "(", open + 1, close, max);
        if (close < open)
            GenerateParentheses.backtrack(ans, cur + ")", open, close + 1, max);
    }
}
