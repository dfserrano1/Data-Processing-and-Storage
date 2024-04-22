public class Main {
    private static void printSuccess(int testNum) {
        System.out.println("Test " + testNum + " passed.");
    }

    public static void main(String[] args) {
        InMemoryDB db = new InMemoryDB();

        Integer val = db.get("A");
        if (val == null) {
            printSuccess(1);
        }

        try {
            db.put("A", 5);
        }
        catch (IllegalStateException e) {
            printSuccess(2);
        }

        db.begin_transaction();
        printSuccess(3);

        db.put("A", 5);
        printSuccess(4);

        Integer test5 = db.get("A");
        if (test5 == null) {
            printSuccess(5);
        }

        db.put("A", 6);
        printSuccess(6);

        db.commit();
        printSuccess(7);

        val = db.get("A");
        if (val == 6) {
            printSuccess(8);
        }

        try {
            db.commit();
        }
        catch (IllegalStateException e) {
            printSuccess(9);
        }

        try {
            db.rollback();
        }
        catch (IllegalStateException e) {
            printSuccess(10);
        }

        val = db.get("B");
        if (val == null) {
            printSuccess(11);
        }

        db.begin_transaction();
        printSuccess(12);

        db.put("B", 10);
        printSuccess(13);

        db.rollback();
        printSuccess(14);

        val = db.get("B");
        if (val == null) {
            printSuccess(15);
        }
    }
}
