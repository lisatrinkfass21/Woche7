/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reciprocalarraysum;

/**
 *
 * @author Lisa
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

/**
 * Class wrapping methods for implementing reciprocal array sum in parallel.
 */
public final class ReciprocalArraySum {

    /**
     * Default constructor.
     */
    public ReciprocalArraySum() {
    }

    /**
     * Sequentially compute the sum of the reciprocal values for a given array.
     *
     * @param input Input array
     * @return The sum of the reciprocals of the array input
     */
    protected static double seqArraySum(final double[] input) {
        double sum = 0;
        for (double e : input) {
            sum += (1 / e);
        }
        return sum;

        // ToDo: Compute sum of reciprocals of array elements
    }

    /**
     * This class stub can be filled in to implement the body of each task
     * created to perform reciprocal array sum in parallel.
     */
    private static class ReciprocalArraySumTask extends RecursiveAction {

        /**
         * Starting index for traversal done by this task.
         */
        private final int startIndexInclusive;
        /**
         * Ending index for traversal done by this task.
         */
        private final int endIndexExclusive;
        /**
         * Input array to reciprocal sum.
         */
        private final double[] input;
        /**
         * Intermediate value produced by this task.
         */
        private double value;

        private static int SEQUENTIAL_THRESHOLD = 50000;

        /**
         * Constructor.
         *
         * @param setStartIndexInclusive Set the starting index to begin
         * parallel traversal at.
         * @param setEndIndexExclusive Set ending index for parallel traversal.
         * @param setInput Input values
         */
        ReciprocalArraySumTask(final int setStartIndexInclusive, final int setEndIndexExclusive, final double[] setInput) {
            this.startIndexInclusive = setStartIndexInclusive;
            this.endIndexExclusive = setEndIndexExclusive;
            this.input = setInput;

        }

        /**
         * Getter for the value produced by this task.
         *
         * @return Value produced by this task
         */
        public double getValue() {
            return value;
        }

        @Override
        protected void compute() {

            if (this.endIndexExclusive - this.startIndexInclusive <= SEQUENTIAL_THRESHOLD) {
                double[] arr = new double[this.endIndexExclusive - this.startIndexInclusive];
                for (int i = 0; i < arr.length; i++) {
                    arr[i] = input[i + this.startIndexInclusive];
                }
                this.value = ReciprocalArraySum.seqArraySum(input);
            } else {
                int halb = input.length / 2;
                ReciprocalArraySumTask task1 = new ReciprocalArraySumTask(0, halb, input);
                ReciprocalArraySumTask task2 = new ReciprocalArraySumTask(halb, input.length, input);
                invokeAll(task1, task2);
                task1.join();
                task2.join();
                this.value = task1.getValue() + task2.getValue();

            }
        }

        // TODO: Implement Thread forking on Threshold value. (If size of
        // array smaller than threshold: compute sequentially else, fork
        // 2 new threads
    }

    /**
     * TODO: Extend the work you did to implement parArraySum to use a set
     * number of tasks to compute the reciprocal array sum.
     *
     * @param input Input array
     * @param numTasks The number of tasks to create
     * @return The sum of the reciprocals of the array input
     */
    protected static double parManyTaskArraySum(final double[] input,
            final int numTasks) {
        double sum = 0;

        ForkJoinPool pool = new ForkJoinPool(numTasks);
        ReciprocalArraySumTask root = new ReciprocalArraySumTask(0, input.length, input);
        pool.invoke(root);
        sum = root.getValue();

        // ToDo: Start Calculation with help of ForkJoinPool
        return sum;
    }
}
