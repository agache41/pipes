package io.github.agache41.ormpipes.pipes.base.othogonal.enums;

public enum Model {
    Fixed(true, true),
    VariablePositions(true, false),
    VariableNames(false, true);

    private final boolean validNames;
    private final boolean validPositions;

    Model(boolean validNames, boolean validPositions) {
        this.validNames = validNames;
        this.validPositions = validPositions;
    }

    public boolean hasValidNames() {
        return this.validNames;
    }

    public boolean hasValidPositions() {
        return this.validPositions;
    }

    public void verify(boolean validNames, boolean validPositions) {
        if (this.validNames && !validNames)
            throw new RuntimeException(" No valid naming, please check your class!");
        if (this.validPositions && !validPositions)
            throw new RuntimeException(" No valid positioning, please check your class!");
    }
}
