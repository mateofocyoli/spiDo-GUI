package users;

import java.security.InvalidParameterException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import com.google.gson.annotations.Expose;

/**
 * Class to represent a person's credentials. They consist in username and password and the first
 * is saved in plain text while the second is hashed and saved as byte hash.
 */
public class Credentials implements Comparable<Credentials> {

    @Expose
    private final String username;
    @Expose
    private final byte[] passwordHash;

    private static final String HASH_ALGORITHM = "SHA-256";

    /**
     * Generate a byte hash of the string passed using the algorithm selected.
     * @param password The string to be hashed
     * @return The {@code byte[]} containing the hash.
     */
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

    /**
     * Constructor for the Credentials object. The password is hashed and then saved.
     * @param username The person's username (can not be null nor blank)
     * @param password The person's password in plain text (can not be null nor blank)
     * @throws InvalidParameterException if an invalid parameter is passed
     */
    public Credentials(String username, String password) {
        if(username == null)
            throw new InvalidParameterException("username cannot be null");

        if(username.isBlank())
            throw new InvalidParameterException("username cannot be blank");

        if(password == null)
            throw new InvalidParameterException("password cannot be null");
        
        if(password.isBlank())
            throw new InvalidParameterException("password cannot be blank");

        this.username = username;
        this.passwordHash = generateHash(password);
    }

    /**
     * Checks if the credentials passed are the same of this object's.
     * It compares (case sensitive) the username and then checks the password's hashes.
     * In order to check two credentials object refer to {@code equals(..)} 
     * @param username The username to check
     * @param password The password to check
     * @return {@code true} if the credentials correspond.
     */
    public boolean check(String username, String password) {
        
        if(username == null || password == null)
            return false;

        return (username.compareTo(this.username) == 0 && checkPassword(password));
    }

    /**
     * Hashes the password passed and compares it to the one saved.
     * @param password The password to check
     * @return {@code true} If the passwords are the same
     */
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
