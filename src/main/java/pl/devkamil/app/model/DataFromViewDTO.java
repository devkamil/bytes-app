package pl.devkamil.app.model;

import java.util.Objects;

public class DataFromViewDTO {

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

    public DataFromViewDTO(){}

    public DataFromViewDTO(String pathToFile, String fileExtension, String inputBytes, String outputBytes) {
        this.pathToFile = pathToFile;
        this.fileExtension = fileExtension;
        this.inputBytes = inputBytes;
        this.outputBytes = outputBytes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DataFromViewDTO that = (DataFromViewDTO) o;
        return Objects.equals(pathToFile, that.pathToFile) &&
                Objects.equals(fileExtension, that.fileExtension) &&
                Objects.equals(inputBytes, that.inputBytes) &&
                Objects.equals(outputBytes, that.outputBytes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pathToFile, fileExtension, inputBytes, outputBytes);
    }

    @Override
    public String toString() {
        return "DataFromViewDTO{" +
                "pathToFile='" + pathToFile + '\'' +
                ", fileExtension='" + fileExtension + '\'' +
                ", inputBytes='" + inputBytes + '\'' +
                ", outputBytes='" + outputBytes + '\'' +
                '}';
    }
}
