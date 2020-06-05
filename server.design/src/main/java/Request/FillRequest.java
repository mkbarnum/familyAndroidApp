package Request;

/** The FillRequest Object
 *
 */
public class FillRequest {
    /** the Username that will be used to generate tables for
     *
     */
    String username;

    /** the number of generations that will be genereated
     *
     */
    int generations;

    /** Constructor for FillRequest object
     *
     */

    boolean hasGeneration;

    public FillRequest(String username, int generations) {
        this.username = username;
        this.generations = generations;
    }

    public FillRequest(){}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getGenerations() {
        return generations;
    }

    public void setGenerations(int generations) {
        this.generations = generations;
    }

    public boolean isHasGeneration() {
        return hasGeneration;
    }

    public void setHasGeneration(boolean hasGeneration) {
        this.hasGeneration = hasGeneration;
    }
}
