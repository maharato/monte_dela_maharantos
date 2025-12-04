package Circle.monte_dela_maharantos;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MonteCarloPiScatterFrame extends JFrame {

    private enum Mode { SEQ, PAR, BOTH }
    private Mode mode;

    private ScatterPanel scatterPanel;
    private JLabel piLabel;
    private JLabel liveErrorLabel; // NEW
    private JLabel errorLabel;
    private JLabel seqLabel;
    private JLabel parLabel;

    private JTextField pointsField;
    private JTextField tasksField;
    private JTextField threadsField;

    private volatile boolean running = false;
    private ParallelPiEstimator estimator;

    private double finalPiPar = 0;
    private long insideCount = 0;
    private long totalCount = 0;

    public MonteCarloPiScatterFrame() {
        initUI();
    }

    public void setSequentialOnly(long points, int tasks, int threads, double piSeq) {
        mode = Mode.SEQ;
        setInputs(points, tasks, threads);

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
        lockInputs();
    }

    private void lockInputs() {
        pointsField.setEditable(false);
        tasksField.setEditable(false);
        threadsField.setEditable(false);
    }

    private void initUI() {
        setTitle("Monte Carlo π Visualization");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pointsField = new JTextField("", 8);
        tasksField = new JTextField("", 5);
        threadsField = new JTextField("", 5);

        topPanel.add(new JLabel("Points:"));
        topPanel.add(pointsField);
        topPanel.add(new JLabel("Tasks:"));
        topPanel.add(tasksField);
        topPanel.add(new JLabel("Threads:"));
        topPanel.add(threadsField);

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

        setSize(920, 750);
        setLocationRelativeTo(null);
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
            showFinal();
        }).start();
    }

    private void runParallel(long points, int tasks, int threads) {
        estimator = new ParallelPiEstimator();
        SimulationConfig config = new SimulationConfig(points, tasks, threads);

        estimator.setPointListener((x, y, inside) -> {
            if (!running) return;
            updatePointUI(x, y, inside);
        });

        new Thread(() -> {
            estimator.estimatePi(config);
            showFinal();
        }).start();
    }

    private void updatePointUI(double x, double y, boolean inside) {
        totalCount++;
        if (inside) insideCount++;

        double livePi = 4.0 * insideCount / totalCount;
        double liveErr = Math.abs(Math.PI - livePi);

        SwingUtilities.invokeLater(() -> {
            scatterPanel.addPoint(x, y, inside);
            piLabel.setText("Live π = " + String.format("%.6f", livePi));
            liveErrorLabel.setText("Live Error = " + String.format("%.6f", liveErr)); // NEW
        });
    }

    private void showFinal() {
        running = false;
        SwingUtilities.invokeLater(() -> {
            piLabel.setText("Final π = " + String.format("%.6f", finalPiPar));
            errorLabel.setText("Final Error = " +
                    String.format("%.6f", Math.abs(Math.PI - finalPiPar)));
            parLabel.setText("Parallel π = " + String.format("%.6f", finalPiPar));
        });
    }

    private static class ScatterPanel extends JPanel {

        private static class PointData {
            double x, y;
            boolean inside;
            PointData(double x, double y, boolean inside) {
                this.x = x;
                this.y = y;
                this.inside = inside;
            }
        }

        private final List<PointData> points = new ArrayList<>();

        public ScatterPanel() {
            setBackground(Color.WHITE);
        }

        public void addPoint(double x, double y, boolean inside) {
            points.add(new PointData(x, y, inside));
            if (points.size() > 60000) points.remove(0);
            repaint();
        }

        public void clearPoints() {
            points.clear();
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            int size = Math.min(getWidth(), getHeight()) - 60;
            int ox = 30, oy = 30;

            g.setColor(Color.BLACK);
            g.drawRect(ox, oy, size, size);

            g.setColor(Color.LIGHT_GRAY);
            g.drawArc(ox, oy, size * 2, size * 2, 90, -90);

            for (PointData p : points) {
                int px = ox + (int)(p.x * size);
                int py = oy + (int)((1 - p.y) * size);
                g.setColor(p.inside ? Color.GREEN : Color.RED);
                g.fillOval(px - 2, py - 2, 4, 4);
            }
        }
    }
}
