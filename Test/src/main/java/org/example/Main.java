package org.example;


import org.moeaframework.core.NondominatedPopulation;
import org.moeaframework.core.Solution;
import org.moeaframework.core.variable.EncodingUtils;
import org.moeaframework.Executor;

public class Main {
    public static void main(String[] args) {
        TripletMatchingProblem problem = new TripletMatchingProblem();
        NondominatedPopulation result = new Executor()
                .withProblem(problem)
                .withAlgorithm("NSGAII")
                .withMaxEvaluations(10000)
                .run();


        System.out.println("Tìm được " + result.size() + " giải pháp không trội.");
        for (Solution solution : result) {
            int devIndex = EncodingUtils.getInt(solution.getVariable(0));
            int desIndex = EncodingUtils.getInt(solution.getVariable(1));
            int pmIndex = EncodingUtils.getInt(solution.getVariable(2));

            String developer = problem.getDevelopers().get(devIndex);
            String designer = problem.getDesigners().get(desIndex);
            String pm = problem.getProjectManagers().get(pmIndex);


            double stability = -solution.getObjective(0);
            double satisfaction = -solution.getObjective(1);

            System.out.println("Giải pháp:");
            System.out.println("Developer: " + developer);
            System.out.println("Designer: " + designer);
            System.out.println("Project Manager: " + pm);
            System.out.println("Stability Score: " + stability);
            System.out.println("Satisfaction Score: " + satisfaction);
            System.out.println("---------------------------");
        }
    }
}
