package org.example;

import org.moeaframework.problem.AbstractProblem;
import org.moeaframework.core.Solution;
import org.moeaframework.core.variable.EncodingUtils;

import java.util.Arrays;
import java.util.List;

public class TripletMatchingProblem extends AbstractProblem {
    private final List<String> developers = Arrays.asList("D1", "D2", "D3", "D4", "D5");
    private final List<String> designers = Arrays.asList("S1", "S2", "S3", "S4", "S5");
    private final List<String> projectManagers = Arrays.asList("P1", "P2", "P3", "P4", "P5");
    private final List<List<String>> devPreferences = Arrays.asList(
            Arrays.asList("S1", "S2", "S3", "S4", "S5", "P1", "P2", "P3", "P4", "P5"),
            Arrays.asList("S2", "S3", "S4", "S5", "S1", "P2", "P3", "P4", "P5", "P1"),
            Arrays.asList("S3", "S4", "S5", "S1", "S2", "P3", "P4", "P5", "P1", "P2"),
            Arrays.asList("S4", "S5", "S1", "S2", "S3", "P4", "P5", "P1", "P2", "P3"),
            Arrays.asList("S5", "S1", "S2", "S3", "S4", "P5", "P1", "P2", "P3", "P4")
    );

    private final List<List<String>> desPreferences = Arrays.asList(
            Arrays.asList("D1", "D2", "D3", "D4", "D5", "P1", "P2", "P3", "P4", "P5"),
            Arrays.asList("D2", "D3", "D4", "D5", "D1", "P2", "P3", "P4", "P5", "P1"),
            Arrays.asList("D3", "D4", "D5", "D1", "D2", "P3", "P4", "P5", "P1", "P2"),
            Arrays.asList("D4", "D5", "D1", "D2", "D3", "P4", "P5", "P1", "P2", "P3"),
            Arrays.asList("D5", "D1", "D2", "D3", "D4", "P5", "P1", "P2", "P3", "P4")
    );

    private final List<List<String>> pmPreferences = Arrays.asList(
            Arrays.asList("D1", "D2", "D3", "D4", "D5", "S1", "S2", "S3", "S4", "S5"),
            Arrays.asList("D2", "D3", "D4", "D5", "D1", "S2", "S3", "S4", "S5", "S1"),
            Arrays.asList("D3", "D4", "D5", "D1", "D2", "S3", "S4", "S5", "S1", "S2"),
            Arrays.asList("D4", "D5", "D1", "D2", "D3", "S4", "S5", "S1", "S2", "S3"),
            Arrays.asList("D5", "D1", "D2", "D3", "D4", "S5", "S1", "S2", "S3", "S4")
    );

    public TripletMatchingProblem() {
        super(3, 2);
    }

    @Override
    public void evaluate(Solution solution) {
        int devIndex = EncodingUtils.getInt(solution.getVariable(0));
        int desIndex = EncodingUtils.getInt(solution.getVariable(1));
        int pmIndex = EncodingUtils.getInt(solution.getVariable(2));

        String developer = developers.get(devIndex);
        String designer = designers.get(desIndex);
        String pm = projectManagers.get(pmIndex);


        double stability = calculateStability(devIndex, desIndex, pmIndex);
        double satisfaction = calculateSatisfaction(devIndex, desIndex, pmIndex);
        solution.setObjective(0, -stability);
        solution.setObjective(1, -satisfaction);
    }


    private double calculateStability(int devIndex, int desIndex, int pmIndex) {

        int devRankDes = devPreferences.get(devIndex).indexOf(designers.get(desIndex));
        int devRankPm = devPreferences.get(devIndex).indexOf(projectManagers.get(pmIndex));


        int desRankDev = desPreferences.get(desIndex).indexOf(developers.get(devIndex));
        int desRankPm = desPreferences.get(desIndex).indexOf(projectManagers.get(pmIndex));


        int pmRankDev = pmPreferences.get(pmIndex).indexOf(developers.get(devIndex));
        int pmRankDes = pmPreferences.get(pmIndex).indexOf(designers.get(desIndex));


        int totalRank = devRankDes + devRankPm + desRankDev + desRankPm + pmRankDev + pmRankDes;
        return (24 - totalRank) / 24.0;
    }
    private double calculateSatisfaction(int devIndex, int desIndex, int pmIndex) {
        double devAvg = (devPreferences.get(devIndex).indexOf(designers.get(desIndex)) +
                devPreferences.get(devIndex).indexOf(projectManagers.get(pmIndex))) / 2.0;

        double desAvg = (desPreferences.get(desIndex).indexOf(developers.get(devIndex)) +
                desPreferences.get(desIndex).indexOf(projectManagers.get(pmIndex))) / 2.0;

        double pmAvg = (pmPreferences.get(pmIndex).indexOf(developers.get(devIndex)) +
                pmPreferences.get(pmIndex).indexOf(designers.get(desIndex))) / 2.0;

        double averageRank = (devAvg + desAvg + pmAvg) / 3.0;

        return (4.0 - averageRank) / 4.0;
    }
    @Override
    public Solution newSolution() {
        Solution solution = new Solution(3, 2);
        solution.setVariable(0, EncodingUtils.newInt(0, developers.size() - 1));
        solution.setVariable(1, EncodingUtils.newInt(0, designers.size() - 1));
        solution.setVariable(2, EncodingUtils.newInt(0, projectManagers.size() - 1));
        return solution;
    }
}

