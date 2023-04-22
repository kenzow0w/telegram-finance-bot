package ru.telegram.utils;

import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

import static ru.telegram.utils.PieChartReport.generatePieChart;

@Getter
@Setter
@Component
public class Utils {

    @Autowired
    Operation operation;

    @Test
    public void test() {
        try {
            generatePieChart(new File("target.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
