package de.maibornwolff.ste.bookingham_palace.system.auth;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class Tokens {

    private static Map<String, String> tokens = new HashMap<>();

    public static String create(String username){
        String token = new RandomString(16, ThreadLocalRandom.current()).nextString();
        tokens.put(token, username);

        return token;
    }

    public static String getUser(String token){
        return tokens.get(token);
    }

    public static Boolean verify(String token) {
        return tokens.containsKey(token);
    }

    public static void clear(String token) {
        tokens.remove(token);
    }
}
