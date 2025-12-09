package Circle.monte_dela_maharantos;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MonteCarloPiScatterFrame extends JPanel {

    private enum Mode {
        SEQ, PAR, BOTH
    }
    private Mode mode;

    private  ScatterPanel scatterPanel;
    private  JLabel piLabel;
    private  JLabel liveErrorLabel; // NEW
    private  JLabel errorLabel;
    private  JLabel seqLabel;
    private  JLabel parLabel;

    private JTextField pointsField;
    private JTextField tasksField;
    private JTextField threadsField;

    private volatile boolean running = false;
    private ParallelPiEstimator estimator;

    private double finalPiPar = 0;
    private long insideCount = 0;
    private long totalCount = 0;
     JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

    public MonteCarloPiScatterFrame() {
        initUI();
    }

    public void setSequentialOnly(long points, int tasks, int threads, double piSeq) {
        mode = Mode.SEQ;
        setInputs(points);

        seqLabel.setText("Sequential π = " + String.format("%.6f", piSeq));
        parLabel.setText("Parallel π = ---");

        finalPiPar = piSeq;
        startSimulation();
    }

    public void setParallelOnly(long points, int tasks, int threads, double piPar) {
        mode = Mode.PAR;
        setInputs(points, tasks, threads);

        seqLabel.setText("Sequential π = ---");
        parLabel.setText("Parallel π = pending...");

        finalPiPar = piPar;
        startSimulation();
    }

    public void setConfig(long points, int tasks, int threads, double piSeq, double piPar) {
        mode = Mode.BOTH;
        setInputs(points, tasks, threads);

        seqLabel.setText("Sequential π = " + String.format("%.6f", piSeq));
        parLabel.setText("Parallel π = pending...");

        finalPiPar = piPar;
        startSimulation();
    }

    private void setInputs(long points, int tasks, int threads) {
        pointsField.setText(String.valueOf(points));
        tasksField.setText(String.valueOf(tasks));
        threadsField.setText(String.valueOf(threads));
        
        topPanel.add(new JLabel("Tasks:"));
        topPanel.add(tasksField);
        topPanel.add(new JLabel("Threads:"));
        topPanel.add(threadsField);
         
        lockInputs();
    }
     private void setInputs(long points) {
        pointsField.setText(String.valueOf(points));
        tasksField.setVisible(false);
        threadsField.setVisible(false);
       
        lockInputs();
    }

    private void lockInputs() {
        pointsField.setEditable(false);
        tasksField.setEditable(false);
        threadsField.setEditable(false);
    }

    private void initUI() {

        setLayout(new BorderLayout());

       
        pointsField = new JTextField("", 8);
     
        tasksField = new JTextField("", 5);
        threadsField = new JTextField("", 5);

        topPanel.add(new JLabel("Points:"));
        topPanel.add(pointsField);
       

        add(topPanel, BorderLayout.NORTH);

        JPanel bottomPanel = new JPanel(new GridLayout(5, 1)); // 5 not 4
        seqLabel = new JLabel("Sequential π = ---");
        parLabel = new JLabel("Parallel π   = ---");
        piLabel = new JLabel("Live π = ---");
        liveErrorLabel = new JLabel("Live Error = ---"); // NEW
        errorLabel = new JLabel("Final Error = ---");

        bottomPanel.add(seqLabel);
        bottomPanel.add(parLabel);
        bottomPanel.add(piLabel);
        bottomPanel.add(liveErrorLabel); // NEW
        bottomPanel.add(errorLabel);

        add(bottomPanel, BorderLayout.SOUTH);

        scatterPanel = new ScatterPanel();
        add(scatterPanel, BorderLayout.CENTER);

        setSize(400, 400);

    }

    private void startSimulation() {
        running = true;
        scatterPanel.clearPoints();
        insideCount = 0;
        totalCount = 0;

        long points = Long.parseLong(pointsField.getText());
        if (mode == Mode.SEQ) {
            runSequential(points);
        } else {
            int tasks = Integer.parseInt(tasksField.getText());
            int threads = Integer.parseInt(threadsField.getText());
            runParallel(points, tasks, threads);
        }
    }

    private void runSequential(long points) {
        new Thread(() -> {
            for (long i = 0; i < points && running; i++) {
                double x = Math.random();
                double y = Math.random();
                boolean inside = (x * x + y * y <= 1.0);

                updatePointUI(x, y, inside);
            }
            showFinal("seq");
        }).start();
    }

    private void runParallel(long points, int tasks, int threads) {
        estimator = new ParallelPiEstimator();
        SimulationConfig config = new SimulationConfig(points, tasks, threads);

        estimator.setPointListener((x, y, inside) -> {
            if (!running) {
                return;
            }
            updatePointUI(x, y, inside);
        });

        new Thread(() -> {
            estimator.estimatePi(config);
            showFinal("par");
        }).start();
    }

    private void updatePointUI(double x, double y, boolean inside) {
        totalCount++;
        if (inside) {
            insideCount++;
        }

        double livePi = 4.0 * insideCount / totalCount;
        double liveErr = Math.abs(Math.PI - livePi);

        SwingUtilities.invokeLater(() -> {
            scatterPanel.addPoint(x, y, inside);
            piLabel.setText("Live π = " + String.format("%.6f", livePi));
            liveErrorLabel.setText("Live Error = " + String.format("%.6f", liveErr)); // NEW
        });
    }

    private void showFinal(String type) {
        running = false;
        SwingUtilities.invokeLater(() -> {
            piLabel.setText("Final π = " + String.format("%.6f", finalPiPar));
            errorLabel.setText("Final Error = "
                    + String.format("%.6f", Math.abs(Math.PI - finalPiPar)));
            if (type.equals("par")) {
                parLabel.setText("Parallel π = " + String.format("%.6f", finalPiPar));
            }
        });
    }

    private static class ScatterPanel extends JPanel {

        private static class PointData {

            final float x, y;
            final boolean inside;

            PointData(double x, double y, boolean inside) {
                this.x = (float) x;
                this.y = (float) y;
                this.inside = inside;
            }
        }

        private final List<PointData> points = new ArrayList<>(200000);
        private final Object lock = new Object();

        private static final int MAX_POINTS = 200000; // Limit to avoid memory blow
        private static final int BATCH_SIZE = 1500;   // Faster frame loading
        private int pointsToDraw = 0;

        private final Timer animationTimer;

        public ScatterPanel() {
            setBackground(Color.WHITE);
            setDoubleBuffered(true);

      
            animationTimer = new Timer(1000 / 60, e -> {
                synchronized (lock) {
                    if (pointsToDraw < points.size()) {
                        pointsToDraw = Math.min(pointsToDraw + BATCH_SIZE, points.size());
                        repaint();
                    }
                }
            });
            animationTimer.start();
        }

        public void addPoint(double x, double y, boolean inside) {
            synchronized (lock) {
                if (points.size() >= MAX_POINTS) {
                    points.remove(0); // Remove oldest → continuous animation
                }
                points.add(new PointData(x, y, inside));
            }
        }

        public void clearPoints() {
            synchronized (lock) {
                points.clear();
                pointsToDraw = 0;
                repaint();
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int size = Math.min(getWidth(), getHeight()) - 60;
            int ox = 30, oy = 30;

            // Draw square & quarter circle
            g2.setColor(Color.BLACK);
            g2.drawRect(ox, oy, size, size);
            g2.setColor(Color.GRAY);
            g2.drawArc(ox, oy, size * 2, size * 2, 90, -90);

            synchronized (lock) {
                for (int i = 0; i < pointsToDraw; i++) {
                    PointData p = points.get(i);
                    int px = ox + (int) (p.x * size);
                    int py = oy + (int) ((1 - p.y) * size);
                    g2.setColor(p.inside ? new Color(0, 150, 0) : Color.RED);
                    g2.fillRect(px, py, 2, 2); // Faster than oval
                }
            }
        }
    }

}
