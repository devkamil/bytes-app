package pl.devkamil.app.model;

public class RequestFromView {

    private String pathToFile;
    private String fileExtension;
    private String inputBytes;
    private String outputBytes;

    public String getPathToFile() {
        return pathToFile;
    }

    public void setPathToFile(String pathToFile) {
        this.pathToFile = pathToFile;
    }

    public String getFileExtension() {
        return fileExtension;
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    public String getInputBytes() {
        return inputBytes;
    }

    public void setInputBytes(String inputBytes) {
        this.inputBytes = inputBytes;
    }

    public String getOutputBytes() {
        return outputBytes;
    }

    public void setOutputBytes(String outputBytes) {
        this.outputBytes = outputBytes;
    }

    public RequestFromView(){}

    public RequestFromView(String pathToFile, String fileExtension, String inputBytes, String outputBytes) {
        this.pathToFile = pathToFile;
        this.fileExtension = fileExtension;
        this.inputBytes = inputBytes;
        this.outputBytes = outputBytes;
    }


}
