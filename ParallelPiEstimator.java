/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Circle.monte_dela_maharantos;

/**
 *
 * @author ahmed
 */
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadLocalRandom;

public class ParallelPiEstimator implements PiEstimator {

    /** 
     * Visualization Point Callback 
     */
    public interface PointListener {
        void onPointGenerated(double x, double y, boolean inside);
    }

    private PointListener listener;

    // Setter for GUI to register callback
    public void setPointListener(PointListener listener) {
        this.listener = listener;
    }


    @Override
    public double estimatePi(SimulationConfig config) {

        long totalPoints = config.getTotalPoints();
        int numTasks = config.getNumTasks();
        int numThreads = config.getNumThreads();
//       Runtime.getRuntime().availableProcessors

        ExecutorService executor = Executors.newFixedThreadPool(numThreads);

     long pointsPerTask = totalPoints / numTasks;   

        List<Future<Long>> results = new ArrayList<>();

        for (int i = 0; i < numTasks; i++) {

            Callable<Long> task = () -> {
                long inside = 0;
                for (long j = 0; j < pointsPerTask; j++) {
                    double x = ThreadLocalRandom.current().nextDouble();
                    double y = ThreadLocalRandom.current().nextDouble();

                    boolean insidePoint = (x * x + y * y <= 1.0);
                    if (insidePoint) inside++;

                  
                    
                    if (listener != null) {
                        double px = x;
                        double py = y;
                        listener.onPointGenerated(px, py, insidePoint);
                    }
                }
                return inside;
            };

            results.add(executor.submit(task));
        }

        long totalInside = 0;

        for (Future<Long> f : results) {
            try {
                totalInside += f.get();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        executor.shutdown();

        return 4.0 * totalInside / totalPoints;
    }
}
