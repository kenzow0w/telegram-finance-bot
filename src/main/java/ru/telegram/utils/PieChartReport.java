package ru.telegram.utils;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.springframework.beans.factory.annotation.Autowired;
import ru.telegram.service.ExpansesServiceImpl;
import ru.telegram.service.IncomeServiceImpl;

import java.awt.*;
import java.io.File;
import java.io.IOException;


public class PieChartReport{

    @Autowired
    ExpansesServiceImpl expansesService;

    @Autowired
    IncomeServiceImpl incomeService;

    public static void generatePieChart(File outputFile) throws IOException {

        // Create a dataset for the chart
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("Apple", 30);
        dataset.setValue("Banana", 20);
        dataset.setValue("Cherry", 50);


        // Create the chart
        JFreeChart chart = ChartFactory.createPieChart("Расходы", dataset, true, true, false);

        // Customize the chart
        chart.setBackgroundPaint(new Color(Color.WHITE.getRGB()));// Transparent background


        // Save the chart as a JPEG file
        ChartUtils.saveChartAsJPEG(outputFile, chart, 800, 600);
    }

}
