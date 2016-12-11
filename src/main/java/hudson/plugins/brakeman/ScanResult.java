package hudson.plugins.brakeman;

/**
 * Created by Thomas Baird on 10/11/16.
 * This is a java class used to store the result of a
 * Brakeman Output scan.
 */
public class ScanResult {

    private String filename;
    private boolean successful;

    public ScanResult(boolean successful, String filename) {
        this.successful = successful;
        this.filename = filename;
    }

    public String getFilename() {
        return filename;
    }

    public boolean isSuccessful() {
        return successful;
    }
}
