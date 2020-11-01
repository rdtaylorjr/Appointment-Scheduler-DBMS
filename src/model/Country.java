package model;

/**
 * Creates Country objects that represent the countries where the customers live
 */
public class Country {

    private int id;
    private String name;

    /**
     * Class constructor
     * @param id the country ID
     * @param name the country name
     */
    public Country(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Default class constructor
     */
    public Country() { }

    /**
     * Gets the country ID
     * @return the country ID
     */
    public int getID() {
        return id;
    }

    /**
     * Sets the country ID
     * @param id the country ID
     */
    public void setID(int id) {
        this.id = id;
    }

    /**
     * Gets the country name
     * @return the country name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the country name
     * @param name the country name
     */
    public void setName(String name) {
        this.name = name;
    }

}
