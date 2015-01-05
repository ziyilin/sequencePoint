package edu.sjtu.stap.checkmate.framework.instrument;

public class MethodInfor {
    private String methodName;
    private String methodDescription;
    private String methodSignature;

    public MethodInfor(String name, String description) {
        this.methodDescription = description;
        this.methodName = name;
        this.methodSignature = null;
    }

    public MethodInfor(String name, String description, String signature) {
        this.methodDescription = description;
        this.methodName = name;
        this.methodSignature = signature;
    }

    public String getMethodDescription() {
        return methodDescription;
    }

    public String getMethodName() {
        return methodName;
    }

    public String getMethodSignature() {
        return methodSignature;
    }

    public void setMethodDescription(String methodDescription) {
        this.methodDescription = methodDescription;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public void setMethodSignature(String methodSignature) {
        this.methodSignature = methodSignature;
    }
}
