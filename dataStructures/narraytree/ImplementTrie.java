import java.util.HashMap;
import java.util.Map;

/*
https://leetcode.com/explore/learn/card/trie/147/basic-operations/1047/
Implement a problems.trie with insert, search, and startsWith methods.
Example:
Trie problems.trie = new Trie();
problems.trie.insert("apple");
problems.trie.search("apple");   // returns true
problems.trie.search("app");     // returns false
problems.trie.startsWith("app"); // returns true
problems.trie.insert("app");
problems.trie.search("app");     // returns true
Note:
You may assume that all inputs are consist of lowercase letters a-z.
All inputs are guaranteed to be non-empty strings.
 */
public class ImplementTrie {
    public static void main(String[] args) {
        System.out.println("**************************  Method 1 **************************");
        Trie trie = new Trie();
        trie.insert("apple");
        System.out.println(trie.search("apple") + " should be [true].");   // returns true
        System.out.println(trie.search("app") + " should be [false].");     // returns false
        System.out.println(trie.startsWith("app") + " should be [true]."); // returns true
        trie.insert("app");
        System.out.println(trie.search("app") + " should be [true].");     // returns true
        System.out.println("**************************  Method 2 **************************");
        Trie_Array trieArray = new Trie_Array();
        trieArray.insert("apple");
        System.out.println(trieArray.search("apple") + " should be [true].");   // returns true
        System.out.println(trieArray.search("app") + " should be [false].");     // returns false
        System.out.println(trieArray.startsWith("app") + " should be [true]."); // returns true
        trieArray.insert("app");
        System.out.println(trieArray.search("app") + " should be [true].");     // returns true

        System.out.println("**************************  Method 3 **************************");
        Trie_Revision trieRevision = new Trie_Revision();
        trieRevision.insert("apple");
        System.out.println(trieRevision.search("apple") + " should be [true].");   // returns true
        System.out.println(trieRevision.search("app") + " should be [false].");     // returns false
        System.out.println(trieRevision.startsWith("app") + " should be [true]."); // returns true
        trieRevision.insert("app");
        System.out.println(trieRevision.search("app") + " should be [true].");     // returns true
        trieRevision = new Trie_Revision();
        trieRevision.insert("a");
        System.out.println(trieRevision.search("a") + " should be [true].");   // returns true
        System.out.println(trieRevision.startsWith("a") + " should be [true]."); // returns true
    }
}

class Trie_Revision {
    TrieNode[] nodes;

    /**
     * Initialize your data structure here.
     */
    public Trie_Revision() {
        nodes = new TrieNode[26];
    }

    /**
     * Inserts a word into the trie.
     */
    public void insert(String word) {
        TrieNode pre = nodes[word.charAt(0) - 'a'];
        if (pre == null) {
            pre = new TrieNode(word.charAt(0));
            nodes[word.charAt(0) - 'a'] = pre;
        }
        for (int i = 1; i < word.length(); i++) {
            TrieNode cur = insert(pre, word.charAt(i));
            pre = cur;
        }
        pre.isEnd = true;
    }

    private TrieNode insert(TrieNode node, char cur) {
        TrieNode curNode = node.child[cur - 'a'];
        if (curNode == null) {
            curNode = new TrieNode(cur);
            node.child[cur - 'a'] = curNode;
        }
        return curNode;
    }

    /**
     * Returns if the word is in the trie.
     */
    public boolean search(String word) {
        TrieNode search = search(this.nodes, word);
        return search != null && search.isEnd;
    }

    private TrieNode search(TrieNode[] nodes, String word) {
        if (nodes == null) return null;
        char cur = word.charAt(0);
        if (nodes[cur - 'a'] != null && nodes[cur - 'a'].c == cur) {
            if (word.length() > 1) {
                return search(nodes[cur - 'a'].child, word.substring(1));
            } else {
                return nodes[cur - 'a'];
            }
        }
        return null;
    }

    /**
     * Returns if there is any word in the trie that starts with the given prefix.
     */
    public boolean startsWith(String prefix) {
        return search(this.nodes, prefix) != null;
    }

    class TrieNode {
        char c;
        TrieNode[] child = new TrieNode[26];
        boolean isEnd = false;

        public TrieNode() {
        }

