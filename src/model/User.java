package model;

/**
 * Creates User objects that represent the users of the program
 */
public class User {

    private int id;
    private String username;
    private String password;

    /**
     * Class constructor
     * @param id the user ID
     * @param username the username
     * @param password the password
     */
    public User(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    /**
     * Default class constructor
     */
    public User() { }

    /**
     * Gets the user ID
     * @return the user ID
     */
    public int getID() {
        return id;
    }

    /**
     * Sets the user ID
     * @param id the user ID
     */
    public void setID(int id) {
        this.id = id;
    }

    /**
     * Gets the username
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username
     * @param username the username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the password
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password
     * @param password the password
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
