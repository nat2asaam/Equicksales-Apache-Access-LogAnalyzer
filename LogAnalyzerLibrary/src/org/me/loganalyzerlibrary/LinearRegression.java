/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.me.loganalyzerlibrary;

/**
 *
 * @author nasaam
 */
import java.util.Arrays;
import java.util.Optional;

public class LinearRegression {

    public double beta;

    public double[] weights;

    private double learningRate;

    private int epochs;

    //private Function<T, R>
    public LinearRegression(int featuresCount, int epochs,double learningRate) {
        weights = new double[featuresCount];
        this.epochs = epochs;
        this.learningRate=learningRate;
    }

    public Optional<Double> predict(double[] inputs) {
        if (inputs == null || inputs.length <= 0) {
            return Optional.empty();
        }

        double result = 0d;
        for (int i = 0; i < inputs.length; i++) {
            result = inputs[i] * weights[i] + result;
        }

        result = result + beta;

        return Optional.of(result);
    }

    public void trainSGD(double[][] trainData, double[] result) {

        if (trainData == null || trainData.length <= 0) {
            throw new RuntimeException("Input data can not be null");
        }
        // Stochastic Gradient descent
        for (int e = 0; e < epochs; e++) {
            double mse = 0d;
            for (int i = 0; i < trainData.length; i++) {
                double[] tempInput = trainData[i];

                Optional<Double> predictedValueOptional = this.predict(tempInput);

                double predictedValue = predictedValueOptional.get();

                double error = predictedValue - result[i];
                mse = error * error + mse;

                for (int j = 0; j < weights.length; j++) {
                    weights[j] = weights[j] - learningRate * error * tempInput[j];
                }
                beta = beta - learningRate * error;
            }

            mse = (Math.sqrt(mse)) / trainData.length;
            System.out.println(" MSE " + mse + " Weights " + Arrays.toString(weights) + " Beta " + beta);
        }

    }

}
