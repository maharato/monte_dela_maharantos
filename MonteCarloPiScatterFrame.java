package Circle;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class MonteCarloPiScatterFrame extends JFrame {

    private ScatterPanel scatterPanel;
    private JLabel piLabel;
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

    public void setConfig(long points, int tasks, int threads, double piSeq, double piPar) {

        pointsField.setText(String.valueOf(points));
        tasksField.setText(String.valueOf(tasks));
        threadsField.setText(String.valueOf(threads));

        pointsField.setEditable(false);
        tasksField.setEditable(false);
        threadsField.setEditable(false);

        seqLabel.setText("Sequential π = " + String.format("%.6f", piSeq));
        parLabel.setText("Parallel π   = pending...");

        this.finalPiPar = piPar;

        startSimulation();
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

        JPanel bottomPanel = new JPanel(new GridLayout(4, 1));
        seqLabel = new JLabel("Sequential π = ---");
        parLabel = new JLabel("Parallel π   = ---");
        piLabel = new JLabel("Live π = ---");
        errorLabel = new JLabel("Final Error = ---");

        bottomPanel.add(seqLabel);
        bottomPanel.add(parLabel);
        bottomPanel.add(piLabel);
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
        int tasks = Integer.parseInt(tasksField.getText());
        int threads = Integer.parseInt(threadsField.getText());

        estimator = new ParallelPiEstimator();
        SimulationConfig config = new SimulationConfig(points, tasks, threads);

        estimator.setPointListener((x, y, inside) -> {
            if (!running) return;

            totalCount++;
            if (inside) insideCount++;

            double livePI = 4.0 * insideCount / totalCount;

            SwingUtilities.invokeLater(() -> {
                scatterPanel.addPoint(x, y, inside);
                piLabel.setText("Live π = " + String.format("%.6f", livePI));
            });
        });

        new Thread(() -> {
            estimator.estimatePi(config);

            SwingUtilities.invokeLater(() -> {
                piLabel.setText("Final π = " + String.format("%.6f", finalPiPar));
                errorLabel.setText("Final Error = " +
                        String.format("%.6f", Math.abs(Math.PI - finalPiPar)));
                parLabel.setText("Parallel π = " + String.format("%.6f", finalPiPar));
            });

            running = false;

        }).start();
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