        public TrieNode(char c) {
            this.c = c;
        }
    }
}

class Trie_Array {
    Trie[] arr;
    boolean end;

    /**
     * Initialize your data structure here.
     */
    public Trie_Array() {
        arr = new Trie[26];
        end = false;
    }

    /**
     * Inserts a word into the problems.trie.
     */
    public void insert(String word) {
        if (word.length() == 0) {
            this.end = true;
            return;
        }
        if (this.arr[word.charAt(0) - 'a'] == null) {
            this.arr[word.charAt(0) - 'a'] = new Trie();
        }
        this.arr[word.charAt(0) - 'a'].insert(word.substring(1));
    }

    /**
     * Returns if the word is in the problems.trie.
     */
    public boolean search(String word) {
        if (word.length() == 0) {
            return this.end;
        }
        if (this.arr[word.charAt(0) - 'a'] == null) {
            return false;
        }
        return this.arr[word.charAt(0) - 'a'].search(word.substring(1));
    }

    /**
     * Returns if there is any word in the problems.trie that starts with the given prefix.
     */
    public boolean startsWith(String word) {
        if (word.length() == 0) {
            return true;
        }
        if (this.arr[word.charAt(0) - 'a'] == null) {
            System.out.println(word.charAt(0));
            return false;
        }
        return this.arr[word.charAt(0) - 'a'].startsWith(word.substring(1));
    }
}

class Trie {
    TrieNode trieNode;

    /**
     * Initialize your data structure here.
     */
    public Trie() {
        trieNode = new TrieNode();
    }

    /**
     * Inserts a word into the problems.trie.
     */
    public void insert(String word) {
        TrieNode cur = trieNode;
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if (cur.children.get(c) == null) {
                // insert a new node if the path does not exist
                cur.children.put(c, new TrieNode());
            }
            cur = cur.children.get(c);
        }
    }

    public void insert_recursive(String word) {
        trieNode = insertHelper(trieNode, word);
    }

    private TrieNode insertHelper(TrieNode trieNode, String word) {
        if (word.isEmpty()) return trieNode;
        char current = word.charAt(0);
        if (word.length() == 1) {
//            If only one word is left out then add it as a child to the current tree.
            trieNode.children.put(current, new TrieNode());
            return trieNode;
        }
        for (Map.Entry<Character, TrieNode> e : trieNode.children.entrySet()) {
            if (current == e.getKey()) {
                trieNode.children.put(e.getKey(), insertHelper(e.getValue(), word.substring(1)));
                return trieNode;
            }
        }
//        Reached the end of the tree.
        TrieNode children = createChildren(trieNode.parentChars, word.substring(1));
        trieNode.children.put(current, children);
        return trieNode;
    }

    private TrieNode createChildren(String parentChars, String substring) {
        TrieNode trieNode = new TrieNode();
        trieNode.parentChars = parentChars;
        char current = substring.charAt(0);
        if (substring.length() > 1) {
            String currentParentChar = parentChars != null ? parentChars + current : String.valueOf(current);
            trieNode.children.put(current, createChildren(currentParentChar, substring.substring(1)));
        } else {
            trieNode.children.put(current, new TrieNode());
        }
        return trieNode;
    }

    /**
     * Returns if the word is in the problems.trie.
     */
    public boolean search(String word) {
        TrieNode trieNode1 = search(trieNode, word);
        return trieNode1 != null && trieNode1.children.isEmpty();
    }

    private TrieNode search(TrieNode trieNode, String word) {
        if (trieNode == null || word.isEmpty()) return trieNode;
        char current = word.charAt(0);
        if (trieNode.children.containsKey(current)) return search(trieNode.children.get(current), word.substring(1));
        else return null;
    }

    /**
     * Returns if there is any word in the problems.trie that starts with the given prefix.
     */
    public boolean startsWith(String prefix) {
        TrieNode trieNode1 = search(trieNode, prefix);
        return trieNode1 != null;
    }

    class TrieNode {
        public Map<Character, TrieNode> children = new HashMap<>();
        String parentChars = null;

        @Override
        public String toString() {
            return "TrieNode{" +
                    "children=" + children +
                    ", parentChars='" + parentChars + '\'' +
                    '}';
        }
    }
}