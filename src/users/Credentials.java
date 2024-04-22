package users;

import java.security.InvalidParameterException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class Credentials implements Comparable<Credentials> {

    private final String username;
    private final byte[] passwordHash;

    private static final String HASH_ALGORITHM = "SHA-256";

    public static byte[] generateHash(String password) {
        try {
            MessageDigest digester = MessageDigest.getInstance(HASH_ALGORITHM);
            return digester.digest(password.getBytes());
        } catch (NoSuchAlgorithmException e) {
            // This should never be trown
            e.printStackTrace();
        }
        return null;
    }

    public Credentials(String username, String password) {
        if(username == null)
            throw new InvalidParameterException("username cannot be null");

        if(username.length() == 0)
            throw new InvalidParameterException("username cannot be empty");

        if(password == null)
            throw new InvalidParameterException("password cannot be null");
        
        if(password.length() == 0)
            throw new InvalidParameterException("password cannot be empty");

        this.username = username;
        this.passwordHash = generateHash(password);
    }

    public boolean check(String username, String password) {
        
        if(username == null || password == null)
            return false;

        return (username.compareTo(this.username) == 0 && checkPassword(password));
    }

    private boolean checkPassword(String password) {
        byte[] hash = generateHash(password);

        if(hash == null)
            return false;  // Should be impossible to reach this

        if(hash.length != passwordHash.length)
            return false;  // Should be impossible to reach this

        for(int i = 0; i < hash.length; i++) {
            if (hash[i] != passwordHash[i])
                return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((username == null) ? 0 : username.hashCode());
        result = prime * result + Arrays.hashCode(passwordHash);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Credentials other = (Credentials) obj;
        if (username == null) {
            if (other.username != null)
                return false;
        } else if (!username.equals(other.username))
            return false;
        if (!Arrays.equals(passwordHash, other.passwordHash))
            return false;
        return true;
    }

    public String getUsername() {
        return username;
    }

    /**
     * Calls compareToIgnoreCase() on the username string. The return values are:
     * < 0: This username precedes lexicographically the other username
     * = 0: The usernames are equal (case-insensitive)
     * > 0: This username follows lexicographically the other username
     * @param o
     * @return
     */
    @Override
    public int compareTo(Credentials o) {
        return this.username.compareToIgnoreCase(o.username);
    }
}
