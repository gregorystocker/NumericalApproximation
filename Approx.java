/**
 * Calculates reinman summs
 * to compare the acceleration function, velocity function and position functions.
 * You can change the endTime to test a different value of time to estimate,
 * or the numRectangles variable to increase or decrease the precision of the estimate.
 * */
public class Approx {
    //This enum gives us a quick and dirty flag that allows us to tell the calcFx function what function to use from the
    //main without writing a new function or using interfaces.
    enum function {POSITION, VELOCITY, ACCELERATION}
    static function whatToCalculate = function.ACCELERATION;
    static int numRectangles =  1000000000;
    static double initialVelocity = 5;
    static double startTime = 0;
    static double endTime = 10;
    static double elapsedTime = endTime - startTime;
    static double acceleration = 9.8;

    //calculates the area under a function 
    static double reinmann(int numRectangles, double start, double end) {
        double deltaX = deltaX(start, end, numRectangles);
        double sum = 0;
        double x;
        for (int i = 0; i < numRectangles; i++) {
            x = findX(start, end, numRectangles, i); // calculates the x value using start + i*deltaX
            sum += rectArea(calcFx(x), deltaX);
        }
        return sum;
    }

    /**
     * x_i = a + i*deltaX is the formula to find the start of the current rectangle i by offsetting from the
     * beginning of the interval.
     *
     * @param a             the beginning of the interval
     * @param b             the end of the interval
     * @param numRectangles the number of rectangles we're using to approximate the integral
     * @param i             the index of the current rectangle.
     * @return
     */
    static double findX(double a, double b, double numRectangles, int i) {
        return a + i * deltaX(a, b, numRectangles);
    }

    /**
     * @param fx     height of the rectangle
     * @param deltaX width of the rectangle
     * @return the area of the given rectangle
     */
    static double rectArea(double fx, double deltaX) {
        return fx * deltaX;
    }

    /**
     * @param a the beginning of the interval we're summing over.
     * @param b the end of the interval
     * @param n the number of rectangles we're using to approximate the integral
     * @return the value of deltaX which represents the width of the rectangles we're using
     */
    static double deltaX(double a, double b, double n) {
        return (b - a) / n;
    }

    /**
     * Gives the height of the rectangle by plugging the value into the original function.
     *
     * @param x
     * @return
     */
    static double calcFx(double x) {
        if (whatToCalculate == function.POSITION) {
            return positionFunction(x);
        } else if (whatToCalculate == function.VELOCITY) {
            return velocityFunction(x);
        } else if (whatToCalculate == function.ACCELERATION){
            return accelerationFunction(x);
    }
        else {
            return 0;
        }
}
    static  double positionFunction(double input){

        return initialVelocity*input + (.5)*acceleration*input*input;
    }
    static double velocityFunction(double input){
        return initialVelocity + acceleration*input;
    }
    static double accelerationFunction(double input){
        return acceleration;
    }

    public static void main(String[] args){
        //increase the number of rectangles to increase the accuracy of the estimation!
        System.out.println("From acceleration to velocity is v + at");
        System.out.println("From velocity to position is vt + (1/2)at^2");
        System.out.println("Starting with acceleration at " + acceleration +  " m/s^2");
        System.out.println("Starting with initial velocity at " + initialVelocity +  " m/s");
        System.out.println("Using " + numRectangles + " rectangles to approximate values");
        System.out.println("Using numerical approximation by way of reinnman sums we can show that summing the area under the curve " +
                "of the acceleration function gives values close to that of the known velocity function");
        whatToCalculate = function.ACCELERATION;
        double result =  reinmann(numRectangles,startTime,endTime);
        System.out.println("The estimated area under the curve of the acceleration function using numerical approximation is:\n" + result  + "m/s");
        System.out.println("The actual value of the velocity at the endTime is:\n" +  velocityFunction(endTime) + "m/s");
        whatToCalculate = function.VELOCITY;
        result =  reinmann(numRectangles,startTime,endTime);
        System.out.println("The estimated area under the curve of the velocity function using numerical approximation is:\n" + result  + "m");
        System.out.println("The actual value of the position at the endTime is:\n" +  positionFunction(endTime) + "m");
    }
}

