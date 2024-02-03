package com.example.spring_boot_backend.service;



import com.example.spring_boot_backend.model.*;
import com.example.spring_boot_backend.repository.FormRepository;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PdfGenerationService {

    @Autowired
    private FormRepository formRepository;

    public byte[] generatePieChartPdf(Long id) {
        //noinspection OptionalGetWithoutIsPresent
        Form form = formRepository.findById(id).get();



        try (PDDocument document = new PDDocument()) {

            List<PDImageXObject> charts = new ArrayList<>();

            for(Section section:form.getSections()){
                for(Question question:section.getQuestions()){
                    if(question.getQuestion_type().equals(QuestionType.OPEN_QUESTION)){
                        BufferedImage bim = createPieChart(question.getQuestion_text(), question.getAnswers());
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        ChartUtils.writeBufferedImageAsPNG(baos, bim);
                        PDImageXObject pdImage = PDImageXObject.createFromByteArray(document, baos.toByteArray(), "chart");
                        charts.add(pdImage);
                        System.out.println("chart added");



                    } else if (question.getQuestion_type().equals(QuestionType.TEXT_QUESTION)) {
                        System.out.println("text question");

                    }
                }
            }
            for (PDImageXObject chart : charts) {

                PDPage page = new PDPage();
                document.addPage(page);


                try (PDPageContentStream contents = new PDPageContentStream(document, page)) {

                    contents.drawImage(chart, 100, 72*6);
                    contents.beginText();
                    contents.setFont(PDType1Font.HELVETICA, 12);




                }


            }

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            document.save(out);

            return out.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }





    private BufferedImage createPieChart(String title, List<Answer> answers) {
        Map<String,Long> answers_text_freq = answers.stream().map(Answer::getAnswer_text).collect(Collectors.groupingBy(e -> e, Collectors.counting()));



        DefaultPieDataset dataset = new DefaultPieDataset();


        for (Map.Entry<String, Long> entry : answers_text_freq.entrySet()) {
            dataset.setValue(entry.getKey(), entry.getValue());
        }


        JFreeChart chart = ChartFactory.createPieChart(
                title,   // chart title
                dataset,              // dataset
                true,                 // include legend
                true,
                false);

        return chart.createBufferedImage(400, 300);
    }
}
