/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.me.loganalyzerlibrary;
/**
 *
 * @author nasaam
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

public class DecisionTree {

    private static final String RIGHT_CHILD = "RIGHT_CHILD";

    private static final String LEFT_CHILD = "LEFT_CHILD";

    private static class TreeNode {

        public Function<Double[], Boolean> question;

        private boolean isLeaf;

        // public Double impurity;
        private TreeNode left;

        private TreeNode right;

        private Double prediction;
    }

    private TreeNode root;

    private BiFunction<Integer, List<Double[]>, Double> gini = (index, data) -> {

        if (data.size() <= 0) {
            return 0.0d;
        }
        Double impurity = 1.0d;

        HashMap<Double, Integer> frequencyMap = findFreqMap(data, index);

        for (Double key : frequencyMap.keySet()) {
            double probablity = (double) frequencyMap.get(key) / (double) data.size();

            impurity = impurity - Math.pow(probablity, 2);

        }

        return impurity;

    };

    private HashMap<Double, Integer> findFreqMap(List<Double[]> data, Integer index) {
        HashMap<Double, Integer> freqMap = new HashMap<>();

        for (int i = 0; i < data.size(); i++) {
            Double point = data.get(i)[index];
            if (freqMap.containsKey(point)) {
                int value = freqMap.get(point);
                freqMap.put(point, value + 1);
            } else {
                freqMap.put(point, 1);
            }
        }

        return freqMap;
    }

    private HashMap<String, List<Double[]>> partitionByQuestion(Function<Double[], Boolean> partitionFunction,
            List<Double[]> dataSet) {
        HashMap<String, List<Double[]>> hashMap = new HashMap<String, List<Double[]>>();

        hashMap.put(RIGHT_CHILD, new ArrayList<>());
        hashMap.put(LEFT_CHILD, new ArrayList<>());

        for (int i = 0; i < dataSet.size(); i++) {
            boolean value = partitionFunction.apply(dataSet.get(i));

            if (value) {
                List<Double[]> list = hashMap.get(RIGHT_CHILD);
                list.add(dataSet.get(i));
            } else {
                List<Double[]> list = hashMap.get(LEFT_CHILD);
                list.add(dataSet.get(i));
            }
        }

        return hashMap;
    }

    private Double informationGain(Double currentImpurity,
            BiFunction<Integer, Double, Function<Double[], Boolean>> question, List<Double[]> data, Integer index,
            Double value) {
        Function<Double[], Boolean> partitonFunction = question.apply(index, value);
        HashMap<String, List<Double[]>> partitionData = partitionByQuestion(partitonFunction, data);

        // Edge case if size is zero
        double leftProability = partitionData.get(LEFT_CHILD).size() > 0
                ? ((double) partitionData.get(LEFT_CHILD).size() / (double) data.size())
                : 0.0d;
        double leftGini = gini.apply(index, partitionData.get(LEFT_CHILD));
        double rightProability = 1 - leftProability;
        double rightGini = gini.apply(index, partitionData.get(RIGHT_CHILD));
        currentImpurity = currentImpurity - (leftProability * leftGini) - (rightGini * rightProability);
        return currentImpurity;
    }

    // TODO handling of categorical column needs to added
    private BiFunction<Integer, Double, Function<Double[], Boolean>> questionGenerator = (index, value) -> {
        Function<Double[], Boolean> question = (data) -> {

            if (data[index].compareTo(value) <= 0) {
                return true;
            } else {
                return false;
            }
        };
        return question;

    };

    public TreeNode buildTree(List<Double[]> data, Integer labelColumnIndex) {
        if (data.size() <= 0) {
            return null;
        }
        // Double impurity = null;
        // if (root == null) {
        Double impurity = gini.apply(labelColumnIndex, data);
        // }

        if (impurity.compareTo(0.0d) == 0) {

            TreeNode node = new TreeNode();
            node.isLeaf = true;
            node.prediction = data.get(0)[labelColumnIndex];
            return node;
        }

        // TODO Leaf node concept and how to use question while classifying
        Double bestInformationGain = 0.0d;
        int finalIndex = -1;
        Double finalValue = Double.MIN_VALUE;

        // in data set last column is label hence removing it
        int numberOfFeatures = labelColumnIndex;

        for (int i = 0; i < numberOfFeatures; i++) {
            HashSet<Double> uniqueDataPoints = getUniqueDataPoints(data, i);

            for (Double value : uniqueDataPoints) {
                Double tempInformationGain = informationGain(impurity, questionGenerator, data, labelColumnIndex,
                        value);

                if (bestInformationGain.compareTo(tempInformationGain) == -1) {
                    bestInformationGain = tempInformationGain;
                    finalIndex = i;
                    finalValue = value;
                }
            }

        }

        Function<Double[], Boolean> question = questionGenerator.apply(finalIndex, finalValue);
        TreeNode node = new TreeNode();
        node.question = question;

        if (root == null) {
            root = node;
        }

        HashMap<String, List<Double[]>> partitionedData = partitionByQuestion(question, data);

        TreeNode left = buildTree(partitionedData.get(LEFT_CHILD), labelColumnIndex);
        TreeNode right = buildTree(partitionedData.get(RIGHT_CHILD), labelColumnIndex);

        node.right = right;
        node.left = left;

        return node;

    }

    private HashSet<Double> getUniqueDataPoints(List<Double[]> data, int index) {
        HashSet<Double> uniqueDataPoints = new HashSet<>();

        data.forEach((n) -> {

            uniqueDataPoints.add(n[index]);
        });

        return uniqueDataPoints;
    }

    public double predict(Double[] data) {
        if (data != null && data.length > 0) {
            TreeNode node = root;

            while (!node.isLeaf) {
                boolean result = node.question.apply(data);

                node = result ? node.right : node.left;
            }

            return node.prediction;
        }

        return -1d;
    }

    public static void main(String[] args) {
        List<Double[]> dataSet = new ArrayList<>();

        Double[] data1 = {.23d, .34d, .67d, 0.1d};
        Double[] data2 = {.23d, .84d, .47d, 0.1d};
        Double[] data3 = {.21d, .64d, .97d, 0.1d};
        Double[] data4 = {.13d, .84d, .47d, 0.2d};
        Double[] data5 = {.13d, .88d, .99d, 0.2d};

        dataSet.add(data4);
        dataSet.add(data3);
        dataSet.add(data2);
        dataSet.add(data1);
        dataSet.add(data5);

        DecisionTree tree = new DecisionTree();
        tree.buildTree(dataSet, data1.length - 1);
        System.out.println("Tree building completed");

        // Prediction test
        double result = tree.predict(data4);

        //assertTrue(result == data1[3]);
        System.out.println("Result " + result);

    }

}
