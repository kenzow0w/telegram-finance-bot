package ru.telegram.utils;

import lombok.Getter;
import lombok.Setter;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.springframework.beans.factory.annotation.Autowired;
import ru.telegram.service.ExpansesServiceImpl;
import ru.telegram.service.IncomeServiceImpl;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

@Getter
@Setter
public class ModelPieChart {

    private String title;
    private List<Data> model = new ArrayList<>();

    public ModelPieChart(String title) {
        this.title = title;
    }

    public void addData(String title, double value) {
        model.add(new Data(title, value));
    }

    @Getter
    @Setter
    private class Data {
        private String title;
        private double value;

        public Data(String title, double value) {
        }
    }

    @Autowired
    private ExpansesServiceImpl expansesService;

    @Autowired
    private IncomeServiceImpl incomeService;

    public String toChartContent() {
        String jsPath = new File("chart.js").getAbsolutePath();
        String text = "<html>\n" +
                "  <head>\n" +
                "    <script type=\"text/javascript\" src=\"file://" + jsPath + "\"></script>\n" +
                "    <script type=\"text/javascript\">\n" +
                "      google.charts.load(\"current\", {packages:[\"corechart\"]});\n" +
                "      google.charts.setOnLoadCallback(drawChart);\n" +
                // Callback that creates and populates a data table,
                // instantiates the pie chart, passes in the data and
                // draws it.
                "      function drawChart() {\n" +
                // Create the data table.
                "        var data = google.visualization.arrayToDataTable([\n" +
                "          ['Task', 'Hours per Day'],\n" +
                getModel() +
                "        ]);\n" +
                "\n" +
                // Set chart options
                "        var options = {\n" +
                "          title: '" + title + "',\n" +
                "          is3D: true,\n" +
                "        };\n" +
                "\n" +
                // Instantiate and draw our chart, passing in some options.
                "        var chart = new google.visualization.PieChart(document.getElementById('piechart_3d'));\n" +
                "        chart.draw(data, options);\n" +
                "      }\n" +
                "    </script>\n" +
                "  </head>\n" +
                "  <body>\n" +
                "    <div id=\"piechart_3d\" style=\"width: 900px; height: 500px;\"></div>\n" +
                "  </body>\n" +
                "</html>";

        return text;
    }

    private String getModel() {
        StringJoiner sj = new StringJoiner(",");
        for (Data d : model) {
            sj.add("['" + d.getTitle() + "'," + d.getValue() + "]");
        }
        return sj.toString();
    }

    public static void generatePieChart(File outputFile) throws IOException {

        // Create a dataset for the chart
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("Apple", 30);
        dataset.setValue("Banana", 20);
        dataset.setValue("Cherry", 50);

        PieDataset pie = dataset;

        // Create the chart
        JFreeChart chart = ChartFactory.createPieChart("Расходы", pie, true, true, false);

        // Customize the chart
        chart.setBackgroundPaint(new Color(Color.WHITE.getRGB()));// Transparent background

        // Save the chart as a JPEG file
        ChartUtils.saveChartAsJPEG(outputFile, chart, 800, 600);
    }
}
