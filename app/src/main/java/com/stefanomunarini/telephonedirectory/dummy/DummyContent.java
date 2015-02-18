package com.stefanomunarini.telephonedirectory.dummy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 */
public class DummyContent {

    /**
     * An array of sample (dummy) items.
     */
    public static List<Contact> ITEMS = new ArrayList<Contact>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static Map<String, Contact> ITEM_MAP = new HashMap<String, Contact>();
    private static int counter = 0;

    static {
        // Add 3 sample items.
        for (int i=0; i<5; i++) {
            addItem(new Contact(String.valueOf(counter), "Stefano", "Munarini", "3406917486"));
            counter++;
            addItem(new Contact(String.valueOf(counter), "Valentina", "Bonfante", "3406917486"));
            counter++;
        }

    }

    private static void addItem(Contact item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class Contact {
        public String id;
        public String name;
        public String surname;
        public String number;

        public Contact(String id, String name, String surname, String number) {
            this.id = id;
            this.name = name;
            this.surname = surname;
            this.number = number;
        }

        @Override
        public String toString() {
            return name + " " + surname;
        }
    }
}
