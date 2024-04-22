import java.util.HashMap;

public class InMemoryDB {
    private boolean transactionInProgress;
    private final HashMap<String, Integer> committedMap;
    private final HashMap<String, Integer> transactionMap;

    public InMemoryDB() {
        transactionInProgress = false;
        committedMap = new HashMap<>();
        transactionMap = new HashMap<>();
    }

    // Starts a transaction during which any number of put() calls can be made. One transaction at a time.
    public void begin_transaction() throws IllegalStateException {
        if (transactionInProgress) { // UNCLEAR IN INSTRUCTIONS.
            throw new IllegalStateException("There is already a transaction in progress.");
        }
        transactionInProgress = true;
    }

    // Creates a new key with the provided val if a key doesn't exist. Else, updates the val of the existing key.
    public void put(String key, int val) throws IllegalStateException {
        if (!transactionInProgress) {
            throw new IllegalStateException("There is no transaction in progress.");
        }
        transactionMap.put(key, val);
    }

    // Returns val associated with the key, or null if key doesn't exist. Can be called anytime.
    public Integer get(String key) {
        return committedMap.get(key);
    }

    // Ends the transaction. Applies any changes made within the transaction to the main state, allowing any future
    // get() calls to see changes made within the transaction.
    public void commit() throws IllegalStateException {
        if (!transactionInProgress) {
            throw new IllegalStateException("There is no transaction in progress.");
        }
        transactionInProgress = false;
        committedMap.putAll(transactionMap);
        transactionMap.clear();
    }

    // Ends the transaction. Aborts all changes made within the transaction.
    public void rollback() throws IllegalStateException {
        if (!transactionInProgress) {
            throw new IllegalStateException("There is no transaction in progress.");
        }
        transactionInProgress = false;
        transactionMap.clear();
    }
}
