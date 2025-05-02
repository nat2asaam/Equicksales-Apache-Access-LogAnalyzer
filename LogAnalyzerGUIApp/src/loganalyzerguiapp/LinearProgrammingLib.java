/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package loganalyzerguiapp;

/**
 *
 * @author nasaam
 */
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.math4.legacy.optim.MaxIter;
import org.apache.commons.math4.legacy.optim.OptimizationData;
//import org.apache.commons.math4.legacy.optim.OptimizationData;
//import org.apache.commons.math4.legacy.optim.linear.*;
import org.apache.commons.math4.legacy.optim.linear.Relationship;
import org.apache.commons.math4.legacy.optim.linear.SimplexSolver;
import org.apache.commons.math4.legacy.optim.linear.LinearConstraint;
import org.apache.commons.math4.legacy.optim.linear.LinearObjectiveFunction;
import org.apache.commons.math4.legacy.optim.PointValuePair;
import org.apache.commons.math4.legacy.optim.linear.LinearConstraintSet;
import org.apache.commons.math4.legacy.optim.nonlinear.scalar.GoalType;
public class LinearProgrammingLib {
    LinearObjectiveFunction  f;
    List<LinearConstraint> constraints; 
    SimplexSolver solver;
    PointValuePair solution; 
    public double []x;
    public double value;
    public LinearProgrammingLib(double[]weights,double bias ){
        f=new LinearObjectiveFunction (weights,bias);
        constraints=new ArrayList();
        solver=new SimplexSolver();
        solution=null;
    }
    public void addLEQConstraint(double[] weights, double value){
        constraints.add(new LinearConstraint(weights,Relationship.LEQ,value));
    }
    public void addGEQConstraint(double[] weights, double value){
        constraints.add(new LinearConstraint(weights,Relationship.GEQ,value));
    }
    public void solveForMax(){
        try{
            LinearConstraintSet constraintsSet=new LinearConstraintSet (constraints); 
            OptimizationData[] optdata=new OptimizationData[]{
                f,
                constraintsSet,
                GoalType.MAXIMIZE,
                new MaxIter(100)
            };
            solution=solver.optimize(optdata);
            x=solution.getPoint();
            value=solution.getValue();
        }
        catch(Exception e){
            System.out.println("Error: "+e.getMessage());
        }
    }
     public void solveForMin(){
        try{
            LinearConstraintSet constraintsSet=new LinearConstraintSet (constraints); 
            OptimizationData[] optdata=new OptimizationData[]{
                f,
                constraintsSet,
                GoalType.MINIMIZE,
                new MaxIter(100)
            };
            solution=solver.optimize(optdata);
            x=solution.getPoint();
            value=solution.getValue();
        }
        catch(Exception e){
            System.out.println("Error: "+e.getMessage());
        }
    }

}
